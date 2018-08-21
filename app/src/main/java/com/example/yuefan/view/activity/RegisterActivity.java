package com.example.yuefan.view.activity;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.yuefan.R;
import com.example.yuefan.presenter.IRegisterPresenter;
import com.example.yuefan.presenter.RegisterPresenter;
import com.unstoppable.submitbuttonview.SubmitButton;

public class RegisterActivity extends AppCompatActivity implements IRegisterActivity,TextWatcher {

    SubmitButton submitButton;
    TextInputEditText textInputEditText;
    TextInputEditText textInputEditText2;
    TextInputEditText textInputEditText3;
    TextInputEditText textInputEditText4;
    IRegisterPresenter registerPresenter;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        submitButton=findViewById(R.id.register_button);
        view=submitButton;
        textInputEditText=findViewById(R.id.register_zhang);
        textInputEditText2=findViewById(R.id.register_mi);
        textInputEditText3=findViewById(R.id.register_mi_2);
        textInputEditText4=findViewById(R.id.register_email_);
        registerPresenter=new RegisterPresenter(this);
        textInputEditText.addTextChangedListener(this);
        textInputEditText2.addTextChangedListener(this);
        textInputEditText3.addTextChangedListener(this);
        textInputEditText4.addTextChangedListener(this);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void Toast(String content)
    {
        Snackbar.make(view,content, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void register() {
        String usename=textInputEditText.getText().toString();
        String firstPassword=textInputEditText2.getText().toString();
        String secondPassword=textInputEditText3.getText().toString();
        String email=textInputEditText4.getText().toString();
        if(!firstPassword.equals(secondPassword))
        {
            Toast("前后输入密码不相同");
        }
        else if(usename.equals("")||firstPassword.equals("")||secondPassword.equals(""))
        {
            Toast("不能为空");
        }
        else {
            registerPresenter.register(usename,secondPassword,email);
        }
    }

    @Override
    public void Success() {
            submitButton.doResult(true);
            finish();
    }

    @Override
    public void error() {
        submitButton.doResult(false);
        Toast("账号已存在");
    }

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
}
