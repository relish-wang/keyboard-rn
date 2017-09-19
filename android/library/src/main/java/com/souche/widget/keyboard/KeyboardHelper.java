package com.souche.widget.keyboard;

import android.content.Context;

import com.facebook.react.bridge.Callback;

/**
 * @author Relish Wang
 * @since 2017/09/18
 */
public class KeyboardHelper {

    public static void showKeyboard(
            Context context,
            String paramsFromJs,
            EasyCallback callback) {
        switch (paramsFromJs) {
            case "IDCard":
                new KeyboardDialog(context, R.xml.keyboard_id_card, callback).show();
                break;
            case "CarNumber":
                break;
        }
    }
}
