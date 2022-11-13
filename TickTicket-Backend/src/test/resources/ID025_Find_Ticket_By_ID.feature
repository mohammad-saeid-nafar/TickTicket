Feature: Find a ticket to an event but its ID
  As a user, I wish to find a ticket to an event by its ID, so that I can view the details of the ticket if it's mine
  As an event organizer, I want to find a ticket to an event by its ID so that I can view the details of the ticket to my event

  Background:
    Given the following user is logged in:
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    | No age requirement |
    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email   | phoneNumber  |    type   |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |
    Given the following Event Schedule exists in the system:
      | Event | id |       start      |        end       |
      | 01    | 01 | 2022-10-15 19:00 | 2022-10-15 22:00 |

  Scenario Outline: Find ticket to an event by its valid ID belonging to the user logged in
    When I search for the ticket with id <id>
    And the ticket with id <id> exists in the system
    And the ticket with id <id> belongs to the user logged in
    Then I should see the ticket with id <id>
    And I should see the ticket with event <event>
    And I should see the ticket with event schedule <eventSchedule>
    And I should see the ticket with user <user>
    And I should see the ticket with event price <price>
    And I should see the ticket with event capacity <capacity>

    Examples:
      | id |  event  |  eventSchedule  |  user  |  price  |  capacity  |
      | 01 | Justin Bieber Tour | 2022-10-15 19:00 | johnDoe |   250    |     600    |

  Scenario Outline: Find ticket to an event by its valid ID not belonging to the user logged in
    When I search for the ticket with id <id>
    And the ticket with id <id> exists in the system
    And the ticket with id <id> does not belong to the user logged in
    Then the system should not find the ticket with id <id>
    And I should see the error message "You do not have access to this ticket"

    Examples:
      | id |
      | 01 |

  Scenario Outline: Find ticket to an event by its invalid ID
    When I search for the ticket with id <id>
    And the ticket with id <id> does not exist in the system
    Then the system should not find the ticket with id <id>
    And I should see the error message "Ticket with id <id> was nto found"

    Examples:
      | id |
      | 03 |