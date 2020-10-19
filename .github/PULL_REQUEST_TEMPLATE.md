# Issue Title

TICKET-47 : develop 정립을 위한 코드 통합 작업

<br>

### 완료 조건

- [ ] `통합을 완료하였는가`
- [ ] `잘 돌아가는가`

이거 세모는 못주나요 ㅋㅋㅋ  

<br> 

## relate to issue
*What went wrong:
Execution failed for task ':asciidoctor'.
(LoadError) no such file to load -- asciidoctor

RESTDOCS를 만들기 위해 ``gradlew build -Djasypt.encryptor...`` 를 실행하니 위와 같은 오류가 나왔습니다.  
이전에 제가 따로 적용하려고 시도했을 때랑 같은 오류네요 ㅜㅜ  

이 부분은 다른 분들도 동일하게 실패가 뜨는지 확인하고 싶습니다...  
***

**수정이 되었으면 하는 점(의견 듣기위해 안고쳤습니다.)**
1.
account와 ticket은 따로 dto 패키지가 있는데 festival은 없습니다.  
이 부분 festival dto 패키지 추가했으면 좋겠습니다. 그리고 FestivalRequest 또한 DTO같던데 DTO 역할 맞나요?  
맞다면 네이밍을 Dto 붙여서 통일하면 좋겠습니다. TicketCreateDto,TicketUpdateDto,AccountCreateDto 처럼요!  

2.
festival 과 ticket dto 들에서 유효성 검사가 빠져있던데 이 부분 추가되었으면 좋겠습니다. 물론 Errors 처리도 되면 좋겠습니다.  
해당 Errors 처리는 AccountSignUpController 코드를 참고하면 될 것 같습니다.  

3.
JsonConfig.java 파일을 모두 주석처리를 해놓았습니다. 이 파일 삭제되었으면 좋겠습니다.  
그 이유는 내부 코드는 objectmapper의 빈등록하는 @Configuration 파일인데, spring boot는 기본적으로 objectmapper에 대한 빈 제공을 한다고 합니다.
그리고, Errors 객체를 json으로 처리하기위한 ErrorSerializer 라는 custom Serializer가 있는데, @JsonComponent 어노테이션을 사용해서 object mapper에
custom serializer이라는 것을 알리고 등록합니다. 그런데, 이렇게 따로 object mapper를 빈등록하니깐 오류가 발생합니다. 이로 인해 에러 처리가 제대로 되지 않는 것을 발견했습니다.
  
>serializer에 대한 설명  
>return ResponseEntity.ok().body(객체)가 있을 때에 body에 일반적인 객체를 넣으면 알아서 json 형태로 변환이 됩니다.  
>ObjectMapper(다양한 Serializer 가지고 있음)가 자바 빈 스펙을 따르는 객체의 경우에 Bean Serializer를 이용해서 변환해주기 때문입니다.  
>하지만, Errors 객체는 자바 빈 스펙을 준수하지 않기 때문에 ObjectMapper에서 Bean Serializer를 통해 Serialization이 불가합니다.  
>그래서 따로 JsonSerializer<객체>를 extends 받아서 커스텀 serializer를 생성해서 ObjectMapper에 등록을 해주어야 합니다.

4.
테스트 코드는 AccountLoginControllerTest의  
```java
@DisplayName("아이디 비밀번호 일치할 때의 로그인 테스트")
    public void loginTest() throws Exception
```
와 AccountApiControllerTest 를 제외한 모든 테스트가 통과되게끔 하였습니다.  

전자의 경우는 로그인 하였을 때의 권한부여가 제대로 되었는지 확인하는 테스트인데 통합전에는 돌아갔는데 통합을 하니 실패가 뜨네요...  
위에서 언급한 ObjectMapper의 추가적인 빈 등록을 하면서 오류가 발생했던 것 처럼 어떤 부분에서 오류가 나나 봅니다... 아직 해결 못했습니다.  
AccountApiControllerTest의 경우에는 회원 가입이 된 상태가 베이스인 코드인데 AccountSignUpController와 연계를 해야될 것 같기도 하고,  
Dto가 제각기 다르기도 하고해서 냅뒀습니다. 회원 쪽 하고 같이 토의해서 깔끔하게 합치면 좋을 것 같습니다.  


<br>

## Check List
- [x] issue 제목은 유의미한가?
- [x] issue 내용은 issue 내용만 확인하고도 모르는 사람도 파악할 수 있을 정도로 기술되었는가? (무엇을, 언제, 어디서...)
- [ ] reference가 있다면 추가했는가?
- [x] 관련 issue가 있다면 추가했는가?
- [x] 유의미한 Label을 추가했는가?
- [x] Assginees를 추가했는가?
- [ ] Estimate를 추가했는가?
- [ ] 관련 Milestone이 있다면 추가했는가?
- [ ] 관련 Epics가 있다면 추가했는가?

---
