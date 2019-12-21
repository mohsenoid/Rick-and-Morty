package com.mohsenoid.rickandmorty.data.db.dto;

public class DbEpisodeModel {

    private Integer id;
    private String name;
    private String airDate;
    private String episode;
    private String serializedCharacterIds;
    private String url;
    private String created;

    public DbEpisodeModel(Integer id, String name, String airDate, String episode, String serializedCharacterIds, String url, String created) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.episode = episode;
        this.serializedCharacterIds = serializedCharacterIds;
        this.url = url;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAirDate() {
        return airDate;
    }

    public String getEpisode() {
        return episode;
    }

    public String getSerializedCharacterIds() {
        return serializedCharacterIds;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbEpisodeModel that = (DbEpisodeModel) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                airDate.equals(that.airDate) &&
                episode.equals(that.episode) &&
                serializedCharacterIds.equals(that.serializedCharacterIds) &&
                url.equals(that.url) &&
                created.equals(that.created);
    }
}
