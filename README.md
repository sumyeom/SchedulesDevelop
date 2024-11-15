# 일정 관리 앱을 업그이드 해보자!!

Spring을 이용하여 일정을 관리할 수 있는 앱 서버 업그레이드 버전 구현

---
- Level 1 : 일정  CRUD
  - 일정을 저장, 조회, 수정, 삭제
  - 작성 유저명, 할일 제목, 할일 내용, 작성일, 수정일
  - 작성일, 수정일 필드는 JPA Auditing 활용

- Level 2 : 유저 CRUD
  - 유저를 저장, 조회, 삭제
  - 유저명, 이메일, 작성일, 수정일
  - 작성일, 수정일 필드는 JPA Auditing 활용
  - 연관 관계 : 유저 고유 식별자 필드를 가짐

- Level 3 : 회원가입 기능
  - 유저에 비밀 번호 필드 추가

- Level 4 : 로그인
  - Cookie / Session 활용해 로그인 기능 구현
  - 필터를 활용해 인증 처리(@Configuration 활용)
  - 이메일, 비밀번호 활용해서 로그인 기능 구현
  - 회원 가입, 로그인 요청은 인증 처리에서 제외
  - 로그인 시 이메일, 비밀번호가 일치하지 않을 경우 401 반환

- Level 5 : 예외 처리 적용
  - Validation 활용하여 예외처리 적용
  
- Level 6 : 비밀번호 암호화
  - Level 3에서 추가한 비밀번호 필드 암호화 처리

- Level 7 : 댓글 CRUD
  - 생성한 일정에 댓글 남길 수 있다.(댓글과 일정이 연관관계를 가짐)
  - 댓글을 저장, 조회, 수정, 삭제
  - 댓글 내용, 작성일, 수정일, 유저 고유 식별자, 일정 고유 식별자
  - 작성일, 수정일 필드는 JPA Auditing 활용
  
- Level 8 : 영속성 전이를 활용한 삭제
  - 유저를 삭제할 때 해당 유저가 생성한 일정과 댓글도 삭제
  
- Level 9 : 일정 페이징 조회
  - 일정을 Pageable, Page 활용하여 페이지네이션 구현
  - 페이지 번호, 페이지 크기 쿼리 파라미터로 전달
  - 할일 제목, 할일 내용, 댓글 개수, 일정 작성일, 일정 수정일, 일정 작성 유저명 필드 조회
  - 디폴트 페이지 크기 10
  - 수정일 기준으로 내림차순으로 정렬

---
## API 명세서

### Schedules

| 기능           | Method | URL                          | request    | response       | 상태 코드                                                    |
| -------------- | ------ | ---------------------------- | ---------- | -------------- | ------------------------------------------------------------ |
| 일정 생성      | POST   | /schedules              | 요청 body  | 등록 정보      | 200: 정상 등록 <br> 400 : 비정상적인 값                      |
| 전체 일정 조회 | GET    | /schedules              | 요청 param | 다건 응답 정보 | 200: 정상 조회 <br> 404 : 일정 찾을 수 없음                  |
| 선택 일정 조회 | GET    | /schedules/{scheduleId} | 요청 param | 단건 응답 정보 | 200: 정상 조회 <br> 404 : 일정 찾을 수 없음                  |
| 선택 일정 수정 | UPDATE | /schedules/{scheduleId} | 요청 body  | 수정 정보      | 200: 정상 등록 <br> 400 : 비정상적인 값 <br> 404 : 일정 찾을 수 없음 |
| 선택 일정 삭제 | DELETE | /schedules/{scheduleId} | - | -              | 200: 정상 등록 <br> 400 : 비정상적인 값 <br> 404 : 일정 찾을 수 없음                  |

<details>
  <summary>일정 생성</summary>


- 상세 정보

  <table>
    <tr>
      <td>Method</td>
      <td>URL</td>
    </tr>
    <tr>
    	<td>POST</td>
      <td>/schedules</td>
    </tr>
  </table>

- Request : POST /schedules

  ```json
  {
    "name" : "숨염",
    "password" : "qwer!@#$",
    "email" : "sum@gmail.com"
    "title" : "11월 1일 데일리 스크럼",
    "content" : "1. Spring 강의 듣기 | 2. Lv.0 과제 완료"
  }
  ```

  - Request Elements

    <table>
      <tr>
      	<td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
      	<td>id</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 id</td>
      </tr>
      <tr>
      	<td>name</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 이름</td>
      </tr>
      <tr>
      	<td>password</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 비밀번호</td>
      </tr>
      <tr>
      	<td>email</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 이메일</td>
      </tr>
      <tr>
        <td>title</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 제목</td>
      </tr>
      <tr>
        <td>content</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 내용</td>
      </tr>
    </table>

