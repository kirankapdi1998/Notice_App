package com.example.noticeapp.model;

import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    public Validate() {
    }

    public boolean isValidString(EditText text){
        String charPattern = "^[a-zA-Z]*$";

        String charP = text.getText().toString().trim();
        if(charP.matches(charPattern) && charP.length()>0){
            return true;
        }
        text.setError("Character's only !!");
        text.requestFocus();
        return false;
    }

    public boolean isValidEmail(EditText text){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String email = text.getText().toString().trim();
        if(email.matches(emailPattern) && email.length()>0){
            return true;
        }
        text.setError("Invalid Email");
        text.requestFocus();
        return false;
    }

    public  boolean isValidPassword(EditText pass) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String passwd = pass.getText().toString().trim();
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(passwd);
        if(passwd.length() < 8){
            pass.setError("Password must contain atleast 8 characters");
            pass.requestFocus();
            return false;
        }
        if(passwd.length() > 20){
            pass.setError("Password must not exceeds 20 characters");
            pass.requestFocus();
            return false;
        }

        if(!(matcher.matches())){
            pass.setError("Password must contain at least 1 Alphabet, 1 Number and 1 Special Character");
            pass.requestFocus();
            return false;
        }

        return true;

    }


    public boolean isValidString(EditText text, TextInputLayout error){
        String charPattern = "^[a-zA-Z]*$";
        Log.v("track", "inside valid");
//        String charP = text.getText().toString().trim();
        if(text.getText().toString().matches(charPattern) && text.getText().length()>0){
            return true;
        }
        error.setError("Character's only !!");
        Log.v("track", "inside valid");
        error.requestFocus();
        return false;
    }

    public boolean isValidEmail(EditText text, TextInputLayout error){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        String email = text.getText().toString().trim();
        if(email.matches(emailPattern) && email.length()>0){
            return true;
        }
        error.setError("Invalid Email");
        error.requestFocus();
        return false;
    }


    public  boolean isValidPassword(EditText pass, TextInputLayout error) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String passwd = pass.getText().toString().trim();
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(passwd);
        if(passwd.length() < 8){
            error.setError("Password must contain atleast 8 characters");
            error.requestFocus();
            return false;
        }
        if(passwd.length() > 20){
            error.setError("Password must not exceeds 20 characters");
            error.requestFocus();
            return false;
        }

        if(!(matcher.matches())){
            error.setError("Password must contain at least 1 Alphabet, 1 Number and 1 Special Character");
            error.requestFocus();
            return false;
        }

        return true;

    }

    public  boolean isValidCPassword(EditText pass, EditText cpass, TextInputLayout error) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        String passwd = pass.getText().toString().trim();
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(passwd);
        if(passwd.length() < 8){
            error.setError("Password must contain atleast 8 characters");
            error.requestFocus();
            return false;
        }
        if(passwd.length() > 20){
            error.setError("Password must not exceeds 20 characters");
            error.requestFocus();
            return false;
        }

        if(!(matcher.matches())){
            error.setError("Password must contain at least 1 Alphabet, 1 Number and 1 Special Character");
            error.requestFocus();
            return false;
        }

        if (!pass.getText().toString().equals(cpass.getText().toString())){
            error.setError("Password must match!");
            error.requestFocus();
            return false;
        }
        return true;

    }
    public boolean isValidNumber(EditText phone, TextInputLayout error){
        String num = phone.getText().toString().trim();
        if(TextUtils.isEmpty(num)){
            error.requestFocus();
            error.setError("Field Required");
            return false;
        }
        if( !(num.matches("[0-9]{1,7}"))){
            error.requestFocus();
            error.setError("Invalid Roll Number(Max 7 digit)");
            return false;
        }

        return true;
    }
}
