package com.ohgiraffers.section01.aop;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MemberService {

    // DAO를 필드로 선언하고 의존성주입 코드를 작성해줘야한다.
    private  final  MemberDAO memberDAO;

    public MemberService(MemberDAO memberDAO){ this.memberDAO = memberDAO;}//생성자 의존성주입 생성자가 하나일때는 따로 위에 적어주지 않아도가능

//핵심 기능 작성
    public Map<Long, MemberDTO> selectMembers(){

        System.out.println("selectMembers 메소드 실행");
        return memberDAO.selectMembers();//전체 목록조회
    }

    public MemberDTO selectMember(Long id){

        System.out.println("selectMember 메소드 실행");
        return memberDAO.selectMember(id);//특정 아이디 조회
    }
}
