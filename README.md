# 🏥 Doctor K

## 📢 Introduction
**Doctor K** — A multilingual healthcare information platform providing filtered hospital searches by supported languages, medical departments, and locations.

<img width="1919" height="905" alt="image" src="https://github.com/user-attachments/assets/49a397af-ef18-456b-8382-f34ac1b4d256" />


## 📝 Service Overview
Doctor K is a multilingual web service designed to assist foreign residents in Korea. It simplifies the process of finding medical facilities by allowing users to filter hospitals based on their preferred language, required medical specialty, and region.


## 👥 Developer

| Name      | Role            |
|--------|--------------|
| Chaeyeon Kwak  | Full-stack development |



## 🛠 Tech Stack

- **Framework**: Spring Boot  
- **Language**: Java  
- **Database**: MariaDB  
- **ORM**: Spring Data JPA  
- **Build Tool**: Maven
- **Documentation**: Swagger  

## 📊 Database Entity Relationship Diagram (ERD)

<img width="1713" height="1562" alt="image" src="https://github.com/user-attachments/assets/bd4a6188-7543-47dd-90b9-28d470aa851d" />

- The system dynamically switches the target database tables based on the user's language selection to provide localized content efficiently.
  
| Selected Language | Target Tables |
| :--- | :--- |
| **Korean (Default)** | `gangnam_hospital`, `gangdong_hospital` |
| **English** | `en_gangnam_hospital`, `en_gangdong_hospital` |


## 📁 Key File Structure
```
MedicalForForeigns/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/medical/
│ │ │ ├── common
│ │ │ │ └── JPAUtil.java
│ │ │
│ │ │ ├── config
│ │ │ │ └── SecurityConfig.java
│ │ │
│ │ │ ├── dto/
│ │ │ │ ├── MemberRegisterDto.java
│ │ │ │ ├── MemberInfoChangedDto.java
│ │ │ │ ├── MemberFavoriteDto.java
│ │ │ │ ├── HospitalReviewDto.java
│ │ │ │ ├── HospitalReservationDto.java
│ │ │ │ ├── ChangedReviewDto.java
│ │ │ │ ├── ChangedReservationDto.java
│ │ │ │ └── LanguageDetectRequestDto.java
│ │ │ │
│ │ │ ├── service/ 
│ │ │ │ ├── DeeplService.java
│ │ │ │ └── HospitalService.java
│ │ │ │
│ │ │ ├── controller/ 
│ │ │ │ └── HospitalController.java
│ │ └── resources/
│ │ │ └── application.properties
│
└── pom.xml

```


## 📌 Key Features

### ✅ Hospital Search & Directory
- Advanced Filtering: Find healthcare providers based on Service Language, Department, and Region.
- Detailed Information: View comprehensive hospital profiles, including available services and user reviews.


### 🌐 Multilingual Support
- DeepL API Integration: Supports real-time translation features through seamless integration with the DeepL API.


## 🚀 Getting Started

```bash
# Clone the repository
git clone https://github.com/kwak513/medical-foreigns-back
cd medical-foreigns-back

# Run the application (via CLI or IntelliJ)
./mvnw spring-boot:run

```
## 💡 How to Run in Eclipse
- Import Project: Select Import → Maven → Existing Maven Projects.
- Select Directory: Browse to the cloned project folder.
- Run Application: Right-click the project → Run As → Spring Boot App.

 
⚠️ Before running the application, ensure you configure the environment settings in the src/main/resources/application.properties file:
```
spring.datasource.url=jdbc:mariadb://localhost:3306/dbname
spring.datasource.username=your_username
spring.datasource.password=your_password
server.port=8080
spring.jpa.hibernate.ddl-auto=update
```
## 🚀 API Documentation (Swagger)
The API documentation is automatically generated and can be accessed via Swagger UI once the server is running:
http://localhost:8080/swagger-ui/index.html

## 🧩 Related Repository
**Frontend**: [Link to Frontend Repo](https://github.com/kwak513/medical-for-foreigns)


