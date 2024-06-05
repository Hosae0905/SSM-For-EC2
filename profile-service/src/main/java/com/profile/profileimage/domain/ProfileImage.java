package com.profile.profileimage.domain;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProfileImage {
    private final String memberEmail;
    private final String imagePath;
}
