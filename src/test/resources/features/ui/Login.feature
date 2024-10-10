Feature: Verify login

  @login
  Scenario: verify user can login
    Given user goes to "https://cashwise.us"


  @signUp
  Scenario: verify user can sign up with valid email
    Given user goes to "https://cashwise.us"
    When user signs up with valid credentials
    Then verify user signed up successfully



