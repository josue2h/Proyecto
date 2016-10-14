package com.jos.android.ipcalculator.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.jos.android.ipcalculator.Hardcode;
import com.jos.android.ipcalculator.R;
import com.jos.android.ipcalculator.models.CalculatorIp;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ControllerDetail extends AppCompatActivity {
    @Bind(R.id.container_detail)
    LinearLayout container_detail;
    private ValidOctet validOctet;
    private CalculatorIp calculatorIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controller_detail);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        validOctet = new ValidOctet(getApplicationContext());
        calculatorIp =  new CalculatorIp(getApplicationContext());

        String input = getIntent().getExtras().getString(Hardcode.ID_INPUT);
        validOctet.setInputAddressMask(input);
        calculatorIp.changeDates(validOctet.getOctets());
        container_detail.addView(calculatorIp.getView());
    }
}
