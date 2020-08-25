package com.example.spring_study.service;

import com.example.spring_study.domain.Member;
import com.example.spring_study.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class MemberServiceTest {

    MemoryMemberRepository repository;
    MemberService service;

    @BeforeEach
    public void beforeEach(){
        repository = new MemoryMemberRepository();
        service = new MemberService(repository); // 생성자로 repository를 넣어 주입하며 service 객체 생성
    }

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("yejin");

        //when
        Long id = service.join(member);

        //then
        Member findMember = service.findOne(id).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void findMembers() {
        //given
        Member member1 = new Member();
        member1.setName("name1");

        Member member2 = new Member();
        member2.setName("name1");

        //when
        service.join(member1);
        /*
        같은 이름의 회원을 가입시키면 IllegalStateException 예외 발생
         */
        assertThrows(IllegalStateException.class,
                ()-> service.join(member2));

        //then

    }
}