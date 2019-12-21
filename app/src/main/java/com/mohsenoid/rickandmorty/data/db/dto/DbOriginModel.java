package com.mohsenoid.rickandmorty.data.db.dto;

public class DbOriginModel {

    private String name;
    private String url;

    public DbOriginModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbOriginModel that = (DbOriginModel) o;
        return name.equals(that.name) &&
                url.equals(that.url);
    }
}
