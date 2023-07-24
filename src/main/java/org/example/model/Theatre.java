package org.example.model;

public class Theatre {
    private long id;
    private String name;
    private String location;

    public Theatre() {
    }

    public Theatre(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public Theatre(long id, String name, String location) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
