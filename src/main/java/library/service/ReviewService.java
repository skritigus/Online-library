package library.service;

import library.dto.create.ReviewCreateDto;
import library.dto.get.ReviewGetDto;
import library.exception.NotFoundException;
import library.mapper.ReviewMapper;
import library.model.Book;
import library.model.Review;
import library.model.User;
import library.repository.BookRepository;
import library.repository.ReviewRepository;
import library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public ReviewGetDto getReviewById(Long id, Long bookId) {
        bookRepository.findById(id).orElseThrow(() -> new NotFoundException("Book with id " + id + " not found"));
        Review review = reviewRepository.findByIdAndBookId(id, bookId)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        return ReviewMapper.toDto(review);
    }

    public ReviewGetDto createReview(Long bookId, ReviewCreateDto reviewDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
        Review review = ReviewMapper.fromDto(reviewDto);
        book.getReviews().add(review);
        review.setBook(book);

        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User with id " + reviewDto.getUserId() + " not found"));
        user.getReviews().add(review);
        review.setUser(user);

        return ReviewMapper.toDto(reviewRepository.save(review));
    }

    public ReviewGetDto updateReview(Long id, Long bookId, ReviewCreateDto reviewDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
        Review reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        reviewEntity.setComment(reviewDto.getComment());
        reviewEntity.setRating(reviewDto.getRating());

        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User with id " + reviewDto.getUserId() + " not found"));
        user.getReviews().add(reviewEntity);
        reviewEntity.setUser(user);

        return ReviewMapper.toDto(reviewRepository.save(reviewEntity));
    }

    public void deleteReview(Long id, Long bookId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
        reviewRepository.findByIdAndBookId(id, bookId)
                .orElseThrow(() -> new NotFoundException("Review with id " + id + " not found"));
        reviewRepository.deleteById(id);
    }
}
