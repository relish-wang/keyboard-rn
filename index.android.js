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
  TextInput,
  NativeModules,
  DeviceEventEmitter,
  Dimensions,
  Button,
  findNodeHandle
} from 'react-native';
//import {CustomTextInput} from 'react-native-custom-keyboard';

export default class keyboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      customKeyboardType: 'IDCard',
      textIdCard: '',
      textCarNumber: ''
    };
  }
  render() {
    return (
      <View style={styles.container}>
        <TextInput
          style={styles.textInput}
          placeholder='普通'/>
        <TextInput
          ref="idCardInput"
          style={styles.textInput}
          placeholder="身份证"
          onChangeText={(text) =>{
            this.setState({textIdCard: text})
          }} >
          {this.state.textIdCard}
        </TextInput>
        <TextInput
          ref="carNumberInput"
          style={styles.textInput}
          placeholder="车牌号" >
          {this.state.textCarNumber}
        </TextInput>
        <Button
          title='失去焦点'
          style={styles.button}
          onPress={()=>{
            this.refs.idCardInput.blur();
        }}></Button>
      </View>
    );
  }

  componentDidMount() {
      NativeModules.SomeModule.install(findNodeHandle(this.refs.idCardInput), this.state.customKeyboardType);
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: Dimensions.get('window').width,
    height: Dimensions.get('window').height
  },
  textInput: {
    width: Dimensions.get('window').width,
    height: 80
  },
  button: {
    flex: 1,
    width: Dimensions.get('window').width,
    height: 120
  }
})
AppRegistry.registerComponent('keyboard', () => keyboard);
