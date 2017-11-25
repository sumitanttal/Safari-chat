package com.sumit.safarichat.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sumit.safarichat.appcontroller.AppController;
import com.sumit.safarichat.interfaces.CallBackRequestListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 6/10/2017.
 */

public class VolleyRequest {

    public static void makePostRequest(int method_type,final Activity activity, final HashMap<String,String> table, final String url, final CallBackRequestListener callBackRequestListener){
        // Tag used to cancel the request

        String tag_string_req = "string_req";

        final ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setMessage("Loading...");
        if(pDialog.isShowing())
        pDialog.dismiss();
            pDialog.show();
          //  String temp="http://bidmeapp.com/webservices/ver1/login_bouncer.php?log_email=sumitanttal64@gmail.com&log_pswd=8fJHTEFm";
        StringRequest strReq = new StringRequest(method_type,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                MessageUtility.showLog("on response","on response");
                MessageUtility.showLog("on response","on response"+response);
                callBackRequestListener.onSuccess(response);
                pDialog.dismiss();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                MessageUtility.showLog("on error","on error");
                MessageUtility.showLog("on error","on error"+error.toString());
                callBackRequestListener.onError(error.toString());
                pDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                return table;
            }

        };


        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }
    public static void makeFCmRequest(int method_type, final Context activity, final HashMap<String,String> table, final String url, final CallBackRequestListener callBackRequestListener){
        // Tag used to cancel the request

        String tag_string_req = "string_req";


        //  String temp="http://bidmeapp.com/webservices/ver1/login_bouncer.php?log_email=sumitanttal64@gmail.com&log_pswd=8fJHTEFm";
        StringRequest strReq = new StringRequest(method_type,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                return table;
            }

        };


        strReq.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


    }




}
