Feature: Create User
  As a prospective member, I want to register a member account in the system

  Background: 
   
    Given the following members exist in the system:
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly1235!    | 2022-10-01    |
      | 2           | aly2      | Aly1233!   | 2022-10-03    |

  Scenario Outline: Register a member account successfully
    When a new member attempts to register with "<id>" , "<username>", "<password>" , "<date>"
    Then a new member account shall exist with "<id>" , "<username>", "<password>" , "<date>"
    Then there are 3 members in the system.

    Examples: 
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly1235!   | 2022-10-01    |
      | 2           | aly2      | Aly1234!   | 2022-10-03    |
      | 3           | aly3      | Aly12345!  | 2022-10-05    |   

  Scenario Outline: The user tries to create an account with a used username, he tries with a new username that is available and registers successfully
    When a new member attempts to register with "<id>" , "<username>", "<password>" , "<date>"
    Then the following "An account with this username already exists, please try another username" shall be raised.
    When a new member attempts to register with "<id>" , "<username2>", "<password>" , "<date>"
    Then a new member account shall exist with "<id>" , "<username2>", "<password>" , "<date>"
    Then there are 4 members in the system.

    Examples: 
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly1235!   | 2022-10-01    |
      | 2           | aly2      | Aly1234!   | 2022-10-03    |
      | 3           | aly3      | Aly12345!  | 2022-10-05    | 
      | 4           | aly4      | Aly12345!  | 2022-10-05    | 


  Scenario Outline: Register member account failed
    When a new member attempts to register with "<id>" , "<email>" , "<password>"
    Then the following "<error>" shall be raised.
    Then there are 4 members in the system.
    Then there is no member account for "<email>"

    Examples: 
      | id          | username  | password   | createdDate   | error
      | 1           | aly1      | Aly1235!   | 2022-10-01    | An account with this username already exists, please try another username
      | 2           | newUser   | aly1234!   | 2022-10-03    | Please have at least one upper case letter, one lower case letter, one number, and one special character
      | 3           | newUser2  | Aly        | 2022-10-05    | Password is too short, please have a password with at least 8 characters