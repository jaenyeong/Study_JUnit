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

## AAA

### 모든 테스트 코드에 해당 (준비, 실행, 단언)
* Arrange(준비)
  * 테스트 코드 실행전에 시스템이 적절한 상태에 있는지 확인
  * 객체를 생성하거나 이것과 의사소통하거나 다른 API를 호출하는 것 등
  * 우리가 필요한 상태로 있다면 준비 상태 생략 가능
* Act(실행)
  * 테스트 코드 실행
  * 일반적으로 단일 메소드 호출
* Assert(단언)
  * 실행한 코드가 기대한 대로 동작하는지 확인
  * 실행한 코드의 반환값 또는 그 외 필요한 객체들의 새로운 상태를 검사
  * 테스트한 코드와 다른 객체들 사이의 의사소통을 검사
* After(사후) : 상황에 따라 필요
  * 테스트를 실행할 때 어떠한 자원을 할당했다면 잘 정리되었는지 확인

## FIRST

### FIRST: 좋은 테스트의 속성(조건)

* F(fast: 빠른)
* I(isolated: 고립된)
* R(repeatable: 반복 가능한)
* S(self*validating: 스스로 검증 가능한)
* T(timely: 적시의)

## [Right]-BICEP

### [Right]-BICEP : 무엇을 테스트 할 것인가?

* Right : 결과가 올바른가?
* B : 경계 조건(boundary conditions)은 맞는가?
  * 고려해야 할 경계 조건
    * 모호하고 일관성 없는 입력 값 (예: 특수 문자가 포함된 파일이름)
    * 잘못된 양식의 데이터 (예: 최상위 도메인이 빠진 메일주소)
    * 수치적 오버플로를 일으키는 계산
    * 비거나 빠진 값 (예: 0.0, "", null)
    * 이성적인 기대값을 훨씬 벗어나는 값 (예: 150세의 나이)
    * 교실의 당번표처럼 중복을 허용해서는 안되는 목록에 중복 값이 있는 경우
    * 정렬이 안된 정렬 리스트 혹은 그 반대 (예: 정렬 알고리즘에 이미 정렬된 입력 값을 넣거나 정렬 알고리즘의 역순 데이터를 넣는 경우)
    * 시간 순이 맞지 않는 경우 (예: HTTP 서버가 OPTIONS 메소드의 결과를 POST 메소드보다 먼저 반환하지 않고 그 후에 반환하는 경우)
  * CORRECT (잠재적인 경계 조건)
    * [C] Conformance(준수) : 값이 기대한 양식을 준수하고 있는가?
    * [O] Ordering(순서) : 값의 집합이 적절하게 정렬 되거나 정렬되지 않았나?
    * [R] Range(범위) : 이성적인 최솟값과 최댓값 안에 있는가?
    * [R] Reference(참조) : 코드 자체에서 통제할 수 없는 어떤 외부 참조를 포함하고 있는가?
    * [E] Existence(존재) : 값이 존재하는가? (non null, non zero, 집합에 존재하는가?)
    * [C] Cardinality(기수) : 정확히 충분한 값들이 있는가?
    * [T] Time(절대적, 상대적 시간) : 모든 것이 순서대로 일어나는가? 정확한 시간에? 정시에?
* I : 역 관계(inverse relationship)를 검사할 수 있는가?
* C : 다른 수단을 활용하여 교차 검사(cross*check)를 할 수 있는가?
* E : 오류 조건(error conditions)을 강제로 일어나게 할 수 있는가?
  * 고려해야 할 제약 사항
    * 메모리가 가득 찰 때
    * 디스크 공간이 가득 찰 때
    * 벽시계 시간에 관한 문제들 (서버와 클라이언트 간 시간이 달라서 발생하는 문제들)
    * 네트워크 가용성 및 오류들
    * 시스템 로드
    * 제한된 색상 팔레트
    * 매우 높거나 낮은 비디오 해상도
* P : 성능 조건(performance characteristics)은 기준에 부합하는가?
  * 주의 사항
    * 전형적으로 코드 덩어리를 충분한 횟수만큼 실행하길 원할 것.  
      이렇게 타이밍과 CPU 클록 주기에 관한 이슈를 제거
    * 반복하는 코드 부분을 자바(JVM)가 최적화하지 못하는지 확인해야 함
    * 최적화되지 않은 테스트는 한 번에 수 밀리초가 걸리는 일반적인 테스트 코드보다 매우 느림.  
      느린 테스트들은 빠른 것과 분리할 것.
    * 동일한 머신이라도 실행 시간은 시스템 로드처럼 잡다한 요소에 따라 달라질 수 있음
  * 도구
    * jmeter
    * JUnitPerf
    
## 경게 조건 CORRECT

### CORRECT 기억법

* [C] Conformance(준수)
   * 이메일 주소, 전화번호, 계좌 번호, 파일 이름 등 양식 있는 문자열 데이터 등을 검증할 때는 많은 규칙이 필요
   * 이러한 규칙들을 잘 준수하고 있는지, 명세를 잘 설계할 것
