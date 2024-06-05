package com.profile.profileimage.application.port.in;

import com.profile.profileimage.domain.ProfileImage;

public interface GetProfileImageInPort {
    ProfileImage getProfileImage(GetProfileImageCommand command);
}
