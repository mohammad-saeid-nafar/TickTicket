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
    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    | No age requirement |
    Given the following events exist in the system: 
      | id |         name       | description | capacity | cost |   address   |     email   | phoneNumber  |    type   | 
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | jb@mail.com | 456 789 1011 | Pop Music |
    Given the following Event Schedule exists in the system:
      | Event | id |       start      |        end       | isRecurrent | 
      | 01    | 01 | 2022-10-15 19:00 | 2022-10-15 22:00 | False       |
    Given the following tickets exist in the system:
      | User    |        Event       | id | BookingDate |  
      | johnDoe | Justin Bieber Tour | 01 | 2022-10-05  |
      | bruceJ2 | Justin Bieber Tour | 02 | 2022-10-06  |

  Scenario Outline: Delete a user account successfully with deletion of tickets
    When the user attempts to delete the account with username "<target>" and password "<password>" 
    Then there are "<numberOfUsers>" users in the system. 
    Then there are "<numberOfProfiles>" profiles in the system. 
    Then there are "<numberOfTickets>" tickets in the system.

    Examples: 
      | target    |    password  | numberOfUsers | numberOfProfiles | numberOfTickets | 
      | johnDoe   | myP@assword1 |             1 |                1 |               1 |

  Scenario Outline: Attempting to delete a user that does not exist
    When the user attempts to delete the user account with username "<target>" and password "<password>"
    Then the user account with the username "<target>" does not exist 
    Then the following "<error>" should be raised.
    Then the number of users in the system is 3 
    Then the number of profiles in the system is 3 
    Then the number of tickets in the system is 2 

    Examples: 
      | target      | password | error                        |
      | lea_jillina | Lea12345 | This username does not exist |

    Scenario Outline: Attempting to delete a user with an incorrect password
    When the user attempts to delete the user account with username "<target>" and password "<password>"
    Then the user account with the username "<target>" does not exist 
    Then the following "<error>" should be raised.
    Then the number of users in the system is 3 
    Then the number of profiles in the system is 3 
    Then the number of tickets in the system is 2 

    Examples: 
      | target    |   password  | error                                                    |
      |johnDoe   | myP@assword211 | The account cannot be deleted with an incorrect password |
