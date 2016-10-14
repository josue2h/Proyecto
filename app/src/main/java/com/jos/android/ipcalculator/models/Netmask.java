package com.jos.android.ipcalculator.models;

/**
 * Created by Josue on 21/09/2016.
 */
public class Netmask {
    private int size;
    private int position;
    private NumberIp first_octet;
    private NumberIp second_octet;
    private NumberIp third_octet;
    private NumberIp fourth_octet;

    public Netmask(int inputSize) {
        size = inputSize;
        calcularPosicion();
        calcularOctetos();
    }

    public void changeMask(int input_number) {
        size = input_number;
        calcularMask();
    }

    public void calcularMask() {
        calcularPosicion();
        calcularOctetos();
    }

    private void calcularPosicion() {
        if (size > 0 && size <= 8) {
            position = 1;
        }
        if (size > 8 && size <= 16) {
            position = 2;
        }
        if (size > 16 && size <= 24) {
            position = 3;
        }
        if (size > 24 && size <= 32) {
            position = 4;
        }
    }

    private void calcularOctetos() {
        int aux_size = size;
        String first_binary = "";
        String second_binary = "";
        String third_binary = "";
        String fourth_binary = "";
        for (int i = 0; i <= 32; i++) {
            if(i > 0 && i <= 8) {
                if (aux_size > 0 ) {
                    first_binary += 1;
                    aux_size--;
                }else{
                    first_binary += 0;
                }
            }

            if(i > 8 && i <= 16) {
                if (aux_size > 0 ) {
                    second_binary += 1;
                    aux_size--;
                }else{
                    second_binary += 0;
                }
            }

            if(i > 16 && i <= 24) {
                if (aux_size > 0 ) {
                    third_binary += 1;
                    aux_size--;
                }else{
                    third_binary += 0;
                }
            }

            if(i > 24 && i <= 32) {
                if (aux_size > 0 ) {
                    fourth_binary += 1;
                    aux_size--;
                }else{
                    fourth_binary += 0;
                }
            }
        }
        first_octet = new NumberIp(first_binary);
        second_octet = new NumberIp(second_binary);
        third_octet = new NumberIp(third_binary);
        fourth_octet = new NumberIp(fourth_binary);
    }

    public int getSize() {
        return size;
    }

    public int getPosition() {
        return position;
    }

    public String getFirstOctet(int i) {
        if (i == 0) {
            return first_octet.getNumberBinary();
        }else {
            return first_octet.getNumberDecimal()+"";
        }
    }

    public String getSecondOctet(int i) {
        if (i == 0) {
            return second_octet.getNumberBinary();
        }else {
            return second_octet.getNumberDecimal()+"";
        }
    }

    public String getThirdOctet(int i) {
        if (i == 0) {
            return third_octet.getNumberBinary();
        }else {
            return third_octet.getNumberDecimal()+"";
        }
    }

    public String getFourthOctet(int i) {
        if (i == 0) {
            return fourth_octet.getNumberBinary();
        }else {
            return fourth_octet.getNumberDecimal()+"";
        }
    }

    public String getNetmaskDecimal() {
        return first_octet.getNumberDecimal()+"."+
                second_octet.getNumberDecimal()+"."+
                third_octet.getNumberDecimal()+"."+
                fourth_octet.getNumberDecimal();
    }

    public String getNetmaskBinary() {
        return first_octet.getNumberBinary()+"."+
                second_octet.getNumberBinary()+"."+
                third_octet.getNumberBinary()+"."+
                fourth_octet.getNumberBinary();
    }

    public int getNumberSelection() {
        int resp = 0;
        switch(position) {
            case 1:
                resp = first_octet.getNumberDecimal();
                break;
            case 2:
                resp = second_octet.getNumberDecimal();
                break;
            case 3:
                resp = third_octet.getNumberDecimal();
                break;
            case 4:
                resp = fourth_octet.getNumberDecimal();
                break;
        }
        return resp;
    }
}
