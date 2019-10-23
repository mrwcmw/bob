package gov.moonbase.resources.concrete;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import gov.moonbase.model.Patch;
import gov.moonbase.model.User;
import gov.moonbase.resources.iface.BobResourceTemplate;
import gov.moonbase.services.concrete.BobService;
import io.swagger.annotations.*;

import io.swagger.jaxrs.PATCH;

import java.net.HttpURLConnection;
import java.sql.*;
import java.util.List;

@Api(value="Bob")
@ApiModel(value="Bob Integration Service", description="Welcome to the Bob Integration Service")
@Path(value="/service")
public class BobResource implements BobResourceTemplate {

    @Override
    @POST
    @Path("/user")
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
            @ApiParam (value="JSON representing a User", required=true) User user,
            @HeaderParam("USER_DN") String actorDn
    ) throws Exception {
        List<User> userList = BobService.getInstance().newUser(user, "");

        if (userList.size() > 0) {
            UriBuilder builder = uriInfo.getAbsolutePathBuilder();
            builder.path(user.getId());
            return Response.created(builder.build()).build();
        }

        return Response.ok("New User was not created").build();
    }

    @Override
    @GET
    @Path("/user/{id}")
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
    ) throws Exception {

        List<User> userList = BobService.getInstance().existingUsers(id);

        if (userList.size() < 1) {
            userList = BobService.getInstance().newUser(null, id);
        }
        else {
            return Response.ok( userList.get(0) ).build();
        }

        return Response.status( Response.Status.NOT_FOUND ).entity("User " + id + " could not be found or created.").build();
    }

    @Override
    @PATCH
    @Path("/user/{id}")
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
    ) throws Exception {
        if (patch == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("You must pass in a change").build();
        }
        else {
            if (patch.getOp().toLowerCase().equals("remove") || patch.getOp().toLowerCase().equals("replace")) {
                if (patch.getPath().toLowerCase().equals("id")) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("The dn/id field cannot be changed").build();
                }
            }
            else {
                return Response.status(Response.Status.BAD_REQUEST).entity("Unsupported Operation").build();
            }
        }
        int updates;
        try {
            updates = BobService.getInstance().updateUser(id, actorDn, patch);
        }
        catch (SQLException sqle) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Unknown database error").build();
        }

        return Response.ok(updates + " changes made to id: " + id).build();
    }

 }

