package com.profile.profile_service.adapter.in.web;

import com.profile.profile_service.application.port.in.GetProfileImageCommand;
import com.profile.profile_service.application.port.in.GetProfileImageInPort;
import com.profile.profile_service.application.port.in.PostProfileImageCommand;
import com.profile.profile_service.application.port.in.PostProfileImageInPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileImageController {

    private final PostProfileImageInPort postProfileImageInPort;
    private final GetProfileImageInPort getProfileImageInPort;

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<Object> register(@RequestPart String memberEmail, MultipartFile profileImage) {
        PostProfileImageCommand command = PostProfileImageCommand.builder().memberEmail(memberEmail).profileImage(profileImage).build();
        return ResponseEntity.ok().body(postProfileImageInPort.registerProfile(command));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/{memberEmail}")
    public ResponseEntity<Object> getProfileImage(@PathVariable String memberEmail) {
        GetProfileImageCommand command = GetProfileImageCommand.builder().memberEmail(memberEmail).build();
        return ResponseEntity.ok().body(getProfileImageInPort.getProfileImage(command));
    }
}
