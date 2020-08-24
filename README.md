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
- **@GetMapping** : @Cotroller 주석이 붙은 클래스 내에서 사용자에게 응답할 view를 생성하는 메소드의 어노테이션
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



#### 3. Test

- JUnit이라는 프레임워크로 테스트 실행
- 각 테스트에는 의존관계가 없어야함
- **@Test** : 테스트를 실행할 메소드에 붙는 어노테이션
- **@BeforeEach** : 테스트가 실행되기 전 실행되야하는 함수에 붙는 어노테이션, 테스트 간의 서로 영향이 없도록 새로운 객체를 생성하는 등의 역할
- **@AfterEach** : 각 테스트가 종료할 때 마다 실행될 함수에 붙는 어노테이션, DB에 저장된 더미 데이터를 삭제하는 등의 역할



### 📗 WEEK 3