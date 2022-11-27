Feature: Add new event type (ID017)
  As a user, I want to add a new event type

  Background:

    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    |  0                 |

  Scenario Outline: Attempting to add a new event type successfully
    When the user  attempts to create an event type with name "<targetName>", description "<description>" and age requirement "<ageRequirement>"
    Then the event type is created

    Examples:

      |    name    | description |   ageRequirement   |
      | Party      | Big Party   | 18                 |

  Scenario Outline: User attempts to create an event type with a used name
    When the user "<User>" attempts to create an event type with name <name>, description "<description>" and age requirement age requirement "<ageRequirement>"
    Then the following "An event type with this name already exists" shall be raised.
    Examples:

      |    name    | description |   ageRequirement   |
      | Pop Music  |    Music    | 0                  |


  Scenario Outline: User unsuccessfully attempts to creates an Event Type 
    When the user "<User>" attempts to create an event with name "<targetName>", description "<description>", and age requirement age requirement "<ageRequirement>"
    Then the following "<error>" shall be raised.

    Examples:
      |    name      | description   |   ageRequirement   | error                                                                         |
      |              | description   | 0                  | Name of event type cannot be blank                                            |
      | testName     |               | 0                  | Description of event type cannot be blank                                   |
      | testName     | description   | -1                 | Invalid age requirement                                                      |
