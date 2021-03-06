# Lunch Voting System

Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides new menu each day.

## Github Repository:
git clone https://github.com/ZhukovDima/lunch-voting-system.git

## RUN
mvn clean package

### Application deployed in application context `lunch-voting-system`.

The application uses Hibernate Second Level Cache on User and Restaurant resources

## cURL operations on a User resource
##### Retrieves all users
curl -s http://localhost:8080/lunch-voting-system/rest/users -u admin@mail.com:admin
##### Retrieves a user
curl -s http://localhost:8080/lunch-voting-system/rest/users/1 -u admin@mail.com:admin
##### Creates a user
curl -s http://localhost:8080/lunch-voting-system/rest/users -X POST -d'{"name":"New user","email":"newuser@mail.com","password":"new123","roles":["ROLE_USER"]}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Updates a user
curl -s http://localhost:8080/lunch-voting-system/rest/users/1 -X PUT -d '{"id":1,"name":"Updated user","email":"updateduser@mail.com","password":"new123","roles":["ROLE_USER"]}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Deletes a user
curl -s http://localhost:8080/lunch-voting-system/rest/users/1 -X DELETE -u admin@mail.com:admin

## cURL operations on a Restaurant resource
##### Retrieves all restaurants
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants -u user@mail.com:password
##### Retrieves a restaurant
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/1 -u user@mail.com:password
##### Creates a new restaurant
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants -X POST -d '{"name":"New restaurant"}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Updates a restaurant
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/1 -X PUT -d '{"id":1,"name":"Updated restaurant"}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Deletes a restaurant
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/1 -X DELETE -u admin@mail.com:admin

## cURL operations on a Menu resource 
##### Retrieves all menus of restaurants for today by default
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/menus -u user@mail.com:password
##### Retrieves all menus of restaurants for the given date
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/menus?date=2018-05-12 -u user@mail.com:password
##### Retrieves menus for a given restaurant for today by default
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus -u user@mail.com:password
##### Retrieves a menu for a given restaurant and the given date
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus?date=2018-05-12 -u user@mail.com:password
##### Retrieves a menu of a given restaurant
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus/1 -u user@mail.com:password
##### Creates a new menu for a given restaurant
curl http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus -s -X POST -d '{"dateEntered":"2018-05-13"}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Updates a menu for a given restaurant
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus/1 -s -X PUT -d '{"id":1, "dateEntered":"2018-05-14"}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Deletes a menu for a given restaurant
curl -s http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus/1 -X DELETE -u admin@mail.com:admin

## cURL operations on a MenuItem resource 
##### Creates a new menu item
curl http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus/1/items -s -X POST -d '{"name":"New item","price":950}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Updates a menu item
curl http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus/1/items/1 -s -X PUT -d '{"name":"Updated item","price":320}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Deletes a menu item
curl http://localhost:8080/lunch-voting-system/rest/restaurants/1/menus/1/items/1 -s -X DELETE -u admin@mail.com:admin

## cURL operations on a Vote resource
##### Creates a new vote
curl http://localhost:8080/lunch-voting-system/rest/votes/1 -s -X POST -H 'Content-Type: application/json' -u user2@mail.com:password2
##### Retrieves current vote by user
curl -s http://localhost:8080/lunch-voting-system/rest/votes -u user@mail.com:password