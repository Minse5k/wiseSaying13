package com.back.domain.wiseSaying.entity;

public class WiseSaying {
    private int id;
    private String author;
    private String content;

    public WiseSaying(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toListString() {
        return id + " / " + author + " / " + content;
    }
}
