Feature: Get reviews for event
  As a user, I want to see all the reviews for a specific event, so that I can evaluate if the event suits me

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

    Given the following reviews exist in the system:
      | id  | title   | rating  | description                           | userId  | eventId |
      | 6   | Amazing | 5       | The quality of the wine was amazing   | 2       | 4       |
      | 7   | Good    | 3       | Wine was good but cheese not as much  | 3       | 4       |

    Scenario: Successfully view reviews for event
      Given that the current date and time is "2022-11-05 12:00"
      When the user with id "2" attempts to view the reviews for the event with id "4"
      Then the following reviews will be displayed to the user:
        | id  | title   | rating  | description                           | userId  | eventId |
        | 6   | Amazing | 5       | The quality of the wine was amazing   | 2       | 4       |
        | 7   | Good    | 3       | Wine was good but cheese not as much  | 3       | 4       |

    Scenario: Error when attempting to view reviews for an event that has not started yet
      Given that the current date and time is "2022-11-05 12:00"
      When the user with id "2" attempts to view the reviews for the event with id "5"
      Then the error "Event has not started yet" will be displayed to the user

    Scenario: Error when attempting to view reviews for an event that has no reviews
      Given that the current date and time is "2022-12-10 12:00"
      When the user with id "2" attempts to view the reviews for the event with id "5"
      Then the error "Event has no reviews" will be displayed to the user




