package com.jos.android.ipcalculator.models;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jos.android.ipcalculator.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josue on 03/10/2016.
 */
public class Keyboard extends Activity{
    private Context context;
    private View keyboard;

    @Bind(R.id.keyboard_input)
    TextView keyboard_input;

    @Bind(R.id.key_1)
    TextView key_1;
    @Bind(R.id.key_2)
    TextView key_2;
    @Bind(R.id.key_3)
    TextView key_3;
    @Bind(R.id.key_4)
    TextView key_4;
    @Bind(R.id.key_5)
    TextView key_5;
    @Bind(R.id.key_6)
    TextView key_6;
    @Bind(R.id.key_7)
    TextView key_7;
    @Bind(R.id.key_8)
    TextView key_8;
    @Bind(R.id.key_9)
    TextView key_9;
    @Bind(R.id.key_0)
    TextView key_0;

    @Bind(R.id.key_slash)
    TextView key_slash;
    @Bind(R.id.key_point)
    TextView key_point;
    @Bind(R.id.key_delete)
    TextView key_delete;
    @Bind(R.id.key_ok)
    TextView key_ok;

    private static final String [] KEYBOARD_VALUE = {"0","1","2","3","4","5","6","7","8","9","/","."};
    private String input = null;
    public Keyboard(Context context) {
        this.context = context.getApplicationContext();
        input = "";
        loadView();
    }

    private void loadView() {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        keyboard = inflater.inflate(R.layout.view_keyboard, null, true);
        ButterKnife.bind(this,keyboard);
    }

    @OnClick(R.id.key_1)
    public void oneKeypress(){
        input += KEYBOARD_VALUE[1];

    }

    @OnClick(R.id.key_2)
    public void twoKeypress(){
        input += KEYBOARD_VALUE[2];
    }

    @OnClick(R.id.key_3)
    public void threeKeypress(){
        input += KEYBOARD_VALUE[3];
    }

    @OnClick(R.id.key_4)
    public void fourKeypress(){
        input += KEYBOARD_VALUE[4];
    }

    @OnClick(R.id.key_5)
    public void fiveKeypress(){
        input += KEYBOARD_VALUE[5];
    }

    @OnClick(R.id.key_6)
    public void sixKeypress(){
        input += KEYBOARD_VALUE[6];
    }

    @OnClick(R.id.key_7)
    public void sevenKeypress(){
        input += KEYBOARD_VALUE[7];
    }

    @OnClick(R.id.key_8)
    public void eightKeypress(){
        input += KEYBOARD_VALUE[8];
    }

    @OnClick(R.id.key_9)
    public void nineKeypress(){
        input += KEYBOARD_VALUE[9];
    }

    @OnClick(R.id.key_0)
    public void zeroKeypress(){
        input += KEYBOARD_VALUE[0];
    }

    @OnClick(R.id.key_slash)
    public void slashKeypress(){
        input += KEYBOARD_VALUE[10];
    }

    @OnClick(R.id.key_point)
    public void pointKeypress(){
        input += KEYBOARD_VALUE[11];
    }

    @OnClick(R.id.key_delete)
    public void deleteKeypress(){
        input = input.substring(0,input.length()-1);
    }

    @OnClick(R.id.key_ok)
    public void okKeypress(){

    }

    public void changeValue() {
        keyboard_input.setText(input);
    }

    public View getView() {
        return keyboard;
    }


}
