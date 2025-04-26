package com.analia.web.rs.media;

import java.util.ArrayList;
import java.util.List;

public class VideoResponse {

    private List<String> videos = new ArrayList<>();


    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }
}
