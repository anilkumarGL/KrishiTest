package entekrishi.com.krishitest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.provider.Settings.Secure;

import java.text.NumberFormat;
import java.util.Locale;

import entekrishi.com.krishitest.model.Data;
import entekrishi.com.krishitest.model.ModelClassMapper;
import entekrishi.com.krishitest.model.LoginRsp;
import entekrishi.com.krishitest.Rest.*;
import entekrishi.com.krishitest.common.*;

public class MainActivity extends ActionBarActivity implements View.OnClickListener, OnPostExecuteListener {
    private Button btnLogin;
    private EditText txtUserName;
    private  EditText txtPwd;
    private String deviceid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String datevalues= (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date());

        btnLogin = (Button) findViewById(R.id.button_login) ;
        txtUserName = (EditText) findViewById(R.id.username) ;
        txtUserName.setText("kirankrishnan@axtecindia.com");
        txtPwd = (EditText) findViewById(R.id.password) ;
        txtPwd.setText("kiran123");
        findViewById(R.id.button_login).setOnClickListener(this);
        //getting unique id for device
        deviceid = Secure.getString(getContentResolver(), Secure.ANDROID_ID);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                login();
                break;
        }
    }

    private void login() {
        if (NetworkListener.isNetworkConnected(getApplicationContext())) {
            RestApi api = new RestApi(this);
            api.setMessage("Authenticating...");
            api.setPostExecuteListener(this);
            String urlCall = Utils.BASE_URL + Utils.LOGIN_URL+ "?username="+ txtUserName.getText()+"&password="+txtPwd.getText()+"&device="+deviceid;
            api.get(urlCall, Utils.LOGIN_URL);
        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
        }
    }

    @Override
    public void onSuccess(ModelClassMapper model) {
        LoginRsp response = (LoginRsp)model;
        if (response.msg.toString().equalsIgnoreCase(Utils.LOGIN_SUCCESS)) {
            // take token;
            Data.getInstance().setToken(response.token);
            startActivity(new Intent(this, HomeTab.class));
        } else {
            Utils.showInfoDialog(this, Utils.MSG_TITLE, response.msg, null);
        }
    }

    @Override
    public void onFailure() {
        Utils.showInfoDialog(this, Utils.MSG_TITLE, Utils.MSG_NO_INTERNET, null);
    }
}