package com.inflearnbeginner.beginner.repository;

import com.inflearnbeginner.beginner.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//인터페이스 다중 상속이 가능하다.
//Spring data Jpa는 자동으로 Bean처리한다.
public interface SpringDataJpaMemberRepository extends JpaRepository<Member,Long>,MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
