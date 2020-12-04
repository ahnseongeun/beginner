package com.inflearnbeginner.beginner.service;

import com.inflearnbeginner.beginner.domain.Member;
import com.inflearnbeginner.beginner.repository.MemberRepository;
import com.inflearnbeginner.beginner.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/*
실제 컨테이너에 있는 Bean들을 가져와서 ServiceTest하는 방법
//이렇게 Spring container를 이용해서 Test하는 것을 통합테스트라고 한다.
 */

//SpringbootTest를 사용하는 이유는 스프링 컨테이너와 테스트를 함께 실행한다.
@SpringBootTest
//@Transactional을 사용하는 이유는 Test에서 실제로 DB에 데이터를 저장하지 않고 롤백할때 사용한다.
//하지만 실제 Service에서 사용하면 그냥 commit된다.
@Transactional
class MemberServiceIntegrationTest {


    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Commit //강제 Commit
    public void join() {
        Member member=new Member();
        member.setName("testuser1");

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