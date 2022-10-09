Feature: Create User
  As a prospective member, I want to register a member account in the system

  Background:    
    Given the following members exist in the system:
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly12355   | 2022-10-01    |
      | 2           | aly2      | Aly12335   | 2022-10-03    |

  Scenario Outline: Update user password successfully
    When a new member attempts to update his password with "<username>", "<oldPassword>" , "<newPassword>"
    Then the user information  are updated to be "<id>" , "<username>", "<newPpassword>" , "<date>"
    Then the user's password is updated to be "<newPassword>".

    Examples: 
      | id          | username  | oldPassword | newPassword   |
      | 1           | aly1      | Aly12355    | Aly123556     |

  Scenario Outline: The user tries to update his password to "<newPassword>", he tries with a wrong password
    When a new member attempts to update his password with "<username>", "<oldPassword>" , "<newPassword>"
    Then the following "The old password entered is not correct. Please try again" shall be raised.
    When a new member attempts to update his password with "<username>", "<oldPassword>" , "<newPassword>"
    Then the user information  are updated to be "<id>" , "<username>", "<newPassword>" , "<date>"
    Then the user's password is updated to be "<newPassword>".

    Examples: 
      | id   | username  | oldPassword  | newPassword   | Message                                                     |
      | 1    | aly2      | Aly1235      | Aly123556     | The old password entered is not correct. Please try again   |
      | 1    | aly2      | Aly12335     | Aly123556     | Password updated successfully                               |

  Scenario Outline: Updating password failed
    When a new member attempts to update his password with "<username>", "<oldPassword>" , "<newPassword>"
    Then the following "<error>" shall be raised.
    Then the user password is not not updated

    Examples: 
      | id          | username  | oldPassword | newPassword   | error                                   |
      | 1           | aly1      | Aly12355    | Aly           | The new password entered is too short   |