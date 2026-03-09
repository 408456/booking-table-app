package goltsman.bookingtableapp.service;

import goltsman.bookingtableapp.model.request.restaurant.CreateCuisineRequest;
import goltsman.bookingtableapp.model.request.restaurant.UpdateCuisineRequest;
import goltsman.bookingtableapp.model.responce.MessageResponse;
import goltsman.bookingtableapp.model.responce.restaurant.CuisineResponse;

import java.util.List;

public interface CuisineService {

    CuisineResponse create(CreateCuisineRequest request);

    CuisineResponse update(Long id, UpdateCuisineRequest request);

    MessageResponse delete(Long id);

    CuisineResponse getCuisine(Long id);

    List<CuisineResponse> getCuisines();

}