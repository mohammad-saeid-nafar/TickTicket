Feature: Show upcoming events of user

  Background:
    Given the following users exists in the system:
      | Id | username | password  | created    |
      | 1  | user1    | Password1 | 2022-01-01 |
    Given the following events exist in the system:
      | Id| name  | description  | capacity  | cost  | address | email        | phoneNumber | start            | end              | isRecurrent | recurrences |
      | 2 | Jazz  | Jazz concert | 20        | 25    | 123 Ave | jazz@mail.ca | 123456789   | 2022-09-15 12:00 | 2022-09-15 23:59 | false       | []          |
      | 3 | Pop   | Pop concert  | 200       | 50    | 321 Ave | pop@mail.ca  | 987654321   | 2022-10-15 12:00 | 2022-10-15 23:59 | false       | []          |
    Given the following users are registered in the following events:
      | userId | event Ids |
      | 1      | [2,3]     |


  Scenario Outline: Show a user his upcoming events
    Given that the current date and time is "<dateTime>"
    When the user with "<userId>" attempts to see his upcoming events
    Then the number of events shown is "<numEvents>"
    Then the events with "<eventIds>" will be shown

    Examples:
      | dateTime         | userId | numEvents | eventIds |
      | 2022-10-10 12:00 | 1      | 1         | [3]      |
      | 2022-09-01 12:00 | 1      | 0         | [2,3]    |

  Scenario: Show a user his upcoming events fails
    Given that the current date and time is "2022-10-16 12:00"
    When the user with userId "1" attempts to see his upcoming events
    Then the system shall raise the error "You do not have any upcoming events."

