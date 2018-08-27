package com.example.yuefan.Event;

/**
 * Created by 67698 on 2018/8/27.
 */

public class MessageEvent {

    private int ChangeSign;

    public MessageEvent(int ChangeSign)
    {
        this.ChangeSign=ChangeSign;
    }

    public int getChangeSign()
    {
        return ChangeSign;
    }

    public void setChangeSign(int ChangeSign)
    {
        this.ChangeSign=ChangeSign;
    }


}
