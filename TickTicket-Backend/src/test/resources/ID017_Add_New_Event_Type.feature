Feature: Add new event type (ID017)
  As a user, I want to add a new event type

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
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |
    Given the following Event Schedule exists in the system:
      | Event | id |       start      |        end       |
      | 01    | 01 | 2022-10-15 19:00 | 2022-10-15 22:00 |

  Scenario Outline: Attempting to add a new event type successfully
    When the user "<User>" attempts to create an event type with name "<targetName>", description "<description>" and age requirement "<ageRequirement>"
    Then there are 2 event types in the system
    Then the event type is created

    Examples:

      | id |    name    | description |   ageRequirement   |
      | 01 | Pop Music  |    Music    | No age requirement |
      | 02 | targetName | description | ageRequirement     | 

  Scenario Outline: User attempts to create an event type with a used name, then tries with a new name that is available and creates successfully
    When the user "<User>" attempts to create an event type with name "Pop Music", description "<description>" and age requirement age requirement "<ageRequirement>"
    Then the following "An event type with this name already exists" shall be raised.
    When the user "<User>" attempts to create an event type with name "<targetName>", description "<description>" and age requirement age requirement "<ageRequirement>"
    Then there are 2 event types in the system 

    Examples:

      | id |    name    | description |   ageRequirement   |
      | 01 | Pop Music  |    Music    | No age requirement |
      | 02 | targetName | description | ageRequirement     | 



      | id |    name    | description   |   ageRequirement   | error                                       |
      | 02 | Pop Music  | <description> | No age requirement | An event type with this name already exists |

  Scenario Outline: User unsuccessfully attempts to creates an Event Type 
    When the user "<User>" attempts to create an event with name "<targetName>", description "<description>", and age requirement age requirement "<ageRequirement>"
    Then the following "<error>" shall be raised.
    Then there is still 1 evet type in the system.

    Examples:

      | id |    name      | description   |   ageRequirement   | error                                                                         |
      | 02 | Pop Music    | <description> | No age requirement | An event type with this name already exists                                   |
      | 02 |              | <description> | No age requirement | The name field of an Event Type cannot be empty                               |
      | 02 | <targetName> |               | No age requirement | The description field of an Event Type cannot be empty                        |
      | 02 | <targetName> | <description> |                    | Please provide an age requirement or specify that there is no age requirement |
