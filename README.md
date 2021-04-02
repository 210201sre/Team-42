# TEAM BANKING

## Overview:
Team Banking is a banking application that leverages a postgresSQL DB hosted on AWS's RDS. Team Banking uses Spring Framework to handle requests, FluentD for logging, Prometheus to track metrics, and Granfana for data visualization. Integrated into a Jenkin's pipeline that is deployed to Kubernetes after passing quality gates in SonarCloud.

## Technologies Used:

* Java
* Spring
* Kubernetes
* Prometheus
* Grafana
* Jenkins
* AWS RDS
* PostgreSQL
* Sonarcloud
* FluentD
* Loki
* Log4J
* REST
* Git

## Features
* Add checking and savings accounts to database
* Get checking and savings accounts by ID
* Get all checking and savings accounts
* Transfer money between two specified accounts
* Add customers and employees 
* Get all customers and employees to database
* Add user to database
* Get all users
* Get user by username
* Pay salary to user


## Getting Started:
1. Clone contents of repo
2. Spin up a Kubernetes cluster
   1. With a PostgreSQL DB
3. Set up a Jenkins pipeline for deployment
4. Trigger pipeline with a push to GitHub


## Usage:

Use your EC2 URL to send HTTP requests to. Below are examples of URIs you can add to the end of your URL for requests, as well as details on the HTTP methods used and how to structure requests.

  "GET" to to get all checking accounts
  `/checkings_accounts`
  
  "GET" to get all checking accounts
  `/checkings_accounts`
  
  "GET" to get checking account by ID  `/checkings_accounts/{id}`
  
  "GET" to get all savings accounts
   `/savings_accounts` 
  
  "GET" to get savings account by ID
   `/savings_accounts/{id}`

  "GET" to get all users who are customers
   `/customers` 
  
  "GET" to get all users who are employees
   `/employees`
  
  "GET" to get all users 
   `/users` 
  
  "GET" to get a user by ID
   `/users/{userid}` 

  "POST" to create new checking account
  `/checkings_accounts/{id}`
  `{
    "balance":"10000"
   `}
 
  "POST" to create new savings account
  `/savings_accounts/{id}`
  `{
    "balance":"10000",
    "intrest":".01"
   `}
 
  "POST" to transfer money between accounts
  `/accounts/transfer/{id1}/{id2}/{amount}`

  "POST" to grant access to user
  `/accounts/access/{actid}/{userid}`
  
  "POST" to pay salary
  `/accounts/salary`

  "POST" to add an employee 
  `/employees/{id}`
  `{
    "id":"1",
    "firstName":"leeve",
    "lastName":"beeve",
	"zipcode":"77777",
	"city":"newark",
	"streetAdress":"707 mex street",
	"state":"nj",
    "salary":"50000"
   }`

  "POST" to add a customer 
  `/customers/{id}`
  `{
    "id":"4",
    "firstName":"jeeves",
    "lastName":"beeve",
	"zipcode":"77777",
	"city":"flushing",
	"streetAdress":"707 mex street",
	"state":"ny",
    "dateJoined":"null"
   }`

  "POST" to create a user
  `/users` 
  `{
    "id":"1",
    "username":"kingsidedata",
    "password":"password",
    "employee":"false"
   }`

 



### Contributors: Robert Maule, Fatima Melgar, Thomas Latza, Elizabeth Villanueva
