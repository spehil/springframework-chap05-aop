package com.ohgiraffers.section01.aop;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository //컴퍼넌트스캔으로 bean으로 등록된다.
public class MemberDAO {

    private  final Map<Long, MemberDTO> memberMap;

    public MemberDAO(){
        memberMap = new HashMap<>();
        memberMap.put(1L, new MemberDTO(1L,"홍길동"));
        memberMap.put(2L, new MemberDTO(2L,"유관순"));

    }

    public Map<Long, MemberDTO> selectMembers(){

        return  memberMap;
    }

    public MemberDTO selectMember(Long id){
        MemberDTO returnMember = memberMap.get(id);

        //id 가 null일때 exception발생
        if(returnMember == null)throw  new RuntimeException("해당하는id의 회원이 없습니다.");//RuntimeException 예외처리를 강제화하지 않아서 throw처리가 없음.

        return  returnMember;
    }

}
