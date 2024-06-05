package com.profile.profile_service.application.port.out;

import com.profile.profile_service.domain.ProfileImage;

public interface ProfileImageOutPort {
    ProfileImage registerProfile(ProfileImage profileImage);

    ProfileImage findProfile(String memberEmail);
}
