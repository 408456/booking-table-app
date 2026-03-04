package goltsman.bookingtableapp.mapper;

import goltsman.bookingtableapp.model.request.RefreshTokenRequest;
import goltsman.bookingtableapp.model.request.UserCredentialsRequest;
import goltsman.bookingtableapp.model.responce.JwtAuthenticationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    @Mapping(target = "token", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    JwtAuthenticationResponse mapUserCredentialsToJwtResponse(UserCredentialsRequest request);

    @Mapping(target = "token", ignore = true)
    @Mapping(target = "refreshToken", source = "refreshToken")
    JwtAuthenticationResponse mapRefreshTokenRequestToJwtResponse(RefreshTokenRequest request);
}
