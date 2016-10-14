package com.jos.android.ipcalculator.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.jos.android.ipcalculator.Hardcode;
import com.jos.android.ipcalculator.R;
import com.jos.android.ipcalculator.controllers.ControllerKeyboard;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Josue on 12/10/2016.
 */

public class AdapterInput extends ArrayAdapter {
    @Bind(R.id.item_name)
    TextView item_name;
    @Bind(R.id.item_value)
    TextView item_value;

    private Context context;
    private List<String> list;
    private ListenerAdapterInput listener;

    public AdapterInput(Context context, List<String> list, ListenerAdapterInput listener) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view  = LayoutInflater.from(context).inflate(R.layout.dual_item, null);
        ButterKnife.bind(this, view);

        item_name.setText(position+"");
        item_value.setText(list.get(position));
        item_value.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position,list.get(position));
            }
        });

        return view;
    }

    public void modify(int size_subnet) {
        if (list.size() != size_subnet) {
            if (size_subnet > list.size()){
                for (int i = list.size(); i < size_subnet; i++) {
                    list.add("");
                }
            }else{
                for (int i = (list.size()-1); i >= size_subnet; i--) {
                    list.remove(i);
                }
            }
            notifyDataSetChanged();
        }
    }
    public void changeData(int position, String data) {
        list.set(position, data);
        notifyDataSetChanged();
    }


}
