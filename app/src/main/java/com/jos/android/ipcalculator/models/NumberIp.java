package com.jos.android.ipcalculator.models;

/**
 * Created by Josue on 21/09/2016.
 */
public class NumberIp extends Binary{
    int number;
    public NumberIp(int number) {
        super(number);
        this.number = number;
    }

    public NumberIp(String number) {
        super(number);
        this.number = converterDecimal(number);
    }

    public int converterDecimal(String number) {
        int res = 0;
        int multipler = 1;
        for (int i = number.length()-1; i >= 0; i--) {
            res += Integer.parseInt(""+number.charAt(i))*multipler;
            multipler += multipler;
        }
        return res;
    }

    public int getNumberDecimal() {
        return number;
    }

    public void changeNumber(int input_number) {
        number = input_number;
        super.changeBinary(input_number);
    }

    public void changeNumber(String input_number) {
        number = converterDecimal(input_number);
        super.changeBinary(number);
    }
}
