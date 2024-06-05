package com.profile.profile_service.application.port.in;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
public class PostProfileImageCommand {
    private final String memberEmail;
    private final MultipartFile profileImage;
}
