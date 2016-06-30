import * as React from 'react';
import {
  StyleSheet,
  Text,
  View,
  AppRegistry,
} from 'react-native';

import { WebViewBridge } from 'react-native-webview-bridge';

const Uris = {
  kGoogle: {uri: 'https://www.google.com'},
  kSayHeight: require('./say-height.html'),
};

class Example extends React.Component {
  constructor() {
    super();

    this.state = {};
  }

  componentWillMount() {
    var thiz = this;
    // Remove that
    setTimeout(function() {
      thiz.setState({
        noText: true,
      });
    }, 3000);

    // And add back.
    setTimeout(function() {
      thiz.setState({
        noText: false,
      });
    }, 6000);
  }

  render() {
    var es = [];
    es.push(
      <WebViewBridge
        style={styles.flex}
        javaScriptEnabled={true}
        key='wv'
        source={Uris.kSayHeight} />
    );

    if (!this.state.noText) {
      es.push(
        <View style={styles.center} key='txt'>
          <Text>HAI</Text>
        </View>
      );
    }
    return (
      <View style={styles.flex}>
        {es}
      </View>
    );
  }
}

AppRegistry.registerComponent('Example', () => Example);

const styles = StyleSheet.create({
  flex: {
    flex: 1,
  },
  center: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'yellow',
  },
});
