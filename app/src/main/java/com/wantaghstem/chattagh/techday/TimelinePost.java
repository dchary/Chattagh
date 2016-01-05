package com.wantaghstem.chattagh.techday;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;

/**
 * Created by Daniel on 1/5/2016.
 */
@ParseClassName("TimelinePost")
public class TimelinePost extends ParseObject {

    public TimelinePost(){

    }

    public String getMessageText(){
        return getString("messageText");
    }

    public void setMessageText(String messageText){
        put("messageText",messageText);
    }

    public String getSender(){
        return getString("sender");
    }

    public void setSender(String sender){
        put("sender",sender);
    }

    @Override
    public Date getUpdatedAt() {
        return super.getUpdatedAt();
    }

    @Override
    public Date getCreatedAt() {
        return super.getCreatedAt();
    }
}
