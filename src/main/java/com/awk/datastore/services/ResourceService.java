package com.awk.datastore.services;

import com.awk.datastore.model.Resource;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import javax.ws.rs.NotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ResourceService {
    private Map<String, Map<String, Resource>> resourcesMap = new HashMap<>();

    private void addResourceMap( String resourceName ) {
        if( resourcesMap.containsKey(resourceName) ) {
            return;
        }
        resourcesMap.put(resourceName, new HashMap<>());
    }

    public Collection<JsonObject> getResources(String resourceMapId) {
        if ( ! resourcesMap.containsKey(resourceMapId) ) {
            throw new NotFoundException("Unknown resourceType: " + resourceMapId);
        }

        return resourcesMap.get(resourceMapId).values()
                .stream()
                .map(resource -> resource.getObject())
                .collect(Collectors.toList());
    }

    public Collection<String> getResourceIds() {
        return resourcesMap.keySet();
    }

    public JsonObject getResource( String resourceMapId, String resourceId ) throws NotFoundException {
        if ( ! resourcesMap.containsKey(resourceMapId) ) {
            throw new NotFoundException("Unknown resourceType: " + resourceMapId);
        }
        if ( ! resourcesMap.get(resourceMapId).containsKey(resourceId) ) {
            throw new NotFoundException("Could not find resource with id: " + resourceId);
        }

        return resourcesMap.get(resourceMapId).get(resourceId).getObject();
    }

    public void addResource( String resourceMapId, JsonObject resourceJson ) {
        if( ! resourcesMap.containsKey(resourceMapId) ) {
            this.addResourceMap(resourceMapId);
        }
        Resource resource = new Resource(resourceJson.getString("id"), resourceJson);
        Map<String, Resource> resourceMap = resourcesMap.get(resourceMapId);
        resourceMap.put(resource.getId(), resource);
    }
}
