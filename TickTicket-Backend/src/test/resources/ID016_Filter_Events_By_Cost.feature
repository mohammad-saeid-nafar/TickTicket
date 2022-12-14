Feature: Filter Events By Cost Range

  As a user
  I would like to filter events given a cost range 
  To view them in an organized way

  Background: 
    Given the following users exist in the system:
      | id          | username  | password   | createdDate   |
      | 1           | aly1      | Aly1235!   | 2022-10-01    |
      | 2           | aly2      | Aly1233!   | 2022-10-03    |

    And Given the following events exist in the system: 
      | id          | name               | description                         | capacity   | cost   | address             | email            | phoneNumber  | Type        |
      | 1           | Wine and Cheese    | Graduation Wine and Cheese          | 80         |  20    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Other       |
      | 2           | OSM Concert        | Classical Music Concert             | 450        |  35    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Classical   |
      | 3           | Graduation Ceremony| Graduation Ceremony for 2024 class  | 130        |  80    |  McGill Lower Field |  aly2@gmail.com  | 514-777-7777 | Other       |  

  Scenario: Successfully view filtered events when 1 exists between the cost range <x> and <y>
    
    When the user attempts to view the filtered events by a cost range between <10> and <25>
    Then the number of events displayed shall be "1"
    Then the following list of Events are generated:
      | id          | name              | description                 | capacity   | cost   |  address           | email            | phoneNumber  |
      | 1           | Wine and Cheese   | Graduation Wine and Cheese  | 80         |  20    |  2620 rue Stanley  |  aly1@gmail.com  | 514-888-8888 |

  Scenario: Unsuccessfully view filtered events when no events exist between the cost range <x> and <y>

    When the user attempts to view the filtered events by a cost range between <0> and <10>
    Then the number of events displayed shall be "0"
    Then no Events will be generated

  Scenario: Successfully view filtered events when 2 exists between the cost range <x> and <y>
    
    When the user attempts to view the filtered events by a cost range between <10> and <50>
    Then the number of events displayed shall be "2"
    Then the following list of Events are generated:
      | id          | name               | description                         | capacity   | cost   | address             | email            | phoneNumber  | Type        |
      | 1           | Wine and Cheese    | Graduation Wine and Cheese          | 80         |  20    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Other       |
      | 2           | OSM Concert        | Classical Music Concert             | 450        |  35    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Classical   |
  
  Scenario: Successfully view filtered events when 3 exists between the cost range <x> and <y>
    
    When the user attempts to view the filtered events by a cost range between <10> and <95>
    Then the number of events displayed shall be "3"
    Then the following list of Events are generated:
      | id          | name               | description                         | capacity   | cost   | address             | email            | phoneNumber  | Type        |
      | 1           | Wine and Cheese    | Graduation Wine and Cheese          | 80         |  20    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Other       |
      | 2           | OSM Concert        | Classical Music Concert             | 450        |  35    |  2620 rue Stanley   |  aly1@gmail.com  | 514-888-8888 | Classical   |
      | 3           | Graduation Ceremony| Graduation Ceremony for 2024 class  | 130        |  80    |  McGill Lower Field |  aly2@gmail.com  | 514-777-7777 | Other       |  
  
      
 