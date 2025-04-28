package edu.du.userservice.Repository;

import edu.du.userservice.Entity.Experience;
import edu.du.userservice.Entity.Experience.ExperienceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    Page<Experience> findByUserId(Long userId, Pageable pageable);
    Page<Experience> findByStatusAndCampaignId(ExperienceStatus status, Long campaignId, Pageable pageable);
    Page<Experience> findByStatus(ExperienceStatus status, Pageable pageable);
    Page<Experience> findByCampaignId(Long campaignId, Pageable pageable);
}