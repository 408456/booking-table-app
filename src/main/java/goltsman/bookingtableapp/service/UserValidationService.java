package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.exception.ResourceAlreadyExistsException;
import goltsman.bookingtableapp.model.User;

public interface UserValidationService {

    void validateEmailForCreate(String email);

    void validatePhoneForCreate(String phone);

    void validateEmailForUpdate(String newEmail, User user);

    void validatePhoneForUpdate(String newPhone, User user);

    boolean isEmailChanged(String newEmail, User user);
}
