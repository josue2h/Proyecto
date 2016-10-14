package com.jos.android.ipcalculator.controllers;

import android.util.Log;

import java.util.List;

/**
 * Created by Josue on 13/10/2016.
 */

public class ConverterInput {
    private Integer[] octets;

    public ConverterInput() {
        octets = new Integer[]{0,0,0,0,0};
    }

    public Integer[] getArray(String input) {
        String data = "";
        int position = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) == '.')||(input.charAt(i) == '/')) {
                octets[position] = convertStringNumber(data);
                data = "";
                position++;
            }else {
                data += input.charAt(i);
                if ((i+1)==input.length()) octets[position] = convertStringNumber(data);
            }
        }
        return octets;
    }

    public int  convertStringNumber(String data) {
        int result = 0;
        try {
            result = Integer.parseInt(data);
        }catch (NumberFormatException e) {
            Log.d("ERROR","FAILURE_CONVERT");
        }
        return result;
    }

    public Long[] converterArray(List<String> list) {
        Long[] result = new Long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = (long)convertStringNumber(list.get(i));
        }
        return result;
    }
}
