package goltsman.bookingtableapp.repository.specification;

import goltsman.bookingtableapp.model.entity.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecification {

    public static Specification<Restaurant> titleContains(String title) {
        return (root, query, cb) ->
                title == null ? null :
                        cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Restaurant> addressContains(String address) {
        return (root, query, cb) ->
                address == null ? null :
                        cb.like(cb.lower(root.get("address")), "%" + address.toLowerCase() + "%");
    }

    public static Specification<Restaurant> cuisineId(Long cuisineId) {
        return (root, query, cb) ->
                cuisineId == null ? null :
                        cb.equal(root.join("cuisines").get("id"), cuisineId);
    }

    public static Specification<Restaurant> minAvgSum(BigDecimal min) {
        return (root, query, cb) ->
                min == null ? null :
                        cb.greaterThanOrEqualTo(root.get("avgSum"), min);
    }

    public static Specification<Restaurant> maxAvgSum(BigDecimal max) {
        return (root, query, cb) ->
                max == null ? null :
                        cb.lessThanOrEqualTo(root.get("avgSum"), max);
    }

    public static Specification<Restaurant> isPublished(Boolean value) {
        return (root, query, cb) ->
                value == null ? null :
                        cb.equal(root.get("isPublished"), value);
    }

}