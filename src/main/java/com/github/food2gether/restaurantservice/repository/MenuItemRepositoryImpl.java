package com.github.food2gether.restaurantservice.repository;

import com.github.food2gether.shared.model.MenuItem;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MenuItemRepositoryImpl implements MenuItemRepository {

  @Override
  public List<MenuItem> findMenuItemsByRestaurantId(Long id) {
    return this.list("restaurant.id", id);
  }

}
