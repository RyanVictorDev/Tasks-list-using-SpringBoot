package com.back.tasks.users;

import com.back.tasks.api.io.user.UserCreateRequest;
import com.back.tasks.api.io.user.UserResponse;
import com.back.tasks.domain.entity.user.UserEntity;
import com.back.tasks.domain.exception.IllegalValueException;
import com.back.tasks.domain.io.enums.UserRole;
import com.back.tasks.domain.repository.user.UserRepository;
import com.back.tasks.domain.service.user.impl.UserServiceImpl;
import com.back.tasks.domain.service.user.impl.assembler.UserAssembler;
import com.back.tasks.domain.validation.user.impl.UserValidationImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServicesTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserValidationImpl userValidationImpl;

    @Mock
    private UserAssembler userAssembler;

    @InjectMocks
    private UserServiceImpl userService;

    private UserCreateRequest request;

    @BeforeEach
    void setUp() {
        request = new UserCreateRequest();
        request.setName("Ryan Victor");
        request.setEmail("ryan@example.com");
        request.setPassword("123456");
        request.setRole(UserRole.USER);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        UserEntity savedUser = new UserEntity();
        savedUser.setId(1L);
        savedUser.setName(request.getName());
        savedUser.setEmail(request.getEmail());
        savedUser.setRole(request.getRole());
        savedUser.setDeleted(false);

        when(userRepository.save(any(UserEntity.class))).thenReturn(savedUser);

        UserResponse response = userService.create(request);

        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(request.getRole(), response.getRole());
        verify(userValidationImpl, times(1)).validateCreation(request);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void shouldThrowExceptionWhenValidationFails() {
        doThrow(new IllegalValueException("Invalid email")).when(userValidationImpl).validateCreation(request);

        IllegalValueException ex = assertThrows(IllegalValueException.class, () -> userService.create(request));
        assertEquals("Invalid email", ex.getMessage());
        verify(userRepository, never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenPasswordTooShort() {
        request.setPassword("12");
        doThrow(new IllegalValueException("Password must be at least 4 characters")).when(userValidationImpl).validateCreation(request);

        IllegalValueException ex = assertThrows(IllegalValueException.class, () -> userService.create(request));
        assertEquals("Password must be at least 4 characters", ex.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenNameIsEmpty() {
        request.setName("");
        doThrow(new IllegalValueException("Name is required")).when(userValidationImpl).validateCreation(request);

        IllegalValueException ex = assertThrows(IllegalValueException.class, () -> userService.create(request));
        assertEquals("Name is required", ex.getMessage());
    }

    @Test
    void shouldGetAllUsersSuccessfully() {
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setName("Ryan");
        user1.setEmail("ryan@example.com");
        user1.setRole(UserRole.USER);

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setName("Victor");
        user2.setEmail("victor@example.com");
        user2.setRole(UserRole.ADMIN);

        List<UserEntity> entities = List.of(user1, user2);
        List<UserResponse> responses = List.of(
                UserResponse.builder().id(1L).name("Ryan").email("ryan@example.com").role(UserRole.USER).build(),
                UserResponse.builder().id(2L).name("Victor").email("victor@example.com").role(UserRole.ADMIN).build()
        );

        when(userRepository.findAll()).thenReturn(entities);
        when(userAssembler.parseUserEntityToResponse(entities)).thenReturn(responses);

        List<UserResponse> result = userService.getAll();

        assertEquals(2, result.size());
        assertEquals("Ryan", result.get(0).getName());
        verify(userRepository, times(1)).findAll();
        verify(userAssembler, times(1)).parseUserEntityToResponse(entities);
    }
}
