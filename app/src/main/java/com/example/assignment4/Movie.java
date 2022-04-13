package com.example.assignment4;

public class Movie {

    private String id;
    private String title;
    private String description;
    private String poster;

    Movie(){}

    public Movie(String title, String description)
    {
        this.title = title;
        this.description = description;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }
}
