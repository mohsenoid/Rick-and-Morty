package com.mohsenoid.rickandmorty.data.service.model;

import org.json.JSONException;
import org.json.JSONObject;

class InfoModel {

    private static final String TAG_COUNT = "count";
    private static final String TAG_PAGES = "pages";
    private static final String TAG_NEXT = "next";
    private static final String TAG_PREV = "prev";

    private int count;
    private int pages;
    private String next;
    private String prev;

    private InfoModel(int count, int pages, String next, String prev) {
        this.count = count;
        this.pages = pages;
        this.next = next;
        this.prev = prev;
    }

    static InfoModel fromJson(JSONObject jsonObject) throws JSONException {

        int count = jsonObject.getInt(TAG_COUNT);
        int pages = jsonObject.getInt(TAG_PAGES);
        String next = jsonObject.getString(TAG_NEXT);
        String prev = jsonObject.getString(TAG_PREV);

        return new InfoModel(count, pages, next, prev);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }
}
