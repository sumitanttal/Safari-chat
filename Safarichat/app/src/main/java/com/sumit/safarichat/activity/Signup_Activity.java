package com.sumit.safarichat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.telephony.TelephonyManager;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.sumit.safarichat.R;
import com.sumit.safarichat.constants.ApiConstants;
import com.sumit.safarichat.databinding.ActivitySignupBinding;
import com.sumit.safarichat.interfaces.CallBackRequestListener;
import com.sumit.safarichat.utils.MessageUtility;
import com.sumit.safarichat.utils.Validator;
import com.sumit.safarichat.utils.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Signup_Activity extends ParentActivity {
    Activity mActivity;
    Context mContext;
    ActivitySignupBinding mBinding;
    public final int LOGIN_ACT_REQ_CODE=101;
    String  str_country;
    TextInputLayout firstname_layout,lastname_layout,phone_layout,email_layout,pas_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        mContext=getBaseContext();
        mBinding= DataBindingUtil.setContentView(mActivity, R.layout.activity_signup);

        init_Views();

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String currentCountryISO = telephonyManager.getSimCountryIso().toUpperCase();
        mBinding.ccp.setDefaultCountryUsingNameCode(currentCountryISO);
        mBinding.ccp.resetToDefaultCountry();
        str_country = mBinding.ccp.getSelectedCountryName();
        MessageUtility.showLog( "country name", mBinding.ccp.getDefaultCountryName());
      /*  str_country_code="+"+registerActivity.ccp.getDefaultCountryCode();*/
        MessageUtility.showLog("country code", mBinding.ccp.getDefaultCountryCode());
        mBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                MessageUtility.showLog( "country code",  mBinding.ccp.getSelectedCountryName());
                str_country =  mBinding.ccp.getSelectedCountryName();

            }
        });
    }

    @Override
    public void onTransfer(int code) {

    }
    private void init_Views() {

         firstname_layout= findViewById(R.id.lay_firstname).findViewById(R.id.text_input_layout);
        firstname_layout.setHint(getResources().getString(R.string.prompt_firstname));

         lastname_layout= findViewById(R.id.lay_lastname).findViewById(R.id.text_input_layout);
        lastname_layout.setHint(getResources().getString(R.string.prompt_lastname));

        /* country_layout= findViewById(R.id.lay_country).findViewById(R.id.text_input_layout);
        country_layout.setHint(getResources().getString(R.string.prompt_country));*/

         phone_layout= findViewById(R.id.lay_phone).findViewById(R.id.text_input_layout);
        phone_layout.setHint(getResources().getString(R.string.prompt_mobile));

         email_layout= findViewById(R.id.lay_username).findViewById(R.id.text_input_layout);
        email_layout.setHint(getResources().getString(R.string.prompt_username));

         pas_layout= findViewById(R.id.lay_password).findViewById(R.id.text_input_layout);
        pas_layout.setHint(getResources().getString(R.string.prompt_password));
        EditText et_password=findViewById(R.id.lay_password).findViewById(R.id.edittext);
        et_password.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_password.setSelection(et_password.getText().length());

    }
    public void actionBack(View view){
        finish();
    }
    public void actionSignup(View view){
        EditText username_vw=email_layout.findViewById(R.id.edittext);
        EditText pass_vw=pas_layout.findViewById(R.id.edittext);
        EditText firstname_vw=firstname_layout.findViewById(R.id.edittext);
        EditText lastname_vw=lastname_layout.findViewById(R.id.edittext);
       // EditText country_vw=country_layout.findViewById(R.id.edittext);
        EditText phone_vw=phone_layout.findViewById(R.id.edittext);
        String str_username=username_vw.getText().toString().trim();
        String str_pass=pass_vw.getText().toString().trim();
        String str_firstname=firstname_vw.getText().toString().trim();
        String str_lastname=lastname_vw.getText().toString().trim();
      //  String str_country=country_vw.getText().toString().trim();
        String str_phone=phone_vw.getText().toString().trim();
        if(Validator.isValidfields(mActivity,str_username,str_pass,str_firstname,str_lastname,str_country,str_phone)){

                makeSignupReq(str_username,str_pass,str_firstname,str_lastname,str_country,str_phone);
        }
    }

    private void makeSignupReq(String str_username, String str_pass, String str_firstname, String str_lastname, String str_country, String str_phone) {

        HashMap<String, String> nMap=new HashMap<>();
        JSONObject reader=new JSONObject();
        try {
            reader.put("result",true);
            reader.put("username",str_username);
            reader.put("password",str_pass);
            reader.put("first_name",str_firstname);
            reader.put("last_name",str_lastname);
            reader.put("phone",str_phone);
            reader.put("country",str_country);
            reader.put("registration_type",ApiConstants.DEVICE_TYPE_ANDROID);
            reader.put("device_token","s6d54f5sd4f564sd65f465sd4f654sd6f54");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nMap.put("user_details", reader.toString());


        MessageUtility.showLog("map", nMap + "");
        //getUrl(params);
        VolleyRequest.makePostRequest(ApiConstants.METHOD_POST,mActivity, nMap, ApiConstants.ACTION_REGISTER, new CallBackRequestListener() {
            @Override
            public void onSuccess(String result) {
                MessageUtility.showLog("success", result);
                try {
                    JSONObject reader=new JSONObject(result);
                    int status=reader.getInt("status");
                    String message=reader.getString("message");
                    if(status==1) {
                       /* String user_id=reader.getString("user_id");
                        String username=reader.getString("username");
                        String first_name=reader.getString("first_name");
                        String last_name=reader.getString("last_name");
                        String phone=reader.getString("phone");
                        String country=reader.getString("country");*/

                      /*  SharedPreference userPref=SharedPreference.getInstance();
                        userPref.saveUser(mContext,firstname,last_name,"",userid,emailaddress,jobtitle,company,country);
                        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                        LoginActivity.super.setOnActivityTrasfer(intent,HOME_ACT_REQ_CODE);*/
                       /* if(first_name.isEmpty()  || phone.isEmpty()){
                            Intent intent = new Intent(mActivity, EditSocialUserProfileActivity.class);
                            setOnActivityTrasfer(intent, EDIT_SOCIAL_USER_ACT_REQ_CODE);
                        }else {
                            Intent intent = new Intent(mActivity, CameraPreview.class);
                            setOnActivityTrasfer(intent, CAMERA_ACT_REQ_CODE);
                        }*/
                        Intent intent = new Intent(mActivity, LoginActivity.class);
                        setOnActivityTrasfer(intent, LOGIN_ACT_REQ_CODE);
                    }
                    else{
                        MessageUtility.showSnackBar(mActivity,message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {
                MessageUtility.showLog("error", error);
            }
        });

    }
}
