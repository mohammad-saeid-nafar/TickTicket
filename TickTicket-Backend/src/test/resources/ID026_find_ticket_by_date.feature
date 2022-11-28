Feature: Find tickets by date

  As a user
  I would like to find tickets by date

  Background:
    Given the following users exist in the system:
      | id          | username  | password   | created date  |
      | 01 |  johnDoe  | myP@assword1 | 2022-07-12 |
      | 02 |  bruceJ2  |  BrUcE_@214  | 2022-10-03 |

    Given the following user profiles exists in the system:
      | User | id |   name    |    address   | dateOfBirth | profilePicture |       email       | phoneNumber  |
      | 01   | 01 | John Doe  | 120 Street 1 | 1999-02-22  |    img1.jpg    | johndoe@gmail.com | 438 566 3241 |
      | 02   | 02 | Bruce Jil | 11 Street 17 | 2000-04-30  |    img2.jpg    | bruce_j@gmail.com | 438 866 5551 |

    Given the following Event Type exists in the system:
      | id |    name    | description |   ageRequirement   |
      | 01 | Pop Music  |    Music    | 0 |
      | 02 | Party | Big Crowd | 18                 |

    Given the following events exist in the system:
      | id |         name       | description | capacity | cost |   address   |     email   | phoneNumber  |    type   |
      | 01 | Justin Bieber Tour |  Music Tour |    600   |  250 | True Square | johndoe@gmail.comm | 438 566 3241 | Pop Music |

    Given the following Event Schedule exists in the system:
      | Event | id |       start      |        end       |
      | 01    | 01 | 2022-10-15 19:00 | 2022-10-15 22:00 |

  Scenario: Successfully find tickets by date when 2 exists
    Given the following tickets exist in the system:
      | id          | booking date      | event                       | user      |
      | 1           | 2022-11-30        | Justin Bieber Tour  | bruceJ2      |
      | 2           | 2022-11-30        | Justin Bieber Tour  | bruceJ2      |

    When the user attempts to find a ticket by user and inputs "2022-10-14"
    Then the number of tickets displayed shall be "1"
    Then the following list of tickets is generated:
      | id          | booking date      | event                       | user      |
      | 1           | 2022-10-14        | Justin Bieber Tour  | bruceJ2      |

    When the user attempts to find a ticket by user and inputs "2022-11-30"
    Then the number of tickets displayed shall be "1"
    Then the following list of tickets is generated:
      | id          | booking date      | event                       | user      |
      | 2           | 2022-11-18        | Justin Bieber Tour  | bruceJ2      |