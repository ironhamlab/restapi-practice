# Library REST API

도서 대여 시스템을 구현한 REST API 프로젝트입니다.
Spring Boot를 기반으로 회원, 도서, 대여 관리 기능을 제공합니다.

## 기술 스택

### Backend
- **Java 21**
- **Spring Boot 4.0.6**
- **Spring Web MVC**
- **Gradle**

### Libraries
- **Lombok** - 보일러플레이트 코드 감소
- **Bean Validation** - 입력 데이터 검증
- **SpringDoc OpenAPI** - API 문서 자동화 (Swagger UI)
- **Spring Boot DevTools** - 개발 생산성 향상

## 주요 기능

### 1. 회원 관리
- 회원 목록 조회 (이름 검색 지원)
- 회원 상세 조회
- 회원 등록

### 2. 도서 관리
- 도서 목록 조회 (제목 검색, 대여 상태별 필터링 지원)
- 도서 상세 조회
- 도서 등록

### 3. 대여 관리
- 도서 대여
- 도서 반납
- 대여 정보 조회
- 대여 상태 관리 (대여 중, 반납 완료)

## 프로젝트 구조

```
src/main/java/com/practice/restapi/
├── Application.java              # Spring Boot 애플리케이션 진입점
├── controller/
│   └── LibraryController.java    # REST API 엔드포인트 정의
├── dto/
│   ├── BookDTO.java              # 도서 DTO
│   ├── MemberDTO.java            # 회원 DTO
│   ├── RentalDTO.java            # 대여 DTO
│   └── RentalRequest.java        # 대여 요청 DTO
├── enums/
│   ├── BookStatus.java           # 도서 상태 (AVAILABLE, RENTED)
│   └── RentalStatus.java         # 대여 상태 (RENTED, RETURNED)
├── exception/
│   ├── LibraryExceptionHandler.java        # 전역 예외 처리
│   ├── BookNotFoundException.java          # 도서 미발견 예외
│   ├── MemberNotFoundException.java        # 회원 미발견 예외
│   ├── RentalNotFoundException.java        # 대여 정보 미발견 예외
│   └── BookAlreadyRentedException.java     # 도서 중복 대여 예외
└── common/
    ├── ResponseMessage.java      # API 응답 메시지 포맷
    └── ErrorResponse.java        # 에러 응답 포맷
```

## API 명세

### 회원 API

| Method | Endpoint | Description | Request | Response |
|--------|----------|-------------|---------|----------|
| GET | `/api/v1/library/members` | 회원 목록 조회 | Query: `name` (optional) | 200 OK |
| GET | `/api/v1/library/members/{memberNo}` | 회원 상세 조회 | Path: `memberNo` | 200 OK |
| POST | `/api/v1/library/members` | 회원 등록 | Body: MemberDTO | 201 Created |

### 도서 API

| Method | Endpoint | Description | Request | Response |
|--------|----------|-------------|---------|----------|
| GET | `/api/v1/library/books` | 도서 목록 조회 | Query: `title`, `status` (optional) | 200 OK |
| GET | `/api/v1/library/books/{bookNo}` | 도서 상세 조회 | Path: `bookNo` | 200 OK |
| POST | `/api/v1/library/books` | 도서 등록 | Body: BookDTO | 201 Created |

### 대여 API

| Method | Endpoint | Description | Request | Response |
|--------|----------|-------------|---------|----------|
| POST | `/api/v1/library/rentals` | 도서 대여 | Body: RentalRequest | 201 Created |
| GET | `/api/v1/library/rentals/{rentalNo}` | 대여 정보 조회 | Path: `rentalNo` | 200 OK |
| PATCH | `/api/v1/library/rentals/{rentalNo}/return` | 도서 반납 | Path: `rentalNo` | 204 No Content |

## 실행 방법

### 1. 프로젝트 클론
```bash
git clone https://github.com/ironhamlab/restapi-practice.git
cd restapi-practice
```

### 2. 애플리케이션 실행
```bash
# Gradle Wrapper를 사용한 실행
./gradlew bootRun

# 또는 JAR 파일 빌드 후 실행
./gradlew build
java -jar build/libs/restapi-0.0.1-SNAPSHOT.jar
```

### 3. API 문서 확인
서버 실행 후 브라우저에서 아래 URL로 접속
```
http://localhost:8080/swagger-ui.html
```

## API 사용 예시

### 회원 등록
```bash
curl -X POST http://localhost:8080/api/v1/library/members \
  -H "Content-Type: application/json" \
  -d '{
    "username": "user01",
    "name": "김철수",
    "email": "user01@example.com"
  }'
```

### 도서 등록
```bash
curl -X POST http://localhost:8080/api/v1/library/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "클린 코드",
    "author": "로버트 C. 마틴",
    "isbn": "978-89-6626-250-5",
    "publishedAt": "2013-12-24"
  }'
```

### 도서 대여
```bash
curl -X POST http://localhost:8080/api/v1/library/rentals \
  -H "Content-Type: application/json" \
  -d '{
    "memberNo": 1,
    "bookNo": 1
  }'
```

### 도서 반납
```bash
curl -X PATCH http://localhost:8080/api/v1/library/rentals/1/return
```

### 회원 목록 조회 (이름 검색)
```bash
curl http://localhost:8080/api/v1/library/members?name=홍길동
```

### 도서 목록 조회 (대여 가능한 도서만)
```bash
curl http://localhost:8080/api/v1/library/books?status=AVAILABLE
```

## 구현 특징

### 1. RESTful API 설계
- HTTP 메서드를 적절히 활용 (GET, POST, PATCH)
- 리소스 기반 URI 설계
- 적절한 HTTP 상태 코드 반환 (200, 201, 204, 404)
- Location 헤더를 통한 생성된 리소스 URI 제공

### 2. 유효성 검증
- Bean Validation을 활용한 입력 데이터 검증
- `@NotBlank`, `@PastOrPresent` 등의 어노테이션 활용
- 커스텀 예외를 통한 명확한 에러 메시지 제공

### 3. 예외 처리
- `@RestControllerAdvice`를 사용한 전역 예외 처리
- 커스텀 예외 클래스 정의
- 일관된 에러 응답 포맷 제공

### 4. 비즈니스 로직
- 도서 대여 시 대여 가능 여부 검증
- 대여 기간 자동 계산 (14일)
- 반납 시 도서 상태 자동 업데이트
- 중복 반납 방지

### 5. 응답 포맷 통일
- 성공 응답: ResponseMessage (상태 코드, 메시지, 데이터)
- 에러 응답: ErrorResponse (상태 코드, 메시지, 타임스탬프)

## 개발 환경

- **IDE**: IntelliJ IDEA
- **Java**: 21
- **Build Tool**: Gradle 8.x
- **Spring Boot**: 4.0.6

## 주요 학습 내용

- Spring Boot를 활용한 REST API 개발
- RESTful API 설계 원칙 적용
- Bean Validation을 통한 입력 검증
- 전역 예외 처리 구현
- Swagger를 통한 API 문서화
- HTTP 상태 코드의 적절한 활용
- DTO 패턴을 통한 계층 간 데이터 전달

## 개선 가능한 점

- 데이터베이스 연동 (현재는 In-Memory 저장소 사용)
- Spring Data JPA를 활용한 영속성 관리
- 서비스 레이어 분리로 관심사 분리
- 단위 테스트 및 통합 테스트 추가
- 페이징 처리
- 인증/인가 기능 추가

## 라이선스

이 프로젝트는 학습 목적으로 작성되었습니다.
