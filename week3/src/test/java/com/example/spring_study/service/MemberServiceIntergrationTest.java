package com.example.spring_study.service;

import com.example.spring_study.domain.Member;
import com.example.spring_study.repository.MemberRepository;
import com.example.spring_study.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberRepository repository;
    @Autowired
    MemberService service;

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