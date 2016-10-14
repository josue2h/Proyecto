package com.jos.android.ipcalculator.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jos.android.ipcalculator.Hardcode;
import com.jos.android.ipcalculator.R;
import com.jos.android.ipcalculator.adapters.AdapterList;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControllerSupernet extends Fragment {
    @Bind(R.id.back_input)
    LinearLayout back_input;
    @Bind(R.id.input)
    TextView input;
    @Bind(R.id.back_number_host)
    LinearLayout back_number_host;

    @Bind(R.id.options_classful)
    LinearLayout options_classful;
    @Bind(R.id.spinner_mask)
    Spinner spinner_mask;
    @Bind(R.id.btn_calculate)
    Button btn_calculate;

    @Bind(R.id.head_result)
    TextView head_result;
    @Bind(R.id.lv_result)
    ListView lv_result;

    private AdapterList adapter;

    public ControllerSupernet() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supernet, container, false);
        ButterKnife.bind(this, view);
        initialize();
        return view;
    }

    private void initialize() {
        back_number_host.setVisibility(View.GONE);
        options_classful.setVisibility(View.GONE);
        spinner_mask.setVisibility(View.GONE);
        btn_calculate.setText("ADD IP/MASK");
        head_result.setText("Lista de direcciones");
        if (adapter == null){
            adapter =  new AdapterList(getContext(), new ArrayList<String>(), true);
            lv_result.setAdapter(adapter);
        }
    }

    @OnClick(R.id.input)
    public void handleClickInput() {
        back_input.setBackgroundResource(R.color.colorAccent);

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
        if (input.length() != 0) {
            adapter.addItem(input.getText().toString());
            back_input.setBackgroundResource(R.color.colorGrey);
        }

    }
}
