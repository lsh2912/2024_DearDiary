package com.example.mydiaryapp.Event;

public class Event {
    private int id;
    private final String title;
    private final String description;
    private final String tag;
    private String date;
    public Event(int id, String title, String tag, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() { return date; }
    public String getDescription() {
        return description;
    }
}
