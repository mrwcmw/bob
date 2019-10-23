package gov.moonbase.resources.iface;

import gov.moonbase.model.Patch;
import gov.moonbase.model.User;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.HttpURLConnection;

public interface BobResourceTemplate {

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_HTML)
    @ApiOperation(value="create",notes="This is the create operation on Bob", response=String.class)
    @ApiResponses({
            @ApiResponse(
                    code= HttpURLConnection.HTTP_OK,
                    message="Indeterminate creation of user",
                    response=String.class),
            @ApiResponse(
                    code= HttpURLConnection.HTTP_CREATED,
                    message="Successful creation of user",
                    response=String.class),
            @ApiResponse(
                    code= HttpURLConnection.HTTP_BAD_REQUEST,
                    message="Bad Request",
                    response=String.class),
            @ApiResponse(
                    code= HttpURLConnection.HTTP_INTERNAL_ERROR,
                    message="Error Adding User - Internal Server Error",
                    response=String.class)
    })
    public Response createUser (
            @Context HttpHeaders headers,
            @Context UriInfo uriInfo,
            @ApiParam(value="JSON representing a User", required=true) User user,
            @HeaderParam("USER_DN") String actorDn
    ) throws Exception;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @ApiResponses({
            @ApiResponse(
                    code= HttpURLConnection.HTTP_OK,
                    message="Successful retrieval of user",
                    response=String.class),
            @ApiResponse(
                    code= HttpURLConnection.HTTP_CREATED,
                    message="User did not exist.  Successful creation of user",
                    response=String.class),
            @ApiResponse(
                    code= HttpURLConnection.HTTP_NOT_FOUND,
                    message="Error Retrieving User - Not Found",
                    response=String.class),
            @ApiResponse(
                    code= HttpURLConnection.HTTP_INTERNAL_ERROR,
                    message="Error Retrieving User - Internal Server Error",
                    response=String.class)
    })
    public Response getUser (
            @Context HttpHeaders headers,
            @Context UriInfo uriInfo,
            @PathParam("id") String id,
            @HeaderParam("USER_DN") String actorDn
    ) throws Exception;

    @PATCH
    @Consumes({"application/json"})
    @Produces(MediaType.TEXT_HTML)
    @ApiOperation(
            value = "Update Discrete User Values",
            notes = "Update Discrete User Values.  Recognized operations are",
            response = String.class
    )
    @ApiResponses({
            @ApiResponse(
                    code= HttpURLConnection.HTTP_NO_CONTENT,
                    message="Successful Processing of all User Updates",
                    response=String.class),
            @ApiResponse(
                    code= HttpURLConnection.HTTP_BAD_REQUEST,
                    message="Possibly because of invalid JSON syntax",
                    response=String.class),
            @ApiResponse(
                    code= HttpURLConnection.HTTP_INTERNAL_ERROR,
                    message="Error Updating User - Internal Server Error",
                    response=String.class)
    })
    public Response updateUser(
            @Context HttpHeaders headers,
            @ApiParam(value = "Unique Identifier for User", required = true) @PathParam("id") String id,
            @HeaderParam("X-UserDN") String actorDn,
            @ApiParam(value = "List of Changes", required = true) Patch patch
    ) throws Exception;
}
