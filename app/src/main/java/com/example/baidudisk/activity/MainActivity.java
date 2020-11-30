package com.example.baidudisk.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.baidudisk.R;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
EditText mUserEt,mPswEt;
Button mLoginBtn;
private ImageButton mPswVisibleBtn;
private boolean mPswVisible=false;
private static final String SP_IS_LOGIN="is_login";
private static final String USER="user";
private static final String PSW="psw";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarFullTransparent();
        mPswVisibleBtn=findViewById(R.id.ib_login_psw_visible);
        mLoginBtn=findViewById(R.id.btn_login);
        mUserEt=findViewById(R.id.et_login_user);
        mPswEt=findViewById(R.id.et_login_psw);
        setPswVisible();
        mUserEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
             setBtnBg(s);
            }
        });
        mPswEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setBtnBg(s);
            }
        });
        mLoginBtn.setOnClickListener(this);
        mPswVisibleBtn.setOnClickListener(this);
    }

    private void setPswVisible() {
        mPswVisibleBtn.setBackgroundResource(mPswVisible?R.mipmap.open:R.mipmap.hide);
        mPswEt.setTransformationMethod(mPswVisible? HideReturnsTransformationMethod.getInstance(): PasswordTransformationMethod.getInstance());
        mPswEt.setSelection(mPswEt.getText().toString().length());
        mPswVisible=!mPswVisible;
    }

    private void setBtnBg(Editable s) {
        if(mUserEt.getText().length()>0&&mPswEt.getText().length()>0)
        {
            mLoginBtn.setEnabled(true);
            mLoginBtn.setBackgroundColor(getResources().getColor(R.color.colorBtnEnable));
            mLoginBtn.setTextColor(Color.WHITE);
        }
        else
        {
            mLoginBtn.setEnabled(false);
            mLoginBtn.setBackgroundColor(getResources().getColor(R.color.colorBtnDissablr));
            mLoginBtn.setTextColor(getResources().getColor(R.color.colorTextDisable));
        }
    }

    //状态栏透明
    protected void setStatusBarFullTransparent() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            Window window=getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_login:
                if("swust".equals(mPswEt.getText().toString().trim())&&"swust".equals(mUserEt.getText().toString().trim()))
            {
                saveUserNameAndPsw();
                Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra("str1",mUserEt.getText().toString());
                startActivity(intent);
            }else
                    Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_login_psw_visible:
                setPswVisible();
                break;
        }
    }

    /**
     * 保存用户名和密码
     */
    private void saveUserNameAndPsw(){
        getSharedPreferences(SP_IS_LOGIN, Context.MODE_PRIVATE)
                .edit()
                .putString(USER,mUserEt.getText().toString())
                .putString(PSW,mPswEt.getText().toString())
                .apply();
    }
    /**
     * 填充用户名和密码
     */
    private void fillUserNameAndPsw(){
        String user= getSharedPreferences(SP_IS_LOGIN,MODE_PRIVATE)
                .getString(USER,"");
        String psw =getSharedPreferences(SP_IS_LOGIN,MODE_PRIVATE)
                .getString(PSW,"");
        mUserEt.setText(user);
        mPswEt.setText(psw);
        mUserEt.setSelection(mUserEt.getText().length());
    }
    @Override
    protected void onStart() {
        super.onStart();
        fillUserNameAndPsw();
    }
}