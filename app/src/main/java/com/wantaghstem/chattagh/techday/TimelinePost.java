package com.wantaghstem.chattagh.techday;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Daniel on 1/5/2016.
 */
@ParseClassName("TimelinePost")
public class TimelinePost extends ParseObject {

    public TimelinePost(){

    }

    public String getCommentReference(){
        return getString("commentReference");
    }

    public void setCommentReference(String objectId){
         put("commentReference",objectId);
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

    public int getVoteCount(){
        return getNumber("votecount").intValue();
    }

    public void setVoteCount(int voteCount){
        put("votecount", voteCount);
    }

    public int getCommentCount(){
        return getNumber("comments").intValue();
    }

    public void setCommentCount(int commentCount){
        put("comments", commentCount);
    }

    public void incrementVotes(){
        increment("votecount");
    }

    public void decrementVotes(){
        increment("votecount", -1);
    }

    public List<String> getVoters(){
        return getList("voters");
    }

    public void addVoter(String voter){
        add("voters", voter);
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
