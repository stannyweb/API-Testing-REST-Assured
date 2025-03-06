package examples;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Chapter1Test {

    @Test
    void requestZipCode6000_checkPlaceNameInResponseBody_expectStaraZagora() {

        given().when().get("http://zippopotam.us/BG/6000").then().assertThat().body("places[0].'place name'", equalTo("Стара Загора / Stara Zagora"));


    }
}
