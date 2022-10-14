Feature: Edit User Profile
  As a user, I want to edit my Profile

  Background: 
    Given the following Users exist in the sytem:
      | Id  | username | password         | created    |
      | 001 | user1    | Password123      | 2022-01-13 |
      | 002 | user2    | Password456      | 2021-06-17 |
    Given the following Profiles exist in the system:
      | Id  | name  | address  |  dateOfBirth  | profilePicture   | email           | phoneNumber     |
      | 001 | name1 | address1 |  2001-02-19   | "profile_pic_1"  | name1@mail.com  | (514)111-0000   |
      | 002 | name2 | address2 |  2000-05-17   | "profile_pic_2"  | name2@mail.com  | (514)222-0000   |

  Scenario Outline: Edit a User Profile successfully
    When the member with "<Id>" attempts to update their account with "<newName>", "<newAddress>", "<newProfilePicture>", "<newEmail>", "<newDateOfBirth>", and "<newPhoneNumber>"
    Then the member account with "<Id>" shall be updated with "<newName>", "<newAddress>", "<newProfilePicture>", "<newEmail>", "<newDateOfBirth>", and "<newPhoneNumber>"
    Then there are 2 members in the system.

    Examples: 
      | Id  | name     | address     |  dateOfBirth  | profilePicture       | email              | phoneNumber     |
      | 001 | newName1 | newAddress1 |  1999-03-25   | "new_profile_pic_1"  | newName1@mail.com  | (514)000-1111   |
      | 002 | newName2 | newAddress2 |  2001-08-02   | "new_profile_pic_2"  | newName2@mail.com  | (514)000-2222   |

  Scenario Outline: Edit a User Profile successfully when only editing the phone number
    When the member with "<Id>" attempts to update their account with "<newPhoneNumber>"
    Then the member account with "<Id>" shall be updated with "<newPhoneNumber>"
    Then there are 2 members in the system.

    Examples: 
      | Id  | name     | address     |  dateOfBirth  | profilePicture       | email              | phoneNumber     |
      | 001 | name1    | address1    |  2001-02-19   | "profile_pic_1"      | name1@mail.com     | (514)123-4567   |
      | 002 | name2    | address2    |  2000-05-17   | "profile_pic_2"      | name2@mail.com     | (514)891-1011   | 

  Scenario Outline: Edit a User Profile unsuccessfully
    When the member with "<Id>" attempts to update their account with "<newName>", "<newAddress>", "<newProfilePicture>", "<newEmail>", "<newDateOfBirth>", and "<newPhoneNumber>"
    Then the following "<error>" shall be raised.
    Then there are 2 members in the system.

    Examples: 
      | Id  | name     | address     |  dateOfBirth  | profilePicture       | email              | phoneNumber     | error
      | 001 |          | newAddress1 |  1999-03-25   | "new_profile_pic_1"  | newName1@mail.com  | (514)000-1111   | The name cannot be empty 
      | 001 | newName1 |             |  1999-03-25   | "new_profile_pic_1"  | newName1@mail.com  | (514)000-1111   | The address cannot be empty
      | 001 | newName1 | newAddress1 |               | "new_profile_pic_1"  | newName1@mail.com  | (514)000-1111   | The date of birth cannot be empty
      | 001 | newName1 | newAddress1 |  1999-03-25   | "new_profile_pic_1"  |                    | (514)000-1111   | The email cannot be empty
      | 001 | newName1 | newAddress1 |  1999-03-25   | "new_profile_pic_1"  | abcdefg            | (514)000-1111   | The email provided is not a valid email
      | 001 | newName1 | newAddress1 |  1999-03-25   | "new_profile_pic_1"  | newName1@mail.com  |                 | The phone number cannot be empty
      | 001 | newName1 | newAddress1 |  1999-03-25   | "new_profile_pic_1"  | newName1@mail.com  | 222             | The phone number provided is not a valid phone number