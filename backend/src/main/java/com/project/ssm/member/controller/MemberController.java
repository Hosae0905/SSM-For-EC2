package com.project.ssm.member.controller;


import com.project.ssm.member.model.Member;
import com.project.ssm.member.model.request.*;
import com.project.ssm.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;

    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity<Object> signup(
            @RequestPart(value = "member") @Valid PostMemberSignupReq req,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        return ResponseEntity.ok().body(memberService.signup(req, profileImage));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<Object> login(@RequestBody @Valid PostMemberLoginReq req) {
        return ResponseEntity.ok().body(memberService.login(req));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/check/id")
    public ResponseEntity<Object> checkId(@RequestBody @Valid GetMemberCheckIdReq req) {
        return ResponseEntity.ok().body(memberService.checkId(req));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/update")
    public ResponseEntity<Object> updatePassword(
            @RequestPart(value = "member") @Valid PatchMemberUpdatePasswordReq req,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(memberService.updatePassword(member, req, profileImage));
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/delete")
    public ResponseEntity<Object> delete() {
        Member member = (Member) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok().body(memberService.delete(member));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/read")
    public ResponseEntity<Object> memberRead() {
        return ResponseEntity.ok().body(memberService.read());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile/{memberIdx}")
    public ResponseEntity<Object> getProfileImage(@PathVariable Long memberIdx) {
        return ResponseEntity.ok().body(memberService.getMemberProfile(memberIdx));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/chatroommembers")
    public ResponseEntity<Object> getChatRoomMembers(@RequestParam(value = "chatRoomId") String chatRoomId ){
        return ResponseEntity.ok().body(memberService.getChatRoomMembers(chatRoomId));
    }
}
