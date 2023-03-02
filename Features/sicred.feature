Feature: Sicred Challege

  Scenario: Should add a new costumer
    Given I access groceryCrud
    And I swap theme to "Bootstrap V4 Theme"
    When I Add a new costumer "Gabriel Silva de Azevedo"
    Then Should respond with "Your data has been successfully stored into the database. Edit Record or Go back to list"
    And Close browser

  Scenario: Should add a new costumer and delete it
    Given I access groceryCrud
    And I swap theme to "Bootstrap V4 Theme"
    When I Add a new costumer "Isadora Sampaio"
    Then Should respond with "Your data has been successfully stored into the database. Edit Record or Go back to list"
    When I delete the costumer "Isadora Sampaio"
    Then It shouldn't exists
    And Close browser