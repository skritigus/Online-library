package library.service;

import java.util.List;
import java.util.Optional;

import library.dto.get.AuthorGetDto;
import library.dto.get.UserGetDto;
import library.exception.NotFoundException;
import library.repository.AuthorRepository;
import library.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorGetDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Author with id " + id + " not found"));
        return AuthorGetDto.toDto(author);
    }

    public List<Author> getAuthorByName(String name)  {
        return authorRepository.findAuthorsByName(name);
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
