package com.example.yuefan.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avos.avoscloud.AVUser;
import com.example.yuefan.R;


//
public class ChangeZhiLiaoActivity extends AppCompatActivity {

    Button button;
    EditText jianjie;
    EditText aihao;
    AVUser avUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_zhi_liao);
        avUser=AVUser.getCurrentUser();
        button=findViewById(R.id.change_button2);
        jianjie=findViewById(R.id.change_jianjie);
        aihao=findViewById(R.id.change_aihao);
        jianjie.setText(avUser.getString("jianjie"));
        aihao.setText(avUser.getString("aihao"));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 avUser=AVUser.getCurrentUser();
                if(jianjie.getText().toString()!=null)avUser.put("jianjie",jianjie.getText().toString());
                if (aihao.getText().toString()!=null)avUser.put("aihao",aihao.getText().toString());
                avUser.saveInBackground();
                Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
