package library.service;

import java.util.List;
import java.util.Optional;

import library.dto.get.CategoryGetDto;
import library.dto.get.ReviewGetDto;
import library.exception.NotFoundException;
import library.model.Category;
import library.repository.ReviewRepository;
import library.model.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public ReviewGetDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        return ReviewGetDto.toDto(review);
    }

    public List<Review> getReviewByRating(int rating)  {
        return reviewRepository.findReviewsByRating(rating);
    }

    public Review createReview(Review review) {
        return reviewRepository.save(review);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
