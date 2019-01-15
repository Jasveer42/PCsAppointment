/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * REST Web Service
 *
 * @author jasve
 */
@Path("Application")
public class GenericResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
    }

    /**
     * Retrieves representation of an instance of pack.GenericResource
     * @return an instance of java.lang.String
     */
   @GET
    @Path("Registration/{UserId}&{email}&{firstName}&{lastName}&{password}&{UserType}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getXml(@PathParam("UserId") String UID,
            @PathParam("email") String emailID,
            @PathParam("firstName") String fname, @PathParam("lastName") String lname, @PathParam("password") String pass,
            @PathParam("UserType") String UserTyp) throws SQLException {
        JSONObject mainObject = new JSONObject();

        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        mainObject.accumulate("TimeStamp", timeStampSeconds);
        try {
            Connection con = GetConnection.getConn();
            String sql = "INSERT INTO Users VALUES(?,?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, UID);
            stm.setString(2, emailID);
            stm.setString(3, fname);
            stm.setString(4, lname);
            stm.setString(5, pass);
            stm.setString(6, UserTyp);
           

            int value = stm.executeUpdate();
            if (value >= 1) {
                mainObject.accumulate("Status", "ok");
            } else {
                mainObject.accumulate("Status", "Error");
            }

            stm.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            mainObject.accumulate("Status", "Error");
        }

        return mainObject.toString();
    }

    @GET
    @Path("AddRequest/{RequestId}&{StartTime}&{EndTime}&{Date}&{UserID}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getXml1(@PathParam("UserId") String UID,
            @PathParam("StartTime") String StartT,
            @PathParam("EndTime") String EndT, @PathParam("Date") String dateOfApt, @PathParam("RequestId") String RID) 
            throws SQLException {
        JSONObject mainObject = new JSONObject();

        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        mainObject.accumulate("TimeStamp", timeStampSeconds);
        try {
            Connection con = GetConnection.getConn();
            String sql = "INSERT INTO Users VALUES(?,?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, UID);
            stm.setString(2, StartT);
            stm.setString(3, EndT);
            stm.setString(4, dateOfApt);
            stm.setString(5, RID);
           
           

            int value = stm.executeUpdate();
            if (value >= 1) {
                mainObject.accumulate("Status", "ok");
            } else {
                mainObject.accumulate("Status", "Error");
            }

            stm.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            mainObject.accumulate("Status", "Error");
        }

        return mainObject.toString();
    }
    @GET
    @Path("Login/{UserId}&{Passd}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getXm2(@PathParam("UserId") String UserID, @PathParam("Passd") String Pass) {
        JSONObject mainObject = new JSONObject();

        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        mainObject.accumulate("TimeStamp", timeStampSeconds);
        try {
            Connection con = GetConnection.getConn();
            String sql = "SELECT UserId,Password FROM Users WHERE UserId=? AND Password=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, UserID);
            stm.setString(2, Pass);

            ResultSet rs = stm.executeQuery();
            String status = "Error", message = "Crendtial are wrong";
            while (rs.next()) {
                status = "Ok";
                mainObject.accumulate("UserID", rs.getString("UserId"));
              //  mainObject.accumulate("FirstName", rs.getString("First_Name"));
                message = "Login Success";
            }
            mainObject.accumulate("Status", status);
            mainObject.accumulate("Message", message);

            rs.close();
            stm.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            mainObject.accumulate("Status", "Error");
        }

        return mainObject.toString();
    }
    @GET
    @Path("UserProfile/{Id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getXm3(@PathParam("Id") String UserID) {
        JSONObject mainObject = new JSONObject();

        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        mainObject.accumulate("TimeStamp", timeStampSeconds);
        try {
            Connection con = GetConnection.getConn();

            String sql = "SELECT FirstName,LastName,EmailId, UserID FROM USERS Where UserId=?";
            PreparedStatement stm = con.prepareStatement(sql);
            stm.setString(1, UserID);

            ResultSet rs = stm.executeQuery();

            JSONObject singleUser = new JSONObject();
            while (rs.next()) {

                singleUser.accumulate("fname", rs.getString("FirstName"));
                singleUser.accumulate("lname", rs.getString("LastName"));
                singleUser.accumulate("email", rs.getString("EmailId"));
                singleUser.accumulate("UserId", rs.getString("UserId"));
                
            }

            mainObject.accumulate("Status", "Ok");
            mainObject.accumulate("Detail", singleUser);

            rs.close();
            stm.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            mainObject.accumulate("Status", "Error");
        }

        return mainObject.toString();
    }
    @GET
    @Path("ViewRequest/")
    @Produces(MediaType.APPLICATION_JSON)
    public String getHomeAds() {
        JSONObject mainObject = new JSONObject();

        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        mainObject.accumulate("TimeStamp", timeStampSeconds);
        try {
            Connection con = GetConnection.getConn();
            Statement stm = con.createStatement();

            String sql = "SELECT RequestId, StartTime,EndTime,Description,DateForAppointment FROM Requests";

            ResultSet rs = stm.executeQuery(sql);

            JSONArray mainJSON = new JSONArray();
            while (rs.next()) {
                JSONObject singleUser = new JSONObject();
                singleUser.accumulate("RequestId", rs.getString("RequestId"));
                singleUser.accumulate("StartTime", rs.getString("StartTime"));
                singleUser.accumulate("EndTime", rs.getString("EndTime"));
                singleUser.accumulate("Description", rs.getString("Description"));
                singleUser.accumulate("DateForAppointment", rs.getString("DateForAppointment"));
               

                mainJSON.add(singleUser);
            }
            mainObject.accumulate("Status", "Ok");
            mainObject.accumulate("Detail", mainJSON);
            mainJSON.clear();

            rs.close();
            stm.close();
            con.close();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(GenericResource.class.getName()).log(Level.SEVERE, null, ex);
            mainObject.accumulate("Status", "Error");
        }

        return mainObject.toString();
    }


}
