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