* [O] Ordering(순서)
   * 순서 조건 확인
   * 예 : compareTo() 메소드처럼 순서에 따라 다른 결과값이 나오는 경우
* [R] Range(범위)
   * 인덱스를 다룰 때 고려해야 할 테스트 시나리오
     * 시작과 마지막 인덱스가 같으면 안됨
     * 시작이 마지막보다 크면 안됨
     * 인덱스는 음수가 아니어야 함
     * 인덱스가 허용한 것보다 크면 안됨
     * 개수가 실제 항목 개수와 맞아야 함
* [R] Reference(참조)
   * 메소드 테스트 시 고려 사항
     * 범위를 넘어서는 것을 참조하고 있지 않은지
     * 외부 의존성은 무엇인지
     * 특정 상태에 있는 객체를 의존하고 있는지 여부
     * 반드시 존재해야 하는 그 외 다른 조건들
* [E] Existence(존재)
   * 주어진 값이 존재하는지
   * 막혀 있는 곳(?)에 null 값이 도달한다면 문제 원인을 이해하기 어려움
   * 호출된 메소드가 null을 반환하거나 기대하는 파일이 없거나 네크워크가 다운되었을 때 발생되는 경우에 대한 테스트를 작성할 것
* [C] Cardinality(기수)
   * 울타리 기둥 오류(fencepost errors) : 충분히 생각하지 않아 발생하는 오류
   * 0*1*n 법칙 (ZOM) 테스트는 0, 1, n 이라는 경계 조건에만 집중 (n은 비즈니스 요구사항에 따라 변경될 수 있음)
* [T] Time(시간)
   * 상대적 시간(시간 순서)
     * login-logout, open-read-close처럼 메소드 호출 순서
     * 데이터의 순서가 중요한 것처럼 메소드의 호출 순서도 중요함
     * 타임아웃 문제도 포함됨
       * 타임아웃으로 보호되지 않는 조건 찾아보기
       * 발생하지 않을 일을 기다리느라 코드가 무한 대기에 빠지지 않았는지
       * 대기 시간이 너무 길지는 않은지 
   * 절대적 시간(측정된 시간)
     * 예 : UTC, DST에 따라 하루의 시간이 달라짐
   * 동시성 문제들
     * 동시성과 동기화된 접근 맥락
     * 동시에 같은 객체를 다수의 스레드가 접근할 때 발생하는 일
     * 어떤 전역 또는 인스턴스 수준의 데이터나 메소드에 동기화를 해야 하는지
     * 파일 또는 하드웨어의 외적인 접근 처리
     * 클라이언트에 동시성 요구 사항이 존재할 시 다수의 클라이언트 스레디를 보여주는 테스트를 작성할 필요가 있음

#### 불변식, 불변 
* 불변식(invariant)
  * 프로그램이 실행되는 동안 또는 정해진 기간 동안 반드시 만족해야 하는 조건을 의미
  * 변경을 허용하나 주어진 조건 내에서만 변경을 허용함
* 불변(immutable)
  * 어떠한 변경도 허용하지 않는 것
  * 가변 객체와 구분하는 용도로 사용

## 깔끔한 코드로 리팩토링하기

### 리팩토링 목표
* 중복 비용 최소화
* 낮은 중복성과 높은 명확성 (ROI)
* 좋은 테스트를 만들면 리팩토링이 쉬움

### 리팩토링
* rename (클래스, 메소드, 모든 종류의 변수)
  * 명확성은 대개 코드 의도를 선언하는 것, 좋은 이름은 코드 의도를 전달하는 가장 좋은 수단
* 메소드 추출
  * 저수준의 세부사항 추출로 인하여, 고수준의 정책만 이해하는 것으로 충분하다면 저수준까지 관심이 분산되지 않음
* 충분한 테스트가 없으면 코드를 변경하기 어려움
  * 무턱대고 코드를 이리저리 옮기면 기존 기능들이 깨지기 쉬움
* 디미터(디메테르 또는 데메테르)의 법칙 준수 (최소 지식 원칙)
  * 결합도가 낮은 설계를 위한 법칙 (다른 객체로 전파되는 연쇄적인 메소드 호출을 피하기)
  * 법칙에 따른 허용 범위 (객체 O의 메소드 m은 아래와 같은 타입의 메소드만 호출할 수 있음)
    * O 자기 자신의 메소드
    * m의 파라미터로 넘어온 객체의 메소드
    * m 메소드 안에서 생성되거나 초기화된 객체의 메소드
    * O 객체가 직접 소유하는 객체의 메소드
    * m 메소드 안에서 접근 가능한 전역 변수(객체)의 메소드
* 임시 변수를 사용하는 이유
  * 값 비싼 비용의 계산 값을 캐시에 넣을 때
  * 메소드 몸체에서 변경되는 것들을 수집할 때
  * 코드 의드롤 명확하게 할 때 (한 번만 사용된다 하더라도 유효한 선택)
  * IDEA 변수 리팩토링 단축키 : 해당 객체(임시변수) 드래그 후 option + command + N 키
