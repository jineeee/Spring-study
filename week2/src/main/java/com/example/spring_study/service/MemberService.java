package com.example.spring_study.service;

import com.example.spring_study.domain.Member;
import com.example.spring_study.repository.MemberRepository;
import com.example.spring_study.repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository repository;

    // 생성자로 repository를 주입받음
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public Long join(Member member){

        confirmName(member);
        repository.save(member);
        return member.getId();
    }

    private void confirmName(Member member) { // 같은 이름의 중복 회원 가입 불가
        repository.findByName(member.getName()).ifPresent(m ->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    public List<Member> findMembers(){
        return repository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return repository.findById(memberId);
    }
}
