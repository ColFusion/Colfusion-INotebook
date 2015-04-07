/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.sis.infsci2711.iNotebook.rest;

import edu.pitt.sis.infsci2711.iNotebbok.config.URLConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.pitt.sis.infsci2711.iNotebook.business.UserService;
import edu.pitt.sis.infsci2711.iNotebook.models.UserDBModel;
import edu.pitt.sis.infsci2711.iNotebook.utils.AddUser;
import edu.pitt.sis.infsci2711.iNotebook.viewModels.User;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.POST;
import javax.ws.rs.core.UriBuilder;

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
        try {
            AddUser.addUser(user.getUsername(),user.getPassword());
            return Response.status(200).entity("success").build();
        } catch (Exception e) {
            return Response.status(500).build();
        }

    }
    
    // user login
    @Path("login/")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(final User user){
        UserService userService = new UserService();
        
        try {
            UserDBModel usersDB = userService.checkUser(convertViewModelToDB(user));
            
            if(usersDB == null){
                return Response.status(200).entity("1").build();
            }else{
                //Notebook.RunNotebook(usersDB.getUserName());
                return Response.status(200).entity("http://localhost:8888/tree").build();
            }
        }catch(Exception e){
            System.out.println(e.toString());
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
