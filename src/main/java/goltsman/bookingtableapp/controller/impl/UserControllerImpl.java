package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.controller.UserController;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.request.UpdateUserProfileRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.UserListResponse;
import goltsman.bookingtableapp.model.responce.UserResponse;
import goltsman.bookingtableapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> create(CreateUserRequest createUserRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(createUserRequest));
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateProfile(UpdateUserProfileRequest updateUserProfileRequest) {
        return ResponseEntity.ok(userService.updateProfile(updateUserProfileRequest));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> getUser(Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> delete(Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserListResponse> getUsers(Integer page, Integer pageSize) {
        PageRequest pageable = PageRequest.of(Math.max(page - 1, 0), Math.max(pageSize, 1));
        return ResponseEntity.ok(userService.getUsers(pageable));
    }
}
