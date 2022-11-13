Feature: Cancel ticket
  As a user, I want to cancel one of my tickets

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
      | 1           | 2022-10-10     | 15:00:00  | 1         |  4       | 
      | 2           | 2022-10-10     | 15:00:00  | 3         |  4       |  

    Scenario: Successfully cancel ticket
      When the user "<user>" attempts to cancel the ticket "<ticketID>" with "<userID>" and "<eventID>"
      Then the ticket will be cancel successfully
      And the following tickets exist in the system:
       | id          | date           | time      | userID    | eventID  |
       | 2           | 2022-10-10     | 15:00:00  | 3         |  4       |  

       Examples:
       | user      |   ticketID  | userID             | eventID | 
       | aly1      | 1           | 1                  | 4       |  
       

    Scenario: Error when attempting to cancel a nonexistent ticket
      When the user "<user>" attempts to cancel the ticket "<ticketID>" with "<userID>" and "<eventID>"
      Then the ticket will not cancel successfully
      And the system should display the error message "This ticket does not exist"

       Examples:
       | user      |   ticketID  | userID             | eventID | 
       | aly1      | 2           | 1                  | 4       |  

    Scenario: Error when attempting to cancel the ticket of another user
      When the user "<user>" attempts to cancel the ticket "<ticketID>" with "<userID>" and "<eventID>"
      Then the ticket will not cancel successfully
      And the system should display the error message "This is not your ticket"

       Examples:
       | user      |   ticketID  | userID             | eventID | 
       | aly2      | 1           | 1                  | 4       | 