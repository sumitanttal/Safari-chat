package com.sumit.safarichat.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.sumit.safarichat.R;
import com.sumit.safarichat.constants.ApiConstants;
import com.sumit.safarichat.databinding.ActivityLoginBinding;
import com.sumit.safarichat.interfaces.CallBackRequestListener;
import com.sumit.safarichat.utils.MessageUtility;
import com.sumit.safarichat.utils.Validator;
import com.sumit.safarichat.utils.VolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

public class LoginActivity extends ParentActivity {
    ActivityLoginBinding mBinding;
    Activity mActivity;
    Context mContext;
    private final int CAMERA_ACT_REQ_CODE=103;
    private final int EDIT_SOCIAL_USER_ACT_REQ_CODE=103;
    CallbackManager callbackManager;
    TextInputLayout email_layout,pas_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=this;
        mContext=getBaseContext();
        mBinding= DataBindingUtil.setContentView(mActivity, R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        initFacebookSdk();
        init_Views();
    }

    private void initFacebookSdk() {
        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");
                        requestUserProfile(loginResult);
                        // Profile profile = Profile.getCurrentProfile();
                        //displayMessage(profile);

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    public void actionBack(View view){
        finish();
    }
    public void requestUserProfile(LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject me, GraphResponse response) {
                        if (response.getError() != null) {
                            // handle error
                        } else {
                            MessageUtility.showLog("login response", me.toString());
                            String email = me.optString("email");
                            Log.e("Result", email);
                            String id = me.optString("id");
                            // send email and id to your web server
                            Log.e("Result1", response.getRawResponse());
                            Log.e("Result", me.toString());
                            String json = me.toString();

                            //fragmentTransaction(RegisterFragments.getInstance(),R.id.fragmentsContainer,bundle);
                            String email_str = me.optString("email");

                            String name_str = me.optString("name");
                            //  MySharedPreferences.getInstance().saveSignUpPref(mContext,name_str,email_str,"","", globalConstant.socialUser);
                            //  setOnActivityTransfer(SignUpActivitySecondStep.class,SIGNUP_ACT_SECOND_STEP_REQ_CODE,Login_Activity.this);
                          //  makeLoginReq("", "", ApiConstants.LOGIN_TYPE_SOCIAL);

                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,birthday,first_name,gender,last_name,location,email,picture.type(large)");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }
    private void init_Views() {

         email_layout= findViewById(R.id.lay_username).findViewById(R.id.text_input_layout);
        email_layout.setHint(getResources().getString(R.string.prompt_username));

         pas_layout= findViewById(R.id.lay_password).findViewById(R.id.text_input_layout);
        pas_layout.setHint(getResources().getString(R.string.prompt_password));
        EditText et_password=findViewById(R.id.lay_password).findViewById(R.id.edittext);
        et_password.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
        et_password.setSelection(et_password.getText().length());

    }
    @Override
    public void onTransfer(int code) {

    }
    private void makeLoginReq(String str_email, String str_pass,int social_id,String login_type,String device_type) {

        HashMap<String, String> nMap=new HashMap<>();
        JSONObject reader=new JSONObject();
        try {
            reader.put("result",true);
            reader.put("social_id",social_id);
            reader.put("username",str_email);
            reader.put("password",str_pass);
            reader.put("registration_type",device_type);
            reader.put("login_type",login_type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        nMap.put("login_details", reader.toString());


        MessageUtility.showLog("map", nMap + "");
        //getUrl(params);
        VolleyRequest.makePostRequest(ApiConstants.METHOD_POST,mActivity, nMap, ApiConstants.ACTION_LOGIN, new CallBackRequestListener() {
            @Override
            public void onSuccess(String result) {
                MessageUtility.showLog("success", result);
                try {
                    JSONObject reader=new JSONObject(result);
                    int status=reader.getInt("status");
                    String message=reader.getString("message");
                    if(status==1) {
                        String user_id=reader.getString("user_id");
                        String username=reader.getString("username");
                        String first_name=reader.getString("first_name");
                        String last_name=reader.getString("last_name");
                        String phone=reader.getString("phone");
                        String country=reader.getString("country");

                      /*  SharedPreference userPref=SharedPreference.getInstance();
                        userPref.saveUser(mContext,firstname,last_name,"",userid,emailaddress,jobtitle,company,country);
                        Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                        LoginActivity.super.setOnActivityTrasfer(intent,HOME_ACT_REQ_CODE);*/
                        if(first_name.isEmpty()  || phone.isEmpty()){
                            Intent intent = new Intent(mActivity, EditSocialUserProfileActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("user_id",user_id);
                            intent.putExtra("bundle",bundle);
                            setOnActivityTrasfer(intent, EDIT_SOCIAL_USER_ACT_REQ_CODE);
                        }else {
                            Intent intent = new Intent(mActivity, CameraActivity.class);
                            setOnActivityTrasfer(intent, CAMERA_ACT_REQ_CODE);
                        }
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
    public void actionLogin(View view){
        MessageUtility.showLog("login","clicked");
        EditText username_vw=email_layout.findViewById(R.id.edittext);
        EditText pass_vw=pas_layout.findViewById(R.id.edittext);
        String str_username=username_vw.getText().toString().trim();
        String str_pass=pass_vw.getText().toString().trim();
        if(Validator.isValidfields(mActivity,str_username,str_pass)){
            makeLoginReq(str_username,str_pass,0,ApiConstants.LOGIN_TYPE_MANNUAL,ApiConstants.DEVICE_TYPE_ANDROID);
        }

    }
    public void actionListener(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.facebook_login:
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));
                break;

        }
    }

}
