# Study-Junit
자바와 JUnit을 활용한 실용주의 단위 테스트(제프 랭어, 앤디 헌트, 데이브 토마스 지음) 소스

## [Settings]
### Java
* zulu 11 jdk
### gradle
* 5.2.1
### DB
* HomeBrew formula - Postgresql
* version : 12.2
* port : 5432
* 실행 CLI
  * brew service start postgresql (Postgresql 실행)
  * psql postgres (Postgresql에 접속)
  * CREATE DATABASE junit; (데이터베이스 스키마 생성 - 대문자로 실행해야 됨)
  * \du (출력되는 Role name 확인, persistence.xml 설정 정보 수정)
  
## [Origin source]
* https://github.com/gilbutITbook/006814

---

저작권으로 인해 정리 내용 삭제
