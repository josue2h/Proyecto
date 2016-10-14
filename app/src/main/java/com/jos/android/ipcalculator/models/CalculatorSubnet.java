package com.jos.android.ipcalculator.models;

import android.content.Context;
import android.widget.ListView;

import com.jos.android.ipcalculator.controllers.ListenerControllerSubnet;

import java.util.ArrayList;

/**
 * Created by Josue on 11/10/2016.
 */

public class CalculatorSubnet {
    private Integer[] octects;
    private Netmask netmask;

    private long required_subnets;

    private byte bits_net;
    private byte bits_subnet ;
    private byte bits_host;

    private byte position;
    private int jump;

    private static final byte BASE = 2;
    private static final byte TOTAL_BIT_MASK = 32;

    //private ArrayList<String> list ;


    private Context context;
    private ListenerControllerSubnet listener;

    public CalculatorSubnet(Context context, ListenerControllerSubnet listener) {
        this.context = context;
        this.listener = listener;
        //list = new ArrayList<String>();
    }

    public void changeData(Integer[] input, long size) {
        octects = new Integer[]{input[0], input[1], input[2],input[3]};
        netmask = new Netmask(input[4]);

        required_subnets = size;
    }

    public boolean esPosible() {
        long tamanio_posible = (long)Math.pow(BASE,
                TOTAL_BIT_MASK - netmask.getSize());

        if (tamanio_posible < required_subnets) {
            return false;
        }
        return true;
    }

    public void calcular () {
        calculateBitsSubred();
        calculateSubnetZero();
        calculateSubnets();
    }


    private void calculateBitsSubred() {
        for (byte i = 1; i <= TOTAL_BIT_MASK; i++) {
            if (Math.pow(BASE, i) >= required_subnets ) {
                bits_subnet = i;
                i = TOTAL_BIT_MASK + 1;
            }
        }
        bits_net = (byte) (bits_subnet + netmask.getSize());
        bits_host = (byte) (TOTAL_BIT_MASK - bits_net);
        /*System.out.println("POSIBLE : "+ esPosible());
        System.out.println("Bits net : " +bits_net);
        System.out.println("Bits subnet : " +bits_subnet);
        System.out.println("Bits host : " +bits_host);*/
    }

    private void calculateSubnetZero() {
        position = (byte)netmask.getPosition();
        long jump = 256 - netmask.getNumberSelection();
        int initial_ip = 0;
        int end_ip = (int) jump;
        boolean found = false;

        while (!found) {
            //tem.out.println("ini : "+initial_ip +" - "+"last : "+end_ip);
            if ( (initial_ip <= octects[position-1]) &&
                    (octects[position-1] < end_ip)) {
                octects[position-1] = initial_ip;
                switch (position-1) {
                    case 0:
                        for (int i = 1; i < 4; i++) {
                            octects[i] = 0;
                        }
                        break;
                    case 1:
                        for (int i = 2; i < 4; i++) {
                            octects[i] = 0;
                        }
                        break;
                    case 2:
                        octects[3] = 0;
                        break;
                }
                found = true;
            }else {
                initial_ip = end_ip;
                end_ip +=  jump;
            }
        }

        netmask.changeMask(bits_net);

        /*System.out.println("position = "+netmask.getPosition());
        System.out.println("salto : "+jump);
        System.out.println("Size subnets : "+getSizeSubnet());*/


    }

    private long  getSizeSubnet() {
        long size = 0;
        if(bits_subnet == 0) {
            size = 0;
        }else {
            size = (long)Math.pow(BASE,bits_subnet);
        }
        return size;
    }



    private void calculateSubnets() {
        position = (byte) netmask.getPosition();
        jump = 256 -netmask.getNumberSelection();
        /*System.out.println("--------------------------------------------------");
        System.out.println("position 2 : "+position);
        System.out.println("Salto 2 : "+jump);*/
        //System.out.println("0 .- "+ getOctects());
        listener.addSubred(getOctects());
        for (long i = 1; i < getSizeSubnet(); i++) {
            //list.add(getOctects());
            switch (position) {
                case 1:
                    if((octects[0] + jump )>= 256){
                        System.out.println("error 1");
                    }else {
                        addFirstOctet(jump, i);
                    }
                    break;
                case 2:
                    if((octects[1] + jump ) >= 256){
                        if ((octects[0] + 1 )>= 256) {
                            System.out.println("error 2");
                        }else{
                            addFirstOctet(1, i);
                        }
                    }else {
                        addSecondOctet(jump, i);
                    }
                    break;
                case 3:
                    if((octects[2] + jump)>= 256){
                        if(octects[1] +1 >= 256){
                            if(octects[0] +1 >= 256){
                                System.out.println("error 3");
                            }else {
                                addFirstOctet(1, i);
                            }
                        }else{
                            addSecondOctet(1, i);
                        }
                    }else{
                        addThirdOctet(jump, i);
                    }
                    break;
                case 4:
                    if((octects[3] + jump )!= 256){
                        addFourtOctet(jump, i);
                    }else{
                        if((octects[2] + 1 )!= 256){
                            addThirdOctet(1, i);
                        }else{
                            if((octects[1] + 1 )!= 256){
                                addSecondOctet(1, i);
                            }else{
                                if ((octects[0] + 1 )!= 256){
                                    System.out.println("error 4");
                                }else{
                                    addFirstOctet(1, i);
                                }
                            }
                        }
                    }
                    break;
            }
        }
    }

    private void addFourtOctet(int jump_aux, long i) {
        octects[3] = octects[3] + jump_aux;
        listener.addSubred(getOctects());
        //System.out.println(i+" .- "+octects[0]+"."+octects[1]+"."+octects[2]+"."+octects[3]+"/"+bits_net);
    }
    private void addThirdOctet(int jump_aux,long i) {
        octects[2] = octects[2] + jump_aux;
        octects[3] = 0;
        listener.addSubred(getOctects());
        //System.out.println(i+" .- "+octects[0]+"."+octects[1]+"."+octects[2]+"."+octects[3]+"/"+bits_net);
    }

    private void addSecondOctet(int jump_aux, long i) {
        octects[1] = octects[1] + jump_aux;
        octects[2] = 0;
        octects[3] = 0;
        listener.addSubred(getOctects());
        //System.out.println(i+" .- "+octects[0]+"."+octects[1]+"."+octects[2]+"."+octects[3]+"/"+bits_net);
    }

    private void addFirstOctet(int jump_aux, long i) {
        octects[0] = (octects[0] + jump_aux);
        octects[1] = 0;
        octects[2] = 0;
        octects[3] = 0;
        listener.addSubred(getOctects());
        //System.out.println(i+" .- "+octects[0]+"."+octects[1]+"."+octects[2]+"."+octects[3]+"/"+bits_net);
    }













    private String getOctects() {
        return octects[0]+"."+octects[1]+ "."+octects[2] + "."+octects[3]+"/"+bits_net;
    }
}
