Feature: Find a ticket to an event but its ID
  As a user, I wish to find a ticket to an event by its ID, so that I can view the details of the ticket

  Background:
    Given the following users exist in the system:
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
      | 02 |  bruceJ2  |  BrUcE_@214  | 2022-10-03 |
    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 02 | Pop Music |    Music    | 0                  |
    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  |    type   | organizerId  |
      | 03 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music | 01            |
    Given the following Event Schedule exists in the system:
      | id    | Event id |       start      |        end       |
      | 04    | 03       | 2022-10-15 19:00 | 2022-10-15 22:00 |
    Given the following tickets exist in the system:
      | id    | eventId | userId            |  bookingDate         |
      | 05    | 03      | 02                |  2022-10-14 19:00    |

  Scenario: Find ticket to an event by its valid ID
    When a user attempts to find a ticket with id "05"
    Then the following tickets will be displayed:
      | id    | eventId | userId            |  bookingDate         |
      | 05    | 03      | 02                |  2022-10-14 19:00    |

  Scenario: Find ticket to an event with an invalid ID
    When a user attempts to find a ticket with id "06"
    Then the following error will be displayed ""Ticket 06 not found"