package cashwise.models.pojo;

public class CustomRequest {

    /*

    // Without model
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("firstName", "John");
    requestBody.put("lastName", "Doe");

    // With model
    UserRequest user = new UserRequest();
    user.setFirstName("John");
    user.setLastName("Doe");


    // Serialization
    ObjectMapper mapper = new ObjectMapper();
    String jsonRequest = mapper.writeValueAsString(user);

    // Deserialization
    UserResponse userResponse = mapper.readValue(jsonResponseString, UserResponse.class);


    Response response = RestAssured
    .given()
    .contentType(ContentType.JSON)
    .body(user)
    .when()
    .post("/users");

     */
}
