package com.mohsenoid.rickandmorty.model;

import com.mohsenoid.rickandmorty.utils.Serializer;

import java.util.List;

public class CharacterModel {
    private Integer id;
    private String name;
    private String status;
    private String species;
    private String type;
    private String gender;
    private OriginModel origin;
    private LocationModel location;
    private String image;
    private List<String> episodes;
    private String url;
    private String created;

    public CharacterModel(Integer id, String name, String status, String species, String type, String gender, OriginModel origin, LocationModel location, String image, String episodesSerialized, String url, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.image = image;
        this.episodes = Serializer.deserializeStringList(episodesSerialized);
        this.url = url;
        this.created = created;
    }

    public CharacterModel(Integer id, String name, String status, String species, String type, String gender, OriginModel origin, LocationModel location, String image, List<String> episodes, String url, String created) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.image = image;
        this.episodes = episodes;
        this.url = url;
        this.created = created;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public OriginModel getOrigin() {
        return origin;
    }

    public void setOrigin(OriginModel origin) {
        this.origin = origin;
    }

    public LocationModel getLocation() {
        return location;
    }

    public void setLocation(LocationModel location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getEpisodes() {
        return episodes;
    }

    public String getSerializedEpisodes() {
        return Serializer.serializeStringList(this.episodes);
    }

    public void setEpisodes(List<String> episode) {
        this.episodes = episode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterModel that = (CharacterModel) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                status.equals(that.status) &&
                species.equals(that.species) &&
                type.equals(that.type) &&
                gender.equals(that.gender) &&
                origin.equals(that.origin) &&
                location.equals(that.location) &&
                image.equals(that.image) &&
                episodes.equals(that.episodes) &&
                url.equals(that.url) &&
                created.equals(that.created);
    }
}
