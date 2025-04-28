// src/main/java/edu/du/userservice/Controller/AdminReviewController.java
package edu.du.userservice.Controller;

import edu.du.userservice.Entity.Review;
import edu.du.userservice.Service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class AdminReviewController {

    private final ReviewService reviewService;

    /** 전체 리뷰 조회 */
    @GetMapping
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews();
    }

    /** 리뷰 수정 */
    @PutMapping("/{id}")
    public Review updateReview(
            @PathVariable("id") Long id,
            @RequestBody Review updated
    ) {
        return reviewService.updateReview(id, updated);
    }

    /** 리뷰 삭제 */
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteReview(id);
    }
}