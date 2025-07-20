package com.gokul.springdatajpatablesampleproject.model;


public class PostDTO {
    private long id;
    private String content;
    private String username;

    public PostDTO() {
    }

    public PostDTO(long id, String content, String username) {
        this.id = id;
        this.content = content;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
