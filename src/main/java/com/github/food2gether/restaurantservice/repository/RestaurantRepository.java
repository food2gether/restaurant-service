package com.github.food2gether.restaurantservice.repository;

import com.github.food2gether.shared.model.MenuItem;
import com.github.food2gether.shared.model.Order;
import com.github.food2gether.shared.model.Restaurant;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;

public interface RestaurantRepository extends PanacheRepository<Restaurant> {

  List<Restaurant> listAllForQuery(String query);
}
