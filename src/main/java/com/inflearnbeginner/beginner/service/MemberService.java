package com.inflearnbeginner.beginner.service;

import com.inflearnbeginner.beginner.domain.Member;
import com.inflearnbeginner.beginner.repository.MemberRepository;
import com.inflearnbeginner.beginner.repository.MemoryMemberRepository;
import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired //새로 생성하는 것이 아니라 외부에서 넣어주는 것을 dependency Injection / DI
    public MemberService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    /*
    회원가입
     */
    public Long join(Member member){
        validateDuplicateMember(member); //같은 이름이 있는 중복 회원
        memberRepository.save(member);
        return member.getId();
    }

    /*
    전체 회원 조회
    Spring의 AOP가 없다면 일일이 시간을 찍어줘야하지만 Spring의 AOP를 이용해서 전체 시간을 한번에 제어 할수 있다.
    일일이 시간을 출력하는 비지니스 로직을 나타낸 것이고 findAll()이라는 한줄의 코드가 엄청 길어진 것을 볼수 있다.
     */
    public List<Member> findMembers(){

        long start = System.currentTimeMillis();
        try{
            return memberRepository.findAll();
        }finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("findMembers = "+timeMs+"ms");
        }
    }

    public Optional<Member> findOne(Long id){
        return memberRepository.findById(id);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    //s – the String that contains a detailed message
                    throw new IllegalStateException("이미 존재하는회원이다");
                });
    }
}
