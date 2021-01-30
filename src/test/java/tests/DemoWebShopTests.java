package tests;

import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spec.Request;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DemoWebShopTests {

    @Test
    @DisplayName("Successful added to wish list")
    void successfulAddToWishlistTest() {

        given()
                .spec(Request.spec())
                .body(FileUtils.readFromFile("src/test/resources/body.txt"))
                .cookie(FileUtils.readFromFile("src/test/resources/cookie.txt"))
                .when()
                .post("/addproducttocart/details/2/2")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Added to wish list with assert")
    void addToWishListWithSimpleAssertTest() {

       Response response = given()
                .spec(Request.spec())
                .body(FileUtils.readFromFile("src/test/resources/body.txt"))
                .cookie(FileUtils.readFromFile("src/test/resources/cookie.txt"))
                .when()
                .post("/addproducttocart/details/2/2")
                .then()
                .statusCode(200)
               .extract().response();

       assertTrue(response.asString().contains("The product has been added to your"));
        }

    @Test
    @DisplayName("Added to wish list with body assert")
    void addedToWishListWithBodyAssertTest() {

         given()
                 .spec(Request.spec())
                .body(FileUtils.readFromFile("src/test/resources/body.txt"))
                .cookie(FileUtils.readFromFile("src/test/resources/cookie.txt"))
                .when()
                .post("/addproducttocart/details/2/2")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"));
    }

    @Test
    @DisplayName("Added to wish list with model")
    void addedToWishListWithModel() {

        WishListResponse response = given()
                .spec(Request.spec())
                .body(FileUtils.readFromFile("src/test/resources/body.txt"))
                .cookie(FileUtils.readFromFile("src/test/resources/cookie.txt"))
                .when()
                .post("/addproducttocart/details/2/2")
                .then()
                .statusCode(200)
                .extract().as(WishListResponse.class);
    }
}
