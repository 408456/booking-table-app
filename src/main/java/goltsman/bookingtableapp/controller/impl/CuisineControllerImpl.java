package goltsman.bookingtableapp.controller.impl;

import goltsman.bookingtableapp.controller.CuisineController;
import goltsman.bookingtableapp.model.request.restaurant.CreateCuisineRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateCuisineRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.CuisineResponse;
import goltsman.bookingtableapp.service.CuisineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CuisineControllerImpl implements CuisineController {

    private final CuisineService cuisineService;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CuisineResponse> create(CreateCuisineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuisineService.create(request));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<CuisineResponse> getCuisine(Long id) {
        return ResponseEntity.ok(cuisineService.getCuisine(id));
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ResponseEntity<List<CuisineResponse>> getCuisines() {
        return ResponseEntity.ok(cuisineService.getCuisines());
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CuisineResponse> update(Long id, UpdateCuisineRequest request) {
        return ResponseEntity.ok(cuisineService.update(id, request));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> delete(Long id) {
        return ResponseEntity.ok(cuisineService.delete(id));

    }
}