* 리팩토링 후
  * 테스트를 다시 실행할 것
  * 떨어진 코드들을 새로운 메소드로 추출할 때는 자동화 방법이 없음 > 따라서 수동으로 리팩토링하기 때문에 위험함
  * 명확하고 테스트 가능한 단위들로 리팩토링
    * 의도를 파악하기 쉽고 고립된 방식으로 잘 표현되어 테스트 용이
  * 성능
    * 만약 데이터가 수백만건 이상 처리해야 한다면 최우선 고려 대상이나 그 외는 코드를 깔끔하게 유지하는데 신경 쓸 것
      * 예상보다 성능이 나쁘지 않을 수 있음
      * 일반적으로 성능 우선시 코드 가독성이 낮고 유지 보수 비용이 증가하며 설계 또한 유연하지 않을 가능성이 큼
      * 반대로 깔끔한 설계는 성능 최적화를 즉시 대응할 수 있는 최선의 보호막
    * 성능이 당장 문제될 시 바로 성능 측정할 것
      * 리팩토링 전 후에 코드를 테스트하는 작은 테스트 코드를 작성하여 속도를 측정하여 성능 저하 확인할 것

## 더 큰 설계 문제

### SOLID 클래스 설계 원칙 (로버트 마틴 - SOLID 원칙 지침 제공, 마이클 패더스 - SOLID 작명)

* [S] SRP(단일 책임 원칙)
   * 클래스는 변경할 때 한 가지 이유만 있어야 함
   * 클래스는 작고 단일 목적을 추구
* [O] OCP(개방 폐쇄 원칙)
   * 클래스는 확장에 열려 있고, 변경(수정)에 닫혀 있어야 함
   * 기존 클래스의 변경을 최소화 할 것
* [L] LSP(리스코프 치환 원칙)
   * 하위 타입은 반드시 상위 타입을 대체할 수 있어야 함
   * 클라이언트 입장에서 오버라이딩한 메소드가 기능성을 깨면 안됨
* [I] ISP(인터페이스 분리 원칙)
   * 클라이언트는 필요하지 않는 메소드에 의존하면 안됨
   * 커다란 인터페이스를 다수의 작은 인터페이스로 분할할 것
* [D] DIP(의존성 역전 원칙)
   * 고수준 모듈은 저수준 모듈을 의존해서는 안됨
   * 둘 다 추상 클래스에 의존해야 함
   * 추상 클래스는 구체 클래스에 의존하면 안되고 구체 클래스는 추상 클래스에 의존해야 함

#### SRP를 위한 단계별 예제 리팩토링
* Profile 클래스는 책임 두 개를 정의
  * 프로파일에 관한 정보 추적하기
  * 조건 집합이 프로파일에 매칭되는지 혹은 그 정도를 판단하기
* MatchSet 클래스를 생성하여 필드 및 메소드를 이동하여 리팩토링 (SRP)
* 명령-질의 분리(command-query separation) 원칙
  * 어떤 메소드는 명령을 실행하거나 질의에 대답할 수 있으며, 두 가지 작업을 모두 하면 안됨
  * 질의 메소드가 객체 상태를 바꿔버리면 그 메소드를 두 번 호출하는 것이 불가능할 수도 있음
  * 혹은 두 번째 호출하면 바라지 않는 방향으로 객체 상태가 변질될 가능성도 있음
  * 예 : Iterator 인터페이스에 next() 메소드
* 리팩토링은 코드 동작(API)을 변경하지 않고 코드 구현(내부 로직)을 변경하는 활동
  * 돌아오는 가치 > 테스트 코드를 고치는 비용
  * 실패하는 테스트의 정도(양)를 부정적인 설계 지표로 인식
    * 더 많은 테스트가 동시에 깨질수록 더욱 많은 설계 문제가 있을 것
* 코드 중복은 가장 큰 설계 문제
  * 테스트를 따르기 어려워짐
  * 작은 코드 조각들을 단일 메소드로 추출하면 그 코드 조각들을 변경해야 할 때 미치는 영향을 최소화할 수 있음
  * private 메소드를 테스트하려는 충동은 클래스가 필요 이상으로 커졌다고 볼 수 있음 (SRP 위반)
    * private 메소드가 자꾸 늘어날 시 내부 동작을 새 클래스로 옮기고 public으로 만드는 것이 좋음
    * 메소드가 새로운 클래스에 public 메소드가 되면 테스트 커버리지가 충분하지 않을 것 > 이에 대해 충분한 테스트를 작성할 것
  * 단위 테스트가 어렵다면 설계를 개선할 것
    * 시스템 설계 및 코드 품질이 낮아질수록 단위 테스트의 유지 보수 비용이 증가함
  * answers 컬렉션을 다루는 방법
    * Profile, MatchSet 클래스 양쪽에서 다루는 것은 정보를 너무 많이 가지고 있는 것
    * 기능의 산재(shotgun surgery) : 여러 클래스에 구현 상태가 흩어져 있을 때의 코드 냄새(code smell)
    * answers 컬렉션을 DB 테이블로 교체한다면 여러 군데를 고쳐야 함. 데이터 상태도 혼란 존재
    * AnswerCollection 클래스로 분리
    
