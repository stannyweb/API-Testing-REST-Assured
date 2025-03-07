package examples;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestSpecificationTest {

    public static RequestSpecification createRequestSpecification() {
        return new RequestSpecBuilder().
                setBaseUri("http://zippopotam.us").
                build();
    }

    @ParameterizedTest
    @MethodSource(value = "zipCodeSource")
    void requestZipCodeFromCollection_ExpectSpecifiedPlaceName(String countryCode, String zipCode,
                                                               String expectedPlaceName) {

        given().spec(RequestSpecificationTest.createRequestSpecification()).pathParams("countryCode", countryCode,
                "zipCode", zipCode).when().get(
                "/{countryCode}/{zipCode}").then().assertThat().body("places[0].'place name'",
                equalTo(expectedPlaceName));
    }

    static Stream<Arguments> zipCodeSource() {
        return Stream.of(
                Arguments.of("us", "90210", "Beverly Hills"),
                Arguments.of("us", "12345", "Schenectady"),
                Arguments.of("ca", "B2R", "Waverley")
        );
    }


}
