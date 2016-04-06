package com.immran.notes.model;

import com.orm.SugarRecord;

/**
 * Created by immran on 04/05/2016.
 */
public class Notes extends SugarRecord {
    public String title;
    public String description;
    public long time;

    public Notes(){
    }

    public Notes(String title, String description, long time) {
        this.title = title;
        this.description = description;
        this.time = time;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
