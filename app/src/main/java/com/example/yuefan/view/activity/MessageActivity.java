package com.example.yuefan.view.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.bumptech.glide.Glide;
import com.example.yuefan.R;
import com.example.yuefan.tool.RealFilePath;
import com.example.yuefan.view.adapter.MessageAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessageActivity extends AppCompatActivity {


    String TAG="MessageActivity";
    List<String> contentlist=new ArrayList<>();
    List<Integer> Typelist=new ArrayList<>();
    public static final int leftText=0,rightText=1,leftPic=2,rightPic=3;
    RecyclerView recyclerView;
    MessageAdapter messageAdapter;
    EditText editText;
    ImageView imageView;
    Button button;
    String username;
    AVIMClient tom;
    String itTouxiang;
    AVIMConversation theconversation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode
                (WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_message);
        Intent intent=getIntent();
        username=intent.getStringExtra("username");
        itTouxiang=intent.getStringExtra("imageUrl");
        AVIMClient.setMessageQueryCacheEnable(true);
      tom = AVIMClient.getInstance(AVUser.getCurrentUser().getUsername());

        editText=findViewById(R.id.message_shuru);
        imageView=findViewById(R.id.message_photo);
        button=findViewById(R.id.message_fasong);
        recyclerView=findViewById(R.id.message_rec);
        messageAdapter=new MessageAdapter(this,contentlist,Typelist,itTouxiang);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
      sendMessageToJerryFromTom();
    }



    public void sendMessageToJerryFromTom() {
        String name=AVUser.getCurrentUser().getUsername()+username;

        char[] b=name.toCharArray();
        Arrays.sort(b);
        final String liaoTianName=String.copyValueOf(b);
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    client.createConversation(Arrays.asList(username),liaoTianName, null,false,true,
                            new AVIMConversationCreatedCallback() {
                                @Override
                                public void done(final AVIMConversation conversation, AVIMException e) {
                                    if (e == null) {
                                        theconversation=conversation;
                                        conversation.queryMessages(30, new AVIMMessagesQueryCallback() {
                                            @Override
                                            public void done(List<AVIMMessage> list, AVIMException e) {
                                                AVIMMessage  avimMessage;
                                                Log.d(TAG, "done: messagesize"+list.size());
                                                for(int i=0;i<list.size();i++)
                                                {
                                                   avimMessage=list.get(i);
                                                   if(avimMessage instanceof AVIMTextMessage)
                                                   {
                                                       if(avimMessage.getFrom().equals(AVUser.getCurrentUser().getUsername()))
                                                       {
                                                           contentlist.add(((AVIMTextMessage) avimMessage).getText());
                                                           Typelist.add(rightText);
                                                       }
                                                       else {
                                                           contentlist.add(((AVIMTextMessage) avimMessage).getText());
                                                           Typelist.add(leftText);
                                                       }
                                                   }
                                                   else if(avimMessage instanceof AVIMImageMessage)
                                                   {
                                                       AVIMImageMessage avimImageMessage=(AVIMImageMessage)avimMessage;
                                                       if(avimMessage.getFrom().equals(AVUser.getCurrentUser().getUsername()))
                                                       {
                                                           contentlist.add(avimImageMessage.getFileUrl());
                                                           Typelist.add(rightPic);
                                                       }
                                                       else {
                                                           contentlist.add(avimImageMessage.getFileUrl());
                                                           Typelist.add(leftPic);
                                                       }
                                                   }

                                                }
                                                messageAdapter.notifyDataSetChanged();
                                                recyclerView.scrollToPosition(contentlist.size()-1);
                                            }
                                        });
                                        button.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                AVIMTextMessage msg = new AVIMTextMessage();
                                                if(editText.getText().toString()!="")
                                                {
                                                    msg.setText(editText.getText().toString());
                                                conversation.sendMessage(msg, new AVIMConversationCallback() {
                                                    @Override
                                                    public void done(AVIMException e) {
                                                        if (e == null) {
                                                            Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_SHORT).show();
                                                            contentlist.add(editText.getText().toString());
                                                            Typelist.add(rightText);
                                                            messageAdapter.notifyDataSetChanged();
                                                            recyclerView.scrollToPosition(contentlist.size()-1);
                                                            editText.setText("");
                                                        }
                                                    }
                                                });
                                            }
                                            }
                                        });

                                        imageView.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                selectPhoto();
                                            }
                                        });
                                    }
                                }
                            });
                }
            }
        });
    }



    public  class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {
            if (message instanceof AVIMTextMessage) {
                contentlist.add(((AVIMTextMessage) message).getText());
                Typelist.add(leftText);
                recyclerView.scrollToPosition(contentlist.size()-1);
                messageAdapter.notifyDataSetChanged();
            }
            if (message instanceof AVIMImageMessage)
            {

                AVIMImageMessage imageMessage=(AVIMImageMessage)message;
                contentlist.add(imageMessage.getFileUrl());
                Log.d(TAG, "onMessage: 执行了"+imageMessage.getFileUrl());
                Typelist.add(leftPic);
                recyclerView.scrollToPosition(contentlist.size()-1);
                messageAdapter.notifyDataSetChanged();
            }
        }

        public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client) {

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==23&&resultCode==-1)
        {
            try {
                AVIMImageMessage picture = new AVIMImageMessage(RealFilePath.getPath(getApplicationContext(),Matisse.obtainResult(data).get(0)));
                theconversation.sendMessage(picture, new AVIMConversationCallback() {
                    @Override
                    public void done(AVIMException e) {
                        Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_SHORT).show();
                        contentlist.add(RealFilePath.getPath(getApplicationContext(),Matisse.obtainResult(data).get(0)));
                        Typelist.add(rightPic);
                        messageAdapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(contentlist.size()-1);
                        Log.d(TAG, "done: "+RealFilePath.getPath(getApplicationContext(),Matisse.obtainResult(data).get(0)));
                    }
                });
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void selectPhoto()
    {
        Matisse
                .from(this)
                .choose(MimeType.allOf())
                .countable(true)
                .maxSelectable(1)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .theme(R.style.Matisse_Zhihu)
                .imageEngine(new GlideEngine())
                .forResult(23);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tom.close(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                Toast.makeText(getApplicationContext(),"关闭聊天",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
