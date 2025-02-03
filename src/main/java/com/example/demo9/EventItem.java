package com.example.demo9;

public class EventItem {
    private String id;
    private String title;
    private String date;
    private String location;
    private String description;
    private String price;
    private String maxParticipants;

    public EventItem(String id, String title, String date, String location,
                     String description, String price, String maxParticipants) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.location = location;
        this.description = description;
        this.price = price;
        this.maxParticipants = maxParticipants;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getDescription() { return description; }
    public String getPrice() { return price; }
    public String getMaxParticipants() { return maxParticipants; }

    @Override
    public String toString() {
        return String.format(
                "Title: %s,\nDate: %s,\nLocation: %s,\nDescription: %s,\nPrice: %s,\nMax: %s",
                title, date, location, description, price, maxParticipants);
    }
}