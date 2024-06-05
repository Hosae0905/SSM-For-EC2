package com.profile.profileimage.application.service;

import com.profile.profileimage.application.port.in.GetProfileImageCommand;
import com.profile.profileimage.application.port.in.GetProfileImageInPort;
import com.profile.profileimage.application.port.in.PostProfileImageCommand;
import com.profile.profileimage.application.port.in.PostProfileImageInPort;
import com.profile.profileimage.application.port.out.ProfileImageOutPort;
import com.profile.profileimage.application.port.out.ProfileImageUploadS3OutPort;
import com.profile.profileimage.domain.ProfileImage;
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
