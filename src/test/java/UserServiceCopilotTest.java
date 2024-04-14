import org.example.Role;
import org.example.User;
import org.example.UserRepository;
import org.example.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceCopilotTest {
    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        User user = new User("new_user", "password", "John", "Doe", Role.USER);
        when(userRepository.findByUsername("new_user")).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        verify(userRepository, times(1)).findByUsername("new_user");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldNotCreateUserWhenUsernameExists() {
        User user = new User("existing_user", "password", "John", "Doe", Role.USER);
        when(userRepository.findByUsername("existing_user")).thenReturn(user);

        assertThrows(RuntimeException.class, () -> userService.create(user));
        verify(userRepository, times(1)).findByUsername("existing_user");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = new User("user1", "password", "John", "Doe", Role.USER);
        User user2 = new User("user2", "password", "Jane", "Doe", Role.USER);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<User> users = userService.readAll();

        assertEquals(2, users.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        User existingUser = new User("existing_user", "password", "John", "Doe", Role.USER);
        User newUser = new User("existing_user", "new_password", "John", "Doe", Role.ADMIN);
        when(userRepository.findByUsername("existing_user")).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User updatedUser = userService.update(newUser);

        assertNotNull(updatedUser);
        assertEquals("new_password", updatedUser.getPassword());
        assertEquals(Role.ADMIN, updatedUser.getRole());
        verify(userRepository, times(1)).findByUsername("existing_user");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldNotUpdateUserWhenUsernameDoesNotExist() {
        User user = new User("non_existing_user", "password", "John", "Doe", Role.USER);
        when(userRepository.findByUsername("non_existing_user")).thenReturn(null);

        User updatedUser = userService.update(user);

        assertNull(updatedUser);
        verify(userRepository, times(1)).findByUsername("non_existing_user");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        User user = new User("existing_user", "password", "John", "Doe", Role.USER);
        when(userRepository.findByUsername("existing_user")).thenReturn(user);
        when(userRepository.delete(user)).thenReturn(true);

        Boolean result = userService.delete(user);

        assertTrue(result);
        verify(userRepository, times(1)).findByUsername("existing_user");
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void shouldNotDeleteUserWhenUsernameDoesNotExist() {
        User user = new User("non_existing_user", "password", "John", "Doe", Role.USER);
        when(userRepository.findByUsername("non_existing_user")).thenReturn(null);

        Boolean result = userService.delete(user);

        assertFalse(result);
        verify(userRepository, times(1)).findByUsername("non_existing_user");
        verify(userRepository, never()).delete(any(User.class));
    }
}