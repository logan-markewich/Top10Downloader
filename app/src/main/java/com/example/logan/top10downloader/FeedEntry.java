package com.example.logan.top10downloader;

/**
 * Created by logan on 2017-07-07.
 */

public class FeedEntry {

    protected String title;
    protected String artist;
    protected String summary;
    protected String releaseDate;
    protected String imageURL;

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getSummary() {
        return summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "title=" + title + '\n' +
                ", artist=" + artist + '\n' +
                ", releaseDate=" + releaseDate + '\n' +
                ", imageURL=" + imageURL + '\n';
    }
}
