Feature: verify seller endpoints

  @createSeller
  Scenario: verify I can create a seller
    Given base url "https://backend.cashwise.us"
    When I provide valid authorization token
    And I provide request body with key and value
      | key          | value           |
      | company_name | company abc     |
      | seller_name  | seller abc      |
      | email        | email@gmail.com |
      | phone_number | 1234534532      |
      | address      | 123 Abc street  |
    Then I hit POST "/api/myaccount/sellers" endpoint
    Then I verify status code is 201
