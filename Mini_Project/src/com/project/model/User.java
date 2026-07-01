package com.project.model;            //완성형

public class User {
    private String id;
    private String password;
    private String name;

    // 생성자 (데이터를 처음 담을 때 사용)
    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    // Getter & Setter (데이터를 읽어오거나 수정할 때 사용)
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
