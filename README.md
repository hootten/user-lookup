Coding assessment for DWP application.

"Using the language of your choice please build your own API which calls the API at:
https://bpdts-test-app.herokuapp.com/
and returns people who are listed as either living in London, or whose current
coordinates are within 50 miles of London."

If you are running the application locally, use the following endpoint to access the application:
localhost:8080/usersByCity

The application is configurable through the application.properties in src/main/resources

Assumptions:
No user will ever be returned from https://bpdts-test-app.herokuapp.com/ with the ID of -1.