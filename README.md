

# Spring Study ☕️

### 📕 WEEK 2

#### 1. MVC

- MVC : Mode, View, Controller 로 구성되어 뷰와 비지니스 로직을 분리

##### Controller

```java
@Controller
  public class HelloController {
      @GetMapping("hello-mvc")
      public String helloMvc(@RequestParam("name") String name, Model model) {
          model.addAttribute("name", name);
          return "hello-template";
      }
}
```

##### View

```html
<html xmlns:th="http://www.thymeleaf.org">
<body>
	<p th:text="'hello! ' + ${name}">hello template</p>
</body>
```

- 실행 : http://localhost:8080/hello-mvc?name=yejin
- **@GetMapping** : @Cotroller 주석이 붙은 클래스 내에서 해당 url에 GET방식으로 들어온 요청을 처리할 메소드에 붙는 어노테이션
- **@PostMapping** : @Cotroller 주석이 붙은 클래스 내에서 해당 url에 POST방식으로 들어온 요청을 처리할 메소드에 붙는 어노테이션
- /hello-mvc 라는 url로 매핑하여 함수 실행 후 hello-template.html을 return
- model을 이용해 requestParam으로 넘어온 name 값을 key, value 쌍의 형태로 view에 전달
- view에서는 name이라는 key의 value를 찾아 띄움

실행 구조

<img width="551" alt="image-20200824225551587" src="https://user-images.githubusercontent.com/37479631/91061158-cdade600-e666-11ea-8789-d905f294fce6.png">



#### 2. API

##### - @ResponseBody 문자 반환

##### Controller

```
@GetMapping("hello-string")
@ResponseBody
public String helloString(@RequestParam("name") String name){
	return name;
}
```

##### View

```html
<html xmlns:th="http://www.thymeleaf.org">
<body>
	<p th:text="'안녕하세요. '+ ${data}">안녕하세요!</p>
</body>
</html>
```

- 실행 : http://localhost:8080/hello-string?name=yejin
- **@ResoponseBody** : HTTP 요청의 응답바디 내용으로 자바 객체를 매핑하는 역할 (@Controller 대신 @RestController 어노테이션 사용 시 return값에 자동으로 붙는다)
- requestParam으로 넘어온 name 값을 응답 바디에 담아 문자열을 리턴



##### - @ResponseBody 객체 반환

##### Controller

```java
@GetMapping("hello-api")
@ResponseBody // http의 body에 문자 내용을 직접 반환
public Hello helloAPI(@RequestParam("name") String name){
		Hello hello = new Hello();
		hello.setName(name);
		return hello;
}

static class Hello{
		private String name;

  		public String getName() {
            return name;
			}
			public void setName(String name) {
            this.name = name;
			}
}
```

- requstParam으로 받은 객체를 이용해 Hello 객체 생성 후 JSON 객체로 응답바디에 담아 반환



##### - @ResponseBody의 사용원리

<img width="553" alt="image-20200824233306670" src="https://user-images.githubusercontent.com/37479631/91061197-d999a800-e666-11ea-8f27-7372ac309716.png">


- HTTP의 응답 body에 return 내용을 직접 반환
- viewResolver 대신 HttpMessageConverter가 동작
- 기본 문자 처리 : StringHttpMessageConverter
- 기본 객체 처리 : MappingJackson2HttpMessageConverter
- 이외에도 여러가지 HttpMessageConverter가 기본으로 등록되어 있어 여러 타입의 반환값을 처리



#### 3. Unit Test

- JUnit이라는 프레임워크로 테스트 실행
- 각 테스트에는 의존관계가 없어야함
- **@Test** : 테스트를 실행할 메소드에 붙는 어노테이션
- **@BeforeEach** : 테스트가 실행되기 전 실행되야하는 함수에 붙는 어노테이션, 테스트 간의 서로 영향이 없도록 새로운 객체를 생성하는 등의 역할
- **@AfterEach** : 각 테스트가 종료할 때 마다 실행될 함수에 붙는 어노테이션, DB에 저장된 더미 데이터를 삭제하는 등의 역할



### 📗 WEEK 3

#### 1. 의존성 주입

- 각 클래스 간의 의존관계를 Bean Definition 정보를 바탕으로 컨테이너가 자동으로 연결
- 객체를 컨테이너로부터 주입 받아 실행 시에 동적으로 의존관계가 생성

##### - 컴포넌트 스캔

##### Controller

```java
@Controller
public class MemberController {

    private final MemberService service;

    // 컨트롤러 생성 시 bean으로 등록된 service 객체를 가져와 주입
    @Autowired
    public MemberController(MemberService service){
        this.service = service;
    }
}
```

##### Service
```java
@Service
public class MemberService {

    private final MemberRepository repository;

    // 서비스 생성 시 bean으로 등록된 repository 객체를 가져와 주입
    @Autowired
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }
}
```

##### Repository

```java
@Repository
public class MemoryMemberRepository implements MemberRepository{
  	private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    ...  
}
```

- **@Component** : 해당 클래스가 bean으로 자동 등록되도록 해주는 어노테이션
- **@Autowired** : 빈으로 등록된 객체를 가져와 자동으로 객체에 매핑해주는 어노테이션
-  @Controller, @Service, @Repository 어노테이션 또한 @Component를 포함하므로 스프링 빈으로 자동 등록됨
- 생성자에 @Autowired를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아 주입해줌 (생성자가 1개만 있을 시 생략 가능)
- 스프링 빈은 싱글톤으로 등록되므로 어디서든 모두 같은 인스턴스를 사용



##### - Java 코드 작성

##### Configuration

```java
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();
    }
}
```

- **@Configuration** : 스프링 IOC Container에 해당 클래스가 Bean 구성 클래스임을 알려주는 어노테이션
- **@Bean** : @Configuration 설정된 클래스 내에서 사용되며, 해당 메소드의 리턴 객체가 스프링 빈 객체임을 선언하는 어노테이션
- Service와 Repository 타입을 리턴하는 메소드를 만들고 @Bean 어노테이션을 붙여 해당 타입의 빈 객체 등록
- 각 메소드명으로 빈 객체가 등록



#### 2. AOP

- 여러군데에서 사용되는 중복 코드를 비지니스 로직에서 분리하여 모듈화 하는 것

```java
@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* com.example..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try{
            return joinPoint.proceed();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("END: " + joinPoint.toString() + " "+ timeMs + "ms");
        }
    }
}
```

- **@Aspect** : 흩어진 관심사를 모듈화한 클래스에 Aspect임을 명시하는 어노테이션
- 프록시 패턴이라는 디자인 패턴을 사용
- AOP의 대상인 클래스의 빈이 만들어 질 때 프록시를 자동으로 만들고 원본 클래스 대신 프록시를 빈으로 등록하여 사용함

##### 실행 구조

<img width="553" alt="스크린샷 2020-08-25 오후 5 43 18" src="https://user-images.githubusercontent.com/37479631/91152953-90933380-e6fa-11ea-927b-31807bd744c5.png">



#### 3. Spring 통합 테스트

- **@SpringBootTest** : 스프링 컨테이너와 테스트를 함께 실행시키는 어노테이션
- **@Transactional** : 테스트 시작 전 트랜잭션을 시작하고, 테스트 완료 후 항상 롤백을 하도록 해주는 어노테이션

#### 

