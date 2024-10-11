Feature: test tag endpoints

  @createTag
  Scenario: verify I can create a tag
    Given base url "https://backend.cashwise.us"
    When I provide valid authorization token
    And I provide request body with key and value
      | key         | value                |
      | name_tag    | tag name kiwi        |
      | description | tag name description |
    Then I hit POST "/api/myaccount/tags" endpoint
    Then I verify status code is 201
    Then I hit DELETE "/api/myaccount/tags" endpoint