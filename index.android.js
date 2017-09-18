/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  Button,
  NativeModules
} from 'react-native';
//import {CustomTextInput} from 'react-native-custom-keyboard';

export default class keyboard extends Component {
  render() {
    return (
      <View>
        <Button
          title="不管"
          onPress={()=>{
          NativeModules.SomeModule.callbackMethod('params',(ok)=>{alert('ok')},(error)=>{alert('error')});
        }} />
      </View>
    );
  }
}

AppRegistry.registerComponent('keyboard', () => keyboard);
