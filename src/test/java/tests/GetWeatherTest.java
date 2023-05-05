package tests;

import configuration.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;

public class GetWeatherTest extends BaseTest {
    @ParameterizedTest
    @CsvFileSource(resources = "/cities.csv")
    public void shouldGetWeatherDetailsByCityName(String city){
        given().
                spec(getRequestSpecByCityName(city)).
        when().
                get().
        then().
                spec(getResponseSpec(city));
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/citiesByIds.csv")
    public void shouldGetWeatherDetailsByCityId(int id, String city){
        given().
                spec(getRequestSpecByCityId(id)).
        when().
                get().
        then().
                spec(getResponseSpec(city));
    }
}
