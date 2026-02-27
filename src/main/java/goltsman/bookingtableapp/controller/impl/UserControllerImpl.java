package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.common.RoleConstant;
import goltsman.bookingtableapp.controller.UserController;
import goltsman.bookingtableapp.model.request.CreateUserRequest;
import goltsman.bookingtableapp.model.responce.UserResponse;
import goltsman.bookingtableapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    @Secured(RoleConstant.ADMIN)
    public ResponseEntity<UserResponse> createUser(CreateUserRequest createUserRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(createUserRequest));
    }
}
