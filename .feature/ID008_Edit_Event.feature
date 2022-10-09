Feature: Edit Event
  As a user, I want to edit an event

  Background: 
    Given the following Users exist in the system:
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly1235!   | 2022-10-01    |
      | 2           | aly2      | Aly1233!   | 2022-10-03    |
    Given the following Events exist in the system:
      | id          | name               | description         | capacity   | cost   | address            | email            | phoneNumber  |
      | 1           | event1             | description1        | 80         |  20    |  123 rue Stanley   |  aly1@gmail.com  | 514-888-8888 |
      | 2           | event2             | description2        | 450        |  20    |  123 rue Stanley   |  aly1@gmail.com  | 514-888-8888 |
      | 3           | event3             | description3        | 130        |  80    |  123 rue Stanley   |  aly2@gmail.com  | 514-888-8888 |  

  Scenario: Edit an Event successfully
    When the user "<User>" attempts to update an event with "<Id>" with "<newName>", "<newDescription>", "<newCapacity>", "<newCost>", "<newAddress>", "<newEmail>" and "<newPhoneNumber>"
    Then the event with "<Id>" shall be updated with "<newName>", "<newAddress>", "<newProfilePicture>", "<newEmail>", "<newDateOfBirth>", and "<newPhoneNumber>"
    Then there are "<numberOfEvents>" in the system.

  Examples: 
      | id          | name               | description         | capacity   | cost   | address            | email            | phoneNumber  |
      | 1           | event1             | newDescription1     | 100        |  100   |  456 rue Stanley   |  aly1@gmail.com  | 514-777-7777 |
      | 2           | event2             | newDescription2     | 450        |  200   |  456 rue Stanley   |  aly2@gmail.com  | 514-777-7777 |
      | 3           | event3             | newDescription3     | 130        |  800   |  456 rue Stanley   |  aly2@gmail.com  | 514-777-7777 |  

Scenario: Edit an Event that does not exist
    When the user "<User>" attempts to update an event with "<Id>" with "<newName>", "<newDescription>", "<newCapacity>", "<newCost>", "<newAddress>", "<newEmail>" and "<newPhoneNumber>"
    Then the event with "<Id>" shall not be updated.
    Then the following "<error>" shall be raised.
    Then there are "<numberOfEvents>" in the system.

  Examples: 
      | id          | name                 | description         | capacity   | cost   | address            | email            | phoneNumber  | error
      | 4           | nonExistingEvent1    | newDescription1     | 100        |  100   |  456 rue Stanley   |  aly1@gmail.com  | 514-777-7777 | The event does not exist
      | 5           | nonExistingEvent2    | newDescription2     | 450        |  200   |  456 rue Stanley   |  aly2@gmail.com  | 514-777-7777 | The event does not exist
      | 6           | nonExistingEvent3    | newDescription3     | 130        |  800   |  456 rue Stanley   |  aly2@gmail.com  | 514-777-7777 | The event does not exist

Scenario: Edit an Event unsuccessfully
    When the user "<User>" attempts to update an event with "<Id>" with "<newName>", "<newDescription>", "<newCapacity>", "<newCost>", "<newAddress>", "<newEmail>" and "<newPhoneNumber>"
    Then the event with "<Id>" shall not be updated.
    Then the following "<error>" shall be raised.
    Then there are "<numberOfEvents>" in the system.

Examples: 
      | id          | name          | description         | capacity   | cost   | address            | email            | phoneNumber  | error
      | 1           |               | newDescription1     | 100        |  100   |  456 rue Stanley   |  aly1@gmail.com  | 514-777-7777 | The name cannot be empty
      | 2           | event2        |                     | 450        |  200   |  456 rue Stanley   |  aly2@gmail.com  | 514-777-7777 | The description cannot be empty
      | 3           | event3        | newDescription3     |            |  800   |  456 rue Stanley   |  aly1@gmail.com  | 514-777-7777 | The capacity cannot be empty
      | 4           | event4        | newDescription4     | 100        |        |  456 rue Stanley   |  aly2@gmail.com  | 514-777-7777 | The cost cannot be empty
      | 5           | event5        | newDescription5     | 450        |  200   |                    |  aly1@gmail.com  | 514-777-7777 | The address cannot be empty
      | 6           | event6        | newDescription6     | 130        |  800   |  456 rue Stanley   |                  | 514-777-7777 | The email cannot be empty
      | 7           | event7        | newDescription7     | 130        |  800   |  456 rue Stanley   |  aly2@gmail.com  |              | The phone number cannot be empty






