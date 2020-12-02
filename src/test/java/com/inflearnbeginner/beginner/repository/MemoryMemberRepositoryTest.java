package com.inflearnbeginner.beginner.repository;

import com.inflearnbeginner.beginner.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository=new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
        repository.setSequence(0L);
    }

    @Test
    public void save() {
        Member member=new Member();
        member.setName("testUser");
        repository.save(member);
        Member result= repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);
    }

    @Test
    void findById() {
        Member member=new Member();
        member.setName("testUser");
        assertThat(member.getId()).isEqualTo(null);
        repository.save(member); //member가 저장될때 Id 부여
        assertThat(member.getId()).isEqualTo(1);
        Member result = repository.findById(member.getId()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findByName() {
        Member member=new Member();
        member.setName("testUser");
        repository.save(member);
        Member result = repository.findByName(member.getName()).get();
        assertThat(result).isEqualTo(member);
    }

    @Test
    void findAll() {
        Member member1=new Member();
        member1.setName("testUser1");
        Member member2=new Member();
        member2.setName("testUser2");
        repository.save(member1);
        repository.save(member2);
        List<Member> members = repository.findAll();
        assertThat(members.size()).isEqualTo(2);
    }
}