Feature: Show upcoming events of user

  Background:
    Given the following users exists in the system:
      | Id | username | password  | created    |
      | 1  | user1    | Password1 | 2022-01-01 |
      | 2  | user2    | Password2 | 2022-01-01 |
      | 3  | user3    | Password3 | 2022-01-01 |
      | 4  | user4    | Password4 | 2022-01-01 |
    Given the following events exist in the system:
      | Id| name  | description     | capacity  | cost  | address | email        | phoneNumber | start            | end              | isRecurrent | recurrences | organizerId |
      | 5 | Jazz  | Jazz concert    | 20        | 25    | 123 Ave | jazz@mail.ca | 123456789   | 2022-09-15 12:00 | 2022-09-15 23:59 | false       | []          | 4           |
      | 6 | Pop   | Pop concert     | 200       | 50    | 321 Ave | pop@mail.ca  | 987654321   | 2022-10-15 12:00 | 2022-10-15 23:59 | false       | []          | 4           |
      | 7 | Rap   | Rap concert     | 150       | 75    | 321 Ave | pop@mail.ca  | 987654321   | 2022-10-15 12:00 | 2022-10-15 23:59 | false       | []          | 4           |
    Given the following users are registered in the following events:
      | userId | event Ids |
      | 1      | [6,7]     |
      | 2      | [6]       |
      | 3      | [7]       |

  Scenario Outline: Query user list for event
    When the organizer with "<organizerId>" attempts to query the user list for the event with "<eventId>"
    Then the number of users shown is "<numUser>"
    Then the users with "<userIds>" will be shown

    Examples:
      | organizerId  | eventId | numUser   | userIds  |
      | 4            | 6       | 2         | [1,2]    |
      | 4            | 7       | 2         | [1,3]    |

  Scenario Outline: Query user list for an event fails
    When the organizer with "<organizerId>" attempts to query the user list for the event with "<eventId>"
    Then the system shall raise the error "<error>"

    Examples:
      | organizerId  | eventId | error                                         |
      | 3            | 6       | You are not the organizer of this event.      |
      | 4            | 5       | There are no users registered for this event. |


