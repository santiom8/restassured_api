package com.testing.api.stepDefinitions;

import com.testing.api.models.Resource;
import com.testing.api.requests.ResourceRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import java.util.Date;
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
        response = resourceRequest.getResources();
        logger.info("response is:"+response.jsonPath().prettify());
        Assert.assertEquals(200, response.statusCode());
        List<Resource> resources = resourceRequest.getResourcesEntity(response);
        /*if(resources.isEmpty()) {
            response = resourceRequest.createDefaultResource();
            logger.info(response.statusCode());
            Assert.assertEquals(201, response.statusCode());
        }*/
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
        lastResource = resources.get(resources.size()-1);
        logger.info("the last resource is: "+lastResource);
    }

    @When("I send a PUT request to update the latest resource")
    public void iSendAPUTRequestToUpdateTheLatestResource(String requestBody) {
        logger.info("sending a PUT request to update the latest resource");
        logger.info(requestBody);
        response = resourceRequest.updateResource(resourceRequest.getResourceEntity(requestBody),lastResource.getId());
        logger.info("response from put is:"+response);
    }

    @And("validates the response with the resource JSON schema")
    public void validatesTheResponseWithTheResourceJSONSchema() {
        logger.info("validating the response with the resource JSON schema");
        Assert.assertTrue(resourceRequest.validateSchema(response, "schemas/resourceSchema.json"));
    }

    @And("the response resource should have the following details:")
    public void theResponseResourceShouldHaveTheFollowingDetails(DataTable resourceDataTable) {
        logger.info("the response resource should have the following details");
        Map<String, String> resourceDataMap = resourceDataTable.asMaps().get(0);
        Resource expectedResource = Resource.builder()
                .name(resourceDataMap.get("name"))
                .trademark(resourceDataMap.get("trademark"))
                .stock(Integer.parseInt(resourceDataMap.get("stock")))
                .price(Double.valueOf(resourceDataMap.get("price")))
                .description(resourceDataMap.get("description"))
                .tags(resourceDataMap.get("tags"))
                .is_active(Boolean.valueOf(resourceDataMap.get("is_active")))
                .build();

        Resource actualResource = resourceRequest.getResourceEntityByResponse(response);
        Assert.assertEquals("Name does not match", expectedResource.getName(), actualResource.getName());
        Assert.assertEquals("Trademark does not match", expectedResource.getTrademark(), actualResource.getTrademark());
        Assert.assertEquals("Stock does not match", expectedResource.getStock(), actualResource.getStock());
        Assert.assertEquals("Price does not match", expectedResource.getPrice(), actualResource.getPrice());
        Assert.assertEquals("Description does not match", expectedResource.getDescription(), actualResource.getDescription());
        Assert.assertEquals("Tags does not match", expectedResource.getTags(), actualResource.getTags());
        Assert.assertEquals("Is_active does not match", expectedResource.getIs_active(), actualResource.getIs_active());

        logger.info("Resource details assertion success");
    }
}
