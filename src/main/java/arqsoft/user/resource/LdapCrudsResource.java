package arqsoft.user.resource;

import arqsoft.user.model.User;
import arqsoft.user.service.LdapService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;


@Path( "/ldapcruds" )
public class LdapCrudsResource{

    @Context
    UriInfo uriInfo;

    @POST
    public Response create( User user ){
       LdapService login = new LdapService();
       int code=login.creation(user.getEmail(), user.getPassword(),user.getFirstName(), user.getLastName());
       if (code == 200){
        return Response.status( Response.Status.CREATED ).build( );
      } else if (code == 400){
          return Response.status( Response.Status.BAD_REQUEST ).build( );
      }
        else if (code == 503){
          return Response.status( Response.Status.SERVICE_UNAVAILABLE ).build( );
      }
      else {
        return Response.status( Response.Status.INTERNAL_SERVER_ERROR ).build( );
      }
    }

    @DELETE
    @Path( "{id}" )
    public Response deleteUser( @PathParam( "id" ) String id ){
       LdapService login = new LdapService();
       if ( login.deleteUsers( id ) ){

        return Response.status( Response.Status.OK ).build( );

      }else{

        return Response.status( Response.Status.NO_CONTENT ).build( );

      }

    }


}
