Feature: Delete a user (ID004)
  As a user, I want to delete my user account

  Background: 
    Given the following users exist in the system: 
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
      | 02 |  bruceJ2  |  BrUcE_@214  | 2022-10-03 |
    Given the following user profiles exists in the system: 
      | User | id |   name    |    address   | dateOfBirth | profilePicture |       email       | phoneNumber  |
      | 01   | 01 | John Doe  | 120 Street 1 | 1999-02-22  |    img1.jpg    | johndoe@gmail.com | 438 566 3241 |
      | 02   | 02 | Bruce Jil | 11 Street 17 | 2000-04-30  |    img2.jpg    | bruce_j@gmail.com | 438 866 5551 |

  Scenario Outline: Delete a user account successfully with deletion of tickets
    When the user attempts to delete the account with username "<target>" and password "<password>" 
    Then there are "<numberOfUsers>" users in the system. 
    Then there are "<numberOfProfiles>" profiles in the system.

    Examples: 
      | target    |    password  | numberOfUsers | numberOfProfiles |
      | johnDoe   | myP@assword1 |             1 |                1 |

  Scenario Outline: Attempting to delete a user that does not exist
    When the user attempts to delete the user account with username "<target>" and password "<password>"
    Then the user account with the username "<target>" does not exist 
    Then the following "<error>" should be raised.
    Then the number of users in the system is 3 
    Then the number of profiles in the system is 3

    Examples: 
      | target      | password | error                        |
      | lea_jillina | Lea12345 | User lea_jillina not found   |

    Scenario Outline: Attempting to delete a user with an incorrect password
    When the user attempts to delete the user account with username "<target>" and password "<password>"
    Then the user account with the username "<target>" does not exist 
    Then the following "<error>" should be raised.
    Then the number of users in the system is 3 
    Then the number of profiles in the system is 3

    Examples: 
      | target    |   password  | error                                                    |
      |johnDoe   | myP@assword211 | The account cannot be deleted with an incorrect password |
