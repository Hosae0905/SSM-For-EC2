package com.member.memberservice.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Boolean deleteByMemberEmail(String memberEmail);

    Optional<MemberEntity> findByMemberEmail(String memberEmail);
}
