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
    private final PostSignUpInPort postSignUpInPort;
    private final PostLoginInPort postLoginInPort;
    private final PatchUpdateInPort patchUpdateInPort;
    private final DeleteMemberInPort deleteMemberInPort;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<Object> signup(@RequestPart PostSignUpReq postSignUpReq, @RequestPart(required = false) MultipartFile multipartFile) {
        PostSignUpCommand command = PostSignUpCommand.buildCommand();
        return ResponseEntity.ok().body(postSignUpInPort.signup(command));
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody PostLoginReq postLoginReq) {
        PostLoginCommand command = PostLoginCommand.buildCommand();
        return ResponseEntity.ok().body(postLoginInPort.login(command));
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ResponseEntity<Object> updateMember(@RequestBody PatchUpdateReq patchUpdateReq) {
        PatchUpdateCommand command = PatchUpdateCommand.buildCommand();
        return ResponseEntity.ok().body(patchUpdateInPort.update(command));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteMember(@RequestBody DeleteMemberReq deleteMemberReq) {
        DeleteMemberCommand command = DeleteMemberCommand.buildCommand();
        return ResponseEntity.ok().body(deleteMemberInPort.delete(command));
    }
}
