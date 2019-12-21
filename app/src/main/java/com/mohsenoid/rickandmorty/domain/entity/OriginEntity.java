package com.mohsenoid.rickandmorty.domain.entity;

public class OriginEntity {

    private String name;
    private String url;

    public OriginEntity(String name, String url) {
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
        OriginEntity that = (OriginEntity) o;
        return name.equals(that.name) &&
                url.equals(that.url);
    }
}
