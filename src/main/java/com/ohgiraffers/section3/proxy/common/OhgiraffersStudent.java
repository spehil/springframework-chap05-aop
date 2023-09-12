package com.ohgiraffers.section3.proxy.common;

public class OhgiraffersStudent implements Student{


    @Override
    public void study(int hours) {
        System.out.println(hours + "시간동안 열심히 공부합니다.");

    }

}
