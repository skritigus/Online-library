package library.controller;

import jakarta.validation.Valid;
import library.dto.create.ReviewCreateDto;
import library.dto.get.ReviewGetDto;
import library.model.Review;
import library.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books/{bookId}/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewGetDto> getReviewById(@PathVariable Long id,
                                                      @PathVariable Long bookId) {
        return ResponseEntity.ok(reviewService.getReviewById(id, bookId));
    }

    @PostMapping
    public ResponseEntity<ReviewGetDto> createReview(@PathVariable Long bookId,
                                                     @Valid @RequestBody ReviewCreateDto review) {
        return new ResponseEntity<>(reviewService.createReview(bookId, review), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewGetDto> updateReview(@PathVariable Long id,
                                                     @PathVariable Long bookId,
                                                     @Valid @RequestBody ReviewCreateDto review) {
        return ResponseEntity.ok(reviewService.updateReview(id, bookId, review));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id,
                                               @PathVariable Long bookId) {
        reviewService.deleteReview(id, bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
