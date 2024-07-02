package com.member.memberservice.adapter.in.web;

import com.member.memberservice.adapter.in.web.data.DeleteMemberReq;
import com.member.memberservice.adapter.in.web.data.PatchUpdateReq;
import com.member.memberservice.adapter.in.web.data.PostLoginReq;
import com.member.memberservice.adapter.in.web.data.PostSignUpReq;
import com.member.memberservice.application.port.in.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final SignUpMemberInPort signUpMemberInPort;
    private final LoginMemberInPort loginMemberInPort;
    private final UpdateMemberInPort updateMemberInPort;
    private final DeleteMemberInPort deleteMemberInPort;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Object> signup(@RequestPart PostSignUpReq postSignUpReq, @RequestPart(required = false) MultipartFile profileImage) {
        SignUpMemberCommand command = SignUpMemberCommand.buildCommand(postSignUpReq, profileImage);
        return ResponseEntity.ok().body(signUpMemberInPort.signup(command));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody PostLoginReq postLoginReq) {
        LoginMemberCommand command = LoginMemberCommand.buildCommand(postLoginReq);
        return ResponseEntity.ok().body(loginMemberInPort.login(command));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ResponseEntity<Object> updateMember(@RequestHeader(value = "Authorization") String token, @RequestBody PatchUpdateReq patchUpdateReq) {
        UpdateMemberCommand command = UpdateMemberCommand.buildCommand(patchUpdateReq);
        return ResponseEntity.ok().body(updateMemberInPort.update(command, token));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMember(@RequestHeader(value = "Authorization") String token, @RequestBody DeleteMemberReq deleteMemberReq) {
        DeleteMemberCommand command = DeleteMemberCommand.buildCommand(deleteMemberReq);
        return ResponseEntity.ok().body(deleteMemberInPort.delete(command, token));
    }
}
