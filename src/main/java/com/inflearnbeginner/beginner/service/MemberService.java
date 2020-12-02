package com.inflearnbeginner.beginner.service;

import com.inflearnbeginner.beginner.domain.Member;
import com.inflearnbeginner.beginner.repository.MemberRepository;
import com.inflearnbeginner.beginner.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

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
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
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
