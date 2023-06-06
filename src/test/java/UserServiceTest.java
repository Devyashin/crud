import dao.UserRepository;
import dto.UserDto;
import mapper.UserMapper;
import model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private final static String TEST_NAME = "John Doe";

    private final static String TEST_NAME_2 = "John Doe";

    private final static String TEST_EMAIL = "john.doe@example.com";

    private final static String TEST_EMAIL_2 = "jane.doe@example.com";

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(
                new User(1L, TEST_NAME, TEST_EMAIL, 18),
                new User(2L, TEST_NAME, TEST_EMAIL, 18)
        );
        List<UserDto> userDtos = Arrays.asList(
                new UserDto(1L, TEST_NAME, TEST_EMAIL, 18),
                new UserDto(2L, TEST_NAME_2, TEST_EMAIL_2, 18)
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toUserDto(users)).thenReturn(userDtos);

        List<UserDto> result = userService.getAllUsers();

        assertEquals(userDtos, result);
    }

    @Test
    public void testGetUserById() {
        Long id = 1L;
        User user = new User(id, TEST_NAME, TEST_EMAIL, 18);
        UserDto userDto = new UserDto(id, TEST_NAME, TEST_EMAIL, 18);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toUserDto(user)).thenReturn(userDto);

        UserDto result = userService.getUserById(id);

        assertEquals(userDto, result);
    }

    @Test
    public void testCreateUser() {
        Long id = 1L;
        UserDto userDto = new UserDto(id, TEST_NAME, TEST_EMAIL, 18);
        UserDto savedUserDto = new UserDto(id, TEST_NAME, TEST_EMAIL, 18);
        User user = new User(id, TEST_NAME, TEST_EMAIL, 18);
        User savedUser = new User(id, TEST_NAME, TEST_EMAIL, 18);

        when(userMapper.toUser(userDto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toUserDto(savedUser)).thenReturn(savedUserDto);

        UserDto result = userService.createUser(userDto);

        assertEquals(savedUserDto, result);
    }

    @Test
    public void testDeleteUser() {
        Long id = 1L;
        User user = new User(id, TEST_NAME, TEST_EMAIL, 18);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        userService.deleteUser(id);

        verify(userRepository, times(1)).delete(user);
    }
}