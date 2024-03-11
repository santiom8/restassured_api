package com.testing.api.stepDefinitions;

import com.testing.api.models.Resource;
import com.testing.api.requests.ResourceRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class ResourceSteps {
    private static final Logger logger = LogManager.getLogger(ClientSteps.class);
    private static final ResourceRequest resourceRequest = new ResourceRequest();
    private Resource resource;
    private Resource lastResource;
    private static Map<String, String> resourceDataMap;

    private Response response;

    @Given("there are registered resources in the system")
    public void thereAreRegisteredResourcesInTheSystem() {
        /*logger.info("checking if there are resources in the system");
        response = resourceRequest.getResources();
        logger.info(response.jsonPath().prettify());
        Assert.assertEquals(200, response.statusCode());
        List<Resource> resources = resourceRequest.getResourcesEntity(response);
        if(!resources.isEmpty()) {
            Assert.assertTrue(resources.size() > 4);
            logger.info("there are registered resources in the system");
        }*/
        response = resourceRequest.getResources();
        logger.info(response.jsonPath().prettify());
        Assert.assertEquals(200, response.statusCode());
        List<Resource> resources = resourceRequest.getResourcesEntity(response);
    }

    @When("I send a GET request to view all the resources")
    public void iSendAGETRequestToViewAllTheResources() {
        logger.info("sending a GET request to view all the resources");
        response = resourceRequest.getResources();
        logger.info("Response: "+response.jsonPath().prettify());
    }

    @Then("the response resource should have a status code of {int}")
    public void theResponseShouldHaveAStatusCodeOf(int statusCode) {
        logger.info("the response should have a status code of" + statusCode);
        Assert.assertEquals(statusCode, response.statusCode());
    }


    @And("validates the response with the resource list JSON schema")
    public void validatesTheResponseWithTheResourceListJSONSchema() {
        logger.info("validating the response with the resource list JSON schema");
        Assert.assertTrue(resourceRequest.validateSchema(response, "schemas/resourceListSchema.json"));
    }

    @And("I retrieve the details of the latest resource")
    public void iRetrieveTheDetailsOfTheLatestResource() {
        logger.info("retrieving the details of the latest resource");
        List<Resource> resources = resourceRequest.getResourcesEntity(response);
        logger.info("the resources are: "+resources);
        lastResource = resources.get(resources.size()-1);
        logger.info("the last resource is: "+lastResource);
    }

    @When("I send a PUT request to update the latest resource")
    public void iSendAPUTRequestToUpdateTheLatestResource(String requestBody) {
        logger.info("verifying the request body: "+requestBody);
        response = resourceRequest.updateResource(resourceRequest.getResourceEntity(requestBody),lastResource.getId());
        logger.info("Response: "+response.jsonPath().prettify());
    }

    @And("validates the response with the resource JSON schema")
    public void validatesTheResponseWithTheResourceJSONSchema() {
        logger.info("validating the response with the resource JSON schema");
        Assert.assertTrue(resourceRequest.validateSchema(response, "schemas/resourceSchema.json"));
    }
}
