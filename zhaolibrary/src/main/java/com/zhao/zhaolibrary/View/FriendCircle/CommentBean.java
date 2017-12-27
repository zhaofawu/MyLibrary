package com.zhao.zhaolibrary.View.FriendCircle;

import java.io.Serializable;

/**
 * 评论列表
 */
public class CommentBean implements Serializable {


    /**
     * id : 17
     * worker_id : 60
     * vid : 1
     * post_id : 59
     * comment_id : 16
     * text : 你是
     * add_time : 2017-10-27 17:43:37
     * edit_time : null
     * status : 1
     * worker : {"id":"60","worker_id":"60","name":"覃宏克","avatar":"/upload/1/000/000/000/thumb_59ce0b51c9cad.jpg"}
     * to_worker : {"id":"8","worker_id":"8","name":"赵法武"}
     */

    private String id;
    private String uid;
    private String vid;
    private String post_id;
    private String comment_id;
    private String text;
    private String add_time;
    private String edit_time;
    private String status;
    private UserInfo user=new UserInfo();
    private UserInfo to_user=new UserInfo();


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

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getEdit_time() {
        return edit_time;
    }

    public void setEdit_time(String edit_time) {
        this.edit_time = edit_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public UserInfo getTo_user() {
        return to_user;
    }

    public void setTo_user(UserInfo to_user) {
        this.to_user = to_user;
    }

}
