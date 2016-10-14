package com.jos.android.ipcalculator.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jos.android.ipcalculator.Hardcode;
import com.jos.android.ipcalculator.R;
import com.jos.android.ipcalculator.controllers.ControllerDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josue on 09/10/2016.
 */
public class AdapterList extends ArrayAdapter {
    @Bind(R.id.container_item)
    LinearLayout container_item;
    @Bind(R.id.tv_position)
    TextView tv_position;
    @Bind(R.id.tv_item)
    TextView tv_item;
    @Bind(R.id.btn_delete_item)
    ImageButton btn_delete_item;



    private Context context;
    private boolean type;
    List<String> list;

    public AdapterList(Context context, List<String> list, boolean type) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.type = type;

    }

    private void initialize() {
        if (type) {
            container_item.setWeightSum(7);
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, null);
        ButterKnife.bind(this, view);
        initialize();
        tv_position.setText(position+"");
        tv_item.setText(list.get(position));

        tv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ControllerDetail.class);
                intent.putExtra(Hardcode.ID_INPUT,list.get(position));
                context.startActivity(intent);
            }
        });

        btn_delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        return  view;
    }

    public void addItem(String item) {
        list.add(item);
        notifyDataSetChanged();
    }

    public void ClearItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }


}
