package com.jos.android.ipcalculator.models;

/**
 * Created by Josue on 21/09/2016.
 */
public class Ip {
    private NumberIp first_octet;
    private NumberIp second_octet;
    private NumberIp third_octet;
    private NumberIp fourth_octet;

    public Ip(int firstInput, int secondInput, int thirdInput, int fourthInput) {
        first_octet = new NumberIp(firstInput);
        second_octet = new NumberIp(secondInput);
        third_octet = new NumberIp(thirdInput);
        fourth_octet = new NumberIp(fourthInput);
    }

    public int getFirstDecimal() {
        return first_octet.getNumberDecimal();
    }

    public int getSecondDecimal() {
        return second_octet.getNumberDecimal();
    }

    public int getThirdDecimal() {
        return third_octet.getNumberDecimal();
    }

    public int getFourthDecimal() {
        return fourth_octet.getNumberDecimal();
    }

    public String getFirstBinary() {
        return first_octet.getNumberBinary();
    }

    public String getSecondBinary() {
        return second_octet.getNumberBinary();
    }

    public String getThirdBinary() {
        return third_octet.getNumberBinary();
    }

    public String getFourthBinary() {
        return fourth_octet.getNumberBinary();
    }

    public String getIpDecimal() {
        return getFirstDecimal()+" . "+getSecondDecimal()+" . "+
                getThirdDecimal()+" . "+getFourthDecimal();
    }

    public String getIpBinary() {
        return getFirstBinary()+" . "+getSecondBinary()+" . "+
                getThirdBinary()+" . "+getFourthBinary();
    }

    public void changesDates(int input_number1, int input_number2,
                             int input_number3, int input_number4) {
        first_octet.changeNumber(input_number1);
        second_octet.changeNumber(input_number2);
        third_octet.changeNumber(input_number3);
        fourth_octet.changeNumber(input_number4);

    }

    public int getNumberSeccion(int i) {
        int resp = 0;
        switch (i) {
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
