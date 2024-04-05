import org.example.Role;
import org.example.User;
import org.example.UserRepository;
import org.example.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void testCreateUser_Success() {
        User newUser = new User("john_doe", "password", "John", "Doe", Role.USER);

        when(userRepository.findByUsername("john_doe")).thenReturn(null);
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.create(newUser);

        assertNotNull(createdUser);
        assertEquals("john_doe", createdUser.getUsername());
        assertEquals("John", createdUser.getFirstName());
        assertEquals("Doe", createdUser.getLastName());
        assertEquals(Role.USER, createdUser.getRole());

        verify(userRepository, times(1)).findByUsername("john_doe");
        verify(userRepository, times(1)).save(newUser);
    }

    @Test
    void testCreateUser_Failure_UserExists() {
        User existingUser = new User("john_doe", "password", "John", "Doe", Role.USER);

        when(userRepository.findByUsername("john_doe")).thenReturn(existingUser);

        assertThrows(RuntimeException.class, () -> userService.create(existingUser));

        verify(userRepository, times(1)).findByUsername("john_doe");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testReadAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("john_doe", "password", "John", "Doe", Role.USER));

        when(userRepository.findAll()).thenReturn(userList);

        List<User> retrievedUsers = userService.readAll();

        assertEquals(1, retrievedUsers.size());
        assertEquals("john_doe", retrievedUsers.get(0).getUsername());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testUpdateUser_Success() {
        User existingUser = new User("john_doe", "password", "John", "Doe", Role.USER);
        User updatedUser = new User("john_doe", "new_password", "John", "Doe", Role.ADMIN);

        when(userRepository.findByUsername("john_doe")).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(updatedUser);

        assertNotNull(result);
        assertEquals("new_password", result.getPassword());
        assertEquals(Role.ADMIN, result.getRole());

        verify(userRepository, times(1)).findByUsername("john_doe");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUser_Failure_UserNotFound() {
        User userToUpdate = new User("john_doe", "password", "John", "Doe", Role.USER);

        when(userRepository.findByUsername("john_doe")).thenReturn(null);

        User result = userService.update(userToUpdate);

        assertNull(result);

        verify(userRepository, times(1)).findByUsername("john_doe");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_Success() {
        User existingUser = new User("john_doe", "password", "John", "Doe", Role.USER);

        when(userRepository.findByUsername("john_doe")).thenReturn(existingUser);
        when(userRepository.delete(existingUser)).thenReturn(true);

        boolean result = userService.delete(existingUser);

        assertTrue(result);

        verify(userRepository, times(1)).findByUsername("john_doe");
        verify(userRepository, times(1)).delete(existingUser);
    }

    @Test
    void testDeleteUser_Failure_UserNotFound() {
        User userToDelete = new User("john_doe", "password", "John", "Doe", Role.USER);

        when(userRepository.findByUsername("john_doe")).thenReturn(null);

        boolean result = userService.delete(userToDelete);

        assertFalse(result);

        verify(userRepository, times(1)).findByUsername("john_doe");
        verify(userRepository, never()).delete(any(User.class));
    }
}