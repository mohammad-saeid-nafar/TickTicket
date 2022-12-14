Feature: Book a ticket for an event
  As a user/attendee, I want to book a ticket for an event, so that I can attend the event

  Background:
    Given the following user is logged in:
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    | No age requirement |
    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  |    type   |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |
    Given the following Event Schedule exists in the system:
      | Event | id |       start      |        end       |
      | 01    | 01 | 2022-10-15 19:00 | 2022-10-15 22:00 |

  Scenario Outline: Book a ticket for an event successfully
    When the user "<user>" attempts to book a ticket for the event "<event>" on "<date>" at "<time>"
    Then the ticket is created successfully
    Then the user should be able to see the ticket for the event "<event>"

    Examples:
      | user      | event              | date       | time       |
      | johnDoe   | Justin Bieber Tour | 2022-10-10 | 15:00:00   |

  Scenario Outline: Book a ticket for a user that is not in the system
    When the user "<user>" attempts to book a ticket for the event <event> on <date> at <time>
    Then the ticket is not created
    Then the system should display the error message <eror> "User not found"

    Examples:
      | user      | event                  | date       | time       | error          |
      | johnDoe   | Justin Timberlake Tour | 2022-10-10 | 15:00:00   | User not found |

  Scenario Outline: Book a ticket for an event that is not in the system
    When the user "<user>" attempts to book a ticket for the event "<event>" on "<date>" at "<time>"
    Then the ticket is not created
    Then the system should display the error message <error> "Event not found"

    Examples:
      | user      | event                  | date       | time       | error           |
      | johnDoe   | Justin Timberlake Tour | 2022-10-10 | 15:00:00   | Event not found |