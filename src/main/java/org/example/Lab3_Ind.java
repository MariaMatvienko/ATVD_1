package org.example;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.hc.core5.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Lab3_Ind {
    private static final String baseUrl = "https://petstore.swagger.io/v2";

    private static final String ORDER = "/store/order";
    private static final String apiKey = "special-key";
    private static final int ORDER_ID = 122;
    private static final int PET_ID = 21;

    @BeforeClass
    public void setUp(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyPostNewOrder() {
        Map<String, ?> body = Map.of(
                "id", ORDER_ID,
                "petId", PET_ID,
                "quantity", 5,
                "shipDate", "2023-04-20T13:39:55.054Z",
                "status", "Matvienko Maria 122-20sk-1.21",
                "complete", true
        );

        given().contentType(ContentType.JSON)
                .body(body)
                .header("api_key", apiKey)
                .post(ORDER)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyPostNewOrder")
    public void verifyGetOrder() {
        given().pathParams("orderId", ORDER_ID)
                .header("api_key", apiKey)
                .get(ORDER + "/{orderId}")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyGetOrder")
    public void verifyDeleteOrder() {
        given().pathParams("orderId", ORDER_ID)
                .header("api_key", apiKey)
                .delete(ORDER + "/{orderId}")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
