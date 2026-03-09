package goltsman.bookingtableapp.service.impl;

import goltsman.bookingtableapp.exception.ResourceAlreadyExistsException;
import goltsman.bookingtableapp.mapper.CuisineMapper;
import goltsman.bookingtableapp.model.entity.Cuisine;
import goltsman.bookingtableapp.model.request.restaurant.CreateCuisineRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateCuisineRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.CuisineResponse;
import goltsman.bookingtableapp.repository.CuisineRepository;
import goltsman.bookingtableapp.service.CuisineService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CuisineServiceImpl implements CuisineService {

    private final CuisineRepository cuisineRepository;
    private final CuisineMapper cuisineMapper;

    @Override
    @Transactional
    public CuisineResponse create(CreateCuisineRequest request) {
        if (cuisineRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Кухня с таким названием уже существует");
        }
        Cuisine cuisine = cuisineMapper.mapCreateCuisineRequestToCuisine(request);
        cuisineRepository.save(cuisine);
        return cuisineMapper.mapCuisineToCuisineResponse(cuisine);
    }

    @Override
    @Transactional
    public CuisineResponse update(Long id, UpdateCuisineRequest request) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Кухня с id " + id + " не найдена"));
        cuisineMapper.mapUpdateCuisineRequestToCuisine(request, cuisine);
        cuisineRepository.save(cuisine);
        return cuisineMapper.mapCuisineToCuisineResponse(cuisine);
    }

    @Override
    @Transactional
    public MessageResponse delete(Long id) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Кухня с id " + id + " не найдена"));
        cuisineRepository.delete(cuisine);
        return MessageResponse.builder().message("Кухня успешно удалена").build();
    }

    @Override
    @Transactional(readOnly = true)
    public CuisineResponse getCuisine(Long id) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Кухня с id " + id + " не найдена"));
        return cuisineMapper.mapCuisineToCuisineResponse(cuisine);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CuisineResponse> getCuisines() {
        return cuisineRepository.findAll().stream().map(cuisineMapper::mapCuisineToCuisineResponse).toList();
    }
}