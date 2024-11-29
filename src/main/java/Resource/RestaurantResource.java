package Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.Array;

@Path("/api/v1/restaurantes/")
public class RestaurantResource {

    @PUT
    @Path("/{id}/{displayName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateOrCreateRestaurant(@PathParam("id") int id, @PathParam("displayName") String displayName){
        if (id < 0 || displayName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else if (id == 0) {
            String dummyResponseBody= """
                    {
                      "success": true,
                      "data": {
                        "id": 1731155346
                      }
                    }""";

            return Response.ok(dummyResponseBody).build();
        } else if (id == 1) {
            String dummyResponseBody= """
                {
                  "success": true,
                  "data": {
                    "id": 1731155346
                  }
                }""";

            return Response.status(Response.Status.CREATED).build();
        } else if (id == 2) {
            String dummyResponseBody= """
                    {
                      "success": false,
                      "error": {
                        "code": 404,
                        "message_key": "request.missingarguments"
                      }
                    }""";

            return Response.status(Response.Status.BAD_REQUEST).build();

        }else{
            String dummyResponseBody= """
                    {
                      "success": false,
                      "error": {
                        "code": 401,
                        "message_key": "authorization.failed"
                      }
                    }""";

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }


    }
    @GET
    @Path("/{search}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello( @PathParam("search") String search) {

        String dummyResponseBody = """
                {
                  "success": true,
                  "data": [
                    {
                      "id": 1731155346,
                      "displayname": "Fat Baby",
                      "address": {
                        "street": "Musterstr.",
                        "number": 42,
                        "city": "Aachen",
                        "postalcode": 52072
                      }
                    },
                    ...
                  ]
                }""";

        return Response.status(Response.Status.FOUND).build();

    }
    @GET
    @Path("/{search}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAllRestaurants() {

        String dummyResponseBody = """
                {
                  "success": true,
                  "data": [
                    {
                      "id": 1731155346,
                      "displayname": "Fat Baby",
                      "address": {
                        "street": "Musterstr.",
                        "number": 42,
                        "city": "Aachen",
                        "postalcode": 52072
                      }
                    },
                    ...
                  ]
                }""";

        return Response.status(Response.Status.FOUND).build();

    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getRestaurant(@PathParam("id") int id) {
        if (id < 0) {
            String dummyResponseBody = """
                    {
                      "success": true,
                      "data": {
                        "id": 1731155346,
                        "displayname": "Fat Baby",
                        "address": {
                          "street": "Musterstr.",
                          "number": 42,
                          "city": "Aachen",
                          "postalcode": 52072
                        }
                      }
                    }""";

            return Response.status(Response.Status.FOUND).build();
        }else {
            String dummyResponseBody = """
                    {
                      "success": false,
                      "error": {
                        "code": 404,
                        "message_key": "restaurant.notfound"
                      }
                    }""";

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteRestaurant(@PathParam("id") int id) {
        if (id < 0) {
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

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @PUT
    @Path("/{id}/{entries}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateMenu(@PathParam("id") int id, @PathParam("entries") String entries) {
        if (id < 0) {
            String dummyResponseBody = """
                    {
                      "success": true,
                      "data": null
                    }
                           \s""";

            return Response.status(Response.Status.OK).build();
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

            return Response.status(Response.Status.UNAUTHORIZED).build();
        }else{
            String dummyResponseBody = """
                    {
                      "success": false,
                      "error": {
                        "code": 404,
                        "message_key": "menu.notfound"
                      }
                    }""";

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getMenuFromRestaurant(@PathParam("id") int id) {
        if (id < 0) {
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

            return Response.status(Response.Status.FOUND).build();
        }else {
            String dummyResponseBody = "{\n" +
                    "  \"success\": false,\n" +
                    "  \"error\": {\n" +
                    "    \"code\": 404,\n" +
                    "    \"message_key\": \"restaurant.notfound\"\n" +
                    "  }\n" +
                    "}";

            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
