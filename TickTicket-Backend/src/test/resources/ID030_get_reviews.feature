Feature: Get reviews for event
  As a user, I want to see all the reviews, so that I can find an event that suits me

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

  Scenario: Successfully view all reviews
    Given the following reviews exist in the system:
      | id  | title   | rating  | description                           | userId  | eventId |
      | 6   | Amazing | 5       | The quality of the wine was amazing   | 2       | 4       |
      | 7   | Good    | 3       | Wine was good but cheese not as much  | 3       | 4       |
    When the user with id "2" attempts to view all the reviews
    Then the following reviews will be displayed to the user:
      | id  | title   | rating  | description                           | userId  | eventId |
      | 6   | Amazing | 5       | The quality of the wine was amazing   | 2       | 4       |
      | 7   | Good    | 3       | Wine was good but cheese not as much  | 3       | 4       |

  Scenario: Error when attempting to view reviews when there are no reviews in the system
    When the user with id "2" attempts to view all the reviews
    Then the error "There are no reviews" will be displayed to the user




