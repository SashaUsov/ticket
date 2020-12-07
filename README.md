The application interacts with the Postgres cloud database. If you want to work with a local database, please change the
appropriate properties in the application.properties file. Specify your local database address as spring.datasource.url
, username that has privileges to work with your database spring.datasource.username and password
spring.datasource.password respectively.

There is no need to create tables, they will be created automatically by the application using liquibase.

You can see all available urls and examples of requests to them by downloading this
collection https://www.getpostman.com/collections/85d3f62760994e350e28.
