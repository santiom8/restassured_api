package com.testing.api.requests;

import com.google.gson.Gson;
import com.testing.api.models.Client;
import com.testing.api.utils.Constants;
import com.testing.api.utils.JsonFileReader;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ClientRequest extends BaseRequest {
    private String endpoint;


    /**
     * this functions calls the requestGet function to returns the response to the get request of clients
     * @return Response
     */
    public Response getClients() {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestGet(endpoint, createBaseHeaders());
    }

    public Response getClient(String clientId) {
        endpoint = "";
        return requestGet(endpoint, createBaseHeaders());
    }

    /**
     * creates a client by POST request
     * @param client
     * @return response
     */
    public Response createClient(Client client) {
        endpoint = String.format(Constants.URL, Constants.CLIENTS_PATH);
        return requestPost(endpoint, createBaseHeaders(), client);
    }

    public Response updateClient(Client client, String clientId) {
        endpoint = "";
        return requestPut(endpoint, createBaseHeaders(), client);
    }

    public Response deleteClient(String clientId) {
        endpoint = "";
        return requestDelete(endpoint, createBaseHeaders());
    }


    /**
     * this function returns the client entity from the response
     * @param response
     * @return Client
     */
    public Client getClientEntity(@NotNull Response response) {
        return response.as(Client.class);
    }

    /**
     * this function returns the list of clients from the response
     * @param response
     * @return List<Client>
     */
    public List<Client> getClientsEntity(@NotNull Response response) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.getList("", Client.class);
    }

    public Response createDefaultClient() {
        JsonFileReader jsonFile = new JsonFileReader();
        return this.createClient(jsonFile.getClientByJson(Constants.DEFAULT_CLIENT_FILE_PATH));
    }

    public Client getClientEntity(String clientJson) {
        Gson gson = new Gson();
        return gson.fromJson(clientJson, Client.class);
    }

    /**
     * this function validates the response with the client JSON schema
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
}
