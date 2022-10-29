package com.newgbaxl.blastmaze;

public class Note {

    private int id;
    private String name;
    private String details;
    private String timestamp;

    public static final Note[] notes = {
            new Note(1,"Setting1", "Meeting with the team",
                    "10/21/22"),
            new Note(2,"Setting2","Mobile Device App Course",
                    "10/23/22"),
            new Note(3,"Setting3","Concert in Tampa",
                    "10/25/22"),
            new Note(4,"Setting4","Visiting Friends",
                    "10/26/19"),
            new Note(5,"Setting5", "Mobile Device App Assignment 5",
                    "10/27/22")
    };

    public Note() {
    }

    public Note(int id, String name, String details, String timestamp) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}