package com.profile.profile_service.application.port.in;

import com.profile.profile_service.domain.ProfileImage;

public interface GetProfileImageInPort {
    ProfileImage getProfileImage(GetProfileImageCommand command);
}
