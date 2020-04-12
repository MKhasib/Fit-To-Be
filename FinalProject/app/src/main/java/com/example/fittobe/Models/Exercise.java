package com.example.fittobe.Models;

public class Exercise {


    private String name;
    private String video_url;

    public Exercise(String name, String video_url) {
        this.name = name;
        this.video_url = video_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", video_url='" + video_url + '\'' +
                '}';
    }
}
