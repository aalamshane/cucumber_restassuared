package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.junit.Assert.*;

public class UserStepDefinitions {

    private Response response;

    @Given("I have access to the Users API")
    public void i_have_access_to_the_Users_API() {
        // Set up base URL for the Users API
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @When("I retrieve user with ID {int}")
    public void i_retrieve_user_with_ID(int userId) {
        response = RestAssured.get("/users/" + userId);
    }

    @Then("the status code should be {int}")
    public void the_status_code_should_be_ID(int status) {
        assertNotNull(response);
        response.then().statusCode(status);

    }
    @Then("I should receive details of the user")
    public void i_should_receive_details_of_the_user() {
        assertNotNull(response);
        // Validate that the response contains user details
        assertNotNull(response.jsonPath().getString("data.email"));
        assertNotNull(response.jsonPath().getString("data.first_name"));
        assertNotNull(response.jsonPath().getString("data.last_name"));
        assertNotNull(response.jsonPath().getString("data.avatar"));

    }

    @When("I update user with ID {int}")
    public void i_update_user_with_ID(int userId) {
        // Define the request payload for updating the user
        String requestBody = "{"
                + "\"name\": \"Updated Name\","
                + "\"job\": \"Updated Job\""
                + "}";

        // Send a PUT request to update the user with the given ID
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .put("/users/" + userId);
    }

    @Then("the user details should be updated successfully")
    public void the_user_details_should_be_updated_successfully() {
        assertNotNull(response);
        response.then().statusCode(200);
    }

    @When("I delete user with ID {int}")
    public void i_delete_user_with_ID(int userId) {
        // Send a DELETE request to delete the user with the given ID
        response = RestAssured.delete("/users/" + userId);
    }

    @Then("the user should be removed from the system")
    public void the_user_should_be_removed_from_the_system() {
        assertNotNull(response);
        response.then().statusCode(204);
    }
    @When("I create a new user")
    public void i_create_a_new_user() {
        // Define the request payload for creating a new user
        String requestBody = "{"
                + "\"name\": \"John Doe\","
                + "\"job\": \"Software Engineer\""
                + "}";

        // Send a POST request to create a new user
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post("/users");
    }

    @Then("the user should be created successfully")
    public void the_user_should_be_created_successfully() {
        assertNotNull(response);
        response.then().statusCode(201); // Expecting status code 201 Created for successful creation

        JsonPath jsonPath = response.jsonPath();
        assertNotNull(jsonPath.getString("id"));
        assertEquals("John Doe", jsonPath.getString("name"));
        assertEquals("Software Engineer", jsonPath.getString("job"));

    }
    @When("I attempt to create a new user with invalid input data")
    public void i_attempt_to_create_a_new_user_with_invalid_input_data() {
        // Define the request payload with invalid input data
        String requestBody = "{"
                + "\"email\": \"sydney@fife\","

                + "}";

        // Send a POST request to create a new user with invalid input data
        response = RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .post("/register");
    }

    @Then("the user creation should fail")
    public void the_user_creation_should_fail() {
        assertNotNull(response);
        response.then().statusCode(4400); // Expecting status code 400 Bad Request for failed creation
    }
    @Then("the user creation should fail with other code")
    public void the_user_creation_should_fail_with_other_code() {
        assertNotNull(response);
        response.then().statusCode(4400); // Expecting status code 4400 Bad Request for failed creation
    }
}
