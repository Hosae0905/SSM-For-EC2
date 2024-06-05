package com.profile.profileimage.application.port.out;

import com.profile.profileimage.domain.ProfileImage;

public interface ProfileImageOutPort {
    ProfileImage registerProfile(ProfileImage profileImage);

    ProfileImage findProfile(String memberEmail);
}
