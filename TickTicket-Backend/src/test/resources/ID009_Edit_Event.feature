Feature: Edit Event
  As a user, I want to edit an event

  Background: 
    Given the following Users exist in the system:
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly1235!   | 2022-10-01    |
    Given the following Events exist in the system:
      | id          | name               | description         | capacity   | cost   | address            | email            | phoneNumber  | organizerId |
      | 2           | event1             | description1        | 80         |  20    |  123 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | 1           |

  Scenario: Edit an Event successfully
    When the organizer attempts to update an event with id 2 with name "event2", description "description2", capacity 100, cost 50, address "321 rue Stanley", email "aly2@gmail.com" and phoneNumber "514-777-7777"
    Then the event with id 2 shall be updated with name "event2", description "description2", capacity 100, cost 50, address "321 rue Stanley", email "aly2@gmail.com" and phoneNumber "514-777-7777"

  Scenario : Edit an Event that does not exist
    When the organizer attempts to update an event with name "event3", description "description3", capacity 100, cost 50, address "321 rue Stanley", email "aly2@gmail.com" and phoneNumber "514-777-7777"
    Then the event with id 3 shall not be updated.
    Then the following "Event 3 not found" shall be raised.