- Response

  - 200 : 정상 등록

  ```plaintext
  HTTP/1.1 200 OK
  ```

  ```json
  {
    "scheduleId": 1
  }
  ```

  - 400 : 비정상적인 값

  ```plaintext
  HTTP/1.1 400 Bad Request
  ```

  ```json
  {
    "status": 400,
    "code": "INVALID_INPUT_VALUE",
    "message": "잘못된 입력값입니다.",
    "detailMessage": "[password] :비밀번호를 입력해주세요."
  }
  ```

  - Response Elements

    <table>
      <tr>
      	<td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
      	<td>scheduleId</td>
        <td>Integer</td>
        <td>필수</td>
        <td>일정 id</td>
      </tr>
    </table>

    </details>



<details>
  <summary>전체 일정 조회</summary>


- 상세 정보

  <table>
    <tr>
    	<td>Method</td>
      <td>URL</td>
    </tr>
    <tr>
    	<td>GET</td>
      <td>/schedules</td>
    </tr>
  </table>

- Request : GET /schedules

  ```plaintext
  - no prameter
  - name=수염
  - updatedDate=2024-11-08
  - name=수염&updatedDate=2024-11-08
  ```

- Response

  - 200 : 정상 조회

  ```plaintext
  HTTP/1.1 200 OK
  ```

  ```json
  [{
      "scheduleId": 1,
      "name": "수염",
      "email": "sum@gmail.com",
      "title": "11월 8일 데일리 스크럼1",
      "content": "1. Spring 강의 듣기 | 2. Lv.0 과제 완료",
      "createdDate": "2024-11-08T11:55:28",
      "updatedDate": "2024-11-08T11:55:28"
  },
  {
      "scheduleId": 2,
      "name": "수염이",
      "email": "yeom123@gmail.com",
      "title": "11월 12일 데일리 스크럼4",
      "content": "1. 복습",
      "createdDate": "2024-11-12T12:18:47",
      "updatedDate": "2024-11-12T12:18:47"
  }]
  ```

  - 404 : 일정 찾을 수 없음

  ```plaintext
  HTTP/1.1 404 Not Found
  ```

  ```json
  {
    "status": 404,
    "code": "SCHEDULE_NOT_FOUND",
    "message": "해당 일정을 찾을 수 없습니다.",
    "detailMessage": "해당 일정을 찾을 수 없습니다."
  }
  ```

  - Response Elements

    <table>
      <tr>
        <td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
        <td>scheduleId</td>
        <td>Integer</td>
        <td>필수</td>
        <td>일정 id</td>
      </tr>
      <tr>
        <td>name</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 이름</td>
      </tr>
      <tr>
        <td>email</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 이메일</td>
      </tr>
      <tr>
        <td>title</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 제목</td>
      </tr>
      <tr>
        <td>content</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 내용</td>
      </tr>
      <tr>
        <td>createDate</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 등록 날짜 timestamp</td>
      </tr>
      <tr>
        <td>updateDate</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 수정 날짜 timestamp</td>
      </tr>
    </table>

    </details>



<details>
  <summary>선택 일정 조회</summary>


- 상세 정보

  <table>
    <tr>
    	<td>Method</td>
      <td>URL</td>
    </tr>
    <tr>
    	<td>GET</td>
      <td>/schedules/{scheduleId}</td>
    </tr>
  </table>

- Request : GET /schedules/{scheduleId}

- Response

  - 200 : 정상 조회

  ```plaintext
  HTTP/1.1 200 OK
  ```

  ```json
  {
    "scheduleId": 15,
    "name": "수염ww",
    "email": "sumyeom221232@gmail.com",
    "title": "11월 8일",
    "content": "1. 코드카타",
    "createdDate": "2024-11-08T12:17:28",
    "updatedDate": "2024-11-08T12:17:28"
  }
  ```

  - 404 : 일정 찾을 수 없음

  ```plaintext
  HTTP/1.1 404 Not Found
  ```

  ```json
  {
    "status": 404,
    "code": "SCHEDULE_NOT_FOUND",
    "message": "해당 일정을 찾을 수 없습니다.",
    "detailMessage": "해당 일정을 찾을 수 없습니다."
  }
  ```

  - Response Elements

    <table>
      <tr>
        <td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
        <td>scheduleId</td>
        <td>Integer</td>
        <td>필수</td>
        <td>일정 id</td>
      </tr>
       <tr>
        <td>name</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 이름</td>
      </tr>
      <tr>
        <td>email</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 이메일</td>
      </tr>
      <tr>
        <td>title</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 제목</td>
      </tr>
      <tr>
        <td>content</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 내용</td>
      </tr>
      <tr>
        <td>createDate</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 등록 날짜 timestamp</td>
      </tr>
      <tr>
        <td>updateDate</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 수정 날짜 timestamp</td>
      </tr>
    </table>

    </details>


<details>
  <summary>선택 일정 수정</summary>


- 상세 정보

  <table>
    <tr>
    	<td>Method</td>
      <td>URL</td>
    </tr>
    <tr>
    	<td>PUT</td>
      <td>/schedules/{scheduleId}</td>
    </tr>
  </table>

