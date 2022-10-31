Feature: Add new event (ID006)
  As a user, I want to add a new event

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
    Given the following tickets exist in the system:
      | User    |        Event       | id | BookingDateTime |
      | johnDoe | Justin Bieber Tour | 01 | 2022-10-05 19:00  |
      | bruceJ2 | Justin Bieber Tour | 02 | 2022-10-06 20:00  |

  Scenario Outline: Attempting to add a new event succesfully
    When the user "<User>" attempts to create an event with name "<targetName>", descritption "<description>", capacity "<capacity>", cost "<cost>", address "<address>" and type "<type>"
    When the user "<User>" attempts to create a new Event Schedule starting on "<startDateTime>" and ending on "<endDateTime>"
    Then there are "<numberOfEvents>" users in the system.
    Then there are "<numberOfTickets>" tickets in the system.

    Examples:
      | User    |     targetName    | description | capacity | cost |   address   |   type    |   startDateTime  |    endDateTime   | numberOfEvents | numberOfTickets |
      | johnDoe | Taylor Swift Tour | Music Tour  | 560      | 300  | Bell Center | Pop Music | 2022-12-01 20:00 | 2022-12-01 22:00 |              2 |            1160 |

  Scenario Outline: Attempting to add a new event at the same time as another existing event successfully
    When the user "<User>" attempts to create an event with name "<targetName>", descritption "<description>", capacity "<capacity>", cost "<cost>", address "<address>" and type "<type>"
    When the user "<User>" attempts to create a new Event Schedule starting on "<startDateTime>" and ending on "<endDateTime>"
    Then there are "<numberOfEvents>" users in the system.
    Then there are "<numberOfTickets>" tickets in the system.

    Examples:
      | User    |     targetName    | description | capacity | cost |   address   |   type    |   startDateTime  |    endDateTime   | numberOfEvents | numberOfTickets |
      | johnDoe | Taylor Swift Tour | Music Tour  | 560      | 300  | Bell Center | Pop Music | 2022-10-15 19:00 | 2022-10-15 22:00 |              2 |            1160 |

  Scenario Outline: Attempting to add a new event with non existant event type
    When the user "<User>" attempts to create an event with name "<targetName>", descritption "<description>", capacity "<capacity>", cost "<cost>", address "<address>" and type "<type>"
    Then the event with name "<targetName>" is not created.
    Then the following "<error>" should be raised.
    Then there are "<numberOfEvents>" users in the system.
    Then there are "<numberOfTickets>" tickets in the system.

    Examples:
      | User    | targetName | description | capacity | cost | address | type  | startDateTime    |    endDateTime   | numberOfEvents | numberOfTickets | error                    |
      | johnDoe | US Open    | Tennis Game | 300      | 140  | Court 5 | Sport | 2022-12-01 13:00 | 2022-12-01 15:00 |              1 |             600 | Event Type doesn't exist |

  Scenario Outline: Attempting to add a new event that already exists
    When the user "<User>" attempts to create an event with name "<targetName>", descritption "<description>", capacity "<capacity>", cost "<cost>", address "<address>" and type "<type>"
    Then the event with name "<targetName>" is not created.
    Then the following "<error>" should be raised.
    Then there are "<numberOfEvents>" users in the system.
    Then there are "<numberOfTickets>" tickets in the system.

    Examples:
      | User    |     targetName     | description | capacity |   address   | cost |   type    |   startDateTime  |    endDateTime   | numberOfEvents | numberOfTickets | error                    |
      | johnDoe | Justin Bieber Tour | Music Tour  | 600      | True Square | 250  | Pop Music | 2022-10-15 19:00 | 2022-10-15 22:00 |              1 |             600 | The event already exists |