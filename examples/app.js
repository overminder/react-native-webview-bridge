/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';

import * as React from 'react';
import {
  StyleSheet,
  Text,
  View,
  AppRegistry,
} from 'react-native';

import { WebViewBridge } from 'react-native-webview-bridge';

const injectScript = `
  (function () {
    if (WebViewBridge) {

      WebViewBridge.onMessage = function (message) {
        if (message === "hello from react-native") {
          WebViewBridge.send("got the message inside webview");
        }
      };

      WebViewBridge.send("hello from webview");
    }
  }());
`;

class Example extends React.Component {
  constructor() {
    super();
    this.onBridgeMessage = this.onBridgeMessage.bind(this);
  }

  onBridgeMessage(message) {
    const { webviewbridge } = this.refs;

    switch (message) {
      case "hello from webview":
        webviewbridge.sendToBridge("hello from react-native");
        break;
      case "got the message inside webview":
        console.log("we have got a message from webview! yeah");
        break;
    }
  }

  render() {
    return (
      <View style={styles.container}>

        <WebViewBridge
          style={{flex: 2}}
          ref="webviewbridge"
          onBridgeMessage={this.onBridgeMessage}
          javaScriptEnabled={true}
          injectedJavaScript={injectScript}
          source={require('./body-height.html')} />

        <WebViewBridge
          style={{flex: 1}}
          ref="webviewbridge2"
          onBridgeMessage={this.onBridgeMessage}
          javaScriptEnabled={true}
          injectedJavaScript={injectScript}
          source={require('./test.html')} />
      </View>
    );
  }
}

AppRegistry.registerComponent('Example', () => Example);

const styles = StyleSheet.create({
  container: {
    flex: 1
  }
});

