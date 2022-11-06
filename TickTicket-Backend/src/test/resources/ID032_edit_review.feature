Feature: Edit review
  As a user, I want to edit a review I wrote

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

    Scenario: Successfully edit
      Given that the current date and time is "2022-11-05 12:00"
      When the user with id "2" attempts to edit the review with id "6" with the following:
        | title   | rating  | description    |
        | Great   | 4       | Wine was great |
      Then the review with id "6" will updated successfully

    Scenario: Error when attempting to edit review of another user
      Given that the current date and time is "2022-11-05 12:00"
      When the user with id "2" attempts to edit the review with id "7" with the following:
        | title   | rating  | description    |
        | Great   | 4       | Wine was great |
      Then the error "You cannot edit another user's review" will be displayed to the user

    Scenario: Error when attempting to edit non existent review
      Given that the current date and time is "2022-12-10 12:00"
      When the user with id "2" attempts to edit the review with id "404" with the following:
        | title   | rating  | description    |
        | Great   | 4       | Wine was great |
      Then the error "Review not found" will be displayed to the user




