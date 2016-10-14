package com.jos.android.ipcalculator.controllers;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jos.android.ipcalculator.Hardcode;
import com.jos.android.ipcalculator.R;
import com.jos.android.ipcalculator.adapters.AdapterInput;
import com.jos.android.ipcalculator.adapters.ListenerAdapterInput;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControllerSubnetHete extends Fragment implements ListenerAdapterInput{
    @Bind(R.id.input)
    TextView input;
    @Bind(R.id.number_subnet)
    TextView number_subnet;
    @Bind(R.id.btn_modify)
    Button btn_modify;
    @Bind(R.id.lv_required_host)
    ListView lv_required_host;
    @Bind(R.id.lv_result)
    ListView lv_result;


    private AdapterInput adapter_input;
    private int position;
    private String value;
    private int section;

    private Long[] input_requirements;
    private Integer[] input_array;

    private ConverterInput converter;

    public ControllerSubnetHete() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.controller_subnet_he, container, false);
        ButterKnife.bind(this,view);
        initialize();

        return view;
    }

    private void initialize() {
        if (adapter_input == null) {
            adapter_input =  new AdapterInput(getContext(), new ArrayList<String>(),this);
        }
        lv_required_host.setAdapter(adapter_input);
        adapter_input.modify(4);
        converter = new ConverterInput();
    }

    @Override
    public void onClick(int position, String data) {
        this.position = position;
        Toast.makeText(getContext(), "pos :"+position, Toast.LENGTH_SHORT).show();
        section = 2;
        Intent intent =  new Intent(getContext(), ControllerKeyboard.class);
        intent.putExtra(Hardcode.ID_INPUT,data);
        intent.putExtra(Hardcode.ID_TYPE, Hardcode.TYPE_KEYBOARD[2]);
        startActivityForResult(intent, Hardcode.REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == Hardcode.REQUEST_CODE) && (resultCode == Activity.RESULT_OK)){
            switch (section) {
                case 1:
                    number_subnet.setText(data.getDataString());
                    break;
                case 2:
                    adapter_input.changeData(position, data.getDataString());
                    break;
            }
        }else{
            value = "";
        }
    }

    @OnClick(R.id.number_subnet)
    public void handleClickNumberSubred() {
        section = 1;
        Intent intent =  new Intent(getContext(), ControllerKeyboard.class);
        intent.putExtra(Hardcode.ID_INPUT,number_subnet.getText());
        intent.putExtra(Hardcode.ID_TYPE, Hardcode.TYPE_KEYBOARD[2]);
        startActivityForResult(intent, Hardcode.REQUEST_CODE);
    }

    @OnClick(R.id.btn_modify)
    public void handleClickModify() {
        int size_subnet = Integer.parseInt(number_subnet.getText().toString());
        adapter_input.modify(size_subnet);
    }

    @OnClick(R.id.fab_calculate)
    public void handleClickCalculate() {
        inputUpdate();
    }

    private void inputUpdate() {
        input_array = converter.getArray(input.getText().toString());

    }
}
