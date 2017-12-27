package com.zhao.zhaolibrary.View.FriendCircle;

import com.zhao.zhaolibrary.View.FriendCircle.CommentBean;
import com.zhao.zhaolibrary.View.FriendCircle.UserInfo;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class FriendCircleBean {

    /**
     * id : 4
     * worker_id : 38
     * vid : 1
     * pid : 1
     * cate : 1
     * text : 1f2safdsdsdsa
     * image : []
     * viewcount :
     * add_time : 2017-10-20 14:19:07
     * edit_time :
     * status : 1
     * comment_list : []
     * nickname : 13977576274
     * avatar : /upload/1/000/000/000/thumb_59cdfc6eaf544.png
     * village_name : 南宁广告产业园
     * comments : 0
     * likes : 0
     * like_list : []
     */

    private String id;
    private String uid;
    private String vid;
    private String pid;
    private String cate;
    private String text;
    private String viewcount;
    private String add_time;
    private String edit_time;
    private String status;
    private String nickname;
    private String avatar;
    private String village_name;
    private String comments;
    private String likes;
    private boolean myselfLikeStatus;//本身自己的点赞状态
    private int myselfLikePosition;
    private ArrayList<String> image=new ArrayList<>();
    private ArrayList<CommentBean> comment_list=new ArrayList<>();
    private ArrayList<UserInfo> like_list=new ArrayList<>();

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

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCate() {
        return cate;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getViewcount() {
        return viewcount;
    }

    public void setViewcount(String viewcount) {
        this.viewcount = viewcount;
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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public boolean getMyselfLikeStatus() {
        return myselfLikeStatus;
    }

    public void setMyselfLikeStatus(boolean myselfLikeStatus) {
        this.myselfLikeStatus = myselfLikeStatus;
    }

    public int getMyselfLikePosition() {
        return myselfLikePosition;
    }

    public void setMyselfLikePosition(int myselfLikePosition) {
        this.myselfLikePosition = myselfLikePosition;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public ArrayList<CommentBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(ArrayList<CommentBean> comment_list) {
        this.comment_list = comment_list;
    }

    public ArrayList<UserInfo> getLike_list() {
        return like_list;
    }

    public void setLike_list(ArrayList<UserInfo> like_list) {
        this.like_list = like_list;
    }
}
