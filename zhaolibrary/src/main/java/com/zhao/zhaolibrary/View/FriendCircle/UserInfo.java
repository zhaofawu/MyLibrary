package com.zhao.zhaolibrary.View.FriendCircle;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/27 0027.
 */

public class UserInfo implements Serializable{

    /**
     * id : 1
     * worker_id : 8
     * name : 赵法武
     * avatar : /upload/1/000/000/000/thumb_59cdf2facdbe3.jpg
     * edit_time :
     */

    private String id;
    private String uid;
    private String name;
    private String avatar;
    private String edit_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(String edit_time) {
        this.edit_time = edit_time;
    }
}
