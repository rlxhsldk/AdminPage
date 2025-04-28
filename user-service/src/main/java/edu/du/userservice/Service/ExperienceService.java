package edu.du.userservice.Service;

import edu.du.userservice.Entity.Experience;
import edu.du.userservice.Entity.Experience.ExperienceStatus;
import edu.du.userservice.dto.ExperienceDto;
import edu.du.userservice.dto.RemarkDto;
import edu.du.userservice.Repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    // Entity → DTO 변환 함수
    private final Function<Experience, ExperienceDto> toDto = exp ->
            ExperienceDto.builder()
                    .id(exp.getId())
                    .userId(exp.getUser().getId())
                    .username(exp.getUser().getNickname())
                    .campaignId(exp.getCampaign().getId())
                    .campaignName(exp.getCampaign().getName())
                    .applyDate(exp.getApplyDate())
                    .status(exp.getStatus().name())
                    .remark(exp.getRemark())
                    .build();

    /** 관리자: 전체 신청 목록 조회 (필터 + 페이징) */
    public Page<ExperienceDto> listExperiences(String status, Long campaignId, int page, int size) {
        ExperienceStatus st = (status != null ? ExperienceStatus.valueOf(status) : null);
        var pageable = PageRequest.of(page, size);
        if (st != null && campaignId != null) {
            return experienceRepository.findByStatusAndCampaignId(st, campaignId, pageable).map(toDto);
        } else if (st != null) {
            return experienceRepository.findByStatus(st, pageable).map(toDto);
        } else if (campaignId != null) {
            return experienceRepository.findByCampaignId(campaignId, pageable).map(toDto);
        } else {
            return experienceRepository.findAll(pageable).map(toDto);
        }
    }

    /** 회원: 내 신청 목록 조회 (페이징) */
    public Page<ExperienceDto> listMyExperiences(Long userId, int page, int size) {
        return experienceRepository.findByUserId(userId, PageRequest.of(page, size)).map(toDto);
    }

    /** 회원: 신청 취소 */
    @Transactional
    public void cancelExperience(Long id, Long userId) {
        var exp = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("신청을 찾을 수 없습니다. ID: " + id));
        if (!exp.getUser().getId().equals(userId)) {
            throw new RuntimeException("권한이 없습니다.");
        }
        exp.setStatus(ExperienceStatus.CANCELLED);
        experienceRepository.save(exp);
    }

    /** 관리자: 신청 승인 (사유 포함) */
    @Transactional
    public ExperienceDto approveExperience(Long id, RemarkDto remarkDto) {
        var exp = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("신청을 찾을 수 없습니다. ID: " + id));
        exp.setStatus(ExperienceStatus.APPROVED);
        exp.setRemark(remarkDto.getRemark());
        return toDto.apply(experienceRepository.save(exp));
    }

    /** 관리자: 신청 거부 (사유 포함) */
    @Transactional
    public ExperienceDto rejectExperience(Long id, RemarkDto remarkDto) {
        var exp = experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("신청을 찾을 수 없습니다. ID: " + id));
        exp.setStatus(ExperienceStatus.CANCELLED);
        exp.setRemark(remarkDto.getRemark());
        return toDto.apply(experienceRepository.save(exp));
    }
}

