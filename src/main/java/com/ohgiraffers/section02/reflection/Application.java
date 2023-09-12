package com.ohgiraffers.section02.reflection;

import java.lang.reflect.*;

public class Application {

    public static void main(String[] args) {
        /*.class 문법을 이용하여 Class타입의 인스턴스를 생성할수 있다.
         * Class 타입의 인스턴스는 해당 클래스의 메타 정보를 가진 클래스이다.*/
        Class class1 = Account.class;//클래스 타입의 인스턴스(메타정보를 가진 클래스)
        System.out.println("class1 :" + class1);

        /*Object 클래스의 getClass() 메소드를 이용하면 Class 타입으로 리턴 받아 이용할수 있다.*/
        Class class2 = new Account().getClass();
        System.out.println("class2 : " + class2);


        try {

            /*Class.forName() 메소드를 이용하여 런타임시 로딩하고 그 클래스 메타 정보를 Class타입으로 반환 받을수 있다.*/
            Class class3 = Class.forName("com.ohgiraffers.section02.reflection.Account");
            System.out.println("class3 :" + class3);

            /*Double 자료형 배열을 로드할수 있다.*/
            Class class4 = Class.forName("[D");// [D로 작성
            Class class5 = double[].class;

            System.out.println("class 4 :" + class4);
            System.out.println("class 5 :" + class5);

            /*String 자료형 배열을 로드 할수 있다.*/
            Class class6 = Class.forName("[Ljava.lang.String;");
            Class class7 = String[].class;


            System.out.println("class 6 :" + class6);
            System.out.println("class 7 :" + class7);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //배열은 객체이기때문에 가능한데 기본자료형은 클래스의 메타정보를 가지지 않는다.

        /*원시 자료형 사용시 컴파일 에러 발생*/
//            double d = 1.0;
//            Class class8 = d.getClass();

        /*TYPE 필드를 이용하여 원시형 클래스를 반환 받을수 있다.*/
        Class class8 = Double.TYPE;//wrapper클래스
        System.out.println("class8 : " + class8);

        Class class9 = Void.TYPE;
        System.out.println("class9 : " + class9);

        /*클래스 메타 정보를 이용하여 여러가지 정보를 반환 받는 메소드를 제공한다.*/

        /*상속 된 부모 클래스 반환*/
        Class superClass = class1.getSuperclass();
        System.out.println("superClass : " + superClass);

        /*필드 정보를 반환한다.*/

        Field[] fields = Account.class.getDeclaredFields();
        for (Field field : fields) {
            System.out.println("modifiers : " + Modifier.toString(field.getModifiers())
                    + ", type : " + field.getType()
                    + ", name : " + field.getName());
        }
/*생성자 정보를 반환*/
        Constructor[] constructors = Account.class.getConstructors();
        for(Constructor con : constructors) {
            System.out.println("name : " + con.getName());

            Class[] params = con.getParameterTypes();
            for (Class param : params) {
                System.out.println("paramType :" + param.getTypeName());
            }
        }
    /*생성자를 이용하여 인스턴스 생성*/
        try {
            Account acc = (Account) constructors[0].newInstance("20","110-234-567800", "1234", 10000);
            System.out.println(acc.getBalance());//잔액확인 메소드
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        /*메소드 정보에 접근할수 있다.*/
        Method[] methods = Account.class.getMethods();
        Method getBalanceMethod = null;
        for(Method method : methods){
            System.out.println(Modifier.toString(method.getModifiers())+ ""
            +method.getReturnType().getSimpleName()+""
                    +method.getName());

            if("getBalance".equals(method.getName())){
                getBalanceMethod = method;
            }

        }
        try {
            /*invoke 메소드를 이용해서 메소드를 호출할수 있다.*/
            System.out.println(getBalanceMethod.invoke(constructors[2].newInstance()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
