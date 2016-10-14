package com.jos.android.ipcalculator.controllers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jos.android.ipcalculator.R;

import butterknife.OnFocusChange;

/**
 * Created by Josue on 19/09/2016.
 */
public class ValidOctet {

    private String input;
    private Context context;

    private Integer[] octets;

    private String[] error;

    public ValidOctet(Context context) {
        this.context = context.getApplicationContext();
        octets = new Integer[]{0,0,0,0,0};
        error = new String[]{"0","0","0","0","0","0"};
    }

    public void setInputAddressMask(String input) {
        octets = new Integer[]{0,0,0,0,0};
        error = new String[]{"0","0","0","0","0","0"};
        this.input = input;
        validateInputAddressMask();
    }

    private void validateInputAddressMask() {
        if (validateFormatAddressMask()) {
            updateOctetsAddressMask();
            validateDataAddressMask();
        }else {
            error[5] = "1";
        }
    }

    private boolean validateFormatAddressMask() {
        boolean valid = true;
        int size = 0;
        int number_points  = 0;
        int number_slash  = 0;
        if ((!input.isEmpty())&&(input.length() <= 18)) {
            for (int i = 0; i < input.length(); i++) {
                if ((input.charAt(i) == '.')||((input.charAt(i) == '/'))){
                    if (input.charAt(i) == '.') number_points++;
                    if (input.charAt(i) == '/') number_slash++;
                    if ((size == 0) ||((i+1) == input.length())){
                        valid = false;
                        i = input.length();
                    }else{
                        size = 0;
                    }
                }else {
                    size++;
                }
            }
        }else {
            valid = false ;
        }
        return valid && (number_points == 3) && (number_slash == 1);
    }

    private void updateOctetsAddressMask() {
        String data = "";
        int position = 0;
        for (int i = 0; i < input.length(); i++) {
            if ((input.charAt(i) == '.')||(input.charAt(i) == '/')) {
                changeValue(data, position);
                data = "";
                position++;
            }else {
                data += input.charAt(i);
                if ((i+1)==input.length()) changeValue(data, position);
            }
        }
    }

    private void changeValue(String date, int position) {
        try {
            octets[position] = Integer.parseInt(date);
        }catch (NumberFormatException e) {
            error[position] = "1";
        }
    }


    private void validateDataAddressMask() {
        for (int i = 0; i < error.length; i++) {
            if ((error[i] != "1")&&(i != (error.length-1))){
                if (i == (error.length-2)) {
                    if (!((octets[i] >=0) && (octets[i] <=32)))error[i] = "1";
                }else {
                    if (!((octets[i] >=0) && (octets[i] <=255)))error[i] = "1";
                }
            }
        }
    }
    //otro tipo de entrada
    public void setInputAddress(String input) {
        octets = new Integer[]{0,0,0,0,0};
        error = new String[]{"0","0","0","0","0","0"};
        this.input = input;
        validateInputAddress();
    }

    private void validateInputAddress() {
        if (validateFormatAddress()) {
            updateOctetsAddress();
            validateDataAddress();
        }else {
            error[5] = "1";
        }
    }

    private boolean validateFormatAddress() {
        boolean valid = true;
        int size = 0;
        int number_points  = 0;
        if ((!input.isEmpty())&&(input.length() <= 18)) {
            for (int i = 0; i < input.length(); i++) {
                if ((input.charAt(i) == '.')){
                    number_points++;
                    if ((size == 0) ||((i+1) == input.length())){
                        valid = false;
                        i = input.length();
                    }else{
                        size = 0;
                    }
                }else {
                    size++;
                }
            }
        }else {
            valid = false ;
        }
        return valid && (number_points == 3);
    }

    private void updateOctetsAddress() {
        String data = "";
        int position = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '.') {
                changeValue(data, position);
                data = "";
                position++;
            }else {
                data += input.charAt(i);
                if ((i+1)==input.length()) changeValue(data, position);
            }
        }
    }

    private void validateDataAddress() {
        for (int i = 0; i < (error.length - 1); i++) {
            if (error[i] == "0"){
                if (!((octets[i] >=0) && (octets[i] <=255)))error[i] = "1";
            }
        }
    }

    //validate
    public boolean isValid_input() {
        boolean valid = true;
        for (int i = 0; i < error.length; i++) {
            if (error[i] == "1"){
                valid = false;
            }
        }
        return valid;
    }

    public String getErrorMessage() {
        String error_message = "";
        for (int i = 0; i < error.length; i++) {
            if (error[i].equals("1")) {
                error_message += getError(i)+"\n";
                error[i] = "0";
            }
        }
        return error_message;
    }

    private String getError(int error_position) {
        String error = "";
        switch (error_position){
            case 0:
                error = context.getResources().getString(R.string.first_invalid);
                break;
            case 1:
                error = context.getResources().getString(R.string.second_invalid);
                break;
            case 2:
                error = context.getResources().getString(R.string.third_invalid);
                break;
            case 3:
                error = context.getResources().getString(R.string.fourth_invalid);
                break;
            case 4:
                error = context.getResources().getString(R.string.fifth_invalid);
                break;
            case 5:
                error = context.getResources().getString(R.string.ip_invalid);
                break;
        }
        return error;
    }

    public Integer[] getOctets() {
        return octets;
    }


}
