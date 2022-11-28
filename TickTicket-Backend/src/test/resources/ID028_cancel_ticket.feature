Feature: Cancel ticket
  As a user, 
  I want to cancel one of my tickets
  As I will not be able to attend

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

    Scenario: Successfully cancel ticket
      Given the following tickets exist in the system:
        | user      | event              | date       | time       |
        | johnDoe   | Justin Bieber Tour | 2022-10-10 | 15:00:00   |
      When the user "<user>" attempts to cancel the ticket <ticketID> with <userID> and <eventID>
      Then the ticket will be cancel successfully