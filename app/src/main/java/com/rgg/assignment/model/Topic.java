package com.rgg.assignment.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "topic")
public class Topic {

    public Topic() {
    }

    @Ignore
    public Topic(String title, String genre, String author) {
        super();
        this.title = title;
        this.genre = genre;
        this.author = author;
    }

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("author")
    @Expose
    private String author;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


}
