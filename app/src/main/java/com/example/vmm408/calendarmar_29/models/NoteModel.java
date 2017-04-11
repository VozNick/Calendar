package com.example.vmm408.calendarmar_29.models;

public class NoteModel {
    private String description;
    private float time;
    private boolean isNotify;

    public NoteModel() {
    }

    public NoteModel(String description, float time, boolean notify) {
        this.description = description;
        this.time = time;
        this.isNotify = notify;
    }

    public void setNotify(boolean notify) {
        this.isNotify = notify;
    }

    public boolean isNotify() {
        return isNotify;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public float getTime() {
        return time;
    }
}
