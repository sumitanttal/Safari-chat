package com.sumit.safarichat.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.sumit.safarichat.R;


/**
 * Created by Sumit on 6/10/2017.
 */

public class MessageUtility {
    public static void showToast(Activity activity, String message){
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show();
    }
    public static void showLog(String message_about, String message){
        Log.d("start",message_about+":::::::"+message);
    }
    public static void showSnackBar(Context context, String message) {

        try {
            MessageUtility.showLog( "layout:::","method called");
            LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.snackbar_layout, null);

            View id = view.findViewById(R.id.snackbar_action);
            MessageUtility.showLog( "layout:::",id + "");
            Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).setActionTextColor(Color.RED).show();
        } catch (Exception e) {
            MessageUtility.showLog( "exception:::",e.toString());
        }
    }
}
