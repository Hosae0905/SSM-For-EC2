package com.profile.profile_service.application.port.in;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetProfileImageCommand {
    private final String memberEmail;
}
