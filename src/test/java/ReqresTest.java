import io.restassured.response.Response;
import org.apache.http.protocol.HTTP;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReqresTest {

    String URL = "https://reqres.in";

    @Test
    public void listUsersTest() {
        given()
        .when()
                .get(URL + "/api/users?page=2")
        .then()
                .log().body()
                .statusCode(200)
                .body("data[2].first_name", equalTo("Tobias"));
    }

    @Test
    public void singleUserTest() {
        given()
        .when()
                .get(URL + "/api/users/2")
        .then()
                .log().body()
                .statusCode(200)
                .body("ad.company", equalTo("StatusCode Weekly"));
    }

    @Test
    public void userNotFoundTest() {
        given()
        .when()
                .get(URL + "/api/users/23")
        .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void ListResourceTest() {
        given()
        .when()
                .get(URL + "/api/unknown")
        .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void SingleResourceTest() {
        given()
        .when()
                .get(URL + "/api/unknown/2")
        .then()
                .log().body()
                .statusCode(200);
    }

    @Test
    public void SingleResourceNotFoundTest() {
        given()
        .when()
                .get(URL + "/api/unknown/23")
        .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void createTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
        .when()
                .post(URL + "/api/users")
        .then()
                .log().body()
                .statusCode(201)
                .extract().response();
    }

    @Test
    public void updatePutTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
        .when()
                .put(URL + "/api/users/2")
        .then()
                .log().body()
                .statusCode(200)
                .extract().response();
    }

    @Test
    public void updatePatchTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
        .when()
                .patch(URL + "/api/users/2")
        .then()
                .log().body()
                .statusCode(200)
                .extract().response();
    }

    @Test
    public void deleteTest() {
        given()
        .when()
                .delete(URL + "/api/users/2")
        .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    public void registerSuccesfulTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
        .when()
                .post(URL + "/api/register")
        .then()
                .log().body()
                .statusCode(200)
                .extract().response();
    }

    @Test
    public void registerUnsuccesfulTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}")
        .when()
                .post(URL + "/api/register")
        .then()
                .log().body()
                .statusCode(400)
                .extract().response();
    }

    @Test
    public void loginSuccesfulTest() {
        Response response = given()
                .header(HTTP.CONTENT_TYPE, "application/json")
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
         .when()
                .post(URL + "/api/login")
         .then()
                .log().body()
                .statusCode(200)
                .extract().response();
    }


    @Test
    public void delayedResponceTest() {
        given()
         .when()
                .get(URL + "/api/users?delay=3")
         .then()
                .log().body()
                .statusCode(200);
    }
}
