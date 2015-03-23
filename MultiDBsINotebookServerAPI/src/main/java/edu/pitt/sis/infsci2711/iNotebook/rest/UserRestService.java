/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.pitt.sis.infsci2711.iNotebook.business.UserService;
import edu.pitt.sis.infsci2711.iNotebook.models.UserDBModel;
import edu.pitt.sis.infsci2711.iNotebook.viewModels.User;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;

/**
 * User Restful API Service
 * @author Wu
 */
@Path("User/")
public class UserRestService {
    
    // check username validation
    @Path("checkUserName/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response checkUserNmae(final User user){
        try {
            return null;
        }catch(Exception e){
            return Response.status(500).build();
        }
    }
    
    // user register
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(final User user) {
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
        UserService userService = new UserService();
        try {
            UserDBModel usersDB = userService.add(convertViewModelToDB(user));

            User userInserted = convertDbToViewModelWithoutPwd(usersDB);
            
            return Response.status(200).entity(userInserted).build();
        } catch (Exception e) {
            return Response.status(500).build();
        }

    }
    
    // user login
    @Path("login/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final User user){
        UserService userService = new UserService();
        try {
            UserDBModel usersDB = userService.checkUser(convertViewModelToDB(user));
            
            User userLogin = convertDbToViewModel(usersDB);
            
            return Response.status(200).entity(userLogin).build();
            
        }catch(Exception e){
            return Response.status(500).build();
        }
    }
    
    // user logout
    @Path("logout/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(final User user){
        try {
            return null;
        }catch(Exception e){
            return Response.status(500).build();
        }
    }

    private UserDBModel convertViewModelToDB(final User user) {
        return new UserDBModel(user.getUsername(),user.getPassword(),user.getEmail());
    }

    private List<User> convertDbToViewModel(final List<UserDBModel> usersDB) {
        List<User> result = new ArrayList<User>();
        for (UserDBModel userDB : usersDB) {
            result.add(convertDbToViewModel(userDB));
        }

        return result;
    }

    private User convertDbToViewModel(final UserDBModel userDB) {
        return new User(userDB.getId(), userDB.getUserName(), userDB.getPassword(),userDB.getEmail());
    }
    
    private User convertDbToViewModelWithoutPwd(final UserDBModel userDB) {
        return new User(userDB.getId(), userDB.getUserName(), userDB.getEmail());
    }
}
