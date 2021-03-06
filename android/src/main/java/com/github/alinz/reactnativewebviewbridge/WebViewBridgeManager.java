package com.github.alinz.reactnativewebviewbridge;

import javax.annotation.Nullable;
import java.util.Map;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsoluteLayout;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.views.webview.ReactWebViewManager;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReactContext;

public class WebViewBridgeManager extends ReactWebViewManager {
  private static final String REACT_CLASS = "RCTWebViewBridge";

  public static final int COMMAND_INJECT_BRIDGE_SCRIPT = 100;
  public static final int COMMAND_SEND_TO_BRIDGE = 101;

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @Override
  public @Nullable Map<String, Integer> getCommandsMap() {
    Map<String, Integer> commandsMap = super.getCommandsMap();
    commandsMap.put("injectBridgeScript", COMMAND_INJECT_BRIDGE_SCRIPT);
    commandsMap.put("sendToBridge", COMMAND_SEND_TO_BRIDGE);

    return commandsMap;
  }

  @Override
  public void receiveCommand(WebView root, int commandId, @Nullable ReadableArray args) {
    super.receiveCommand(root, commandId, args);

    switch (commandId) {
      case COMMAND_INJECT_BRIDGE_SCRIPT:
        injectBridgeScript(root);
        break;
      case COMMAND_SEND_TO_BRIDGE:
        sendToBridge(root, args.getString(0));
        break;
    }
  }

  static void evaluateJavaScriptShimmed(WebView root, String jsExpr) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      root.evaluateJavascript(jsExpr, null);
    } else {
      root.loadUrl("javascript:" + jsExpr);
    }
  }

  private void sendToBridge(WebView root, String message) {
    //root.loadUrl("javascript:(function() {\n" + script + ";\n})();");
    String script = "WebViewBridge.onMessage('" + message + "');";
    evaluateJavaScriptShimmed(root, script);
  }


  @Override
  protected WebView createViewInstance(ThemedReactContext reactContext) {
    WebView root = super.createViewInstance(reactContext);
    root.addJavascriptInterface(new JavascriptBridge((ReactContext) root.getContext()), "WebViewBridgeAndroid");
    return root;
  }

  @Override
  public void onDropViewInstance(WebView root) {
    root.removeJavascriptInterface("WebViewBridgeAndroid");
    super.onDropViewInstance(root);
  }

  private void injectBridgeScript(WebView root) {
    //this code needs to be executed everytime a url changes.
    evaluateJavaScriptShimmed(root, ""
    + "(function() {"
        + "if (window.WebViewBridge) return;"
        + "var customEvent = document.createEvent('Event');"
        + "var WebViewBridge = {"
            + "send: function(message) { WebViewBridgeAndroid.send(message); },"
            + "onMessage: function() {}"
        + "};"
        + "window.WebViewBridge = WebViewBridge;"
        + "customEvent.initEvent('WebViewBridge', true, true);"
        + "document.dispatchEvent(customEvent);"
    + "}());");
  }

  @ReactProp(name = "loadWithOverviewModeAndroid")
  public void setLoadWithOverviewMode(WebView view, boolean enabled) {
    view.getSettings().setLoadWithOverviewMode(enabled);
  }
}
