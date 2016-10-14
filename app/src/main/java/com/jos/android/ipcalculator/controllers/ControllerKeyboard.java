package com.jos.android.ipcalculator.controllers;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.jos.android.ipcalculator.Hardcode;
import com.jos.android.ipcalculator.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ControllerKeyboard extends Activity {
    @Bind(R.id.keyboard_input)
    EditText keyboard_input;
    @Bind(R.id.key_slash)
    TextView key_slash;
    @Bind(R.id.key_point)
    TextView key_point;

    private int type;
    private String input = null;
    private ValidOctet validOctet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.view_keyboard);
        ButterKnife.bind(this);
        initializeView();
    }

    private void initializeView() {
        validOctet = new ValidOctet(getApplicationContext());
        input = getIntent().getExtras().getString(Hardcode.ID_INPUT);
        type = getIntent().getExtras().getInt(Hardcode.ID_TYPE);
        if (type == Hardcode.TYPE_KEYBOARD[1]) {
            key_slash.setText("");
        }else {
            if (type == Hardcode.TYPE_KEYBOARD[2]) {
                key_slash.setText("");
                key_point.setText("");
            }
        }
        keyboard_input.setText(input);
    }

    @OnClick(R.id.key_1)
    public void oneKeypress(){
        input += Hardcode.KEYBOARD_VALUE[1];
        changeValue();
    }

    @OnClick(R.id.key_2)
    public void twoKeypress(){
        input += Hardcode.KEYBOARD_VALUE[2];
        changeValue();
    }

    @OnClick(R.id.key_3)
    public void threeKeypress(){
        input += Hardcode.KEYBOARD_VALUE[3];
        changeValue();
    }

    @OnClick(R.id.key_4)
    public void fourKeypress(){
        input += Hardcode.KEYBOARD_VALUE[4];
        changeValue();
    }

    @OnClick(R.id.key_5)
    public void fiveKeypress(){
        input += Hardcode.KEYBOARD_VALUE[5];
        changeValue();
    }

    @OnClick(R.id.key_6)
    public void sixKeypress(){
        input += Hardcode.KEYBOARD_VALUE[6];
        changeValue();
    }

    @OnClick(R.id.key_7)
    public void sevenKeypress(){
        input += Hardcode.KEYBOARD_VALUE[7];
        changeValue();
    }

    @OnClick(R.id.key_8)
    public void eightKeypress(){
        input += Hardcode.KEYBOARD_VALUE[8];
        changeValue();
    }

    @OnClick(R.id.key_9)
    public void nineKeypress(){
        input += Hardcode.KEYBOARD_VALUE[9];
        changeValue();
    }

    @OnClick(R.id.key_0)
    public void zeroKeypress(){
        input += Hardcode.KEYBOARD_VALUE[0];
        changeValue();
    }

    @OnClick(R.id.key_slash)
    public void slashKeypress(){
        if (type == 0) {
            input += Hardcode.KEYBOARD_VALUE[10];
            changeValue();
        }
    }

    @OnClick(R.id.key_point)
    public void pointKeypress(){
        if ((type == 0)||(type==1)) {
            input += Hardcode.KEYBOARD_VALUE[11];
            changeValue();
        }
    }

    @OnClick(R.id.key_delete)
    public void deleteKeypress(){
        if (!input.isEmpty()) {
            input = input.substring(0,input.length()-1);
            changeValue();
        }
    }

    @OnClick(R.id.key_ok)
    public void okKeypress(){
        switch(type) {
            case 0:
                validOctet.setInputAddressMask(input);
                break;
            case 1:
                validOctet.setInputAddress(input);
                break;
            default:
                break;
            }
        if (validOctet.isValid_input()) {
            Intent data = new Intent();
            data.setData(Uri.parse(input));
            setResult(RESULT_OK, data);
            finish();
        }else {
            keyboard_input.setError(validOctet.getErrorMessage());
        }

    }

    public void changeValue() {
        keyboard_input.setText(input);
    }

    @OnClick(R.id.keyboard_input)
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager)
                getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    inputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException npe) {
            Log.e(getLocalClassName(), Log.getStackTraceString(npe));
        }
    }
}
