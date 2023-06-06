package service;

import dao.UserRepository;
import dto.UserDto;
import mapper.UserMapper;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    //Выборка всех пользователей
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toUserDto(users);
    }

    //Выборка пользователя по id
    public UserDto getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(user -> userMapper.toUserDto(user)).orElse(null);
    }

    //Создание нового пользователя по DTO
    public UserDto createUser(UserDto userDto) {
        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setEmail(userDto.getEmail());
        newUser.setAge(userDto.getAge());

        User savedUser = userRepository.save(newUser);
        return userMapper.toUserDto(savedUser);
    }

    //Создание нового пользователя
    public UserDto createUser() {
        User savedUser = userRepository.save(new User());
        return userMapper.toUserDto(savedUser);
    }

    //Обновление полей пользователя
    public UserDto updateUser(Long id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(userDto.getUsername());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setAge(userDto.getAge());

            User savedUser = userRepository.save(existingUser);
            return userMapper.toUserDto(savedUser);
        } else {
            return null;
        }
    }

    //Удаление пользователя
    public boolean deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
            return true;
        } else {
            return false;
        }
    }
}