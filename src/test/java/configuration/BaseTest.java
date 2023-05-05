package configuration;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;

import static org.hamcrest.Matchers.is;

public abstract class BaseTest {
    protected static ResponseSpecification getResponseSpec(String city){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectBody(JsonSchemaValidator.matchesJsonSchema(new File(System.getProperty("jsonSchemaPath"))))
                .expectBody(System.getProperty("nameParam"), is(city))
                .build();
    }
    protected static RequestSpecification getRequestSpecByCityName(String city){
        return new RequestSpecBuilder()
                .setBaseUri(getBaseUrl())
                .addQueryParam(System.getProperty("cityParam"), city)
                .addQueryParam(getAppId(), getApiKey())
                .setContentType(ContentType.JSON)
                .build();
    }
    protected static RequestSpecification getRequestSpecByCityId(int id){
        return new RequestSpecBuilder()
                .setBaseUri(getBaseUrl())
                .addQueryParam(System.getProperty("idParam"), id)
                .addQueryParam(getAppId(), getApiKey())
                .setContentType(ContentType.JSON)
                .build();
    }
    @BeforeAll
    public static void setup() {
        new YamlReader();
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }
    private static String getBaseUrl(){
        return System.getProperty("baseUrl");
    }
    private static String getApiKey(){
        return System.getProperty("apiKey");
    }
    private static String getAppId(){
        return System.getProperty("appIdParam");
    }
}
