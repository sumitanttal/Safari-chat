package com.sumit.safarichat.utils;

import android.content.Context;

import com.sumit.safarichat.R;


/**
 * Created by sumit on 30/10/17.
 */

public class Validator {
    static  String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean isValidfields(Context mContext, String username, String password){
        if(username.isEmpty()){
          //  MessageUtility.showToast(activity,mContext.getResources().getString(R.string.error_empty_email));
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_username));
            return false;
        }

        else if(password.isEmpty()){
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_password));
            return false;
        }
        else if(password.length()<6){
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_invalid_password));
            return false;
        }
        else{
            return true;
        }



    }

    public static boolean isValidfields(Context mContext, String firstname, String lastname, String phone) {
         if(firstname.isEmpty()){
            //  MessageUtility.showToast(activity,mContext.getResources().getString(R.string.error_empty_email));
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_firstname));
            return false;
        }
        else if(lastname.isEmpty()){
            //  MessageUtility.showToast(activity,mContext.getResources().getString(R.string.error_empty_email));
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_lastname));
            return false;
        }
         else if(phone.isEmpty()){
             MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_phone));
             return false;
         }
        else{
            return true;
        }
    }

    public static boolean isValidfields(Context mContext, String username, String password, String fname, String lname, String country, String phone) {
        if(username.isEmpty()){
            //  MessageUtility.showToast(activity,mContext.getResources().getString(R.string.error_empty_email));
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_username));
            return false;
        }

        else if(password.isEmpty()){
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_password));
            return false;
        }
        else if(password.length()<6){
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_invalid_password));
            return false;
        }
        else if(fname.isEmpty()){
            //  MessageUtility.showToast(activity,mContext.getResources().getString(R.string.error_empty_email));
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_firstname));
            return false;
        }
        else if(lname.isEmpty()){
            //  MessageUtility.showToast(activity,mContext.getResources().getString(R.string.error_empty_email));
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_lastname));
            return false;
        }

        else if(country.isEmpty()){
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_country));
            return false;
        }
        else if(phone.isEmpty()){
            MessageUtility.showSnackBar(mContext, mContext.getResources().getString(R.string.error_empty_phone));
            return false;
        }
        else{
            return true;
        }


    }
}
