package com.profile.profile_service.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity, Long> {
    Optional<ProfileImageEntity> findByMemberEmail(String memberEmail);
}
