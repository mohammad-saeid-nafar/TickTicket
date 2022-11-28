Feature: Add Type Preferences To User Profile
    As a user, I want to add a type reference my Profile

  Background:
    Given the following Users exist in the system:
      | Id  | username | password         | created    |
      | 01  | user1    | Password123      | 2022-01-13 |
    Given the following Event Type exists in the system:
      | id |    name     | description |   ageRequirement   |
      | 02 | Pop Music   |    Music    | No age requirement |
      | 03 | Street Food |    Food     | No age requirement |
      | 04 | Comedy      |    Comedy   | No age requirement |
    Given the following Profiles exist in the system:
      | Id  | firstName  | lastName    | address  |  dateOfBirth  | profilePicture   | email           | phoneNumber     | interests |
      | 05  | name1      | famName     | address1 |  2001-02-19   | "profile_pic_1"  | name1@mail.com  | (514)111-0000   |           |

  Scenario : Add 1 type preference to Profile successfully
    When the user with id "01" attempts to add the event type with id "02" to his profile
    Then the user with id <id> shall have the following <interests>:
        | id |    interests     |
        | 01 |    Pop Music     |

  Scenario: Add 3 type preference to Profile successfully
    When the user with id "01" attempts to add the event types with ids ["02","03","04"]  to his profile
    Then the user with id <id> shall have the following <interests>:
      | id |    interests                  |
      | 01 |    Pop Music, Food, Comedy    |

  Scenario: User attempts does not add any type preference to his profile
    When the user with id "01" attempts to add the event types with ids []  to his profile
    Then the user with id <id> shall have no interests
