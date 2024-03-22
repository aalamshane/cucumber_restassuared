Feature: Users API

  Background:
    Given I have access to the Users API

  Scenario: Retrieve user details
    When I retrieve user with ID 2
    Then the status code should be 200
    And I should receive details of the user

  Scenario: Create a new user
    When I create a new user
    Then the user should be created successfully

  Scenario: Update user details
    When I update user with ID 2
    Then the user details should be updated successfully

  Scenario: Delete user
    When I delete user with ID 2
    Then the user should be removed from the system

  Scenario: User creation fails with invalid input data
    Given I have access to the Users API
    When I attempt to create a new user with invalid input data
    Then the user creation should fail
    And the user creation should fail with other code