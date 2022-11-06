Feature: Assign existing event type to event
  As a user, I want to assign an existing event type to an event

  Background:
    Given the following users exist in the system:
      | id | username  |   password   |  created   |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
      | 02 |  bruceJ2  |  BrUcE_@214  | 2022-10-03 |
    Given the following Event Type exists in the system:
      | id |    name   | description |   ageRequirement   |
      | 01 | Pop Music |    Music    | No age requirement |
    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  |    type   |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |

  Scenario Outline: Attempting to add an existing event type to an event with one event type
    When the user "<User>" selects the event "<id>" with event type "<eventType>"
    When the user "<User>" attempts to assign an existing event type "<eventType1>" to an event 
    Then the system assigns the event type "<eventType1>" to event "<id>"
    And the event "<id>" has event types "<eventType>, <eventType1>"

    Examples:
      | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  |    type         |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music, Food |

  Scenario Outline: Attempting to change an existing event type to an event with one event type
    When the user "<User>" selects the event "<id>" with event type "<eventType>"
    When the user "<User>" attempts to replace an existing event type "<eventType>" with "<eventType1>" for event "<id>"
    Then the system assigns the event type "<eventType1>" to event "<id>"
    And the event "<id>" has event type "<eventType1>"

    Examples:
      | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  | type |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Food |

  Scenario Outline: Attempting to add an existing event type to an event that already has that event type
    When the user "<User>" selects the event "<id>" with event type "<eventType>"
    And event "<id>" has event type "<eventType>"
    When the user "<User>" attempts to assign an existing event type "<eventType1>" to event 
    Then the system rejects the request
    And the event "<id>" has event types "<eventType1>"

    Examples:
      | id |         name       | description | capacity | cost |   address   |     email          | phoneNumber  |    type   |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |