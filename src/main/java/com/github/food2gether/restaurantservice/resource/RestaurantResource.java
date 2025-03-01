package com.github.food2gether.restaurantservice.resource;

import com.github.food2gether.restaurantservice.service.RestaurantService;
import com.github.food2gether.shared.model.MenuItem;
import com.github.food2gether.shared.model.Restaurant;
import com.github.food2gether.shared.response.APIResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;

import java.util.List;

@Path("/api/v1/restaurantes/")
public class RestaurantResource {

  @Inject
  RestaurantService service;

  @PUT
  @Path("/{id}/{displayName}")
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
  public Response getAllRestaurants(@QueryParam("query") String query) {

    List<Restaurant> restaurants = this.service.getAll(query);
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

        /*if (id < 0) {
            String dummyResponseBody = """
                    {
                      "success": true,
                      "data": null
                    }""";

            return Response.ok(dummyResponseBody).build();
        }else {
            String dummyResponseBody = """
                    {
                      "success": false,
                      "error": {
                        "code": 404,
                        "message_key": "restaurant.notfound"
                      }
                    }""";

            return Response.status(Response.Status.NOT_FOUND).entity(dummyResponseBody).build();*/

  }

  @PUT
  @Path("/{id}/menu")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response updateMenu(@PathParam("id") int id, List<MenuItem.DTO> menuItemDtos) {
    if (menuItemDtos == null) {
      throw new WebApplicationException("Missing request body", Response.Status.BAD_REQUEST);
    }
    List<MenuItem> menuItems = this.service.createOrUpdateMenuItems(id, menuItemDtos);
    return APIResponse.response(
        Response.Status.OK,
        menuItems.stream()
            .map(MenuItem.DTO::fromMenuItem)
            .toList()
    );
        /*if (id < 0) {
            String dummyResponseBody = """
                    {
                      "success": true,
                      "data": null
                    }
                           \s""";

            return Response.status(Response.Status.OK).entity(dummyResponseBody).build();
        }else if (id == 0) {
            String dummyResponseBody = """
                    {
                      "success": false,
                      "error": {
                        "code": 401,
                        "message_key": "authorization.failed"
                      }
                    }
                          \s""";

            return Response.status(Response.Status.UNAUTHORIZED).entity(dummyResponseBody).build();
        }else{
            String dummyResponseBody = """
                    {
                      "success": false,
                      "error": {
                        "code": 404,
                        "message_key": "menu.notfound"
                      }
                    }""";

            return Response.status(Response.Status.NOT_FOUND).entity(dummyResponseBody).build();
        }*/
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
        /*if (id < 0) {
            String dummyResponseBody = """
                    {
                      "success": true,
                      "data": [
                        {
                          "id": "1",
                          "name": "Springrolles",
                          "description": "Asian fried vegetable rolls",
                          "price": 349,
                          "allergies": [
                            "VEGETARIAN",
                            "VEGAN",
                            "PEANUTS"
                          ]
                        },
                        ...
                      ]
                    }""";

            return Response.status(Response.Status.FOUND).entity(dummyResponseBody).build();

        }else {

            String dummyResponseBody = "{\n" +
                    "  \"success\": false,\n" +
                    "  \"error\": {\n" +
                    "    \"code\": 404,\n" +
                    "    \"message_key\": \"restaurant.notfound\"\n" +
                    "  }\n" +
                    "}";

            return Response.status(Response.Status.NOT_FOUND).entity(dummyResponseBody).build();
        }*/
  }

}
