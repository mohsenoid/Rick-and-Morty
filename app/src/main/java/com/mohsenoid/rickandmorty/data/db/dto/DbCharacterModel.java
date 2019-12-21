package com.mohsenoid.rickandmorty.data.db.dto;

public class DbCharacterModel {

    private Integer id;
    private String name;
    private String status;
    private Boolean isStatusAlive;
    private String species;
    private String type;
    private String gender;
    private DbOriginModel origin;
    private DbLocationModel location;
    private String image;
    private String serializedEpisodes;
    private String url;
    private String created;
    private Boolean isKilledByUser;

    public DbCharacterModel(Integer id, String name, String status, Boolean isStatusAlive, String species, String type, String gender, DbOriginModel origin, DbLocationModel location, String image, String serializedEpisodes, String url, String created, Boolean isKilledByUser) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.isStatusAlive = isStatusAlive;
        this.species = species;
        this.type = type;
        this.gender = gender;
        this.origin = origin;
        this.location = location;
        this.image = image;
        this.serializedEpisodes = serializedEpisodes;
        this.url = url;
        this.created = created;
        this.isKilledByUser = isKilledByUser;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Boolean getStatusAlive() {
        return isStatusAlive;
    }

    public String getSpecies() {
        return species;
    }

    public String getType() {
        return type;
    }

    public String getGender() {
        return gender;
    }

    public DbOriginModel getOrigin() {
        return origin;
    }

    public DbLocationModel getLocation() {
        return location;
    }

    public String getImage() {
        return image;
    }

    public String getSerializedEpisodes() {
        return serializedEpisodes;
    }

    public String getUrl() {
        return url;
    }

    public String getCreated() {
        return created;
    }

    public Boolean getKilledByUser() {
        return isKilledByUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DbCharacterModel that = (DbCharacterModel) o;
        return id.equals(that.id) &&
                name.equals(that.name) &&
                status.equals(that.status) &&
                isStatusAlive.equals(that.isStatusAlive) &&
                species.equals(that.species) &&
                type.equals(that.type) &&
                gender.equals(that.gender) &&
                origin.equals(that.origin) &&
                location.equals(that.location) &&
                image.equals(that.image) &&
                serializedEpisodes.equals(that.serializedEpisodes) &&
                url.equals(that.url) &&
                created.equals(that.created) &&
                isKilledByUser.equals(that.isKilledByUser);
    }
}
