package com.example.yuefan.view.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnonymousUtils;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.yuefan.R;
import com.example.yuefan.presenter.ILoginPresenter;
import com.example.yuefan.presenter.LoginPresenter;
import com.unstoppable.submitbuttonview.SubmitButton;

public class LoginActivity extends AppCompatActivity implements ILoginActivity,View.OnClickListener{

    SubmitButton submitButton;
    TextInputEditText textInputEditText;
    TextInputEditText textInputEditText1;
    ILoginPresenter iLoginPresenter;
    TextView textView;
    TextView textView1;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        submitButton=findViewById(R.id.login_button);
       textInputEditText=findViewById(R.id.login_zhang);
       view=textInputEditText;
       iLoginPresenter=new LoginPresenter(this);
         textInputEditText1=findViewById(R.id.login_mi);
         textView=findViewById(R.id.login_niming);
         textView1=findViewById(R.id.login_register);
        textView.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                submitButton.reset();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textInputEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                submitButton.reset();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void Login() {
        String name=textInputEditText.getText().toString();
        String password=textInputEditText1.getText().toString();
        if (!name.equals("") && !password.equals("")) {
        iLoginPresenter.Login(name,password);
        } else {
            toast("用户名和密码不能为空");
            submitButton.doResult(false);
        }
    }

    private void toast(String content){
        Snackbar.make(view,content,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void isLogin(final boolean isOk) {
        submitButton.doResult(isOk);
        submitButton.setOnResultEndListener(new SubmitButton.OnResultEndListener() {
            @Override
            public void onResultEnd() {
                if (isOk==true)
                {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    toast("失败");
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login_niming:
                Snackbar.make(view,"正在为您创建匿名账号",Toast.LENGTH_LONG).show();
                AVAnonymousUtils.logIn(new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                break;
            case R.id.login_register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
              //  Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(this, android.support.v4.util.Pair.create(textView1, "zhuche"), android.support.v4.util.Pair.create(submitButton, "button")).toBundle();
                startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this,textView1,"zhuche").toBundle());
                break;
            case R.id.login_button:
                Login();
                break;
        }
    }
}
