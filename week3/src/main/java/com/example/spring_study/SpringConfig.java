package com.example.spring_study;

import com.example.spring_study.aop.TimeTraceAop;
import com.example.spring_study.repository.JdbcMemberRepository;
import com.example.spring_study.repository.JpaMemberRepository;
import com.example.spring_study.repository.MemberRepository;
import com.example.spring_study.repository.MemoryMemberRepository;
import com.example.spring_study.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public MemberRepository memberRepository(){
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSource);
////        return new JpaMemberRepository(em);
//    }
}
