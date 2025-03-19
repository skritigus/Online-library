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
    private static final String BOOK_NOT_FOUND_MESSAGE = "Book is not found with id: ";
    private static final String REVIEW_NOT_FOUND_MESSAGE = "Review is not found with id: ";
    private static final String USER_NOT_FOUND_MESSAGE = "User is not found with id: ";

    @Autowired
    public ReviewService(ReviewRepository reviewRepository,
                         BookRepository bookRepository,
                         UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public ReviewGetDto getReviewById(Long id, Long bookId) {
        bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MESSAGE + bookId));
        Review review = reviewRepository.findByIdAndBookId(id, bookId)
                .orElseThrow(() -> new NotFoundException(REVIEW_NOT_FOUND_MESSAGE + id));
        return ReviewMapper.toDto(review);
    }

    public ReviewGetDto createReview(Long bookId, ReviewCreateDto reviewDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MESSAGE + bookId));
        Review review = ReviewMapper.fromDto(reviewDto);
        book.getReviews().add(review);
        review.setBook(book);

        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(()
                        -> new NotFoundException(USER_NOT_FOUND_MESSAGE + reviewDto.getUserId()));
        user.getReviews().add(review);
        review.setUser(user);

        return ReviewMapper.toDto(reviewRepository.save(review));
    }

    public ReviewGetDto updateReview(Long id, Long bookId, ReviewCreateDto reviewDto) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException(BOOK_NOT_FOUND_MESSAGE + bookId));
        Review reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(REVIEW_NOT_FOUND_MESSAGE + id));
        reviewEntity.setComment(reviewDto.getComment());
        reviewEntity.setRating(reviewDto.getRating());

        User user = userRepository.findById(reviewDto.getUserId())
                .orElseThrow(()
                        -> new NotFoundException(USER_NOT_FOUND_MESSAGE + reviewDto.getUserId()));
        user.getReviews().add(reviewEntity);
        reviewEntity.setUser(user);

        return ReviewMapper.toDto(reviewRepository.save(reviewEntity));
    }

    public void deleteReview(Long id, Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException(BOOK_NOT_FOUND_MESSAGE + bookId);
        }
        if (!reviewRepository.existsById(bookId)) {
            throw new NotFoundException(REVIEW_NOT_FOUND_MESSAGE + id);
        }
        reviewRepository.deleteById(id);
    }
}
