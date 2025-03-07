package examples;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationTest {

    public static ResponseSpecification getResponseSpecification() {
        return new ResponseSpecBuilder().
                expectStatusCode(200).
                expectHeader("Content-Type", "application/json").
                build();
    }

    @ParameterizedTest(name = "Country Code: {0}, ZipCode: {1}, City: {2}")
    @MethodSource(value = "zipCodeSource")
    void requestZipCodeFromCollection_ExpectSpecifiedPlaceName(String countryCode, String zipCode
            , String expectedPlaceName) {

        given().
                spec(RequestSpecificationTest.createRequestSpecification()).
                pathParams("countryCode", countryCode, "zipCode", zipCode).
                when().
                get("/{countryCode}/{zipCode}").
                then().
                spec(ResponseSpecificationTest.getResponseSpecification()).
                body("places[0].'place name'", equalTo(expectedPlaceName));
    }

    static Stream<Arguments> zipCodeSource() {
        return Stream.of(
                Arguments.of("us", "90210", "Beverly Hills"),
                Arguments.of("us", "12345", "Schenectady"),
                Arguments.of("ca", "B2R", "Waverley"));
    }
}
