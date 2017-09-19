package com.souche.widget.keyboard;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.XmlRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.react.bridge.Callback;

/**
 * @author Relish Wang
 * @since 2017/09/18
 */
public class KeyboardDialog extends Dialog {

    private KeyboardView mKeyboardView;
    private Keyboard mKeyboard;
    private Callback mCallback;
    private EasyCallback easyCallback;

    public static KeyboardDialog newInstance(Context context, @XmlRes int resId, Callback callback) {
        return new KeyboardDialog(context, resId, callback);
    }

    public static KeyboardDialog newInstance(Context context, @NonNull Keyboard keyboard, Callback callback) {
        return new KeyboardDialog(context, keyboard, callback);
    }

    public KeyboardDialog(@NonNull Context context, @XmlRes int resId, EasyCallback callback) {
        super(context, R.style.BottomDialog);
        mKeyboard = new Keyboard(context, resId);
        easyCallback = callback;
    }

    public KeyboardDialog(@NonNull Context context, @XmlRes int resId, Callback callback) {
        super(context, R.style.BottomDialog);
        mKeyboard = new Keyboard(context, resId);
        mCallback = callback;
    }

    public KeyboardDialog(@NonNull Context context, @NonNull Keyboard keyboard, Callback callback) {
        super(context, R.style.BottomDialog);
        mKeyboard = keyboard;
        mCallback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        //获得dialog的window窗口
        Window window = getWindow();
        if (window != null) {
            //设置dialog在屏幕底部
            window.setGravity(Gravity.BOTTOM);
            //设置dialog弹出时的动画效果，从屏幕底部向上弹出
            window.setWindowAnimations(R.style.dialogStyle);
            window.getDecorView().setPadding(0, 0, 0, 0);
            //获得window窗口的属性
            android.view.WindowManager.LayoutParams lp = window.getAttributes();
            //设置窗口宽度为充满全屏
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            //设置窗口高度为包裹内容
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            //将设置好的属性set回去
            window.setAttributes(lp);
        }
        @SuppressLint("InflateParams")
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_keyboard, null);
        setContentView(rootView);
        mKeyboardView = (KeyboardView) rootView.findViewById(R.id.keyboard);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setKeyboard(mKeyboard);
        mKeyboardView.setOnKeyboardActionListener(new OnKeyboardActionAdapter(easyCallback) {

            @Override
            public void close() {
                hideKeyboard();
                dismiss();
            }
        });
        showKeyboard();
    }

    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }
}
