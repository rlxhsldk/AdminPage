package edu.du.userservice.Controller;

import edu.du.userservice.Entity.Member;
import edu.du.userservice.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class AdminMemberController {

    private final MemberService memberService;

    /**
     * 사용자 전체 목록 조회
     */
    @GetMapping
    public List<Member> getUsers() {
        return memberService.getAllUsers();
    }

    /**
     * 사용자 정보 수정
     */
    @PutMapping("/{id}")
    public Member updateUser(@PathVariable("id") Long id, @RequestBody Member user) {
        return memberService.updateUser(id, user);
    }

    /**
     * 사용자 삭제
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id")  Long id) {
        memberService.deleteUser(id);
    }
}
