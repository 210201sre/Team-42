# Team Banking




## Overview:
Team Banking is a banking application that leverages a postgresSQL DB hosted on AWS's RDS. Team Banking uses Spring Framework to handle requests, FluentD for logging, Prometheus to track metrics, and Granfana for data visualization. Integrated into a Jenkin's pipeline that is deployed to Kubernetes after passing quality gates in SonarCloud.




## Features:
* Add checking and savings accounts to database
* Get checking and savings accounts by ID
* Get all checking and savings accounts
* Transfer money between two specified accounts
* Add customers and employees to database
* Get all customers and employees
* Add user to database
* Get all users
* Get user by username
* Pay salary to user




## Technologies Used:


* Java
* Spring
* Kubernetes
* Maven
* Git
* Log4J
* PostgreSQL
* AWS RDS
	* Grafana
* Loki
* FluentD
* REST
* Prometheus
* Sonarcloud
* Jenkins
* GitHub
	

## How to Set Up / Get Started:
1. Clone contents of repo
2. Spin up a Kubernetes cluster
   1. With a PostgreSQL DB
3. Set up a Jenkins pipeline for deployment
4. Trigger pipeline with a push to GitHub





## Usage of Project:
   * Send GET request method to “~/checkings_accounts” to get all checking accounts
   * Send GET request method to “~/checkings_accounts/{id}” to get checking account by ID
   * Send GET request method to “~/savings_accounts” to get all savings accounts
   * Send GET request method to “~/savings_accounts/{id}” to get savings account by ID


   * Send GET request method to “~/customers” to obtain a JSON collection of all users who are customers
   * Send GET request method to “~/employees” to obtain a JSON collection of all users who are employees
   * Send GET request method to “~/users” to obtain a JSON collection of all users 
   * Send GET request method to “~/users/{userid}” to obtain JSON representation of a user associated with the id passed in as ‘userid’


   * Send POST request method to “~/checkings_accounts/{id}” to insert new checking account into database
   * Send POST request method to “~/savings_accounts/{id}” to insert new savings account into database
   * Send POST request method to “~/accounts/transfer/{id1}/{id2}/{amount}”
   * Move ‘amount’ from account with ‘id1’ to account with ‘id2’
   * Send POST request method to “~/accounts/access/{actid}/{userid}”
   * Grant the user described by ‘userid’ access to the account found by ‘actid’
   * Send POST request method to “~/employees/{id}” to add an employee to the database
   * Send POST request method to “~/customers/{id}” to add a customer to the database
   * Send POST request method to “~/users” to save a user passed via JSON object in the request body
   * Send POST request method to “~/users/salary” to pay salary to users




### Contributors: Robert Maule, Fatima Melgar, Thomas Latza, Elizabeth Villanueva