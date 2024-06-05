package com.profile.profileimage.application.port.in;

import com.profile.profileimage.domain.ProfileImage;

public interface PostProfileImageInPort {

    ProfileImage registerProfile(PostProfileImageCommand command);
}
