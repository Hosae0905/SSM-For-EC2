package com.profile.profileimage.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ProfileImageUploadS3OutPort {
    String uploadToS3(MultipartFile profileImage);
}