- Request : PUT /schedules/{scheduleId}

  ```json
  {
    "userName" : "수염",
    "password" : "qwer!@#$2",
    "title" : "수정한 제목",
    "content" : "수정한 내용"
  }
  ```

  - Request Elements

    <table>
      <tr>
      	<td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
      	<td>name</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 이름</td>
      </tr>
      <tr>
      	<td>password</td>
        <td>String</td>
        <td>필수</td>
        <td>작성자 비밀번호</td>
      </tr>
      <tr>
        <td>title</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 제목</td>
      </tr>
      <tr>
        <td>content</td>
        <td>String</td>
        <td>필수</td>
        <td>일정 내용</td>
      </tr>


    </table>

- Response

  - 200 : 정상 수정

  ```plaintext
  HTTP/1.1 200 OK
  ```

  ```json
  {
    "scheduleId": 1
  }
  ```

  - 400 : 비정상적인 값

  ```plaintext
  HTTP/1.1 400 Bad Request
  ```

  ```json
  {
    "status": 400,
    "code": "INVALID_INPUT_VALUE",
    "message": "잘못된 입력값입니다.",
    "detailMessage": "[password] :비밀번호를 입력해주세요."
  }
  ```
  
  - 403 :  비밀번호 불일치
    
  ```plaintext
  HTTP/1.1 403 Forbidden
  ```

  ```json
  {
    "status": 403,
    "code": "INVALID_PASSWORD",
    "message": "비밀 번호가 일치하지 않습니다.",
    "detailMessage": "비밀 번호가 일치하지 않습니다."
  }
  ```

  - 404 : 일정 찾을 수 없음

  ```plaintext
  HTTP/1.1 404 Not Found
  ```

  ```json
  {
    "status": 404,
    "code": "SCHEDULE_NOT_FOUND",
    "message": "해당 일정을 찾을 수 없습니다.",
    "detailMessage": "해당 일정을 찾을 수 없습니다."
  }
  ```

  - Response Elements

    <table>
      <tr>
      	<td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
      	<td>scheduleId</td>
        <td>Integer</td>
        <td>필수</td>
        <td>일정 id</td>
      </tr>
    </table>

</details>

<details>
  <summary>선택 일정 삭제</summary>


- 상세 정보

 <table>
  <tr>
    <td>Method</td>
    <td>URL</td>
  </tr>
  <tr>
    <td>DELETE</td>
    <td>/schedules/{scheduleId}</td>
  </tr>
</table>


- Request : DELETE /schedules/{scheduleId}

  ```plaintext
  password=123
  ```

- Response

  - 200 : 정상 삭제

  ```plaintext
  HTTP/1.1 200 OK
  ```

  - 400 : 비정상적인 값
    
  ```plaintext
  HTTP/1.1 400 Bad Request
  ```

  ```json
  {
    "status": 400,
    "code": "INVALID_INPUT_VALUE",
    "message": "잘못된 입력값입니다.",
    "detailMessage": "[password] :비밀번호를 입력해주세요."
  }
  ```
  
  - 403 :  비밀번호 불일치
    
  ```plaintext
  HTTP/1.1 403 Forbidden
  ```

  ```json
  {
    "status": 403,
    "code": "INVALID_PASSWORD",
    "message": "비밀 번호가 일치하지 않습니다.",
    "detailMessage": "비밀 번호가 일치하지 않습니다."
  }
  ```
  
  
  - 404 : 일정 찾을 수 없음

  ```plaintext
  HTTP/1.1 404 Not Found
  ```

  ```json
  {
    "status": 404,
    "code": "SCHEDULE_NOT_FOUND",
    "message": "해당 일정을 찾을 수 없습니다.",
    "detailMessage": "해당 일정을 찾을 수 없습니다."
  }
  ```

  </details>

---
### User

