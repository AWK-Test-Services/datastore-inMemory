package com.awk.datastore.model;

import javax.json.JsonObject;

import static java.util.Objects.requireNonNull;

public class Resource {
    private String id;
    private JsonObject object;

    public Resource(String id, JsonObject jsonObject) {
        this.id = requireNonNull(id);
        object = requireNonNull(jsonObject);
    }

    public String getId() {
        return id;
    }

    public JsonObject getObject() {
        return object;
    }
}
