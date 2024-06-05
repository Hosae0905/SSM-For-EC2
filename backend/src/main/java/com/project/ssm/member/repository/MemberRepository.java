package com.project.ssm.member.repository;

import com.project.ssm.member.model.Member;
import com.project.ssm.member.model.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository{
    Optional<Member> findByMemberEmail(String memberEmail);
    Optional<Member> findMemberByIdx(Long memberIdx);
    List<Member> findByMemberNameContaining(String keyword);
}
