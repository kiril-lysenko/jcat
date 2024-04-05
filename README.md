

## About The Cat Info Project


This project contain solution on testing task from  WG with the addition of
the developer's imagination.

Project contains:

* Table with information about cats.
* Cats' colors statistic information
* Cats' tail and whiskers statistic information
* Ability to add cat's data with validation

### Built With

* [![Java][Java]][java-url]
* [![Spring Boot][Spring_Boot]][spring-url]
* [![Liquibase][Liquibase]][liquibase-url]
* [![PostgreSQL][postgres]][postgres-url]
* [![JavaScript][JS]][js-url]
* [![React][React.js]][React-url]
* [![Material UI][material]][material-url]
* [![Docker][Docker]][docker-url]

## Getting Started

For starting application prefer docker-compose, but you also can use maven.

### Maven Instruction

Change liquibase flag and change database configs for your own in application.properties as below

````
spring.liquibase.enabled=true
````

````
mvn clean install
````

````
 mvn --projects backend spring-boot:run
````

### Docker Instruction

For generated targer folder

````
mvn clean install
````

Build and Run Docker Images

````
docker-compose up
````

Rebuild Docker Images

````
docker-compose build
````

Local:

+ http://localhost:8080/
+ http://localhost:8080/swagger-ui/index.html

## Contact

[![LinkedIn][linkedin-shield]][linkedin-url]

<!-- MARKDOWN LINKS & IMAGES -->

[project-screenshot]: readme_image/cat_info_main_page.PNG

[Java]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white

[java-url]: https://docs.oracle.com/en/java/

[Spring_Boot]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white

[spring-url]: https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/

[Liquibase]: https://img.shields.io/badge/-Liquibase-White?style=for-the-badge

[liquibase-url]: https://docs.liquibase.com/workflows/liquibase-community/using-jdbc-url-in-liquibase.html

[postgres]: https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white

[postgres-url]: https://www.postgresql.org/

[JS]: https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black

[js-url]: https://developer.mozilla.org/en-US/docs/Web/JavaScript

[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB

[React-url]: https://reactjs.org/

[material]: https://img.shields.io/badge/Material--UI-0081CB?style=for-the-badge&logo=material-ui&logoColor=white

[material-url]: https://mui.com/

[Docker]: https://img.shields.io/badge/-Docker-fff?style=for-the-badge&logo=Docker

[docker-url]: https://docs.docker.com/

[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555

[linkedin-url]: https://www.linkedin.com/in/kkarpekina
