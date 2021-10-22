/******************************************************************
MOVIE 프로젝트 데이터베이스의 구성

 -- 부모 테이블 --
    - MEMBER (회원 데이터를 저장하는 테이블)
        *   MEMBER_NO - NUMBER / PRIMARY KEY
        *   MEMBER_ID - VARCHAR2(45) / NOT NULL / UNIQUE KEY(중복금지, PRIMARY KEY는 아님)
        * MEMBER_NAME - VARCHAR2(45) / NOT NULL
        *    PASSWORD - VARCHAR2(45) / NOT NULL
        *       PHONE - VARCHAR2(45) 
        *       EMAIL - VARCHAR2(100)
    
    - MOVIEINFO (영화정보를 저장하는 테이블)
        * MOVIE_ID - NUMBER / PRIMARY KEY
        *    TITLE - VARCHAR2(100) / NOT NULL
        *    STORY - VARCHAR2(100) / NOT NULL
        *    GRADE - VARCHAR2(45)  / NOT NULL
        
    - CINEMA (극장정보를 저장하는 테이블)
        * CINEMA_ID - NUMBER / PRIMARY KEY
        *    REGION - VARCHAR2(45) / NOT NULL
        *   C_PHONE - VARCHAR2(45) / NOT NULL
        
 -- 자식 테이블 --
    - BOOK (회원의 예매정보를 저장하는 테이블)
        *   BOOK_ID - NUMBER / NOT NULL / PRIMARY KEY
        * CINEMA_ID - NUMBER / NOT NULL / 참조 CINEMA (CINEMA_ID)
        *  MOVIE_ID - NUMBER / NOT NULL / 참조 MOVIEINFO (MOVIE_ID)
        * MEMBER_NO - NUMBER / NOT NULL / 참조 MEMBER (MEMBER_NO)
        *     PRICE - NUMBER / NOT NULL 
        *   TIME_ID - NUMBER / NOT NULL / 참조 TIMETABLE (TIME_ID)
        
    - TIMETABLE (극장, 영화, 일시별 상영 정보를 저장하는 테이블)
        *     TIME_ID - NUMBER / PRIMARY KEY
        *    MOVIE_ID - NUMBER / NOT NULL / 참조 MOVIEINFO (MOVIE_ID)
        *   CINEMA_ID - NUMBER / NOT NULL / 참조 CINEMA (CINEMA_ID)
        * SCREEN_DATE - VARCHAR2(45) / NOT NULL
        * SCREEN_TIME - VARCHAR2(45) / NOT NULL
        