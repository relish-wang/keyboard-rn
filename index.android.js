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
  Button
} from 'react-native';
//import {CustomTextInput} from 'react-native-custom-keyboard';

export default class keyboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      textIdCard: '',
      textCarNumber: '',
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
          onFocus={()=>{
            NativeModules.SomeModule.callbackMethod('IDCard',(ok)=>{
              switch(ok){
                case "-1":// backspace
                  if(this.state.text.length>0){
                    this.setState({textIdCard: this.state.textIdCard.substring(0, this.state.textIdCard.length-1)});
                  }
                break;
                default://
                  this.setState({textIdCard: this.state.textIdCard+ok});
                break;
              }
            },(error)=>{
              alert('error')});
          }} >
          {this.state.textIdCard}
        </TextInput>
        <TextInput
          ref="carNumberInput"
          style={styles.textInput}
          placeholder="车牌号"
          onFocus={()=>{
            NativeModules.SomeModule.callbackMethod('CarNumber',(ok)=>{
              switch(ok){
                case "-1":// backspace
                  if(this.state.textCarNumber.length>0){
                    this.setState({textCarNumber: this.state.textCarNumber.substring(0, this.state.textCarNumber.length-1)});
                  }
                break;
                default://
                  this.setState({textCarNumber: this.state.textCarNumber+ok});
                break;
              }
            },(error)=>{
              alert('error')});
          }} >
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
      //注册扫描监听
      DeviceEventEmitter.addListener('onKeyboardEvent', this.onKeyboardResult);
  }

  onKeyboardResult = (e)=> {
    if(e.tag==='IDCard'){
      if(e.result==='-1'){
        this.setState({
            textIdCard: this.state.textIdCard.substring(0, this.state.textIdCard.length-1)
        });
      }else if(e.result==='0'){
        this.refs.idCardInput.blur();
      }else{
        this.setState({
            textIdCard: this.state.textIdCard+e.result
        });
      }
    }else if(e.tag==='CarNumber'){
        if(e.result==='-1'){
          this.setState({
              textCarNumber: this.state.textCarNumber.substring(0, this.state.textCarNumber.length-1)
          });
        }else if(e.result==='0'){
          this.refs.idCardInput.blur();
        }else{
          this.setState({
              textCarNumber: this.state.textCarNumber+e.result
          });
        }
    }
      // DeviceEventEmitter.removeListener('onKeyboardEvent',this.onKeyboardResult);//移除扫描监听
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
