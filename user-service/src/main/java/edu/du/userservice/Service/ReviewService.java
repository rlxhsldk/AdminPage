// src/main/java/edu/du/userservice/Service/ReviewService.java
package edu.du.userservice.Service;

import edu.du.userservice.Entity.Review;
import edu.du.userservice.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /** 전체 리뷰 조회 */
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    /** 리뷰 수정 */
    public Review updateReview(Long id, Review updatedReview) {
        Optional<Review> opt = reviewRepository.findById(id);
        if (opt.isPresent()) {
            Review r = opt.get();
            r.setRating(updatedReview.getRating());
            r.setContent(updatedReview.getContent());
            return reviewRepository.save(r);
        } else {
            throw new RuntimeException("리뷰를 찾을 수 없습니다. ID: " + id);
        }
    }

    /** 리뷰 삭제 */
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("삭제할 리뷰가 없습니다. ID: " + id);
        }
        reviewRepository.deleteById(id);
    }
}