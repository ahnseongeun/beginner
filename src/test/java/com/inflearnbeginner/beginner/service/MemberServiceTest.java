package com.inflearnbeginner.beginner.service;

import com.inflearnbeginner.beginner.domain.Member;
import com.inflearnbeginner.beginner.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){ //새로 생성하는 것이 아니라 외부에서 넣어주는 것을 dependency Injection / DI
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    //given -> when -> then이 기본이다.
    @Test
    public void join() {
        Member member=new Member();
        member.setName("testUser");

        Long saveId=memberService.join(member);

        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복회원예외() {
        Member member1 = new Member();
        member1.setName("testUser1");

        Member member2 = new Member();
        member2.setName("testUser1");

        memberService.join(member1);
        IllegalStateException e = Assertions.assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는회원이다");
        //안좋은 방법
//        try {
//            memberService.join(member2);
//            fail("예외가 발생했습니다.");
//        } catch (IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는회원이다");
//        }

    }
    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}