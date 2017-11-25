package com.sumit.safarichat.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.sumit.safarichat.R;
import com.sumit.safarichat.utils.AbsRuntimeMarshmallowPermission;
import com.sumit.safarichat.utils.MessageUtility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class InitialOpenActivity extends AbsRuntimeMarshmallowPermission {
    Activity mActivity;
    Context mContext;
    private final int LOGIN_SIGNUP_ACT_REQ_CODE=101;
    public  final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_open);
        mActivity=this;
        mContext=getBaseContext();

        requestAppPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.VIBRATE,Manifest.permission.ACCESS_NETWORK_STATE}, R.string.message, ALL_PERMISSIONS);

        printHashKey(getApplicationContext());
    }

    @Override
    public void onPermissionGranted(int requestCode) {


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(mActivity, LoginActivity.class);
            startActivity(intent);
        }
    }
    public  void printHashKey(Context pContext) {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
               MessageUtility.showLog("", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("", "printHashKey()", e);
        }
    }
}
