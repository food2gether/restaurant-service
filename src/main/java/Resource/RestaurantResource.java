package Resource;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/restaurantes/")
public class RestaurantResource {

    @PUT
    @Path("/{id}/{displayName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response hello(@PathParam("id") int id, @PathParam("displayName") String displayName){
        if (id < 0 || displayName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        String dummyResponseBody= """
                {
                  "success": true,
                  "data": {
                    "id": 1731155346
                  }
                }""";

        return Response.ok(dummyResponseBody).build();
    }




}
