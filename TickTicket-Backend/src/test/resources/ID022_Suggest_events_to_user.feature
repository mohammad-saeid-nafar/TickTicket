Feature: Suggest event to user (ID022)
  As a user, I want to have suggestions for events depending on my interests

  Background:
    Given the following users exist in the system:
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
      | 02 |  bruceJ2  |  BrUcE_@214  | 2022-10-03 |
    Given the following user profiles exists in the system:
      | User | id |   name    |    address   | dateOfBirth | profilePicture |       email       | phoneNumber  | interests |
      | 01   | 01 | John Doe  | 120 Street 1 | 1999-02-22  |    img1.jpg    | johndoe@gmail.com | 438 566 3241 |     -     |
      | 02   | 02 | Bruce Jil | 11 Street 17 | 2000-04-30  |    img2.jpg    | bruce_j@gmail.com | 438 866 5551 | Pop Music |
    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    | No age requirement |
    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email   | phoneNumber  |    type   |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |

    Scenario: Suggest event to user
    Given I am logged in as "bruceJ2"
    And one of my interests is "Pop Music"
    When I am on the "Home" page
    Then I should see "Justin Bieber Tour" event
    
    Scenario: Suggest event to user who does not have any interests
    Given I am logged in as "johnDoe"
    And I do not have any interests
    When I am on the "Home" page
    Then the error "No suggestions for you" should be displayed
