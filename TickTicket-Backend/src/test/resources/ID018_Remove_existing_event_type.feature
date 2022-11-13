Feature: Remove existing event type (ID018)
As an organizer, I would like to remove an event type I created

Background:
    Given the following users exist in the system:
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
      | 02 |  bruceJ2  |  BrUcE_@214  | 2022-10-03 |
    Given the following user profiles exists in the system:
      | User | id |   name    |    address   | dateOfBirth | profilePicture |       email       | phoneNumber  |
      | 01   | 01 | John Doe  | 120 Street 1 | 1999-02-22  |    img1.jpg    | johndoe@gmail.com | 438 566 3241 |
      | 02   | 02 | Bruce Jil | 11 Street 17 | 2000-04-30  |    img2.jpg    | bruce_j@gmail.com | 438 866 5551 |
    Given the following Event Type exists in the system:
      | id |    name    | description |   ageRequirement   |
      | 01 | Pop Music  |    Music    | No age requirement |
      | 02 | targetName | description | 14                 | 
    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email   | phoneNumber  |    type   |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |
    Given the following Event Schedule exists in the system:
      | Event | id |       start      |        end       |
      | 01    | 01 | 2022-10-15 19:00 | 2022-10-15 22:00 |

    Scenario Outline: Attempting to remove an event type successfully
    When "johnDoe" attempts to remove the event type "targetName"
    And "johnDoe" is the creator of this event type
    Then the event type "targetName" is removed from the system
    Examples:

      | id |    name    | description |   ageRequirement   |
      | 01 | Pop Music  |    Music    | No age requirement |
      

  Scenario Outline: Attempting to remove an event type that was created by another user
  When "bruceJ2" attempts to remove the event type "targetName"
  And "bruceJ2" is not the creator of this event type
  Then the event type "targetName" is not removed from the system
  And the error message "You are not the creator of this event type" is displayed

  Scenario Outline: Attempting to remove an event type that is in use
  When "johnDoe" attempts to remove the event type "Pop Musice"
  Then the event type "Pop Music" is not removed from the system
  And the error message "This event type is in use" is displayed