Feature: Find tickets by user

  As a user
  I would like to find tickets by user
 
  Background: 
    Given the following users exist in the system:
      | id          | username  | password   | created date  |
      | 1           | aly1      | Aly1235!   | 2022-10-01    |
      | 2           | aly2      | Aly1233!   | 2022-10-03    |

    Scenario: Successfully view tickets when none are existant 
    When the user attempts to find the tickets by user
    Then the number of tickets displayed shall be "0"
    Then there shall be no generated list

  Scenario: Successfully find tickets by date when 1 exists
    Given the following tickets exist in the system: 
      | id          | booking date      | event                       | user      |
      | 1           | 2022-11-30        | Graduation Wine and Cheese  | aly1      |
    When the user attempts to find a ticket by user and inputs "aly1"
    Then the number of tickets displayed shall be "1"
    Then the following list of tickets is generated:
      | id          | booking date      | event                       | user      |
      | 1           | 2022-01-01        | Graduation Wine and Cheese  | aly1      |

  Scenario: Successfully find tickets when 2 tickets exists
    Given the following tickets exist in the system: 
      | id          | booking date      | event                       | user      |
      | 1           | 2022-01-01        | Graduation Wine and Cheese  | aly1      |
      | 2           | 2022-05-05        | Classical Music Concert     | aly1      |  
    When the user attempts to find tickets by user and inputs "aly1"
    Then the number of tickets displayed shall be "2"
    Then the following list of tickets are generated:
      | id          | booking date      | event                       | user      |
      | 1           | 2022-01-01        | Graduation Wine and Cheese  | aly1      |
      | 2           | 2022-05-05        | Classical Music Concert     | aly1      |  