Feature: Create Gist

  Scenario: Unauthenticated user can't create gist
    When unauthorised user attempts to create a private gist "Sample Gist" with files:
      | filename      | content   | type       | language |
      | test_file.txt | test file | text/plain | Text     |
    Then he should receive response with code 401

  Scenario: Authenticated user can create gist
    When Authorised user attempts to create a private gist "Sample Gist" with files:
      | filename      | content   | type       | language |
      | test_file.txt | test file | text/plain | Text     |
    Then he should receive response with code 201
    And create a private gist "Sample Gist" response should have files:
      | filename      | content   | type       | language |
      | test_file.txt | test file | text/plain | Text     |

  Scenario: Authenticated user can't create gist without files
    When Authorised user attempts to create a private gist "Sample Gist" without files:
    Then he should receive response with code 422
    And response should have error message "Validation Failed"
