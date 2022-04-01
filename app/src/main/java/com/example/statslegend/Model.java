package com.example.statslegend;

public class Model {
    String id, title, description;

    public Model(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Model(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }


}
