@active
Feature: Resource testing CRUD

  Scenario: Get the List of resources
    Given there are registered resources in the system
    When I send a GET request to view all the resources
    Then the response resource should have a status code of 200
    And validates the response with the resource list JSON schema

  Scenario: Update the Last Resource
    Given there are registered resources in the system
    And I retrieve the details of the latest resource
    When I send a PUT request to update the latest resource
    """
    {
    "name": "Incredible mouse",
    "trademark": "nike",
    "stock": 324,
    "price": 1212,
    "description": "Mouses to play and work, ergonomically designed for hours of comfortable use",
    "tags": "mouse",
    "active": true
    }
    """
    Then the response resource should have a status code of 200
    And the response resource should have the following details:
      | name             | trademark | stock | price | description                                                                  | tags  | active |
      | Incredible mouse | nike      | 324   | 1212  | Mouses to play and work, ergonomically designed for hours of comfortable use | mouse | true   |
    And validates the response with the resource JSON schema