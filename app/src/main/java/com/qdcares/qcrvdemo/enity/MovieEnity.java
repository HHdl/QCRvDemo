package com.qdcares.qcrvdemo.enity;

import java.util.List;

/**
 * Created by handaolin on 2017/10/22.
 */

public class MovieEnity {
    public int total;
    public List<Movie> subjects;
    public int count;
    public int start;
    public String title;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Movie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Movie> subjects) {
        this.subjects = subjects;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
