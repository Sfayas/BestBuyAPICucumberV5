Feature: Tetsing Product Service API

Background: Connect RESTAssured
Given BaseURI and Headers
#And ReadTestData

Scenario: searchProductWithLimit
Given User serach products with "limit"
Then System returns success code "200"
And limit should be given limit

Scenario: searchProductwithColumnFilters
Given User serach products with "ColumnFilters"
Then System returns success code "200"
And verify system returns only selected columns

Scenario: searchSpecificProductwithSomeCriteria
Given User serach products with "SearchCriteria"
Then System returns success code "200"
And Verify the results matching with SearchCriteria

