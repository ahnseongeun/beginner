package com.inflearnbeginner.beginner;

import com.inflearnbeginner.beginner.aop.TimeTraceAop;
import com.inflearnbeginner.beginner.repository.*;
import com.inflearnbeginner.beginner.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//스프링에 빈을 직접 등록할때 사용한다.
@Configuration
public class SpringConfig {

/*
    Jdbc관련된 설정
    private final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource=dataSource;
    }*/

/*
    Jpa를 사용할때는 EntityManager가 필수이다.
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em){
        this.em=em;
    }*/

    /*
    Spring data JPA는 알아서 구현체를 만들어서 Bean에 등록한다.
     */
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }

    /*
    여기서 중요한 부부은 Interface로 구현한 MemberRepository를
    구현체인 JdbcMemberRepository 와 MemoryMemberRepository를 선택해서 입력받을수 있다.
    OCP(open-closed principle) 확장에는 열려있고 수정에는 닫혀있다.
    데이터를 DB에 저장하므로 서버를 다시 실행해도 데이터가 안전하게 저장된다.
     */
 //   @Bean
 //   public MemberRepository memberRepository(){ -> Spring data Jpa는 Bean 설정은 안해도된다.
        //return new MemoryMemberRepository();//Interface로 new로 새롭게 생성이 안된다. 그래서 구현체를 사용해야한다.
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        //return new JpaMemberRepository(em);
//    }

    /*
    이렇게 객체로 만들고 DI를 하는 것처럼 편리한 점이 스프링을 사용하는 것이다.
     */

}
