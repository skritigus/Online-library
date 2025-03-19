package library.service;

import library.dto.create.UserCreateDto;
import library.dto.get.UserGetDto;
import library.exception.NotFoundException;
import library.mapper.UserMapper;
import library.model.User;
import library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserGetDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        return UserMapper.toDto(user);
    }

    public UserGetDto createUser(UserCreateDto userDto) {
        User userEntity = UserMapper.fromDto(userDto);
        return UserMapper.toDto(userRepository.save(userEntity));
    }

    public UserGetDto updateUser(Long id, UserCreateDto userDto) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(userDto.getPassword());
        userEntity.setName(userDto.getName());
        return UserMapper.toDto(userRepository.save(userEntity));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