| 기능       | Method | URL                  | request    | response       | 상태 코드                                                    |
|----------| ------ |----------------------| ---------- | -------------- | ------------------------------------------------------------ |
| 유저 저장    | POST   | /users          | 요청 body  | 등록 정보      | 200: 정상 등록 <br> 400 : 비정상적인 값                      |
| 전체 유저 조회 | GET    | /users          | 요청 param | 다건 응답 정보 | 200: 정상 조회 <br> 404 : 일정 찾을 수 없음                  |
| 선택 유저 조회 | GET    | /users/{userId} | 요청 param | 단건 응답 정보 | 200: 정상 조회 <br> 404 : 일정 찾을 수 없음                  |
| 선택 유저 삭제 | DELETE | /users/{userId} | -  | -              | 200: 정상 등록 <br> 400 : 비정상적인 값 <br> 404 : 일정 찾을 수 없음                  |


  <details>
    <summary>유저 저장</summary>

  - 상세 정보
  
     <table>
      <tr>
        <td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
        <td>POST</td>
        <td>/users/{usersId}</td>
      </tr>
    </table>
  
  - Request : GET /users
    ```json
    {
      "name" : "노랑",
      "email" : "yellow@gmail.com",
      "password" : "1234"
    }
    ```

    - Request Elements
      <table>
        <tr>
          <td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
          <td>name</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 이름</td>
        </tr>
        <tr>
          <td>email</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 이메일</td>
        </tr>
        <tr>
          <td>password</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 비밀번호</td>
        </tr>
      </table>

  - Response
    - 200 : 정상 등록
      ```plaintext
      HTTP/1.1 200 OK
      ```
      ```json
      {
        "id" : 1
      }
      ```

    - 400 : 비정상적인 값
      ```plaintext
      HTTP/1.1 400 Bad Request
      ```

      ```json
      {
        "status": 400,
        "code": "INVALID_INPUT_VALUE",
        "message": "잘못된 입력값입니다.",
        "detailMessage": "[password] :비밀번호를 입력해주세요."
      }
      ```
    - Response Elements
      <table>
        <tr>
          <td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
          <td>id</td>
          <td>Integer</td>
          <td>필수</td>
          <td>일정 id</td>
        </tr>
      </table>


  </details>

<details>
  <summary>전체 유저 조회</summary>

  - 상세 정보
    <table>
      <tr>
        <td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
        <td>GET</td>
        <td>/users</td>
      </tr>
    </table>

  - Request : GET /users

    ```plaintext
    - no prameter
    - page=1&size=4
    ```
  - Response
    - 200 : 정상 조회

      ```plaintext
      HTTP/1.1 200 OK
      ```

      ```json
      [{
          "id": 1,
          "username": "mark",
          "email": "mark@gmail.com",
          "createdAt": "2024-11-15T12:11:42.568675",
          "modifiedAt": "2024-11-15T12:11:42.568675"
      },
      {
          "id": 2,
          "username": "mark",
          "email": "mark@gmail.com",
          "createdAt": "2024-11-15T12:11:42.568675",
          "modifiedAt": "2024-11-15T12:11:42.568675"
      }]
      ```
    - 404 : 유저 찾을 수 없음
      ```plaintext
      HTTP/1.1 404 Not Found
      ```
      ```json
      {
        "status": 404,
        "code": "USER_NOT_FOUND",
        "message": "해당 유저를 찾을 수 없습니다.",
        "detailMessage": "해당 유저를 찾을 수 없습니다."
      }
      ```

    - Response Elements

      <table>
        <tr>
          <td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
          <td>Id</td>
          <td>Integer</td>
          <td>필수</td>
          <td>유저 id</td>
        </tr>
        <tr>
          <td>name</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 이름</td>
        </tr>
        <tr>
          <td>email</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 이메일</td>
        </tr>
        <tr>
          <td>createDate</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 등록 날짜 timestamp</td>
        </tr>
        <tr>
          <td>updateDate</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 수정 날짜 timestamp</td>
        </tr>
      </table>
</details>

<details>
  <summary>선택 일정 조회</summary>

  - 상세 정보
    <table>
      <tr>
        <td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
        <td>GET</td>
        <td>/usersId/{usersId}</td>
      </tr>
    </table>

  - Request : GET /users/{usersId}

  - Response
    - 200 : 정상 조회
      ```plaintext
      HTTP/1.1 200 OK
      ```
      ~~~json
      {
          "id": 1,
          "username": "mark",
          "email": "mark@gmail.com",
          "createdAt": "2024-11-15T12:11:42.568675",
          "modifiedAt": "2024-11-15T12:11:42.568675"
      }
      ~~~
    - 404 : 유저 찾을 수 없음
      ```plaintext
      HTTP/1.1 404 Not Found
      ```
      ```json
      {
        "status": 404,
        "code": "USER_NOT_FOUND",
        "message": "해당 유저를 찾을 수 없습니다.",
        "detailMessage": "해당 유저를 찾을 수 없습니다."
      }
      ```

    - Response Elements

      <table>
        <tr>
          <td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
          <td>id</td>
          <td>Integer</td>
          <td>필수</td>
          <td>유저 id</td>
        </tr>
        <tr>
          <td>name</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 이름</td>
        </tr>
        <tr>
          <td>email</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 이메일</td>
        </tr>
        <tr>
          <td>createDate</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 등록 날짜 timestamp</td>
        </tr>
        <tr>
          <td>updateDate</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 수정 날짜 timestamp</td>
        </tr>
      </table>
</details>

