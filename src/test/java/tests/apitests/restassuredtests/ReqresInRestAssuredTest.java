package tests.apitests.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import models.apimodels.PostUserModel;
import models.apimodels.UpdateUserModel;
import models.apimodels.UserUnsuccessfulModel;
import org.testng.annotations.Test;
import utilities.GenerateFakeMessage;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

public class ReqresInRestAssuredTest {

    @Test
    public void checkResponseCodeTest() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown/23")
                .then()
                .log()
                .all()
                .statusCode(404);
    }

    @Test
    public void checkFieldsInResponse() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", instanceOf(Integer.class))
                .body("data[0].id", instanceOf(Integer.class));
    }

    @Test
    public void checkBodyTest() {
        JsonPath expectedJson = new JsonPath(new File("src/test/resources/resource.json"));
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log()
                .all()
                .body("", equalTo(expectedJson.getMap("")));
    }

    @Test
    public void userUpdateTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setName(GenerateFakeMessage.getFirstName());
        updateUserModel.setJob(GenerateFakeMessage.getFirstName());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200);
    }

    @Test
    public void checkUpdateTimeTest() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        updateUserModel.setName(GenerateFakeMessage.getFirstName());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(updateUserModel)
                .when()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("name", instanceOf(String.class));
    }

    @Test
    public void checkDeleteTest() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log()
                .all()
                .statusCode(204);
    }

    @Test
    public void registerSuccessfulTest() {
        JsonPath registerJson = new JsonPath(new File("src/test/resources/registerForm.json"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(registerJson.getMap(""))
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("id", instanceOf(Integer.class))
                .body("token", instanceOf(String.class));
    }

    @Test
    public void registerUnsuccessfulTest() {
        UserUnsuccessfulModel userUnsuccessfulModel = new UserUnsuccessfulModel();
        userUnsuccessfulModel.setEmail(GenerateFakeMessage.getEmail());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(userUnsuccessfulModel)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void loginSuccessfulTest() {
        JsonPath loginJson = new JsonPath(new File("src/test/resources/login.json"));
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(loginJson.getMap(""))
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("token", instanceOf(String.class));
    }

    @Test
    public void loginUnsuccessfulTest() {
        UserUnsuccessfulModel userUnsuccessfulModel = new UserUnsuccessfulModel();
        userUnsuccessfulModel.setEmail(GenerateFakeMessage.getEmail());
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .log()
                .all()
                .and()
                .body(userUnsuccessfulModel)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log()
                .all()
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }

    @Test
    public void checkDelayedResponseCodeTest() {
        RestAssured
                .given()
                .log()
                .all()
                .when()
                .get("https://reqres.in/api/users?delay=3)")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", instanceOf(Integer.class))
                .body("data[0].id", instanceOf(Integer.class));
    }
}
