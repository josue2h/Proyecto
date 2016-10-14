package com.jos.android.ipcalculator.models;

/**
 * Created by Josue on 21/09/2016.
 */
public class Binary {
    String number = null;

    private final int DIVIDER = 2;
    public Binary(int number) {
        this.number = convertirBinary(number);
    }

    public Binary(String number) {
        this.number = number;
    }

    public String convertirBinary(int number) {
        String rest = "";
        do {
            rest = number % DIVIDER + rest;
            number = number / DIVIDER;
        }while(number >= 1);
        for (int i=rest.length();i<8;i++) {
            rest = "0" + rest;
        }
        return rest;
    }

    public String getNumberBinary() {
        return number;
    }

    public void changeBinary(int input_number) {
        number = convertirBinary(input_number);
    }
}
