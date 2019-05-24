
# Online-Shopping-System
A web application for online shopping with Spring Boot, FreeMarker &amp; Hibernate


## How to run
1. Configue datasource in `application.yml`. Here I use PostgreSQL.
2. The inial data is imported automatically by `import.sql`
3. Run `OnlineShoppingApplication.java`.


## About
There is a login and registration functionality included.  
There are three roles for user, such as customer, employee, mangager.  
**Customer**: The users with this role can shop for products. Each user has his own shopping cart (session functionality).add items into cart, view list of items and checkout. Checkout is transactional.  
**EMPLOYEE**: The users with this role can view list of  orders, details of an orderMain and their profile.  
**MANAGER**: The users with this role can view list of orders, details of an orderMain and their profile, add and edit products.  
  

 
