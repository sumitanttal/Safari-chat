package com.sumit.safarichat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;


import com.sumit.safarichat.R;
import com.sumit.safarichat.databinding.ActivityLoginSignupBinding;

public class Login_Signup_Activity extends ParentActivity {
    ActivityLoginSignupBinding mBinding;
    Activity   mActivity;
    Context     mContext;
    private final int LOGIN_ACT_REQ_CODE=101;
    private final int SIGNUP_ACT_REQ_CODE=102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity=this;
        mContext=getApplicationContext();
        mBinding= DataBindingUtil.setContentView(mActivity, R.layout.activity_login_signup);

    }

    @Override
    public void onTransfer(int code) {

    }

    public void actionListener(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.login:
                intent=new Intent(mActivity,LoginActivity.class);
                super.setOnActivityTrasfer(intent,LOGIN_ACT_REQ_CODE);
                break;
            case R.id.signup:
                intent=new Intent(mActivity,Signup_Activity.class);
                super.setOnActivityTrasfer(intent,SIGNUP_ACT_REQ_CODE);
                break;
        }
    }
}
