package com.github.food2gether.restaurantservice.service;


import com.github.food2gether.shared.model.MenuItem;
import com.github.food2gether.shared.model.MenuItem.DTO;
import com.github.food2gether.shared.model.Restaurant;
import java.util.List;

public interface RestaurantService {

  Restaurant createOrUpdate(Restaurant.DTO restaurantDto);

  List<MenuItem> getMenuItems(Long id);

  List<Restaurant> getAll(String query);

  Restaurant get(Long id);

  Restaurant delete(Long id);

  List<MenuItem> createOrUpdateMenu(Long id, List<DTO> menuItemDtos);
}
