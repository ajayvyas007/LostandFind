package com.example.ajay.lostandfind.library;

/**
 * Created by ajay on 8/3/17.
 */

public class Utility {
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidLenght(String input,int lenght){

        return input.length() <= lenght ;
    }
}
