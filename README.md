##### 데이터베이스 분리는 MSA의 꽃

##### 아마존, 쿠팡 -> 다른 서비스의 DB에 직접 접근하지 마시오

##### 이벤트 드리븐 -> CDC 트랜잭션 로그, 읽어서 카프카에 쏴준다. 하나하나 분리하고 있음? -> LinkedIn 방식

##### 마이크로서비스는 어느정도 중복을 허용한다. 결국 서비스별로 각각 중복 코드를 가지고 있다.

##### Clouc Native

##### 1. 신축성 - 서비스가 중단되어도 전체적인 장애로 퍼지지 않고 빠르게 복구될 수 있는 것

##### 2. 민첩성 - 독립적으로 빠르게 배포하고 운영하는 것

##### 3. 확장 가능성 - 서비스가 수평적인 확장이 가능한 것

##### 4. 자동화 - 이런 것들이 자동화가 되어 운영에 수고가 들지 않는 것

##### 5. 무상태 - 각 서비스의 상태는 무상태이다.

##### DevOps

##### 로그를 중앙에다 구축하자!

##### Spring Cloud Netflix

##### 모놀리틱에서 의존성 호출 -> 100% 신뢰, 무조건 호출

### Hystrix

##### MSA 환경에서는 신뢰할 수 없음 -> 안정성을 보장할 수 없음, 리스크가 크다

##### 그래서 Circuit Breaker가 필요하다

##### Hystrix는 분산시스템에서 지연 감내, 실패 감내

##### 맨 끝에 서버에서 down이 되면 그걸 물고 있는 요청에서도 스레드를 차지하고 있다.

##### 대기, 대기, 대기

##### @HystrixCommand를 적용하면 메소드 호출을 Intercept하여 대신 실행, 실행된 결과의 성공/실패 여부를 기록하고 통계를 낸다.

##### Circuit open -> 오픈된 메서드는 즉시 에러 반환, 특정 메소드 지연이 시스템 전체 Resource를 모두 소모하여 시스템 전체의 장애를 유발

##### 장애를 유발하는 시스템에 대한 연동을 조기에 차단시킴으로서 나의 시스템을 보호

##### Hystrix로 할 수 있는 것 1. Fallback 처리(500 error), 2. Timeout 처리


##### Ribbon - Client side LoadBalancer
##### 아래와 같이 @LoadBalanced를 붙여주면
```java
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
```
##### restTemplate 객체에 interceptors(ArrayList)가 추가되고 여기에 주소가 담기게 된다.
##### Retry 할 수 있어서 서버가 하나 죽더라도 다른 서버로 갈 수 있음


##### Eureka - Cloud Native하게 하려면 서버가 새롭게 시작될 때 그것을 감지하여 목록을 추가하고 서버가 종료되면 LB를 빼고 하는 방법?
##### 클라우드 환경의 전화번호부
##### Eureka Server(Registry)에 자동으로 자신의 상태를 등록하고 주기적으로 자신이 살아있음을 알림, 서버가 종료되면 자신의 상태를 변경(Down)하거나 자신의 목록을 삭제
##### 클라이언트에서는 서버의 ip가 남지 않고 Eureka에서 주소를 가져오게 된다.


##### 리본 - 클라이언트 로드 밸런싱, Eureka - 서버 주소 관리
##### 이중화를 해야 한다? 이중화가 뭐지?
##### 30초에 한번씩 클라이언트 메모리에 동기화를 하여 유레카 서버에 매번 접근하는 것이 아님 


##### API Gateway -> 클라이언트와 백엔드 서버 사이의 출입문
##### 특정 요청이 왔을 때 내가 원하는 서비스로 라우팅 해줌
##### 리본, 유레카, 히스트릭스 가지고 있음

##### Spring Cloud Zuul은 netflix zuul을 감쌌음
##### 기본 Isolation이 Semaphore, netflix zuul은 threadpool

##### api gateway란?
##### https://www.tibco.com/ko/reference-center/what-is-an-api-gateway

##### 스프링 클라우드 Config -> 서비스의 실행시점에 Config 서버에 프로퍼티를 읽어온다. -> Bean이 재생성이 된다.