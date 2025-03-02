package com.github.food2gether.restaurantservice.repository;

import com.github.food2gether.shared.model.MenuItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.List;

public interface MenuItemRepository extends PanacheRepository<MenuItem> {

  List<MenuItem> findMenuItemsByRestaurantId(Long id);

}
