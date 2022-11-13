Feature: Filter Events By Capacity

  As a user
  I would like to filter events by capacity range
  To view them in an organized way

  Background: 
    Given the following users exist in the system:
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly1235!   | 2022-10-01    |
      | 2           | aly2      | Aly1233!   | 2022-10-03    |

    Scenario: Successfully view events when none are existant 
    When the user attempts to view the events by capacity range
    Then the number of events displayed shall be "0"
    Then there shall be no generated list

  Scenario: Successfully view filtered events when 1 exists
    Given the following events exist in the system: 
      | id          | name              | description                 | capacity   | cost   | address            | email            | phoneNumber  | Type   |
      | 1           | Wine and Cheese   | Graduation Wine and Cheese  | 80         |  20    |  2620 rue Stanley  |  aly1@gmail.com  | 514-888-8888 | Other  |
    When the user attempts to view the filtered events by capacity range
    Then the number of events displayed shall be "1"
    Then the following list of Events are generated:
      | id          | name              | description                 | capacity   | cost   |  address           | email            | phoneNumber  |
      | 1           | Wine and Cheese   | Graduation Wine and Cheese  | 80         |  20    |  2620 rue Stanley  |  aly1@gmail.com  | 514-888-8888 |

  Scenario: Successfully view events when 3 exists
    Given the following events exist in the system: 
      | id          | name               | description                         | capacity   | cost   | address             | email            | phoneNumber  | Type        |
      | 1           | Wine and Cheese    | Graduation Wine and Cheese          | 80         |  20    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Other       |
      | 2           | OSM Concert        | Classical Mucisc Concert            | 450        |  20    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Classical   |
      | 3           | Graduation Ceremony| Graduation Ceremony for 2024 class  | 130        |  80    |  McGill Lower Field |  aly2@gmail.com  | 514-777-7777 | Other       |  
    When the user attempts to view the filtered events by capacity range
    Then the number of events displayed shall be "3"
    Then the following list of Events are generated:
      | id          | name               | description                         | capacity   | cost   | address             | email            | phoneNumber  | Type        |
      | 1           | Wine and Cheese    | Graduation Wine and Cheese          | 80         |  20    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Other       |
      | 3           | Graduation Ceremony| Graduation Ceremony for 2024 class  | 130        |  80    |  McGill Lower Field |  aly2@gmail.com  | 514-777-7777 | Other       |
      | 2           | OSM Concert        | Classical Mucisc Concert            | 450        |  20    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Classical   |