## Mock 객체 사용하여 테스트

### 테스트 예제
* 테스트 예제는 아파치의 HttpComponents 클라이언트와 상호작용하여 REST 호출
* 이 Http 호출 실행의 중요한점
  * 실제 호출에 대한 테스트는 나머지 대다수 다른 빠른 테스트들에 비해 속도가 느림
  * 예제에서 호출하는 Nominatim HTTP API가 항상 가용한지 보장할 수 없음 (통제권 밖)
* 우리는 해당 로직을 의존성이 있는 다른 코드와 분리 하여 해당 로직에 관한 단위 테스트를 희망
* HTTP 호출이 원활하다는 가정하에 아래 로직 테스트 
  * HTTP 호출을 준비하는 로직
  * HTTP 응답으로 생성되는 Address 객체를 생성하는 로직
* 스텁(stub) : 테스트 용도로 하드 코딩한 값을 반환하는 구현체
* 예제 테스트 실행 순서
  * 테스트는 Http의 스텁 인스턴스를 생성. 스텁은 get(String url) 단일 메소드가 있으며 하드 코딩된 JSON 문자열 반환
  * 테스트는 AddressRetriever 객체를 생성, 생성자에 스텁을 전달
  * AddressRetriever 객체는 스텁을 저장
  * 실행될 때 retrieve() 메소드는 먼저 넘어온 파라미터의 포맷을 정하고 그 다음 스텁이 저장된 http 필드에 get() 메소드 호출
  * retrieve() 메소드는 http 필드가 스텁을 참조하는 지 프로덕션 구현을 참조하는지 관여하지 않음
  * 단지 retrieve() 메소드는 get() 메소드를 구현한 객체와 상호 작용하고 있다는 것만 알고 있음
  * 스텁은 테스트에 하드 코딩된 JSON 문자열을 반환
  * 나머지 retrieve() 메소드는 하드 코딩된 JSON 문자열을 파싱하고 그에 따라 Address 객체를 구성
  * 테스트는 반환된 Address 객체의 요소를 검증
* 의존성 변경은 꼭 생서자 주입으로 제한할 필요 없음
  * 세터, 팩토리 메소드 오버라이딩, 추상 팩토리, 구글 주스나 스프링 같은 도구 사용 등으로 대체 가능
* 목(mock)은 의도적으로 흉내낸 동작을 제공하고 수신한 인지가 모두 정상인지 여부를 검증하는 일을 하는 테스트 구조물
* 스텁을 목으로 변환
  * 테스트에 어떤 인자를 기대하는지 명시(스텁 자체에 있는 것과 반대)
  * get() 메소드에 넘겨진 인자들을 잡아서 저장
  * get() 메소드에 저장된 인자들이 기대하는 인자들인지 테스트가 완료될 때 검증하는 능력 지원
* Mockito
  * 테스트 객체 Mock을 만들어주는 Java 용 오픈 소스 프레임워크
* 테스트를 위해 프로덕션 코드 내에 내부 필드를 의존성 주입(생성자를 통한) 방식으로 변경하여 테스트 하면 외부에 노출하기 때문에 좋지 않음
  * 스프링 DI, 구글 주스 등 의존성 주입 도구를 이용하면 효율적
  * 여기서는 mockito에 내장 DI를 이용
  * 모키토 코드 설명
    * http 필드 선언, @Mock 애노테이션 태깅(목을 합성하고자 하는 곳을 의미)
    * retriever 필드 선언, @InjectMocks 애노테이션 태깅(목을 주입하고자 하는 대상을 의미)
    * @Before 메소드에서 AddressRetriever 클래스 인스턴스 생성
    * @Before 메소드에서 MockitoAnnotations.initMocks(this); 실행
      * this는 테스트 클래스 자체를 참조
      * 모키토는 @Mock 애노테이션이 태깅된 필드를 가져와 각각에 대해 목 인스턴스를 합성 (앞선 mock(Http.class) 메소드와 동일)
      * 그 후 @InjectMocks 애노테이션이 태깅된 필드를 가져와 목 객체들을 주입
    * 모키토는 목개체를 주입하기 위해서 적절한 생성자 탐색 후, 적절한 세터 탐색 후 적절한 필드를 찾음
* 주의
  * 테스트가 진짜로 목을 사용하거나 우연하게 여전히 프로덕션 코드를 실행하는지?
  * 목을 끄고 retrieve() 메소드를 HttpImpl 프로덕션 코드와 상호 작용시 약간 느려질 것
  * 간단한 방법은 임시로 프로덕션 코드에 런타임 예외를 던져서 확인해볼 수 있음
    
