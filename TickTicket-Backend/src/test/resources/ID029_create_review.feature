Feature: Creat a review for an event
  As a user/attendee, I want to create a review for an event, that I have attended

  Background:
    Given the following Users exist in the system:
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly1235!   | 2022-10-01    |
      | 2           | aly2      | Aly1233!   | 2022-10-03    |
      | 3           | aly3      | Aly1233!   | 2022-10-03    |
    Given the following events exist in the system:
      | id          | name              | description                 | capacity    | cost   |  address            | email            | phoneNumber  | type     | organizerId | start             | end                 |
      | 4           | Wine and Cheese   | Graduation Wine and Cheese  | 80          |  20    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Food    | 1            | 2022-10-05 12:00  |  2022-10-05 21:00   |
      | 5           | Pop               | Pop concert                 | 150         |  20    |  321 Ave            |  pop@mail.ca     | 514-888-8888 | Music   | 1            | 2022-12-05 12:00  |  2022-12-05 21:00   |
    Given the following tickets exist in the system:
      | id          | date           | time      | userID    | eventID  |
      | 1           | 2022-10-10     | 15:00:00  | 2         |  4       |

    Scenario: Successfully create a review
      When the user "<user>" attempts to create a review for the event "<event>" with the "<title>", "<rating>" and "<description>"
      Then the review is created successfully
      And the following reviews will exist in the system:
        | id  | title   | rating  | description| event           | userId  | eventId |
        |  1  | Good    | 4       | Good Event | Wine and Cheese | 2       | 4       |

      Examples:
      | user      | title    | rating       | description       | event              | 
      | aly2      | Good     | 4            | Good Event        |  Wine and Cheese   |

    Scenario: Error when creating a review for an event you have not attended
      When the user "<user>" attempts to create a review for the event "<event>" with the "<title>", "<rating>" and "<description>"
      Then the review will not be created successfully
      And the system should display the error message "You did not buy a ticket for this event"

      Examples:
      | user      | title    | rating       | description       | event              | 
      | aly2      | Good     | 4            | Good Event        |  Pop               | 

    Scenario: Error when creating a review for an nonexisting event
      When the user "<user>" attempts to create a review for the event "<event>" with the "<title>", "<rating>" and "<description>"
      Then the review will not be created successfully
      And the system should display the error message "Event <event> not found"
      
      Examples:
      | user      | title    | rating       | description       | event              | 
      | aly1      | Good     | 4            | Good Event        |  Protest           | 
    
