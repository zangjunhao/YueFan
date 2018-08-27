package com.example.yuefan.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.github.chrisbanes.photoview.PhotoView;

public class PhotoActivity extends AppCompatActivity {

    String imageurl;
    PhotoView photoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_photo);
       photoView=findViewById(R.id.photo);
       Intent intent =getIntent();
       imageurl=intent.getStringExtra("imageurl");
        Glide.with(this).load(imageurl).into(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            onBackPressed();
            }
        });
    }
}
