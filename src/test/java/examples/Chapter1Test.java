package examples;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class Chapter1Test {

    @Test
    void requestZipCode6000_checkPlaceNameInResponseBody_expectStaraZagora() {

        given().when().get("http://zippopotam.us/BG/6000").then().assertThat().log().body(
        ).body("places.'longitude'", equalTo("25.6419"));

    }

    @Test
    void requestZipCode6000_checkListOfPlacesInResponseBody() {
        given().when().get("http://zippopotam.us/BG/6000").then().assertThat().body("places.'place name'", hasItem(
                "Стара Загора / Stara Zagora"));
    }

    @Test
    void requestZipCode6000_checkListOfPlacesInResponseBody_expectOne() {
        given().when().get("http://zippopotam.us/BG/6000").then().assertThat().body("places.'place name'", hasSize(1));
    }
}
