package com.github.food2gether.restaurantservice.resource;

import com.github.food2gether.restaurantservice.service.RestaurantService;
import com.github.food2gether.shared.model.MenuItem;
import com.github.food2gether.shared.model.Restaurant;
import com.github.food2gether.shared.response.APIResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/v1/restaurants/")
public class RestaurantResource {

  @Inject
  RestaurantService service;

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateOrCreateRestaurant(Restaurant.DTO body) {
    if (body == null) {
      throw new WebApplicationException("Missing request body", Response.Status.BAD_REQUEST);
    }

    Restaurant restaurant = this.service.createOrUpdate(body);

    return APIResponse.response(
        body.getId() == null
            ? Response.Status.CREATED
            : Response.Status.OK,
        Restaurant.DTO.fromRestaurant(restaurant)
    );
  }

  @GET
  @Path("/")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAllRestaurants(@QueryParam("search") String searchQuery) {
    List<Restaurant> restaurants = this.service.getAll(searchQuery);
    return APIResponse.response(
        Response.Status.OK,
        restaurants.stream()
            .map(Restaurant.DTO::fromRestaurant)
            .toList()
    );
  }


  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getRestaurant(@PathParam("id") Long id) {
    return APIResponse.response(Response.Status.OK, this.service.get(id));
  }


  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response deleteRestaurant(@PathParam("id") Long id) {
    Restaurant restaurant = this.service.delete(id);
    return APIResponse.response(Response.Status.OK, Restaurant.DTO.fromRestaurant(restaurant));
  }

  @PUT
  @Path("/{id}/menu")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateMenu(@PathParam("id") Long id, List<MenuItem.DTO> entries) {
    if (entries == null) {
      throw new WebApplicationException("Missing request body", Response.Status.BAD_REQUEST);
    }

    List<MenuItem> menuItems = this.service.createOrUpdateMenu(id, entries);
    return APIResponse.response(
        Response.Status.OK,
        menuItems.stream()
            .map(MenuItem.DTO::fromMenuItem)
            .toList()
    );
  }

  @GET
  @Path("/{id}/menu")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getMenuFromRestaurant(@PathParam("id") Long id) {
    List<MenuItem> menuItems = this.service.getMenuItems(id);

    return APIResponse.response(
        Response.Status.OK,
        menuItems.stream()
            .map(MenuItem.DTO::fromMenuItem)
            .toList()
    );
  }

}
