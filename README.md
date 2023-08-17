# e-Dziennik

_____________


<p align="center">
    School managment application (Backend)
    <br/>
    Note this project is still in develop state
    <br/>
    <br/>
    <a href="https://github.com/SaQie/e-Dziennik-desktop"><strong>Move to client (D) » </strong></a>     <a href="https://github.com/SaQie/"><strong>Move to client (W) »</strong></a>
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
    This project is a backend server for <a href="https://github.com/SaQie/e-Dziennik-desktop"><b>client(D)</b></a> <a href="https://github.com/SaQie/"><b>client(W)</b></a> that consume REST API for receiving a data. That is a full school managment application that allows you to do this kind of operations at the moment

</div>

| Description                   |                                            | Description                 |                                       |
|-------------------------------|--------------------------------------------|-----------------------------|---------------------------------------|
| **Schools option**            | ![#00d1a0](images/School_Building.png)     | **Email confirmation**      | ![#00d1a0](images/Received.png)       |
| **School classes option**     | ![#00d1a0](images/Meeting_Room.png)        | **Document generator**      | ![#00d1a0](images/PDF.png)            |
| **Students option**           | ![#00d1a0](images/Student_Male.png)        | **Internationalization**    | ![#00d1a0](images/Language_Skill.png) |
| **Teachers option**           | ![#00d1a0](images/Teacher.png)             | **Real-Time Chat**          | ![#00d1a0](images/WeChat.png)         |
| **Parents option**            | ![#00d1a0](images/Family.png)              | **Groovy script Execution** | ![#00d1a0](images/Code_File.png)      |
| **Admins option**             | ![#00d1a0](images/Admin_Settings_Male.png) |                             |                                       |
| **Subjects option**           | ![#00d1a0](images/Read.png)                |                             |                                       |
| **Grades option**             | ![#00d1a0](images/Numbers.png)             |                             |                                       |
| **Dynamic app configuration** | ![#FC0000](images/Services.png)            |                             |                                       |

<div>Dynamic app configuration allows to configure our application through REST-API</div>

Actually project is just a base project for future develop

#### Roles in application:

![#FC0000](images/roles.png)

# ✉️ To do

_________

List of tasks I will implement in future in this project:

* *Add email confirmation* ✅
* *Add redis cache for configuration* ✅
* *Add real time client-server chat (student-teacher)* ✅
* *Add email system inside application* ❌
* *Add exports student grades to PDF* ✅
* *Add homeworks that can be sent to teacher* ❌
* *Add notes system per student inside application* ❌
* *Add dashboard that shows recently added grades* ❌
* *Add interactive panel that allows to run groovy script and get result* ✅
* *And more.....*

# 🪨 Milestones

___

- *(15/09/2022) Start project*
    - When I started the project, I decided to implement DAO pattern instead of Repository pattern
- *(21/12/2022)* Start client desktop project for this
  app     <a href="https://github.com/SaQie/e-Dziennik-desktop"><strong>Move to client »</strong></a>
- *(24/03/2023) Change persistence layer to Spring Data Jpa repositories*
    - I decided to change my persistence layer, due to i had a lot of problems with DAO pattern (Pagination,
      transactions etc.)
- *(06/05/2023) Changed architecture layer to CQRS, check <strong>[Architecture Look »](#-architecture-look)</strong>*
- *(31/05/2023)* I decided to temporarily stop developing client desktop application and instead start developing
  Angular web client
- *(30/07/2023)* My first face to WebSocket's - implemented a new one-to-one chat with message history |
  check <strong>[Chat »](#-chat---how-it-works)</strong>

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
* TestContainers
* Redis
* WebSockets
* Groovy

# 🗾 Architecture look

___

*I am currently using CQRS architecture in my project.*
*Architecture look is available in a picture below:*

![#FC0000](images/CQRS_architecture.png)

# 💬 Chat - How it works

___

![#FC0000](images/one-to-one-chat.png)

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
