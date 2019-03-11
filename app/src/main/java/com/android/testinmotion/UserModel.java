package com.android.testinmotion;

public class UserModel {
    private String login;
    private String avatar;

    public UserModel(String login, String avatar) {
        this.login = login;
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }


    public String getAvatar() {
        return avatar;
    }


}
