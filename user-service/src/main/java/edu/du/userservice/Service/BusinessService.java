package edu.du.userservice.Service;

import edu.du.userservice.Entity.Business;
import edu.du.userservice.Repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    // 모든 사업체 조회
    public List<Business> getAllBusinesses() {
        return businessRepository.findAll();
    }

    // 사업체 등록
    public Business createBusiness(Business business) {
        return businessRepository.save(business);
    }

    // 사업체 수정
    public Business updateBusiness(Long id, Business updatedBusiness) {
        Optional<Business> businessOpt = businessRepository.findById(id);
        if (businessOpt.isPresent()) {
            Business business = businessOpt.get();
            business.setName(updatedBusiness.getName());
            business.setIndustry(updatedBusiness.getIndustry());
            business.setRecruitmentPeriod(updatedBusiness.getRecruitmentPeriod());
            business.setMaxPeople(updatedBusiness.getMaxPeople());
            business.setStatus(updatedBusiness.getStatus());  // Enum으로 처리
            return businessRepository.save(business);
        } else {
            throw new RuntimeException("사업체를 찾을 수 없습니다. ID: " + id);
        }
    }

    // 사업체 삭제
    public void deleteBusiness(Long id) {
        if (!businessRepository.existsById(id)) {
            throw new RuntimeException("삭제할 사업체가 존재하지 않습니다. ID: " + id);
        }
        businessRepository.deleteById(id);
    }

    // 사업체 승인
    public Business approveBusiness(Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사업체를 찾을 수 없습니다. ID: " + id));
        business.setStatus(Business.Status.APPROVED);  // Enum으로 처리
        return businessRepository.save(business);
    }

    // 사업체 취소
    public Business cancelBusiness(Long id) {
        Business business = businessRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사업체를 찾을 수 없습니다. ID: " + id));
        business.setStatus(Business.Status.CANCELLED);  // Enum으로 처리
        return businessRepository.save(business);
    }
}