<details>
  <summary>선택 유저 삭제</summary>

  - 상세 정보
    <table>
      <tr>
        <td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
        <td>DELETE</td>
        <td>/usersId/{usersId}</td>
      </tr>
    </table>
    
  - Request : DELETE /schedules/{scheduleId}

    ```plaintext
    password=123
    ```

  - Response

    - 200 : 정상 삭제
    
      ```plaintext
      HTTP/1.1 200 OK
      ```
    - 400 : 비정상적인 값
      ```plaintext
      HTTP/1.1 400 Bad Request
      ```
      ```json
      {
        "status": 400,
        "code": "INVALID_INPUT_VALUE",
        "message": "잘못된 입력값입니다.",
        "detailMessage": "[password] :비밀번호를 입력해주세요."
      }
      ```
    - 403 :  비밀번호 불일치
      ```plaintext
      HTTP/1.1 403 Forbidden
      ```
      ```json
      {
        "status": 403,
        "code": "INVALID_PASSWORD",
        "message": "비밀 번호가 일치하지 않습니다.",
        "detailMessage": "비밀 번호가 일치하지 않습니다."
      }
      ```
    - 404 : 일정 찾을 수 없음

      ```plaintext
      HTTP/1.1 404 Not Found
      ```

      ```json
      {
        "status": 404,
        "code": "USER_NOT_FOUND",
        "message": "해당 유저를 찾을 수 없습니다.",
        "detailMessage": "해당 유저를 찾을 수 없습니다."
      }
      ```
</details>

---
### Login

| 기능       | Method | URL                  | request    | response       | 상태 코드                                                    |
|----------| ------ |----------------------| ---------- | -------------- | ------------------------------------------------------------ |
| 로그인    | POST   | /login          | 요청 body  | 로그인      | 200: 로그인 <br> 400 : 비정상적인 값 <br> 401 : 로그인 실패                     |
| 로그아웃 | POST    | /logout          | 요청 param | 로그아웃 | 200: 로그아웃                  |
| 회원 가입<br>(User 등록과 같음) | POST    | /signup | 요청 body | 유저 등록 | 200: 정상 조회 <br> 400 : 비정상적인 값                  |


<details>
  <summary>로그인</summary>

  - 상세 정보
    <table>
      <tr>
        <td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
        <td>POST</td>
        <td>/login</td>
      </tr>
    </table>
  
  - Request : POST /login
    ```json
    {
      "email" : "mark@gmail.com",
      "password" : "1111"
    }
    ```
  
    - Request Elements

      <table>
        <tr>
        	<td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
        	<td>email</td>
          <td>String</td>
          <td>필수</td>
          <td>로그인 email</td>
        </tr>
        <tr>
        	<td>password</td>
          <td>String</td>
          <td>필수</td>
          <td>로그인 password</td>
        </tr>
      </table>

  - Response
    - 200 : 정상 등록
      ```plaintext
      HTTP/1.1 200 OK
      ```
    - 400 : 비정상적인 값
      ```plaintext
      HTTP/1.1 400 Bad Request
      ```
      ```json
      {
        "status": 400,
        "code": "INVALID_INPUT_VALUE",
        "message": "잘못된 입력값입니다.",
        "detailMessage": "[password] :비밀번호를 입력해주세요."
      }
      ```
    - 401 : 로그인 실패
      ```plaintext
      HTTP/1.1 401 Unauthorized
      ```
      ```json
      {
        "status": 401,
        "code": "INVALID_PASSWORD",
        "message": "비밀 번호가 일치하지 않습니다.",
      "detailMessage": "비밀 번호가 일치하지 않습니다."
      }
      ```
</details>

<details>
  <summary>로그아웃</summary>

  - 상세 정보
    <table>
      <tr>
        <td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
        <td>POST</td>
        <td>/logout</td>
      </tr>
    </table>

  - Request : POST /logout

  - Response
      - 200 : 로그아웃
        ```plaintext
        HTTP/1.1 200 OK
        ```

</details>

<details>
  <summary>회원 가입</summary>

  - 상세 정보
  
     <table>
      <tr>
        <td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
        <td>POST</td>
        <td>/signup</td>
      </tr>
    </table>
  
  - Request : POST /signup
    ```json
    {
      "name" : "노랑",
      "email" : "yellow@gmail.com",
      "password" : "1234"
    }
    ```

    - Request Elements
      <table>
        <tr>
          <td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
          <td>name</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 이름</td>
        </tr>
        <tr>
          <td>email</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 이메일</td>
        </tr>
        <tr>
          <td>password</td>
          <td>String</td>
          <td>필수</td>
          <td>유저 비밀번호</td>
        </tr>
      </table>

  - Response
    - 200 : 정상 등록
      ```plaintext
      HTTP/1.1 200 OK
      ```
      ```json
      {
        "id" : 1
      }
      ```

    - 400 : 비정상적인 값
      ```plaintext
      HTTP/1.1 400 Bad Request
      ```

      ```json
      {
        "status": 400,
        "code": "INVALID_INPUT_VALUE",
        "message": "잘못된 입력값입니다.",
        "detailMessage": "[password] :비밀번호를 입력해주세요."
      }
      ```
    - Response Elements
      <table>
        <tr>
          <td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
          <td>id</td>
          <td>Integer</td>
          <td>필수</td>
          <td>일정 id</td>
        </tr>
      </table>
