package com.mohsenoid.rickandmorty.domain.entity;

import java.util.List;

public class EpisodeEntity {

    private Integer id;
    private String name;
    private String airDate;
    private String episode;
    private List<Integer> characterIds;
    private String url;
    private String created;

    public EpisodeEntity(Integer id, String name, String airDate, String episode, List<Integer> characterIds, String url, String created) {
        this.id = id;
        this.name = name;
        this.airDate = airDate;
        this.episode = episode;
        this.characterIds = characterIds;
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

    public List<Integer> getCharacterIds() {
        return characterIds;
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
        EpisodeEntity that = (EpisodeEntity) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                airDate.equals(that.airDate) &&
                episode.equals(that.episode) &&
                characterIds.equals(that.characterIds) &&
                url.equals(that.url) &&
                created.equals(that.created);
    }
}
