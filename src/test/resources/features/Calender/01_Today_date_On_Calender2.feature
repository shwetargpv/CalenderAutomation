@Regression
Feature: Verify today date selection in calender
  Scenario: Verify selection of today's date
    Given I navigate to calender website
    When I verify today's date selected

  Scenario Outline: Verify selection of today's date
    Given I navigate to calender website
    And I verify selection of "<Last Year Date>"
    Examples:
   |Last Year Date|
   |28 July 2018  |
   |23 April 2020 |
   |30 May 2020   |
   |6 Jan 2021    | 