</details>

---

### Comment

| 기능           | Method | URL                          | request    | response       | 상태 코드                                                    |
| -------------- | ------ | ---------------------------- | ---------- | -------------- | ------------------------------------------------------------ |
| 댓글 작성      | POST   | /schedules/{scheduleId}/comments              | 요청 body  | 등록 정보      | 200: 정상 등록 <br> 400 : 비정상적인 값 <br> 404 : 일정 찾을 수 없음                     |
| 전체 댓글 조회 | GET    | /schedules/{scheduleId}/comments              | 요청 param | 다건 응답 정보 | 200: 정상 조회 <br> 404 : 댓글 찾을 수 없음                  |
| 선택 댓글 조회 | GET    | /schedules/{scheduleId}/comments/{commentsId} | 요청 param | 단건 응답 정보 | 200: 정상 조회 <br> 404 : 댓글 찾을 수 없음                  |
| 선택 댓글 수정 | UPDATE | /schedules/{scheduleId}/comments/{commentsId} | 요청 body  | 수정 정보      | 200: 정상 등록 <br> 400 : 비정상적인 값 <br> 404 : 댓글 찾을 수 없음 |
| 선택 댓글 삭제 | DELETE | /schedules/{scheduleId}/comments/{commentsId} | - | -              | 200: 정상 등록 <br> 400 : 비정상적인 값 <br> 404 : 일정 찾을 수 없음                  |

<details>
  <summary>댓글 생성</summary>


  - 상세 정보

    <table>
      <tr>
        <td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
      	<td>POST</td>
        <td>/schedules/{scheduleId}/comments</td>
      </tr>
    </table>

  - Request : POST /schedules/{scheduleId}/comments

    ```json
    {
      "comment" : "123",
      "username" : "orange"
    }
    ```

    - Request Elements

      <table>
        <tr>
        	<td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
        	<td>comment</td>
          <td>String</td>
          <td>필수</td>
          <td>댓글 내용</td>
        </tr>
        <tr>
        	<td>username</td>
          <td>String</td>
          <td>필수</td>
          <td>작성자 이름</td>
        </tr>
      </table>

  - Response

    - 200 : 정상 등록

      ```plaintext
      HTTP/1.1 200 OK
      ```

      ```json
      {
        "id": 1,
        "comment": "123",
        "createdAt": "2024-11-15T12:42:33.776113",
        "modifiedAt": "2024-11-15T12:42:33.776113"
      }
      ```

    - 400 : 비정상적인 값

      ```plaintext
      HTTP/1.1 400 Bad Request
      ```
      ```json
      {
        "status": 400,
        "code": "INVALID_INPUT_VALUE",
        "message": "잘못된 입력값입니다.",
        "detailMessage": "[username] :이름을 입력해주세요."
      }
      ```

    - Response Elements

      <table>
        <tr>
        	<td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
        	<td>id</td>
          <td>Integer</td>
          <td>필수</td>
          <td>댓글 id</td>
        </tr>
        <tr>
        	<td>comment</td>
          <td>String</td>
          <td>필수</td>
          <td>댓글 내용</td>
        </tr>
        <tr>
          <td>createDate</td>
          <td>String</td>
          <td>필수</td>
          <td>댓글 등록 날짜 timestamp</td>
        </tr>
        <tr>
          <td>updateDate</td>
          <td>String</td>
          <td>필수</td>
          <td>댓글 수정 날짜 timestamp</td>
        </tr>
      </table>

    </details>



<details>
  <summary>전체 댓글 조회</summary>


  - 상세 정보

    <table>
      <tr>
      	<td>Method</td>
        <td>URL</td>
      </tr>
      <tr>
      	<td>GET</td>
        <td>/schedules/{scheduleId}/comments</td>
      </tr>
    </table>

  - Request : GET /home/schedules

    ```plaintext
    - no prameter
    - page=1&size=4
    ```

  - Response

    - 200 : 정상 조회

      ```plaintext
      HTTP/1.1 200 OK
      ```

      ```json
      [
        {
            "id": 1,
            "comment": "123",
            "createdAt": "2024-11-15T12:42:33.776113",
            "modifiedAt": "2024-11-15T12:42:33.776113"
        },
        {
            "id": 2,
            "comment": "123",
            "createdAt": "2024-11-15T12:46:54.918585",
            "modifiedAt": "2024-11-15T12:46:54.918585"
        }
      ]
      ```

    - 404 : 일정 찾을 수 없음

      ```plaintext
      HTTP/1.1 404 Not Found
      ```

      ```json
      {
        "status": 404,
        "code": "SCHEDULE_NOT_FOUND",
        "message": "해당 일정을 찾을 수 없습니다.",
        "detailMessage": "해당 일정을 찾을 수 없습니다."
      }
      ```

    - Response Elements

      <table>
        <tr>
          <td>파라미터</td>
          <td>타입</td>
          <td>필수 여부</td>
          <td>설명</td>
        </tr>
        <tr>
          <td>id</td>
          <td>Integer</td>
          <td>필수</td>
          <td>댓글 id</td>
        </tr>
        <tr>
          <td>comment</td>
          <td>String</td>
          <td>필수</td>
          <td>댓글 내용</td>
        </tr>
        <tr>
          <td>createDate</td>
          <td>String</td>
          <td>필수</td>
          <td>댓글 등록 날짜 timestamp</td>
        </tr>
        <tr>
          <td>updateDate</td>
          <td>String</td>
          <td>필수</td>
          <td>댓글 수정 날짜 timestamp</td>
        </tr>
      </table>

