package com.jos.android.ipcalculator.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jos.android.ipcalculator.Hardcode;
import com.jos.android.ipcalculator.R;
import com.jos.android.ipcalculator.adapters.AdapterList;
import com.jos.android.ipcalculator.models.CalculatorSubnet;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControllerSubnet extends Fragment implements ListenerControllerSubnet{
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
    @Bind(R.id.btn_Float)
    FloatingActionButton btn_Float;
    @Bind(R.id.btn_calculate)
    Button btn_calculate;


    @Bind(R.id.lv_result)
    ListView lv_result;

    private AdapterList adapter;
    private int type_input;
    private Animation animation;

    public ControllerSubnet() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supernet, container, false);
        ButterKnife.bind(this,view);
        initialize();
        return view;
    }

    private void initialize() {
        options_classful.setVisibility(View.GONE);
        spinner_mask.setVisibility(View.GONE);
        btn_Float.setVisibility(View.GONE);
        if (adapter == null){
            adapter =  new AdapterList(getContext(), new ArrayList<String>(), false);
            lv_result.setAdapter(adapter);
        }
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_in_out);
    }

    @OnClick(R.id.input)
    public void handleClickInput() {
        btn_calculate.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        back_input.setBackgroundResource(R.color.colorAccent);
        back_number_host.setBackgroundResource(R.color.colorGrey);
        Intent intent =  new Intent(getContext(), ControllerKeyboard.class);
        intent.putExtra(Hardcode.ID_INPUT,input.getText().toString().trim());
        intent.putExtra(Hardcode.ID_TYPE, Hardcode.TYPE_KEYBOARD[0]);
        type_input = Hardcode.TYPE_KEYBOARD[0];
        startActivityForResult(intent, Hardcode.REQUEST_CODE);
    }

    @OnClick(R.id.number_host)
    public void handleClickNumberHost() {
        btn_calculate.setTextColor(getContext().getResources().getColor(R.color.colorWhite));
        back_number_host.setBackgroundResource(R.color.colorAccent);
        back_input.setBackgroundResource(R.color.colorGrey);
        Intent intent =  new Intent(getContext(), ControllerKeyboard.class);
        intent.putExtra(Hardcode.ID_INPUT,number_host.getText().toString().trim());
        intent.putExtra(Hardcode.ID_TYPE, Hardcode.TYPE_KEYBOARD[2]);
        type_input = Hardcode.TYPE_KEYBOARD[2];
        startActivityForResult(intent, Hardcode.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == Hardcode.REQUEST_CODE) && (resultCode == Activity.RESULT_OK)){
            if (type_input == Hardcode.TYPE_KEYBOARD[0]){ input.setText(data.getDataString());}
            if (type_input == Hardcode.TYPE_KEYBOARD[2]){ number_host.setText(data.getDataString());}
        }
    }

    @OnClick(R.id.btn_calculate)
    public void handleCalculate() {
        btn_calculate.setTextColor(getContext().getResources().getColor(R.color.colorOrange));
        if ((input.length() != 0)&&(number_host.length() != 0)) {
            int number = Integer.parseInt(number_host.getText().toString());

            CalculatorSubnet calc = new CalculatorSubnet(getContext(), this);
            ValidOctet validOctet = new ValidOctet(getContext());
            validOctet.setInputAddressMask(input.getText().toString());

            calc.changeData( validOctet.getOctets(),number);
            if (calc.esPosible()) {
                adapter.clear();
                lv_result.startAnimation(animation);
                calc.calcular();
            }else{
                Toast.makeText(getContext(), "Bits insuficientes", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getContext(), "Se requiere llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void addSubred(String subred) {
        adapter.addItem(subred);
    }
}
