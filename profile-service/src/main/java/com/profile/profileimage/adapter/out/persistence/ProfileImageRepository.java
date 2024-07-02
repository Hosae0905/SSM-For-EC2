package com.profile.profileimage.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long> {
    Optional<ProfileImageEntity> findByMemberEmail(String memberEmail);
}
