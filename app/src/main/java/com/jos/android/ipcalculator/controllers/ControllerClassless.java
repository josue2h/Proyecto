package com.jos.android.ipcalculator.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jos.android.ipcalculator.Hardcode;
import com.jos.android.ipcalculator.R;
import com.jos.android.ipcalculator.models.CalculatorIp;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControllerClassless extends Fragment{
    @Bind(R.id.container_result)
    LinearLayout container_result;

    @Bind(R.id.back_input)
    LinearLayout back_input;
    @Bind(R.id.input)
    TextView input;
    @Bind(R.id.back_number_host)
    LinearLayout back_number_host;
    @Bind(R.id.number_host)
    TextView number_host;
    @Bind(R.id.options_classful)
    LinearLayout options_classful;
    @Bind(R.id.spinner_mask)
    Spinner spinner_mask;
    @Bind(R.id.btn_calculate)
    Button btn_calculate;

    private ValidOctet validOctet;
    private CalculatorIp calculatorIp;

    public ControllerClassless() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.controller_classless, container, false);
        ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        validOctet = new ValidOctet(getContext());
        calculatorIp =  new CalculatorIp(getContext().getApplicationContext());
        back_number_host.setVisibility(View.GONE);
        options_classful.setVisibility(View.GONE);
        spinner_mask.setVisibility(View.GONE);
    }

    @OnClick(R.id.input)
    public void handleClickInput() {
        btn_calculate.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        back_input.setBackgroundResource(R.color.colorAccent);
        container_result.removeAllViews();
        Intent intent =  new Intent(getContext(), ControllerKeyboard.class);
        intent.putExtra(Hardcode.ID_INPUT,input.getText().toString().trim());
        intent.putExtra(Hardcode.ID_TYPE, Hardcode.TYPE_KEYBOARD[0]);
        startActivityForResult(intent, Hardcode.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == Hardcode.REQUEST_CODE) && (resultCode == Activity.RESULT_OK)){
            input.setText(data.getDataString());
        }
    }

    @OnClick(R.id.btn_calculate)
    public void handleCalculate() {
        btn_calculate.setTextColor(getContext().getResources().getColor(R.color.colorOrange));
        if (input.length() != 0) {
            container_result.removeAllViews();
            validOctet.setInputAddressMask(input.getText().toString().trim());
            if (validOctet.isValid_input()) {
                calculatorIp.changeDates(validOctet.getOctets());
                container_result.addView(calculatorIp.getView());
            } else {
                input.setError(validOctet.getErrorMessage());
            }
        }else {
            Toast.makeText(getContext(), "Se requiere llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
}
