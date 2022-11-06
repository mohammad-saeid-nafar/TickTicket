Feature: Add Type Preferences To User Profile
    As a user, I want to ad a type reference my Profile

  Background: 
    Given the following Users exist in the sytem:
      | Id  | username | password         | created    |
      | 001 | user1    | Password123      | 2022-01-13 |
      | 002 | user2    | Password456      | 2021-06-17 |
    Given the following Event Type exists in the system:
      | id |    name     | description |   ageRequirement   |
      | 01 | Pop Music   |    Music    | No age requirement |
      | 02 | Street Food |    Food     | No age requirement |
    Given the following Profiles exist in the system:
      | Id  | name  | address  |  dateOfBirth  | profilePicture   | email           | phoneNumber     | interests |
      | 001 | name1 | address1 |  2001-02-19   | "profile_pic_1"  | name1@mail.com  | (514)111-0000   |           |
      | 002 | name2 | address2 |  2000-05-17   | "profile_pic_2"  | name2@mail.com  | (514)222-0000   |           |

  Scenario Outline: Add 1 type preference to Profile successfully
    Given the member logs in for the first time after signin up
    And the system prompts the user with the event types available in the system
    When the member with "<Id>" attempts to add a type preference "<eventType1>"
    And the member submits their choice
    Then the interests of the member with "<Id>" in their profile shall be updated with ["<eventType1>"]

    Examples: 
      | Id  | name  | address  |  dateOfBirth  | profilePicture   | email           | phoneNumber     |  interests   |
      | 001 | name1 | address1 |  2001-02-19   | "profile_pic_1"  | name1@mail.com  | (514)111-0000   |  eventType1  |
      | 002 | name2 | address2 |  2000-05-17   | "profile_pic_2"  | name2@mail.com  | (514)222-0000   |  eventType1  |

  Scenario Outline: Add 3 type preference to Profile successfully
    Given the member logs in for the first time after signin up
    And the system prompts the user with the event types available in the system
    When the member with "<Id>" attempts to add 3 type preferences "<eventType1>", "<eventType2>", "<eventType3>"
    And the member submits their choices
    Then the interests of the member with "<Id>" in their profile shall be updated with ["<eventType1>", "<eventType2>", "<eventType3>"]

    Examples: 
      | Id  | name  | address  |  dateOfBirth  | profilePicture   | email           | phoneNumber     | interests    |
      | 001 | name1 | address1 |  2001-02-19   | "profile_pic_1"  | name1@mail.com  | (514)111-0000   | eventType1, eventType2, eventType3 |
      | 002 | name2 | address2 |  2000-05-17   | "profile_pic_2"  | name2@mail.com  | (514)222-0000   | eventType1, eventType2, eventType3 |

  Scenario Outline: Skip and do not add any preference
    Given the member logs in for the first time after signin up
    And the system prompts the user with the event types available in the system
    When the member with "<Id>" does not select any perferences
    And the member skips the prompted choices
    Then the interests of the member with "<Id>" in their profile shall not be updated

    Examples: 
      | Id  | name  | address  |  dateOfBirth  | profilePicture   | email           | phoneNumber     | interests    |
      | 001 | name1 | address1 |  2001-02-19   | "profile_pic_1"  | name1@mail.com  | (514)111-0000   |              |
      | 002 | name2 | address2 |  2000-05-17   | "profile_pic_2"  | name2@mail.com  | (514)222-0000   |              |