#### 목 올바르게 사용
* 목은 실제 동작을 대신함
* 안전하게 사용하기 위한 질문
  * 목이 프로덕션 코드의 동작을 올바르게 묘사하고 있는가?
  * 프로덕션 코드는 생각하지 못한 다른 형식으로 반환하는가?
  * 프로덕션 코드는 예외를 던지는가? null을 반환하는가?
* 목은 단위 테스트 커버리지의 구멍(간극-gap)을 만들기 때문에 통합 테스트를 작성하여 이 구멍을 막아야 함

## 테스트 리팩토링

### 불필요한 테스트 코드
* 테스트 코드가 예외를 기대하지 않는다면 (명시적으로 예외를 던지는 단계를 설정하지 않았기 때문에) 예외가 발생하게 놔둠
  * Junit이 예외를 잡아주기 때문에 명시적인 try/catch 블록을 제거 (부가가치가 없음)
* 어떤 변수를 역으로 참조할 경우 null이 아닌 것을 검사하는 것은(not-null) 테스트에선 군더더기이기 때문에 제거

### 추상화 누락
* 좋은 테스트는 클라이언트가 시스템과 어떻게 상호 작용하는지 추상화 함
* 하나의 개념을 구체화 하는 단언문 5줄을 포괄하는 [사용자 정의 단언문] 작성 및 호출로 리팩토링
  * 매처(Matcher)를 사용자 정의로 구현하는 것은 더 많은 코드가 필요하지만 충분한 가치가 있음 (재사용 가능)
* 단일 개념을 구현하는 여러 줄이 되는 코드를 발견했다면 테스트에 그것을 깔끔한 문장 1줄로 추출할 수 있는지 고민할 것
* 누락된 추상화는 [비어 있음] 개념. 단언을 바꾸면 크기 비교를 이해하는 불필요한 정신적 노력을 줄일 수 있음

### 부적절한 정보
* 잘 추상화된 테스트는 코드를 이해하는 데 중요한 것을 부각, 그렇지 않은 것은 보이지 않게 해 줌
* 테스트엔 부적절하지만 당장 컴파일 때문에 데이터를 넣기도 함 (러메소드가 테스트엔 어떤 영향도 없는 부가적인 인수를 취하기도 함)
* 테스트는 그 의미가 불분명한 매직 리터럴(매직 넘버) 등을 포함하고 있음
  * 상수로 선언하지 않은 숫자 리터럴을 [매직 넘버] 라고 하며, 코드에 되도록 사용하지 않는 것을 권장

### 부푼 생성
* 객체를 생성하는 도우미 메소드를 작성하여 호출 (정신 산란한 세부 사항을 숨기기)

### 다수의 단언
* 테스트마다 단언 한 개로 가는 방향이 좋음 
* 사후 조건에 대한 단언이 필요할 때도 있으나 더 자주 여러 개의 단언이 있다는 것은 하나의 테스트 케이스를 두 개 포함하고 있다는 증거
* 테스트를 분리하여 좀 더 테스트 맥락(문맥)에 맞게 기대하는 행동을 기술하게 수정
* 테스트마다 단언 한 개로 가면 깔끔한 테스트명 작명이 쉬움

### 테스트와 무관한 세부 사항들
* 군더더기(로그 끄기, 스트림 닫기 등)들을 @Before, @After 메소드로 이동할 것

### 잘못된 조직
* 테스트에서 어느 부분들이 준비(Arrange), 실행(Act), 단언(Assert) 부분인지 아는 것이 테스트를 빠르게 인지하는 지름길
* AAA를 통해 의도를 분명히 할 것
* 해당 부분에 빈 줄 삽입

### 암시적 의미
* [왜 그러한 결과를 기대하는가] 라는 질문을 충족시켜야 함
* 테스트 준비와 단언 부분을 상호 연관 지을 수 있어야 함 (시간낭비 줄이기)
* 테스트 데이터를 명시적으로 변경
* 의미 있는 상수, 좋은 변수 이름, 더 좋은 데이터, 테스트에 계산을 적게 만드는 것 등이 상호 관련성을 향상 시키는 좋은 방법

### 새로운 테스트 추가

## 테스트 주도 개발(TDD)

### 주된 이익
* 코드가 예상한 대로 동작한다는 자신감
* 좋은 코드(good code)라기보다는 위대하지 않은 코드(not-so-great)일 가능성이 높음

### 단순하게 시작
* TDD 사이클
  * 실패하는 테스트 코드 작성하기
  * 테스트 통과시키기
  * 이전 두 단계에서 추가되거나 변경된 코드 개선하기
* 첫 번째는 시스템에 추가하고자 하는 동작을 정의하는 테스트 코드를 작성하는 것
* 실패하는 테스트 작성 - 기대하는 동작이 아직 시스템에 존재하지 않다는 것을 확인
  * TDD를 할 때 값 비싸고 나쁜 가정들을 피하기 위해 항상 테스트가 먼저 실패하는지 관찰
