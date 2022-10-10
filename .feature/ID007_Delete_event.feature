Feature: Delete an event (ID007)
  As an event organizer, I want to delete my event and remove it from the system.

  Background:
    Given the following users exist:
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
      | 02 | mary_jane |  JaneM123456 | 2022-09-17 |
      | 03 |  bruceJ2  |  BrUcE_@214  | 2022-10-03 |

    Given the following event types exists in the system:
      | id |    name      | description |   ageRequirement   |
      | 01 | Rock Concert |    Music    | 18                 |

    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email   | phoneNumber  |    eventTypes   | organizer |
      | 01 | The Beatles Tour   |  Music Tour |    600   |  250 | True Square | jb@mail.com | 456 789 1011 | [Rock Concert]  | johnDoe   |

    Given the following Event Schedule exists in the system:
      | Event            | id |       start      |        end       | isRecurrent |
      | The Beatles Tour | 01 | 2022-10-15 19:00 | 2022-10-15 22:00 | False       |

    Given the following tickets exist in the system:
      | User    |        Event       | id | BookingDate |
      | johnDoe | The Beatles Tour   | 01 | 2022-10-05  |
      | bruceJ2 | The Beatles Tour   | 02 | 2022-10-06  |

  Scenario Outline: Delete an event successfully
    Given I am logged in as "<username>" with password "<password>"
    When I am the organizer of the event "<event>"
    When the current date and time is "<currentDateTime>"
    When I delete the event "<event>"
    Then the event "<event>" is deleted from the system

    Examples:
      | username | password | event | currentDateTime
      | johnDoe  | myP@assword1 | The Beatles Tour | 2022-10-14 18:00

    Scenario Outline: Delete an event when the user is not the organizer
      Given I am logged in as "<username>" with password "<password>"
      When I am not the organizer of the event "<event>"
      When the current date and time is "<currentDateTime>"
      When I delete the event "<event>"
      Then the event "<event>" is not deleted from the system
      Then the following "<error>" should be raised

      Examples:
        | username | password | event | currentDateTime | error
        | mary_jane | JaneM123456 | The Beatles Tour | 2022-10-14 18:00 | NotOrganizerError
        | bruceJ2 | BrUcE_@214 | The Beatles Tour | 2022-10-14 18:00 | NotOrganizerError

    Scenario Outline: Delete an event when the current date and time is after the event start date and time
        Given I am logged in as "<username>" with password "<password>"
        When I am the organizer of the event "<event>"
        When the current date and time is "<currentDateTime>"
        When I delete the event "<event>"
        Then the event "<event>" is not deleted from the system
        Then the following "<error>" should be raised

        Examples:
          | username | password | event | currentDateTime | error
          | johnDoe  | myP@assword1 | The Beatles Tour | 2022-10-15 19:01 | EventStartedError



