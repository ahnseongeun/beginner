package com.inflearnbeginner.beginner;

import com.inflearnbeginner.beginner.repository.MemberRepository;
import com.inflearnbeginner.beginner.repository.MemoryMemberRepository;
import com.inflearnbeginner.beginner.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//스프링에 빈을 직접 등록할때 사용한다.
@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        return new MemoryMemberRepository();//Interface로 new로 새롭게 생성이 안된다. 그래서 구현체를 사용해야한다.
    }

}
