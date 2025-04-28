package edu.du.userservice.Repository;

import edu.du.userservice.Entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    // 추가적인 메서드는 필요에 따라 작성 가능
}
