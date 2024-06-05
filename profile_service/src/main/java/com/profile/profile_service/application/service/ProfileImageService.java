package com.profile.profile_service.application.service;

import com.profile.profile_service.application.port.in.GetProfileImageCommand;
import com.profile.profile_service.application.port.in.GetProfileImageInPort;
import com.profile.profile_service.application.port.in.PostProfileImageCommand;
import com.profile.profile_service.application.port.in.PostProfileImageInPort;
import com.profile.profile_service.application.port.out.ProfileImageOutPort;
import com.profile.profile_service.application.port.out.ProfileImageUploadS3OutPort;
import com.profile.profile_service.domain.ProfileImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileImageService implements PostProfileImageInPort, GetProfileImageInPort {

    private final ProfileImageOutPort profileImageOutPort;
    private final ProfileImageUploadS3OutPort profileImageUploadS3OutPort;

    @Override
    public ProfileImage registerProfile(PostProfileImageCommand command) {
        String imagePath = profileImageUploadS3OutPort.uploadToS3(command.getProfileImage());
        ProfileImage profileImage = ProfileImage.builder().memberEmail(command.getMemberEmail()).imagePath(imagePath).build();
        return profileImageOutPort.registerProfile(profileImage);
    }

    @Override
    public ProfileImage getProfileImage(GetProfileImageCommand command) {
        return profileImageOutPort.findProfile(command.getMemberEmail());
    }
}
