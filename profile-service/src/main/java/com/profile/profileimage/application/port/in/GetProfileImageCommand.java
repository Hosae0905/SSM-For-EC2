package com.profile.profileimage.application.port.in;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetProfileImageCommand {
    private final String memberEmail;
}
