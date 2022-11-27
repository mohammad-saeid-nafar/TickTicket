Feature: Set age requirements for events (ID023)
    As an organizer of an event, I would like to set an age requirement for a specific event type
    so that I can ensure that only people of a certain age can attend this event.

    Background:

    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    | 0                  |


    Scenario: Set age requirement for an event type success
    When an organizer attempts to change the ageRequirement for the event with Id "01" to 18
    Then the event type should be updated to the following:
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    |   18               |

    Scenario: Set age requirement for an event type error
      When an organizer attempts to change the ageRequirement for the event with Id "01" to -1
      Then the following error will be displayed "Invalid age requirement"


