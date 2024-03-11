package com.testing.api.requests;

import com.google.gson.Gson;
import com.testing.api.models.Resource;
import com.testing.api.utils.Constants;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

public class ResourceRequest extends BaseRequest {

    private String endpoint;


    /**
     * this functions calls the request Get returns the response
     * @return Response
     */
    public Response getResources() {
        endpoint = String.format(Constants.URL, Constants.RESOURCE_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }

    /**
     * creates a resource by POST request
     * @param response not null
     * @return List<Resource>
     */
    public List<Resource> getResourcesEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Resource.class);
    }

    /**
     * creates a resource by POST request
     * @param resource
     * @param resourceId
     * @return response
     */

    public Response updateResource(Resource resource, String resourceId) {
        endpoint = String.format(Constants.URL_WITH_PARAM, Constants.RESOURCE_PATH, resourceId);
        System.out.println("endpoint: " + endpoint);
        return requestPut(endpoint, createBaseHeaders(), resource);
    }

    /**
     * validates the response with the list of resources JSON schema
     * @param response
     * @param schemaPath
     * @return boolean
     */
    public boolean validateSchema(Response response, String schemaPath) {
        try {
            response.then()
                    .assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            return true; // Return true if the assertion passes
        } catch (AssertionError e) {
            // Assertion failed, return false
            return false;
        }
    }

    /**
     * this function returns the resource entity from the response
     * @param resourceJson
     * @return Resource
     */
    public Resource getResourceEntity(String resourceJson) {
        Gson gson = new Gson();
        return gson.fromJson(resourceJson, Resource.class);
    }

    /**
     * this function returns the resource entity from the response
     * @param response
     * @return Resource
     */
    public Resource getResourceEntityByResponse(Response response) {
        return response.as(Resource.class);
    }
}
