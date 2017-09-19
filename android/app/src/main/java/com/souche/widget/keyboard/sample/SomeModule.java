package com.souche.widget.keyboard.sample;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.souche.widget.keyboard.EasyCallback;
import com.souche.widget.keyboard.KeyboardHelper;

import java.util.Random;

/**
 * @author Relish Wang
 * @since 2017/09/18
 */
public class SomeModule extends ReactContextBaseJavaModule {
    public SomeModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "SomeModule";
    }

    @SuppressWarnings("unused")
    @ReactMethod
    public void callbackMethod(final String paramsFromJS, Callback ok, Callback error) {
        KeyboardHelper.showKeyboard(getCurrentActivity(), paramsFromJS, new EasyCallback() {

            @Override
            public void onKeyEvent(String code) {
                onHandleResult(paramsFromJS, code);
            }
        });
    }

    @ReactMethod
    public void promiseMethod(String paramsFromJS, Promise promise) {
        Random r = new Random();
        boolean b = r.nextBoolean();
        if (b) {
            promise.resolve("luck dog!");
        } else {
            promise.reject(new RuntimeException("bad ass!"));
        }

    }

    public void onHandleResult(String tag, String barcodeData) {
        WritableMap params = Arguments.createMap();
        params.putString("tag", tag);
        params.putString("result", barcodeData);
        sendEvent(getReactApplicationContext(), "onKeyboardEvent", params);
    }


    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
