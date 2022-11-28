Feature: Suggest event to user (ID022)
  As a user, I want to have suggestions for events depending on my interests

  Background:
    Given the following users exist in the system:
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
      | 02 |  bruceJ2  |  BrUcE_@214  | 2022-10-03 |
    Given the following user profiles exists in the system:
      | User | id |   name    |    address   | dateOfBirth | profilePicture |       email       | phoneNumber  | interests        |
      | 03   | 01 | John Doe  | 120 Street 1 | 1999-02-22  |    img1.jpg    | johndoe@gmail.com | 438 566 3241 |     -            |
      | 04   | 02 | Bruce Jil | 11 Street 17 | 2000-04-30  |    img2.jpg    | bruce_j@gmail.com | 438 866 5551 | Pop Music, Party |
    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 05 | Pop Music |    Music    | 0                  |
      | 06 | Party     | Big Crowd   | 18                 |
    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  |    type   |
      | 07 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |

    Scenario: Suggest event to user
      When the user with id "02" attempts to view his suggested events
      Then the event with id "07" will be displayed

    Scenario: Suggest event to user who does not have any interests
      When the user with username "01" attempts to view his suggested events
      Then the error "User has no interests" will be displayed

  Scenario: Suggest event to non existent user who does not have any interests
    When the user with id "404" attempts to view his suggested events
    Then the error "User 404 not found." will be displayed
