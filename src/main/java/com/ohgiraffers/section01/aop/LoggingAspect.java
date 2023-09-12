package com.ohgiraffers.section01.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/*@Aspect :pointcut(join point)과 advice(부가기능)를 하나의 클래스 단위로 정의하기 위한 어노테이션이다.*/
@Aspect
@Component //Bean으로 등록
public class LoggingAspect {

    /*@Pointcut : 여러 조인 포인트를 매치하기 위해 지정한 표현식*/

    @Pointcut("execution(* com.ohgiraffers.section01.aop.*Service.*(..))")
    public void logPointcut() {
    }


    /*JointPoint : 포인트 컷으로 패치한 실행시점
     * 이렇게 매치 된 조인 포인트에서 해야 할 일이 어드바이스이다.
     * 매개변수로 전달한 JointPoint 객체는 현재 조인 포인트의 메소드명, 인수 값등의 자세한 정보를 엑세스 할수 있다.
     * */
    @Before("LoggingAspect.logPointcut()")
    public void logBefore(JoinPoint joinPoint) {

        System.out.println("Before joinPoint.getTarget() : " + joinPoint.getTarget());
        System.out.println("Before joinPoint.getSignature() : " + joinPoint.getSignature());
        if (joinPoint.getArgs().length > 0) {

            System.out.println("Before joinPoint.getArgs()[0] :" + joinPoint.getArgs()[0]);
        }


    }
    /*포인트 컷을 동일한 클래스 내에서 사용한다면 클래스명은 생략할수 있다.
    단, 패키지가 다르면 패키지를 포함한 클래스 명을 기술해야한다.*/

    @After("logPointcut()")//동일 클래스 안에 있으면 클래스명까지 명시하지 않아도된다.
    //@After:정상수행일때와 예외시 모두 수행되므로
    public void logAfter(JoinPoint joinPoint) {

        System.out.println("After joinPoint.getTarget() : " + joinPoint.getTarget());
        System.out.println("After joinPoint.getSignature() : " + joinPoint.getSignature());
        if (joinPoint.getArgs().length > 0) {

            System.out.println("After joinPoint.getArgs()[0] :" + joinPoint.getArgs()[0]);
        }


    }

    /*returning  속성은 리턴 값으로 받아올 오브젝트의 매개변수 이름과 동일해야한다.
     * 또한 joinPoinjt는 반드시 첫번째 매개변수로 선언해야한다. */
    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("After Returning result : " + result);
        /*리턴할 결과 값을 변경해 줄수도 있다.*/
        if (result != null && result instanceof Map) {

            ((Map<Long, MemberDTO>) result).put(100L, new MemberDTO(100L, "반환값 가공"));
        }

    }

    /*throwing : 속성의 이름과 매개변수의 이름이 동일해야한다.*/
    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception")
    public void logAfterThorwing(Throwable exception) {

        System.out.println("After Throwing exception" + exception);
    }


    /*Around Advice는 가장 강력한 어드바이스이다. 이 어드바이스는 조인 포인트를 완전히 장악하기 때문에
     * 앞에서 살펴본 어드바이스를 모두 Around 어드바이스로 조합할수 있다.
     * 원본 조인 포인트 실행 여부까지 제어할수 있다.
     * 조인 포인트를 진행하는 호출을 잊는 경우도 발생할수 있기 때문에 주의해야하며
     * 최소한의 요건을 충족하면서도 가장 기능이 약한 어드바이스를 쓰는것이 바람직하다.  */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {

        System.out.println("Around Before : " + joinPoint.getSignature());

        /*원본 조인포인트 실행 코드 (taget object 기능을 수행하라는 코드)*/
        Object result = joinPoint.proceed();

        System.out.println("Around After : " + joinPoint.getSignature());


        /*원본 조인 포인트를 호출한 쪽 혹은 다른 어드바이스가 다시 실행할수 있도록 결과를 반환한다.*/
        return null;

    }
}