* TDD는 점진적인 사고 방식을 통해 개선
* 실패하는 각 테스트에 대해 해당 테스트를 통과할 수 있는 코드만 추가
  * 가능한 가장 작은 증분(increment)을 추가
  * 테스트가 나타나는 [명세]를 정확히 코딩할 것
  * 테스트가 모두 통과한다면 잠재적으로 코드 배포 가능. 그 시점에서 테스트는 시스템이 무엇을 하는지 문서화함
* 실용적인 관점
  * 가장 작은 양의 코드를 작성하는 것은 대부분 먼저 실패하는 또 다른 테스트를 만들 수 있다는 것을 의미
  * 필요한 것보다 더 많은 코드를 작성하는 것은 즉시 통과할 수 있는 많은 테스트를 스스로 만들 수 있다는 의미
  * 좋은 것 같아 보이지만, 적절한 피드백을 받기 전에 많은 양의 코드를 작성하는 옛 방식으로 돌아가는 것
  * 결함이 있는 코드를 작성할 때는 바로 알아내는 것이 더 좋음

### 테스트 정리
* 테스트 리팩토링
* TDD는 거의 모든 코드에 안전한 리팩토링을 가능하게 함

### 또 다른 작은 증분
* 알 필요가 있는 기반에서만 활동, 의도적으로 match() 메소드 구현을 숨김
* match() 메소드를 사용하여 테스트를 통과하는 matches() 메소드 내의 단일 조건문을 추가
* TDD로 생각하는 부분의 일부는 작성할 필요가 있는, 다음 테스트를 결정하는 것
  * 프로그래머로서 임무는 코드가 다루어야 하는 모든 가능한 순열과 시나리오를 이해하는 것
  * TDD로 성공하려면 이들 시나리오를 테스트로 만들고 각 테스트를 통과하게 만드는 코드 증분을 최소화하는 순으로 코드를 작성하는 것

### 다수의 응답 지원 : 작은 설계 우회로
* Profile 객체에서 다수의 Answer 응답을 포함
* Profile 안에 Map<String, Answer> 컬렉션 필드에 보관
* matches() 메소드의 일부 getMatchingProfileAnswer() 메소드를 호출하여 null 여부 검사
  * 이 null 검사를 Answer 클래스로 이동
  * matches() 메소드 호출의 수신자가 변경됨
  * AnswerTest 에 matchAgainstNullAnswerReturnsFalse() 테스트 추가
* TDD를 할 때 다른 코드를 전혀 건드리지 않고 Profile 클래스만 변경할 필요는 없음
  * 필요한 사항이 있다면 설계를 변경, 다른 클래스로 이동 가능
  
### 인터페이스 확장
* 테스트를 리팩토링 하면 TDD 사이클을 짧게 유지하기에 용이
* 각 테스트에 result 임시 변수를 제거
  * AAA를 조금 위반하나 이는 불변의 법칙이 아님
  * 테스트의 반복적 성질로 봤을 때, result 임시 변수는 가치가 없고 없어도 잘 읽힘
  
### 마지막 테스트 & 문서로서의 테스트
* 망가진 테스트 확인 (여기서는 망기진 테스트 중에서 같은 것을 보여주는 테스트가 존재시 망가진 테스트를 제거)
* 테스트 주도 클래스를 잘 이해하려면 테스트명부터 살펴볼 것 (테스트명 개선)
* 반드시 한 클래스에 들어 있을 필요는 없음 (테스트 클래스, 고정물로 나누면 연관된 동작 그룹에 집중할 수 있음)
* 테스트 하려는 동작을 테스트 클래스의 이름으로 넣으면 개별 테스트 이름에서 중복되는 정보를 제거할 수 있음
* 정기적으로 테스트 이름이 서로 잘 어울리는지 확인할 것

### TDD의 리듬
* TDD 사이클은 짧음
* 10분 동안 긍정적인 피드백(테스트 통과)을 받지 못한 경우 작업 중인 코드는 폐기 후 더 작은 단계로 시도할 것

## 까다로운 테스트

### 멀티스레드 코드 테스트
* 동작하는 동시성(concurrent) 코드를 테스트하는 것은 어려움
* 동시성 처리가 필요한 어플리케이션 코드를 테스트하는 것은 기술적으로 단위 테스트 영역이 아님
  * 통합 테스트로 분류하는 것이 나음
  * 이때 어플리케이션 고유의 로직 중 일부는 동시적으로 실행될 수 있음을 고려하여 통합적으로 검증해야 함
* 스레드를 사용하는 코드에 대한 테스트는 느린 경향이 있음
  * 동시성 문제가 없다는 것을 보장하면서 실행 시간의 범위를 확장해야 하기 때문
