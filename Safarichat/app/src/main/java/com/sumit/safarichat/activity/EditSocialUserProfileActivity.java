package com.sumit.safarichat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;
import com.sumit.safarichat.R;
import com.sumit.safarichat.constants.ApiConstants;
import com.sumit.safarichat.databinding.ActivityEditSocialUserProfileBinding;
import com.sumit.safarichat.interfaces.CallBackRequestListener;
import com.sumit.safarichat.utils.MessageUtility;
import com.sumit.safarichat.utils.Validator;
import com.sumit.safarichat.utils.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditSocialUserProfileActivity extends ParentActivity {
    ActivityEditSocialUserProfileBinding mBinding;
    Activity mActivity;
    Context mContext;
    String user_id;
    private final int CAMERA_ACT_REQ_CODE=101;
    TextInputLayout firstname_layout,lastname_layout,phone_layout;
    String  str_country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity=this;
        mContext=getBaseContext();
        mBinding= DataBindingUtil.setContentView(mActivity, R.layout.activity_edit_social_user_profile);
      user_id=  getIntent().getBundleExtra("bundle").getString("user_id");
        init_Views();


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String currentCountryISO = telephonyManager.getSimCountryIso().toUpperCase();
        mBinding.ccp.setDefaultCountryUsingNameCode(currentCountryISO);
        mBinding.ccp.resetToDefaultCountry();
        str_country =  mBinding.ccp.getSelectedCountryName();
        MessageUtility.showLog( "country name", mBinding.ccp.getDefaultCountryName());
      /*  str_country_code="+"+registerActivity.ccp.getDefaultCountryCode();*/
        MessageUtility.showLog("country code", mBinding.ccp.getDefaultCountryCode());
        mBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                MessageUtility.showLog( "country code",  mBinding.ccp.getSelectedCountryName());
                str_country = mBinding.ccp.getSelectedCountryName();

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


        phone_layout= findViewById(R.id.lay_phone).findViewById(R.id.text_input_layout);
        phone_layout.setHint(getResources().getString(R.string.prompt_mobile));

     /*   lay_country= findViewById(R.id.lay_country).findViewById(R.id.text_input_layout);
        lay_country.setHint(getResources().getString(R.string.prompt_country));*/


    }
    public void actionSaveProfile(View view){

        EditText firstname_vw=firstname_layout.findViewById(R.id.edittext);
        EditText lastname_vw=lastname_layout.findViewById(R.id.edittext);
        EditText phone_vw=phone_layout.findViewById(R.id.edittext);
      //  EditText country_vw=lay_country.findViewById(R.id.edittext);
        String str_firstname=firstname_vw.getText().toString().trim();
        String str_lastname=lastname_vw.getText().toString().trim();
        String str_phone=phone_vw.getText().toString().trim();
       // String str_country=country_vw.getText().toString().trim();
        if(Validator.isValidfields(mActivity,str_firstname,str_lastname,str_phone)){

            makeSocialProfileSaveReq(str_firstname,str_lastname,str_phone,str_country);
        }
    }
    private void makeSocialProfileSaveReq( String str_firstname, String str_lastname, String str_phone,String str_country) {

        HashMap<String, String> nMap=new HashMap<>();
        JSONObject reader=new JSONObject();
        try {
            reader.put("result",true);
            reader.put("user_id",user_id);
            reader.put("first_name",str_firstname);
            reader.put("last_name",str_firstname);
            reader.put("last_name",str_lastname);
            reader.put("phone",str_phone);
            reader.put("country",str_country);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        nMap.put("user_details", reader.toString());


        MessageUtility.showLog("map", nMap + "");
        //getUrl(params);
        VolleyRequest.makePostRequest(ApiConstants.METHOD_POST,mActivity, nMap, ApiConstants.ACTION_EDIT_PROFILE, new CallBackRequestListener() {
            @Override
            public void onSuccess(String result) {
                MessageUtility.showLog("success", result);
                try {
                    JSONObject reader=new JSONObject(result);
                    int status=reader.getInt("status");
                    String message=reader.getString("message");
                    if(status==1) {

                        Intent intent = new Intent(mActivity, CameraActivity.class);
                        setOnActivityTrasfer(intent, CAMERA_ACT_REQ_CODE);
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
