Feature: Set age requirements for events (ID023)
    As an organizer of an event, I would like to set an age requirement for this specific event
    so that I can ensure that only people of a certain age can attend this event.

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
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    | No age requirement |
    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  |    type   |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |
    Given the following Event Schedule exists in the system:
      | Event | id |       start      |        end       |
      | 01    | 01 | 2022-10-15 19:00 | 2022-10-15 22:00 |

    Scenario: Set age requirement for an event
    Given I am logged in as "johnDoe"
    When I am on the "Event Details" page for event "Justin Bieber Tour"
    And I click on the "Edit Event" button
    And I set the "Age Requirement" to "18+"
    Then the event should be updated to the following:
    | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  |    type   | ageRequirement |
    | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |     18+        |
    Then no user who are younger than 18 should be able to get a ticket for the event   

    Scenario: Set age requirement for an event error
    Given I am logged in as "bruceJ2"    
    When I am on the "Event Details" page for event "Justin Bieber Tour"
    Then I should not see the "Edit Event" button
    Then I cannot change the age requirement for the event
    And I should see the following error message "You are not authorized to edit this event"


