# e-Dziennik

_____________


<p align="center">
    School managment application (Backend)
    <br/>
    Note this project is still in develop state
    <br/>
    <br/>
    <a href="https://github.com/SaQie/e-Dziennik-desktop"><strong>Move to client »</strong></a>
    <br/>
    <a href="https://github.com/SaQie/e-Dziennik/issues"><strike>Explore the docs</strike></a>
    .
    <a href="https://github.com/SaQie/e-Dziennik/issues"><strong>Report Bug »</strong></a>
    .
    <a href="https://github.com/SaQie/e-Dziennik/issues"><strong>Request Feature » </strong></a>
  </p>


<div>
    <h3>About project <img src="images/Info.png" width="20"></h3>
</div>

_____________

<div>
    This project is a backend server for <a href="https://github.com/SaQie/e-Dziennik-desktop"><b>client</b></a> that consume REST API for receiving a data. That is a full school managment application that allows you to do this kind of operations at the moment (02.05.2023)

</div>

| Description                   |                                            |
|-------------------------------|--------------------------------------------|
| **CRUD schools**              | ![#00d1a0](images/School_Building.png)     |
| **CRUD school classes**       | ![#00d1a0](images/Meeting_Room.png)        |
| **CRUD students**             | ![#00d1a0](images/Student_Male.png)        |
| **CRUD teachers**             | ![#00d1a0](images/Teacher.png)             |
| **CRUD parents**              | ![#00d1a0](images/Family.png)              |
| **CRUD admins**               | ![#00d1a0](images/Admin_Settings_Male.png) |
| **CRUD subjects**             | ![#00d1a0](images/Read.png)                |
| **CRUD grades**               | ![#00d1a0](images/Numbers.png)             |
| **Dynamic app configuration** | ![#FC0000](images/Services.png)            |

<div>Dynamic app configuration allows to configure our application through REST-API</div>

Actually project is just a base project for future develop

#### Roles in application:

![#FC0000](images/roles.png)

# ✉️ To do

_________

List of task's I will implements in future in this project:

* *Add email confirmation* ❌
* *Add redis cache for configuration* ❌
* *Add real time client-server chat (student-teacher)* ❌
* *Add email system inside application* ❌
* *Add exports student grades to PDF* ❌
* *Add homeworks that can be sent to teacher* ❌
* *Add notes system per student inside application* ❌
* *Add dashboard that shows recently added grades* ❌
* *Add interactive panel that allows to run groovy script and get result(for homeworks)* ❌
* *And more.....*

# 👨‍💻 Tech stack

_____

*Tech stack I actually using in this project*:

* Java 17
* Spring Boot 3.0.5
* Spring Data JPA
* Spring Security (JWT)
* Mockito
* JUnit
* Lombok
* Liquibase
* Swagger
* PostgreSQL
* Docker
* Maven
* GitHub with GitHub Workflows

# 🖥️ How to run

_____
Project has a docker-compose file that allows to run application very simple:

* Firstly, install Docker <a href="https://docs.docker.com/desktop/install/windows-install/">Here</a>
* Copy this repository, run command line and type: <br> <code> git clone https://github.com/SaQie/e-Dziennik </code>
* Make sure that in application.properties file this line is not commented: <br><code>url: jdbc:postgresql://postgres:
  5432/diary_db -- for docker</code>
  <br> Or if you want you can run this application manually, but you will need install postgres on your computer and set
  up project manually
* Now, you can run this command that will build the docker containers and start them:
  <br> <code>docker-compose up</code>
* End.

# 📄 Documentation

____

Project documentation is available on : */swagger-ui/index.html*
