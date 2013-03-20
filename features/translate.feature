Feature: Login feature

  Scenario: As a valid user I can log into my app
    And I see "Tili словарь"
    When I press "Перевести"
    Then I see "Введите слово для перевода"

    When I press "ө"
    When I press "ң"
    And I press "Перевести"
    Then I see "өң"
    Then I see "Поиск перевода"