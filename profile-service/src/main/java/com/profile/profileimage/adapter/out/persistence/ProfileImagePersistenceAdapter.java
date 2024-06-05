package com.profile.profileimage.adapter.out.persistence;

import com.profile.profileimage.application.port.out.ProfileImageOutPort;
import com.profile.profileimage.domain.ProfileImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProfileImagePersistenceAdapter implements ProfileImageOutPort {

    private final ProfileImageRepository profileImageRepository;

    @Override
    public ProfileImage registerProfile(ProfileImage profileImage) {
        ProfileImageEntity profileImageEntity = profileImageRepository.save(ProfileImageEntity.builder()
                .memberEmail(profileImage.getMemberEmail())
                .imagePath(profileImage.getImagePath())
                .build());

        return ProfileImage.builder()
                .memberEmail(profileImageEntity.getMemberEmail())
                .imagePath(profileImage.getImagePath())
                .build();
    }

    @Override
    public ProfileImage findProfile(String memberEmail) {
        Optional<ProfileImageEntity> profileImage = profileImageRepository.findByMemberEmail(memberEmail);

        if (profileImage.isPresent()) {
            return ProfileImage.builder()
                    .memberEmail(profileImage.get().getMemberEmail())
                    .imagePath(profileImage.get().getImagePath())
                    .build();
        }

        return null;
    }
}
