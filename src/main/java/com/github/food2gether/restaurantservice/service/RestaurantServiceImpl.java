package com.github.food2gether.restaurantservice.service;

import com.github.food2gether.restaurantservice.repository.MenuItemRepository;
import com.github.food2gether.restaurantservice.repository.RestaurantRepository;
import com.github.food2gether.shared.model.MenuItem;
import com.github.food2gether.shared.model.MenuItem.DTO;
import com.github.food2gether.shared.model.Restaurant;
import io.quarkus.runtime.util.StringUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import java.util.List;

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

  @Transactional
  public Restaurant create(Restaurant.DTO restaurantDto) {
    if (restaurantDto.getId() != null) {
      throw new WebApplicationException(
          "Restaurant id must be null for creating a new Restaurant",
          Status.BAD_REQUEST
      );
    }


    if (StringUtil.isNullOrEmpty(restaurantDto.getDisplayName())
        || restaurantDto.getAddress() == null
        || StringUtil.isNullOrEmpty(restaurantDto.getAddress().getCity())
        || StringUtil.isNullOrEmpty(restaurantDto.getAddress().getCountry())
        || StringUtil.isNullOrEmpty(restaurantDto.getAddress().getPostalCode())
        || StringUtil.isNullOrEmpty(restaurantDto.getAddress().getStreet())) {
      throw new WebApplicationException(
          "Restaurant display name and address must not be null",
          Status.BAD_REQUEST
      );
    }

    Restaurant restaurant = new Restaurant();
    restaurant.setDisplayName(restaurantDto.getDisplayName());
    restaurant.setAddress(restaurantDto.getAddress());
    restaurant.setMenu(List.of());

    this.restaurantRepository.persist(restaurant);
    return restaurant;
  }

  @Transactional
  public Restaurant update(Restaurant.DTO restaurantDto) {
    if (restaurantDto.getId() == null) {
      throw new WebApplicationException("Restaurant id must not be null for updating a Restaurant",
          Status.BAD_REQUEST);
    }

    Restaurant restaurant = this.restaurantRepository.findByIdOptional(restaurantDto.getId())
        .orElseThrow(() -> new NotFoundException("Restaurant not found"));

    if (!StringUtil.isNullOrEmpty(restaurantDto.getDisplayName())) {
      restaurant.setDisplayName(restaurantDto.getDisplayName());
    }

    if (restaurantDto.getAddress() != null) {
      // TODO more advanced checking
      restaurant.setAddress(restaurantDto.getAddress());
    }

    return restaurant;
  }

  @Override
  public List<MenuItem> getMenuItems(Long id) {
    if (this.restaurantRepository.findByIdOptional(id).isEmpty()) {
      throw new NotFoundException("Restaurant not found");
    }
    return this.menuItemRepository.findMenuItemsByRestaurantId(id);
  }

  @Override
  public List<Restaurant> getAll(String query) {
    return query != null
        ? this.restaurantRepository.listAllForQuery(query)
        : this.restaurantRepository.listAll();
  }

  @Override
  public Restaurant get(Long id) {
    return this.restaurantRepository.findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Restaurant not found"));
  }

  @Override
  @Transactional
  public Restaurant delete(Long id) {
    Restaurant restaurant = restaurantRepository.findByIdOptional(id)
        .orElseThrow(() -> new NotFoundException("Restaurant not found"));

    this.restaurantRepository.delete(restaurant);
    return restaurant;
  }

  @Override
  @Transactional
  public List<MenuItem> createOrUpdateMenu(Long restaurantId, List<DTO> menuItemDtos) {
    Restaurant restaurant = this.restaurantRepository.findByIdOptional(restaurantId)
        .orElseThrow(() -> new NotFoundException("Restaurant not found"));

    List<MenuItem> menu = menuItemDtos.stream()
        .map(itemDto ->
            itemDto.getId() == null
                ? this.createMenuItem(itemDto, restaurantId)
                : this.updateMenuItem(itemDto))
        .toList();

    List<MenuItem> currentMenu = restaurant.getMenu();
    currentMenu.clear();
    currentMenu.addAll(menu);

    this.restaurantRepository.persist(restaurant);
    return menu;
  }

  private MenuItem createMenuItem(MenuItem.DTO menuItemDto, Long restaurantId) {
    if (menuItemDto.getId() != null) {
      throw new WebApplicationException(
          "Menu item id must be null for creating a new menu item",
          Status.BAD_REQUEST
      );
    }

    if (StringUtil.isNullOrEmpty(menuItemDto.getName())
        || menuItemDto.getPrice() <= 0) {
      throw new WebApplicationException(
          "Menu item name must not be null and price must be greater than 0",
          Status.BAD_REQUEST
      );
    }

    MenuItem menuItem = new MenuItem();
    menuItem.setName(menuItemDto.getName());
    menuItem.setPrice(menuItemDto.getPrice());
    menuItem.setDescription(
        menuItemDto.getDescription() != null
            ? menuItemDto.getDescription()
            : "");

    menuItem.setRestaurant(this.entityManager.getReference(Restaurant.class, restaurantId));

    return menuItem;
  }

  private MenuItem updateMenuItem(MenuItem.DTO menuItemDto) {
    if (menuItemDto.getId() == null) {
      throw new WebApplicationException(
          "Menu item id must not be null for updating a menu item",
          Status.BAD_REQUEST
      );
    }

    MenuItem menuItem = this.menuItemRepository.findByIdOptional(menuItemDto.getId())
        .orElseThrow(() -> new NotFoundException("Menu item not found"));

    if (!StringUtil.isNullOrEmpty(menuItemDto.getName())) {
      menuItem.setName(menuItemDto.getName());
    }

    if (menuItemDto.getPrice() > 0) {
      menuItem.setPrice(menuItemDto.getPrice());
    }

    if (menuItemDto.getDescription() != null) {
      menuItem.setDescription(menuItemDto.getDescription());
    }

    return menuItem;
  }

}
