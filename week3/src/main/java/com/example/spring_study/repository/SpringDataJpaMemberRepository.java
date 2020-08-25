package com.example.spring_study.repository;

import com.example.spring_study.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 자동으로 인터페이스 타입 구현체를 만들어 스프링 빈으로 등록
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}
