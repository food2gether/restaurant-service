package com.github.food2gether.restaurantservice.service;

import com.github.food2gether.restaurantservice.repository.MenuItemRepository;
import com.github.food2gether.restaurantservice.repository.RestaurantRepository;
import com.github.food2gether.shared.model.MenuItem;
import com.github.food2gether.shared.model.MenuItem.DTO;
import com.github.food2gether.shared.model.Restaurant;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class RestaurantServiceImpl implements RestaurantService {

  @Inject
  EntityManager entityManager;
  @Inject
  RestaurantRepository restaurantRepository;
  @Inject
  MenuItemRepository menuItemRepository;

  @Override
  public Restaurant createOrUpdate(Restaurant.DTO restaurantDto) {
    return restaurantDto.getId() == null ? this.create(restaurantDto) : this.update(restaurantDto);
  }

  @Override
  public Restaurant create(Restaurant.DTO restaurantDto) {
    if (restaurantDto.getId() != null) {
      throw new WebApplicationException("Restaurant id must be null for creating a new Restaurant",
          Status.BAD_REQUEST);
    }
    Restaurant restaurant = new Restaurant();
    restaurant.setDisplayName(restaurantDto.getDisplayName());
    restaurant.setAddress(restaurantDto.getAddress());
    restaurant.setMenu(List.of());

    this.restaurantRepository.persist(restaurant);
    return restaurant;
  }

  public Restaurant update(Restaurant.DTO restaurantDto) {
    if (restaurantDto.getId() == null) {
      throw new WebApplicationException("Restaurant id must not be null for updating a Restaurant",
          Status.BAD_REQUEST);
    }
    Optional<Restaurant> existingRestaurant = restaurantRepository.findByIdOptional(
        restaurantDto.getId());
    if (existingRestaurant.isPresent()) {
      Restaurant restaurant = existingRestaurant.get();
      if (restaurantDto.getDisplayName() != null) {
        restaurant.setDisplayName(restaurantDto.getDisplayName());
      }
      if (restaurantDto.getAddress() != null) {
        restaurant.setAddress(restaurantDto.getAddress());
      }

      this.restaurantRepository.persist(restaurant);
      return restaurant;
    } else {
      throw new NotFoundException("Restaurant not found");
    }
  }

  @Override
  public List<MenuItem> toMenuItems(List<DTO> menuItemDtos) {
    return List.of();
  }

  @Override
  public List<MenuItem> getMenuItems(Long id) {
    return menuItemRepository.list("restaurant.id", id);

  }


  @Override
  public List<Restaurant> getAll(String query) {
    if(query != null){
      return this.restaurantRepository.listAllForQuery(query);
    }
    return this.restaurantRepository.listAll();
  }

  @Override
  public Restaurant get(Long id) {
    return restaurantRepository.findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Restaurant not found"));
  }

  @Override
  public Restaurant delete(Long id) {
    Optional<Restaurant> restaurant = restaurantRepository.findByIdOptional(id);
    if (restaurant.isPresent()) {
      restaurantRepository.delete(restaurant.get());
    } else {
      throw new NotFoundException("Restaurant not found");
    }
    return restaurant.get();
  }

  @Override
  public List< MenuItem> createOrUpdateMenuItems(int restaurantId, List<DTO> menuItemDtos) {
    Restaurant restaurant = restaurantRepository.findByIdOptional((long) restaurantId)
        .orElseThrow(() -> new NotFoundException("Restaurant not found"));


    List<MenuItem> menuItems = new ArrayList<>();
    for (MenuItem.DTO menuItemDto : menuItemDtos) {
      Optional<MenuItem> existingMenuItem = menuItemRepository.findByIdOptional(menuItemDto.getId());
      MenuItem menuItem;

      if (existingMenuItem.isPresent()) {
        // Update existing menu item
        menuItem = existingMenuItem.get();

      } else {
        menuItem = new MenuItem();
        menuItem.setRestaurant(restaurant);

      }

      menuItem.setName(menuItemDto.getName());
      menuItem.setPrice(menuItemDto.getPrice());
      menuItem.setDescription(menuItemDto.getDescription());
      menuItemRepository.persist(menuItem);
      menuItems.add(menuItem);
    }
    return menuItems;
  }


}
