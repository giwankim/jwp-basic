#### 1. 톰켓 서버를 시작하면 서블릿 컨테이너의 초기화 과정이 진행된다. 현재 소스코드에서 초기화되는 과정에 대해 셜명하라.

1. 서블릿 컨테이너는 서블릿과 통신하기에 필요한 `ServletContext` 인스턴스 하나가 초기화된다.
2. `@WebServlet`, `@WebFilter`, `@WebListner` 어노테이션을 달고 있는 클래스들이 하나씩 생성되고 `ServletContext`를 통해 서블릿 컨테이너에 등록된다.
3. `CharacterEncodingFilter`와 `ResourceFilter` 두 클래스의 `init()`메소드가 호출된다.
4. `DispatcherServlet`의 `loadOnServlet` 속성이 0 이상이기에 생성자 외에도 `init()` 메소드가 호출되어 `RequestMapping` 객체를
   생성하고 `RequestMapping#init()` 메소드가 호출되어 요청 URL과 `Controller` 인스턴스를 매핑한다.
5. 서블릿 컨텍스가 초기화되어 `ServletContextEvent`가 발생된다.
6. `ServletContextListener` 인터페이스의 구현체인 `ContextLoaderListener`의 콜백 메소드인 `contextInitialized`가 호출되어 `schema.sql`
   과 `data.sql`이 실행되어 데이터베이스 테이블이 초기화되고 기본 데이터가 적재된다.

스택오버플로우의 [How do servlets work? Instantiation, sessions, shared variables and multithreading](https://stackoverflow.com/questions/3106452/how-do-servlets-work-instantiation-sessions-shared-variables-and-multithreadi)
에 의하면 조금 더 일반화한 서블릿 컨테이너 초기화 과정은 다음과 같다.

* 모든 "web application"을 로드하고 배포한다. 여기서 "web application"은 서버의 특정 URL 네임스페이스 하위에 (예: /catalog) 경우에 따라
  .war 파일로 설치되는 여러 서블릿들과 컨텐츠를 지칭한다.
* 서블릿 컨테이너가 로드되면 서블릿 컨테이너는 하나의 ServletContext 인스턴스를 생성하고 서버의 메모리에 올린다.
    * `ServletContext`는 서블릿이 서블릿 컨테이너와 통신하는데 필요한 메소드들이 정의된 인터페이스이다. 메소드의 예로 파일의 MIME 타입 가져오기, request를 dispatch하기, 서블릿
      로그 파일에 쓰기 등이 있다. 하나의 JVM의 "web application"은 단일 `ServletContext`를 갖는다.
* `web.xml`을 파싱하여 발견된 `<servlet>`, `<filter>`, `<listener>` (혹은 `@WebServlet`, `@WebFilter`, `@WebListener` 어노테이션이
  붙은) 클래스들을 한번 인스턴스로 생성하고, `ServletContext`을 통해 등록하고 서버 메모리에 적재한다.
    * 인스턴스 초기화한 필터들은 `ServletContext`를 가져올 수 있는 `FilterConfig`가 인자로 갖는 `init()` 메소드를 실행한다.
    * 서블릿이 `<servlet><load-on-startup>` 이나 `@WebServlet(loadOnStartup)`에 0 보다 크거나 같은 값을 갖고 있으면 `init()` 메소드가 호출되어 초기화된다.
        * `load-on-startup`값이 없거나 음수등면 `init()` 메소드는 최초로 HTTP 요청이 서블릿에 전달되었을 때 실행된다.
* `WebListener` 어노테이션이 달려있거나 `ServletContext#addListener()` 메소드를 통해서 등록되어 있는 클래스들의 콜백
  메소드인 `ServletContextListener#contextInitialized()`가 `ServletContextEvent`를 인자로 호출된다.
* 서블릿 컨테이너가 내려갈 때는 모든 web application들이 종료되는데 `Servlet`과 `Filter`들의 `destroy()` 메소드가
  호출되고 `Servlet`, `Filter`, `Listener` 객체들이 비할당된다. 마지막으로 `ServletContext`가 내려가는
  과정에서 `ServletContextListener#contextDestroyed()` 메소드가 호출된다.

#### 2. 로컬 환경에서 톰캣 서버를 시작한 후 http://localhost:8080으로 접근하면 질문 목록을 확인할 수 있다. http://localhost:8080으로 접근해서 질문 목록이 보이기까지 소스코드의 호출 순서 및 흐름을 설명하라.

#### 7. next.web.qna 패키지의 ShowController는 멀티스레드 상황에서 문제가 발생할 수 있는 코드이다. 문제가 발생하는 이유를 설명하고 문제가 발생하지 않도록 수정한다.
