package library.controller;

import java.util.List;

import library.dto.get.AuthorGetDto;
import library.dto.get.ReviewGetDto;
import library.exception.NotFoundException;
import library.model.Review;
import library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewGetDto> getReviewById(@PathVariable Long id) {
        ReviewGetDto review = reviewService.getReviewById(id);
        return ResponseEntity.ok(review);
    }

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        return new ResponseEntity<>(reviewService.createReview(review), HttpStatus.CREATED);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review review) {
        Review updatedReview = reviewService.getReviewById(id)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        updatedReview.setComment(review.getComment());
        updatedReview.setRating(review.getRating());
        return ResponseEntity.ok(updatedReview);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