* 멀티스레드 코드를 테스트할 때는 다음 주제를 따라야 함
  * 스레드 통제와 어플리케이션 코드 사이의 중첩 최소화
    * 스레드 없이 다량의 어플리케이션 코드를 단위 테스트할 수 있도록 설계를 변경할 것
    * 남은 작은 코드에 대해 스레드에 집중적인 테스트를 작성할 것
  * 다른 사람의 작업을 믿을 것
    * 자바 5에는 더그 리아(Doug Lea)의 훌륭한 동시성 유틸리티 클래스(java.util.concurrent)가 들어 있음
    * 이것은 충분히 오랜시간 검증 되었음
    * 생산자/소비자(producer/consumer) 문제를 직접 코딩하지 말고 BlockingQueue 클래스를 사용할 것
* gradle - 모키토 임포트 변경
  * implementation와 testImplementation 구분할 것 (아래와 같이 수정)
    * implementation group: 'org.mockito', name: 'mockito-all', version: '1.9.5'
    * testImplementation group: 'org.mockito', name: 'mockito-all', version: '1.9.5'
  * 햄 크래스트 클래스가 junit, mockito 등 여러 라이브러리에 존재
  * 라이브러리에 포함된 햄 크래스트 버전에 따라 특정 메소드가 없는 것으로 나옴

### 데이터베이스 테스트
* 예제에서 QuestionController 클래스에 대한 단위 테스트를 작성하는 것이 의미가 있는가?
  * JPA 관련 [모든 인터페이스]를 모두 스텁으로 만들어 단위 테스트할 순 있겠으나 노력이 많이 들고 테스트가 어려움
  * 진짜 DB와 성공적으로 상호작용하는 QuestionController 클래스에 대한 테스트 작성
  * 자바 코드, 매핑설정, DB 자체가 함께 동작
* 영속적인 모든 상호 작용을 시스템 한곳으로 고립시킬 수 있다면 통합 테스트의 대상은 상당히 소규모로 줄어들 것
* H2 같은 인메모리 DB로 프로덕션 DB를 모사할 경우 속도는 빠를 수 있으나 실제 사용 RDBMS와의 미묘한 차이로 발생할 문제점 등을 예상할 것
* 데이터가 이미 DB에 있다고 가정하는 것은 어려움
  * 시간이 지나면서 데이터는 변질되기 마련 따라서 테스트는 망가짐
  * 테스트 코드와 데이터를 분리시키면 특정 테스트가 왜 통과하는지, 또는 실패하는지 그 이유를 이해하기 더욱 어려워짐
  * 테스트 관점에서 데이터에 의미는 그것을 모두 DB에 부어 넣는 순간 사라짐
  * 테스트 안에서 데이터를 생성하고 관리할 것
* 머신에 있는 DB라면 가장 간단한 경로는 테스트마다 깨끗한 DB로 시작하는 것
  * 적절한 참조 데이터를 포함한 기존에 생성된 DB 인스턴스도 좋음
* 매 테스트는 그 다음 자기가 쓸 데이터를 추가하거나 그것으로 작업하면 테스트 간 의존성 문제를 최소화 할 수 있음
  * 테스트 간 의존성 문제는 다른 테스트에 남아 있던 데이터 때문에 어떤 테스트가 망가지는 것을 의미
* 테스트를 위해 공유된 DB에만 접근할 수 있다면 조금 더 비침습적인 해법 필요
  * DB가 트랜잭션을 지원할 경우 테스트마다 트랜잭션 초기화, 테스트 종료시 롤백
  * 일반적으로 트랜잭션 처리는 @Before, @After 에서 처리
* 통합 테스트는 작성과 유지 보수가 어려움
  * 필수적이나 설계, 유지 보수하기가 어려움
  * 단위 테스트에서 검증하는 로직을 최대화하는 방향으로 통합 테스트 개수와 복잡도를 최소화 할 것
* controller를 목 처리
  * QuestionController 클래스의 find() 메소드를 안전하게 스텁으로 만듦
  * 목으로 처리한 것은 무엇이고, 그것이 질의에 대해 어떻게 반응하고 어떤 부작용을 발생시키는지 충분히 숙지하여야 함
* 어려운 시나리오에 대해 다음 전략을 따를 것
  * 관심사 분리. 어플리케이션 로직은 스레드, DB 등 문제를 일으킬 수 있는 다른 의존성과 분리할 것
    * 의존적인 코드는 고립시켜서 코드 베이스에 만연하지 않게 할 것
  * 느리거나 휘발적인 코드를 목으로 대체, 단위 테스트의 의존성을 끊을 것
  * 필요한 경우 통합 테스트를 작성하되, 단순하고 집중적으로 만들 것

## 프로젝트에서 테스트

### 빠른 도입
* 단위 테스트와 같은 실천법을 배우는 것은 끊임없는 경계를 요구함
* 팀원과의 마찰이 존재할 가능성이 높음
  * 아마 팀원은 조심스럽지 않게 테스트 코드보다 훨씬 빨리 코드를 만들 것
* 처음부터 품질을 통제하며 개발한다면 코드가 엉망이 되는 것을 최소한으로 줄일 수 있음
* 어떻게 단위 테스트가 팀 문화의 습관적인 일부가 될 수 있을지 토론해 볼 것