</details>



<details>
  <summary>선택 일정 조회</summary>


- 상세 정보

  <table>
    <tr>
    	<td>Method</td>
      <td>URL</td>
    </tr>
    <tr>
    	<td>GET</td>
      <td>/schedules/{scheduleId}/comments/{commentsId}</td>
    </tr>
  </table>

- Request : GET /schedules/{scheduleId}/comments/{commentsId}

- Response

  - 200 : 정상 조회

    ```plaintext
    HTTP/1.1 200 OK
    ```

    ```json
    {
        "id": 1,
        "comment": "123",
        "createdAt": "2024-11-15T12:42:33.776113",
        "modifiedAt": "2024-11-15T12:42:33.776113"
    }
    ```

  - 404 : 일정 찾을 수 없음

    ```plaintext
    HTTP/1.1 404 Not Found
    ```

    ```json
    {
      "status": 404,
      "code": "SCHEDULE_NOT_FOUND",
      "message": "해당 일정을 찾을 수 없습니다.",
      "detailMessage": "해당 일정을 찾을 수 없습니다."
    }
    ```

  - Response Elements

    <table>
      <tr>
        <td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
        <td>id</td>
        <td>Integer</td>
        <td>필수</td>
        <td>댓글 id</td>
      </tr>
      <tr>
        <td>comment</td>
        <td>String</td>
        <td>필수</td>
        <td>댓글 내용</td>
      </tr>
      <tr>
        <td>createDate</td>
        <td>String</td>
        <td>필수</td>
        <td>댓글 등록 날짜 timestamp</td>
      </tr>
      <tr>
        <td>updateDate</td>
        <td>String</td>
        <td>필수</td>
        <td>댓글 수정 날짜 timestamp</td>
      </tr>
      </table>

    </details>


<details>
  <summary>선택 일정 수정</summary>


- 상세 정보

  <table>
    <tr>
    	<td>Method</td>
      <td>URL</td>
    </tr>
    <tr>
    	<td>PUT</td>
      <td>/schedules/{scheduleId}/comments/{commentsId}</td>
    </tr>
  </table>

- Request : PUT /schedules/{scheduleId}/comments/{commentsId}

    ```json
    {
      "comment" : "오 대박11ee22",
      "username" : "orange"
    }
    ```

  - Request Elements

    <table>
      <tr>
      	<td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
      	<td>comment</td>
        <td>String</td>
        <td>필수</td>
        <td>댓글 내용</td>
      </tr>
      <tr>
      	<td>username</td>
        <td>String</td>
        <td>필수</td>
        <td>댓글 작성자</td>
      </tr>
      </tr>


    </table>

- Response

  - 200 : 정상 수정

    ```plaintext
    HTTP/1.1 200 OK
    ```

    ```json
    {
        "id": 1,
        "comment": "오 대박11ee22",
        "createdAt": "2024-11-15T08:41:25.018843",
        "modifiedAt": "2024-11-15T08:42:42.589209"
    }
    ```

  - 400 : 비정상적인 값

    ```plaintext
    HTTP/1.1 400 Bad Request
    ```

    ```json
    {
      "status": 400,
      "code": "INVALID_INPUT_VALUE",
      "message": "잘못된 입력값입니다.",
      "detailMessage": "[username] :이름을 입력해주세요."
    }
    ```
  
  - 404 : 유저 찾을 수 없음

    ```plaintext
    HTTP/1.1 404 Not Found
    ```

    ```json
    {
        "status": 404,
        "code": "USER_NOT_FOUND",
        "message": "해당 유저를 찾을 수 없습니다.",
        "detailMessage": "해당 유저를 찾을 수 없습니다."
    }
    ```

  - Response Elements

    <table>
      <tr>
        <td>파라미터</td>
        <td>타입</td>
        <td>필수 여부</td>
        <td>설명</td>
      </tr>
      <tr>
        <td>id</td>
        <td>Integer</td>
        <td>필수</td>
        <td>댓글 id</td>
      </tr>
      <tr>
        <td>comment</td>
        <td>String</td>
        <td>필수</td>
        <td>댓글 내용</td>
      </tr>
      <tr>
        <td>createDate</td>
        <td>String</td>
        <td>필수</td>
        <td>댓글 등록 날짜 timestamp</td>
      </tr>
      <tr>
        <td>updateDate</td>
        <td>String</td>
        <td>필수</td>
        <td>댓글 수정 날짜 timestamp</td>
      </tr>
      </table>

