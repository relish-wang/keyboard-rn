package com.souche.widget.keyboard.sample;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

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

    @ReactMethod
    public void callbackMethod(String paramsFromJS, Callback ok, Callback error) {

        Random r = new Random();
        boolean b = r.nextBoolean();
        if (b) {
            ok.invoke("luck dog!");
        } else {
            error.invoke("bad ass!");
        }
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
}