### 팀과 같은 편 되기
* 팀원마다 테스트에 대한 생각이 다름
* 오랜 언쟁은 모두의 시간을 빼앗는 일이 됨
* 단위 테스트 표준 만들기
  * 다음 두 질문에 대해 생각해 볼 것
    * 개발자들은 어떤 것이 모든 사람의 시간을 많이 낭비하게 만든다고 느끼는지?
    * 모두가 빠르게 동의할 수 있는 표준은 무엇인가?
  * 초창기에 표준화해야 하는 목록
    * 코드를 체크인하기 전에 어떤 테스트를 실행해야 할지 여부
    * 테스트 클래스와 메소드 작명 방식
    * 햄크레스트, 전통적인 단언 사용 여부
    * AAA 사용 여부
    * 선호하는 목 도구 선택
    * 체크인 테스트를 실행할 때 콘솔에 출력을 허용할지 여부
    * 단위 테스트 스위트에서 느린 테스트를 분명하게 식별하고 막을 방법
* 리뷰로 표준 준수 높이기
  * 어떻게 코드 리뷰(code review)를 할 것인가?
  * 리뷰 세션을 통해 단위 테스트 작성자가 다른 팀원들에게 피드백을 요청할 수 있음
  * 페이건 검사(Fagan inspections) 같은 기법을 사용, 리뷰 절차를 공식화할 수도 있음
    * 이러한 사후 리뷰는 적어도 뻔한 표준 위반을 방지하는 관문 역할을 함
  * 풀 리퀘스트 활용(깃허브)
    * 메인 브랜치에 통합하려는 작업 내용에 대해 풀 리퀘스트 제출
    * 다른 팀원은 요청에 주석을 달고 결과적으로 그 변경사항을 pull, merge 함
  * UpSource(인텔리제이 플러그인)
    * 코드 리뷰 도구
    * IDE가 관리하는 애노테이션을 활용하여 코드에 관해 토론하는 기능 제공
* 짝 프로그래밍을 이용한 리뷰
  * 두 프로그래머가 나란히 앉아 소프트웨어를 개발하는 방식
  * 사후 리뷰에는 몇 가지 문제가 있음
    * 최상의 리뷰는 코드를 깊이 이해한 사람에게서 나오나 현실적으로 시간적 여유가 없음
      * 결과적으로 리뷰는 바라는 것보다 더 적은 결함을 찾게됨
      * 사후 리뷰는 가치가 있으나 들이는 노력만큼 가치가 높지는 않음
    * 심각한 문제를 고치는데 너무 늦음

### 지속적 통합으로 수렴
* 지속적 통합(CI) 서버라고 하는 도구의 지원을 받아야 함 (필수)
* 빌드에 문제가 있다면 CI 서버는 개발팀에게 통지
* Hudson, Jenkins, TeamCity, AntHill, CruiseControl, Buildbot, Bamboo 등이 있음

### 코드 커버리지
* 코드 커버리지는 단위 테스트가 실행한 코드의 전체 퍼센트를 측정하는 것
* 엠마(Emma), 코버투라(Cobertura)는 코드 커버리지 도구의 예
  * 엠마는 기본 블록 방식(분기되지 않은 바이트 코드의 덩어리)을 사용, 코버투라는 코드의 줄 수로 측정
* 어떤 도구들은 분기 커버리지를 측정함
* 커버리지는 어느 정도여야 하는가?
  * 커버리지 개념은 오로지 속임수를 써야만 100%에 도달할 수 있다는 제한이 내재되어 있음
  * 대부분의 경우 70% 이하의 커버리지는 불충분하다고 말함
  * 일반적으로 커버리지 밖의 코드 1/3 정도는 테스트되지 않은 상태인데, 나쁜 의존성 때문에 그 코드가 어렵거나 테스트하기 어려움
    * 코드 결함의 30%는 이러한 테스트되지 않은 코드에 있음
  * 제프의 코드 커버리지 이론
    * 낮은 커버리지 영역에서 나쁜 코드의 양도 증가함
* 100% 커버리지는 진짜 좋은가?
  * TDD를 수행하는 개발자들은 일반적으로 정의상 90%를 초과 달성함
* 코드 커버리지의 가치
  * 엠마 같은 도구는 커버리지에서 누락하고 있는 부분을 가시적으로 보여줌
  * 테스트 작성을 완료했다고 생각할 때 커버리지 도구를 실행할 것
  * 커버되지 않은 영역을 보고, 커버되지 않은 코드 영역이 염려된다면 더 많은 테스트를 작성할 것
  * 코드 커버리지 숫자는 그 자체가 큰 의미는 없으나 추세는 중요함
  * 시간이 지나면서 코드 커버리지의 퍼센트가 높아져야 함 (적어도 아래 방향으로 내려가면 안됨)
  * 코드 커버리지 도구는 코드가 어디에서 커버리자가 부족한지, 팀이 어디에서 아래 방향으로 내려가고 있는지 이해하려고 할 때만 사용할 것
