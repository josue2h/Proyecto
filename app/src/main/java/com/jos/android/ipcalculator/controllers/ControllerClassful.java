package com.jos.android.ipcalculator.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
public class ControllerClassful extends Fragment implements
        RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
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
    @Bind(R.id.rg_class)
    RadioGroup rg_class;
    @Bind(R.id.spinner_mask)
    Spinner spinner_mask;
    @Bind(R.id.btn_calculate)
    Button btn_calculate;

    private ValidOctet validOctet;
    private CalculatorIp calculatorIp;
    private String class_type;
    String mask = "";

    private ArrayAdapter<CharSequence> arrayAdapter;

    public ControllerClassful() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.controller_classful, container, false);
        ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        validOctet = new ValidOctet(getContext());

        calculatorIp =  new CalculatorIp(getContext().getApplicationContext());
        arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.class_a_array,
                android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mask.setAdapter(arrayAdapter);
        class_type = Hardcode.CLASS_TYPE[0];
        input.setHint(R.string.hint_ip_classful);
        spinner_mask.setOnItemSelectedListener(this);
        rg_class.setOnCheckedChangeListener(this);
        back_number_host.setVisibility(View.GONE);
    }

    @OnClick(R.id.input)
    public void handleClickInput() {
        btn_calculate.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        back_input.setBackgroundResource(R.color.colorAccent);
        container_result.removeAllViews();
        Intent intent =  new Intent(getContext(), ControllerKeyboard.class);
        intent.putExtra(Hardcode.ID_INPUT,input.getText().toString().trim());
        intent.putExtra(Hardcode.ID_TYPE, Hardcode.TYPE_KEYBOARD[1]);
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
            String aux_input = input.getText().toString().trim() +"/"+ mask;
            validOctet.setInputAddressMask(aux_input);
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        btn_calculate.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        switch (checkedId) {
            case R.id.rb_a:
                arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.class_a_array,
                                                android.R.layout.simple_spinner_item);
                class_type = Hardcode.CLASS_TYPE[0];
                break;
            case R.id.rb_b:
                arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.class_b_array,
                                                android.R.layout.simple_spinner_item);
                class_type = Hardcode.CLASS_TYPE[1];
                break;
            case R.id.rb_c:
                arrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.class_c_array,
                                                android.R.layout.simple_spinner_item);
                class_type = Hardcode.CLASS_TYPE[2];
                break;
        }
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_mask.setAdapter(arrayAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        btn_calculate.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        container_result.removeAllViews();
        int aux_position = 0;
        if (class_type.equals(Hardcode.CLASS_TYPE[0])) aux_position = position;
        if (class_type.equals(Hardcode.CLASS_TYPE[1])) aux_position = position + 8;
        if (class_type.equals(Hardcode.CLASS_TYPE[2])) aux_position = position + 16;
        mask = Hardcode.MASK_VALUE[aux_position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
