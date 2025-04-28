package edu.du.userservice.Controller;

import edu.du.userservice.Entity.Business;
import edu.du.userservice.Service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/businesses")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    // 모든 사업체 조회
    @GetMapping
    public List<Business> getAllBusinesses() {
        return businessService.getAllBusinesses();
    }

    // 사업체 등록
    @PostMapping
    public Business createBusiness(@RequestBody Business business) {
        return businessService.createBusiness(business);
    }

    // 사업체 수정
    @PutMapping("/{id}")
    public Business updateBusiness(@PathVariable Long id, @RequestBody Business updatedBusiness) {
        return businessService.updateBusiness(id, updatedBusiness);
    }

    // 사업체 삭제
    @DeleteMapping("/{id}")
    public void deleteBusiness(@PathVariable Long id) {
        businessService.deleteBusiness(id);
    }

    //사업체 승인

    @PutMapping("/{id}/approve")
    public Business approveBusiness(@PathVariable("id") Long id) {
        return businessService.approveBusiness(id);
    }

    // 사업체 취소
    @PutMapping("/{id}/cancel")
    public Business cancelBusiness(@PathVariable("id") Long id) {
        return businessService.cancelBusiness(id);
    }
}
