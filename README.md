# 🏥 닥터 K (Doctor K)

## 📢 서비스 한줄 소개  
**Doctor K** — 진료 가능 언어, 진료과목, 지역 필터링이 가능한 다국어 병원 정보 웹사이트

---

## 📝 서비스 소개  
**Doctor K**는 외국인이 편리하게 병원 정보를 확인하고,
진료 가능한 언어, 진료과목, 지역 기준으로 필터링하여 자신에게 맞는 병원을 쉽게 찾을 수 있도록 돕는 다국어 웹 서비스입니다.

---

## 👥 개발자 소개

| 이름   | 역할         |
|--------|--------------|
| 곽채연 | Frontend 개발, Backend 개발 |

---

## 🛠 기술 스택

- **Framework**: Spring Boot  
- **Language**: Java  
- **Database**: MariaDB  
- **ORM**: Spring Data JPA  
- **Build Tool**: Maven
- **Documentation**: Swagger  

---

## 📁 주요 파일 구조
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
│ │ │ ├── service/ # JPA 인터페이스
│ │ │ │ ├── DeeplService.java
│ │ │ │ └── HospitalService.java
│ │ │ │
│ │ │ ├── controller/ # API 요청 처리
│ │ │ │ └── HospitalController.java
│ │ └── resources/
│ │ │ └── application.properties
│
└── pom.xml

```
---

## 📌 주요 기능

### ✅ 병원 정보 조회
- 병원 리스트 및 상세 정보 제공
- 진료 가능 언어, 진료과목, 지역 기준 필터링

### 🌐 다국어 지원
- Deepl API 연동으로 다국어 번역 기능 지원

---

## 🚀 백엔드 서버 실행 방법

```bash
# 프로젝트 클론
git clone https://github.com/kwak513/medical-foreigns-back
cd medical-foreigns-back

# 실행 (IntelliJ에서 실행하거나 CLI에서)
./mvnw spring-boot:run

```
## 💡 Eclipse 실행 방법
- 프로젝트를 Import → Maven → Existing Maven Projects로 불러오기
- 프로젝트 선택 후 Run As → Spring Boot App 실행
 
⚠️ src/main/resources/application.properties 파일에서 DB 연결 정보 등 환경 설정 필요
```
spring.datasource.url=jdbc:mariadb://localhost:3306/dbname
spring.datasource.username=your_username
spring.datasource.password=your_password
server.port=8080
spring.jpa.hibernate.ddl-auto=update
```
## 🚀 API 문서 (Swagger)
Swagger UI로 API 문서 확인 가능:
http://localhost:8080/swagger-ui/index.html

## 🧩 관련 레포지토리
**Frontend**: [Link to Frontend Repo](https://github.com/kwak513/medical-for-foreigns)


