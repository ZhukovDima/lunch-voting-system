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

git clone https://github.com/

## cURL operations on a Restaurant resource 
##### Retrieves all restaurants
curl -s http://localhost:8080/rest/restaurants -u user1@mail.com:password1
##### Creates a new restaurant
curl -s http://localhost:8080/rest/restaurants -X POST -d '{"name":"New restaurant"}' -H 'Content-Type: application/json' -u user1@mail.com:password1
##### Retrieves a restaurant
curl -s http://localhost:8080/rest/restaurants/1 -u user1@mail.com:password1
##### Updates a restaurant
curl -s http://localhost:8080/rest/restaurants/1 -X PUT -d '{"id":1,"name":"Updated restaurant"}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Deletes a restaurant
curl -s http://localhost:8080/rest/restaurants/1 -X DELETE -u admin@mail.com:admin

## cURL operations on a Menu resource 
##### Retrieves all menus of restaurants for today by default
curl -s http://localhost:8080/rest/restaurants/menus -u user1@mail.com:password1
##### Retrieves all menus of restaurants for the given date
curl -s http://localhost:8080/rest/restaurants/menus?date=2018-05-12 -u user1@mail.com:password1
##### Retrieves a menu for a given restaurant
curl -s http://localhost:8080/rest/restaurants/1/menus/1 -u user1@mail.com:password1
##### Retrieves a menu for a given restaurant for today by default
curl -s http://localhost:8080/rest/restaurants/1/menus -u user1@mail.com:password1
##### Retrieves a menu for a given restaurant the given date
curl -s http://localhost:8080/rest/restaurants/1/menus?date=2018-05-12 -u user1@mail.com:password1
##### Creates a new menu
curl http://localhost:8080/rest/restaurants/2/menus -s -X POST -d '{"dateEntered":"2018-05-13"}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Updates a menu
curl -s http://localhost:8080/rest/restaurants/1/menus/1 -s -X PUT -d '{"id":1, "restaurant":{"id":1,"name":"Restaurant2"}, "dateEntered":"2018-05-13"}' -H 'Content-Type: application/json' -u admin@mail.com:admin
##### Deletes a menu 
curl -s http://localhost:8080/rest/restaurants/1/menus/1 -X DELETE -u admin@mail.com:admin

## cURL operations on a MenuItem resource 