</details>

<details>
  <summary>선택 일정 삭제</summary>


- 상세 정보

 <table>
  <tr>
    <td>Method</td>
    <td>URL</td>
  </tr>
  <tr>
    <td>DELETE</td>
    <td>/home/schedules/{scheduleId}</td>
  </tr>
</table>


- Request : DELETE /home/schedules/{scheduleId}

  ```plaintext
  password=123
  ```

- Response

  - 200 : 정상 삭제

  ```plaintext
  HTTP/1.1 200 OK
  ```

  - 400 : 비정상적인 값
    
  ```plaintext
  HTTP/1.1 400 Bad Request
  ```

  ```json
  {
    "status": 400,
    "code": "INVALID_INPUT_VALUE",
    "message": "잘못된 입력값입니다.",
    "detailMessage": "[password] :비밀번호를 입력해주세요."
  }
  ```
  
  - 403 :  비밀번호 불일치
    
  ```plaintext
  HTTP/1.1 403 Forbidden
  ```

  ```json
  {
    "status": 403,
    "code": "INVALID_PASSWORD",
    "message": "비밀 번호가 일치하지 않습니다.",
    "detailMessage": "비밀 번호가 일치하지 않습니다."
  }
  ```
  
  
  - 404 : 일정 찾을 수 없음

  ```plaintext
  HTTP/1.1 404 Not Found
  ```

  ```json
  {
    "status": 404,
    "code": "SCHEDULE_NOT_FOUND",
    "message": "해당 일정을 찾을 수 없습니다.",
    "detailMessage": "해당 일정을 찾을 수 없습니다."
  }
  ```

  </details>

---

## ERD

![image](https://github.com/user-attachments/assets/1c0dccc9-8297-42a4-9a60-e88ec11745f0)


---

## SQL

- 테이블 생성

  - Scehdule table

  ~~~sql
  create table schedule (
        created_at datetime(6),
        id bigint not null auto_increment,
        modified_at datetime(6),
        user_id bigint,
        content longtext not null,
        title varchar(255) not null,
        primary key (id)
    )
  ~~~

  - User table

  ```sql
  create table user (
        created_at datetime(6),
        id bigint not null auto_increment,
        modified_at datetime(6),
        email varchar(255) not null,
        password varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
  )

  ```

  - Comment table
  ```sql
    create table comment (
          created_at datetime(6),
          id bigint not null auto_increment,
          modified_at datetime(6),
          schedule_id bigint,
          user_id bigint,
          content varchar(255) not null,
          primary key (id)
      )
  ```

- 값 삽입

  - Schedule table에 삽입

  ~~~sql
  insert 
    into
        schedule (content, created_at, modified_at, title, user_id) 
    values
        (?, ?, ?, ?, ?)
  ~~~

  - User table에 삽입

  ~~~sql
    into
        user (created_at, email, modified_at, password, username) 
    values
        (?, ?, ?, ?, ?)
  ~~~

  - Comment table 에 삽입
  ~~~sql
    insert 
    into
        comment (content, created_at, modified_at, schedule_id, user_id) 
    values
        (?, ?, ?, ?, ?)
  ~~~

- 값 조회

  - 전체 일정 조회

    ~~~sql
    SELECT * FROM schedule
    ~~~

  - 선택 일정 조회

    ~~~sql
    SELECT * FROM schedule where id = 1
    ~~~

  - 작성자 조회

    ~~~sql
    SELECT * FROM user where uid = '9ccb2fd0-97af-11ef-82ae-0fc9c3770cd3'
    ~~~

  - 로그인
    ~~~sql
    select
        u1_0.id,
        u1_0.created_at,
        u1_0.email,
        u1_0.modified_at,
        u1_0.password,
        u1_0.username 
    from
        user u1_0 
    where
        u1_0.email=?
    ~~~

- 값 수정

  - 일정 수정

    ~~~sql
    UPDATE schedule SET title = '11월 1일 데일리 스크럼!!' ,content = '1. Spring 강의 듣기 | 2. Lv.0 과제 완료 | 3. 코드 카타 진행하기' where id = 1
    ~~~

  - 작성자 수정

    ~~~sql
    UPDATE user SET name = '점숨염' WHERE id = '9ccb2fd0-97af-11ef-82ae-0fc9c3770cd3'
    ~~~

- 값 삭제

  - 선택 일정 삭제

    ~~~sql
    DELETE FROM schedule where id = 1
    ~~~

- 값 삭제

  - 선택 일정 삭제

    ~~~sql
    DELETE FROM schedule where id = 1
    ~~~
