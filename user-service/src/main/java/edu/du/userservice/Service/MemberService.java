package edu.du.userservice.Service;

import edu.du.userservice.Entity.Member;
import edu.du.userservice.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> getAllUsers() {
        return memberRepository.findAll();
    }

    public Member updateUser(Long id, Member updatedUser) {
        Optional<Member> userOpt = memberRepository.findById(id);
        if (userOpt.isPresent()) {
            Member user = userOpt.get();
            user.setNickname(updatedUser.getNickname());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            user.setCampaignApplied(updatedUser.getCampaignApplied());
            return memberRepository.save(user);
        } else {
            throw new RuntimeException("사용자를 찾을 수 없습니다. ID: " + id);
        }
    }

    /**
     * 사용자 삭제
     * @param id 삭제할 사용자 ID
     */
    public void deleteUser(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new RuntimeException("삭제할 사용자가 존재하지 않습니다. ID: " + id);
        }
        memberRepository.deleteById(id);
    }
}
