package com.profile.profileimage.adapter.out.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.profile.profileimage.application.port.out.ProfileImageUploadS3OutPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProfileImageUploadToS3 implements ProfileImageUploadS3OutPort {

    @Value("${cloud.aws.s3.profile-bucket}")
    private String bucket;
    private final AmazonS3 s3;

    @Override
    public String uploadToS3(MultipartFile profileImage) {
        return uploadProfileImage(profileImage);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        return str.replace("/", File.separator);
    }

    private String uploadProfileImage(MultipartFile profileImage) {
        String originalName = profileImage.getOriginalFilename();

        String folderPath = makeFolder();
        String uuid = UUID.randomUUID().toString();
        String saveFileName = folderPath + File.separator + uuid + "_" + originalName;

        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(profileImage.getSize());
            metadata.setContentType(profileImage.getContentType());

            s3.putObject(bucket, saveFileName.replace(File.separator, "/"), profileImage.getInputStream(), metadata);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 로컬 파일 시스템에서 파일 삭제
            File localFile = new File(saveFileName);
            if (localFile.exists()) {
                localFile.delete();
            }
            return s3.getUrl(bucket, saveFileName.replace(File.separator, "/")).toString();
        }
    }
}
