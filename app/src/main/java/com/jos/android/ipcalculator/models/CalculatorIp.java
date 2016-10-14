package com.jos.android.ipcalculator.models;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


import com.jos.android.ipcalculator.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Josue on 21/09/2016.
 */
public class CalculatorIp {
    @Bind(R.id.address_b)
    TextView address_b;
    @Bind(R.id.network_d)
    TextView network_d;
    @Bind(R.id.network_b)
    TextView network_b;
    @Bind(R.id.broadcast_d)
    TextView broadcast_d;
    @Bind(R.id.broadcast_b)
    TextView broadcast_b;
    @Bind(R.id.range_host)
    TextView range_host;
    @Bind(R.id.host_valid)
    TextView host_valid;

    private Ip network ;
    private Ip address;
    private Netmask netmask;
    private Ip broadcast;
    private Ip first_address;
    private Ip last_address;

    private int jump_size;
    private int network_bits    ;
    private int host_bits;
    private int networks_number;
    private int host_number;

    private int section_octet;

    private Context context;
    private View view;
    private Animation animation;

    private final static int SIZE_MASK = 32;
    private static final double BASE = 2;
    private static final int AUXRANGE = 256;

    public CalculatorIp(Context context) {
        address = new Ip(192, 168, 2, 12);
        netmask = new Netmask(24);
        this.context = context;
        loadView();

    }

    private void loadView() {
        animation = AnimationUtils.loadAnimation(context, R.anim.translate_in_out);
        view =  LayoutInflater.from(context).inflate(R.layout.view_result_ip, null);
        ButterKnife.bind(this, view);
    }

    public void changeDates(Integer[] inputs) {
        address.changesDates(inputs[0], inputs[1], inputs[2], inputs[3]);
        netmask.changeMask(inputs[4]);
        //address.changesDates(192,168,2,12);
        //netmask.changeMask(24);
        initProceso();

    }

    private void initProceso() {
        calcularBits();
        calcularSizes();
        calcularSeccion();
        calcular();
        fillView();
    }

    private void calcularBits() {
        network_bits = netmask.getSize();
        host_bits = SIZE_MASK - network_bits;
    }

    private void calcularSizes() {
        networks_number = (int)Math.pow(BASE, network_bits);
        host_number = (int)Math.pow(BASE, host_bits);
    }

    private void calcularSeccion() {
        switch (netmask.getPosition()) {
            case 1:
                section_octet = address.getFirstDecimal();
                break;
            case 2:
                section_octet = address.getSecondDecimal();
                break;
            case 3:
                section_octet = address.getThirdDecimal();
                break;
            case 4:
                section_octet = address.getFourthDecimal();
                break;
        }
    }

    private void calcular() {
        jump_size = AUXRANGE - netmask.getNumberSelection();
        calcularSubredes(jump_size);
    }

    private void calcularSubredes(int size_salto) {
        int initial_ip = 0;
        int end_ip = size_salto;
        boolean found = false;

        while (!isSeccion(initial_ip, end_ip)) {
            initial_ip = end_ip;
            end_ip +=  size_salto;
        }
    }

    private boolean isSeccion(int initial_ip, int end_ip) {
        boolean resp = false;

        int first_aux = address.getFirstDecimal();
        int second_aux = address.getSecondDecimal();
        int third_aux = address.getThirdDecimal();
        int fourth_aux = address.getFourthDecimal();

        switch (netmask.getPosition()) {
            case 1:
                if (section_octet >= initial_ip && section_octet < end_ip) {
                    network = new Ip(initial_ip,0,0,0);
                    broadcast = new Ip((end_ip-1),255,255,255);
                    first_address = new Ip(initial_ip,0,0,1);
                    last_address = new Ip((end_ip-1),255,255,254);
                    resp = true;
                }
                break;
            case 2:
                if (section_octet >= initial_ip && section_octet < end_ip) {
                    network = new Ip(first_aux,initial_ip,0,0);
                    broadcast = new Ip(first_aux,(end_ip - 1),255,255);
                    first_address = new Ip(first_aux,initial_ip,0,1);
                    last_address = new Ip(first_aux,(end_ip-1),255,254);
                    resp = true;
                }
                break;
            case 3:
                if (section_octet >= initial_ip && section_octet < end_ip) {
                    network = new Ip(first_aux,second_aux,initial_ip,0);
                    broadcast = new Ip(first_aux,second_aux,(end_ip-1),255);
                    first_address = new Ip(first_aux,second_aux,initial_ip,1);
                    last_address = new Ip(first_aux,second_aux,(end_ip-1),254);
                    resp = true;
                }
                break;
            case 4:
                if (section_octet >= initial_ip && section_octet < end_ip) {
                    network = new Ip(first_aux,second_aux,third_aux,initial_ip);
                    broadcast = new Ip(first_aux,second_aux,third_aux,(end_ip-1));
                    first_address = new Ip(first_aux,second_aux,third_aux,(initial_ip+1));
                    last_address = new Ip(first_aux,second_aux,third_aux,(end_ip-2));
                    resp = true;
                }
                break;
        }
        return resp;
    }

    public Ip getNetwork() {
        return network;
    }

    public Ip getAddress() {
        return address;
    }

    public Netmask getNetmask() {
        return netmask;
    }

    public Ip getBroadcast() {
        return broadcast;
    }

    public Ip getFirst_address() {
        return first_address;
    }

    public Ip getLast_address() {
        return last_address;
    }

    public int getNetwork_bits() {
        return network_bits;
    }

    public int getHost_bits() {
        return host_bits;
    }

    public int getNetworks_number() {
        return networks_number;
    }

    public int getHost_number() {
        return host_number;
    }


    private void fillView() {
        address_b.setText(address.getIpBinary());
        network_d.setText(network.getIpDecimal());
        network_b.setText(network.getIpBinary());
        broadcast_d.setText(broadcast.getIpDecimal());
        broadcast_b.setText(broadcast.getIpBinary());
        range_host.setText(first_address.getIpDecimal().replaceAll(" ", "")+" >> "
                +last_address.getIpDecimal().replaceAll(" ", ""));
        host_valid.setText((host_number-2)+"");
    }

    public View getView() {
        view.startAnimation(animation);
        return view;
    }
}
