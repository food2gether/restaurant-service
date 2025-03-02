package com.github.food2gether.restaurantservice.repository;

import com.github.food2gether.shared.model.Restaurant;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class RestaurantRepositoryImpl implements RestaurantRepository {

  @Override
  public List<Restaurant> listAllForQuery(String query) {
    return this.list("displayName LIKE ?1", "%" + query + "%");
  }
}
