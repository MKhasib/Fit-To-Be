package com.example.fittobe.Models;

public class Exercise {


    private String name;
    private String videoId;
    public Exercise(){}
    public Exercise(String name, String video_url) {
        this.name = name;
        this.videoId = video_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", video_url='" + videoId + '\'' +
                '}';
    }
}
