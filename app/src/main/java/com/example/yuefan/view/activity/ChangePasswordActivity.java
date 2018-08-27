package com.example.yuefan.view.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestPasswordResetCallback;
import com.example.yuefan.R;


//修改密码
public class ChangePasswordActivity extends AppCompatActivity {

    Button button;
    TextInputEditText textInputEditText1;
    TextInputEditText textInputEditText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        textInputEditText1=findViewById(R.id.change_first);
        textInputEditText2=findViewById(R.id.change_second);
        button=findViewById(R.id.change_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textInputEditText1.getText().toString().equals(textInputEditText2.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"前后输入不相同",Toast.LENGTH_SHORT).show();
                }
                else {
                    AVUser.requestPasswordResetInBackground(textInputEditText2.getText().toString(), new RequestPasswordResetCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(),"请去邮箱查看邮件",Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
