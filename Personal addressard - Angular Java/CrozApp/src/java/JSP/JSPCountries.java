/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSP;

import DbControllers.ContactController;
import DbControllers.CountryController;
import DbModels.Country;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Deamon
 */
@WebServlet(name = "JSPCountries", urlPatterns = {"/JSPCountries"})
public class JSPCountries extends HttpServlet {

    private CountryController counCon;
    private List listOfCountries;
    private List countryList;
    private Country coun;
    private String status;

    private String tempid;
    private String objCountry;
    private String tempName;
    private String tempAlpha2;
    private String tempAlpha3;
    private String action;
    private JsonArray countryArrayJson = null;
    private JsonObject countryJson = null;
    JsonObject rootObj = null;
    JsonParser parser = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            StringBuffer sb = new StringBuffer();

            try {

                BufferedReader reader = request.getReader();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            JsonParser parser = new JsonParser();

            rootObj = parser.parse(sb.toString()).getAsJsonObject();

            action = rootObj.get("action").getAsString();

            try {
                tempid = rootObj.get("id").getAsString();
            } catch (Exception e) {
                tempid = null;
            }

            if (action.equals("allCountries")) {
                try {
                    counCon = new CountryController();
                    listOfCountries = new ArrayList<>();
                    listOfCountries = counCon.getAllCountries();

                    Gson gson = new Gson();
                    String json = gson.toJson(listOfCountries);
                    //listOfContacts = (List) new JSONObject();
                    PrintWriter out = response.getWriter();

                    out.print(json);

                } catch (Exception e) {
                    System.out.println("Not able to retrive all contacts");
                }
            }

            if (action.equals("getCountry")) {
                try {
                    counCon = new CountryController();
                    coun = new Country();
                    coun = counCon.searchCountry(Long.valueOf(tempid));
                    listOfCountries = new ArrayList<>();
                    listOfCountries.add(coun);
                    Gson gson = new Gson();
                    String json = gson.toJson(listOfCountries);
                    //listOfContacts = (List) new JSONObject();
                    PrintWriter out = response.getWriter();

                    out.print(json);

                } catch (Exception e) {
                    System.out.println("Not able to retrive all contacts");
                }
            }

            if (action.equals("insertCountry")) {

                try {
                    tempName = rootObj.get("name").getAsString();
                    tempAlpha2 = rootObj.get("alpha_2").getAsString();
                    tempAlpha3 = rootObj.get("alpha_3").getAsString();

                } catch (Exception e) {                  
                    rootObj = null;
                }

                try {
                    boolean statement;
                    counCon = new CountryController();
                    coun = new Country();                 
                    coun.setName(tempName);
                    coun.setAlpha_2(tempAlpha2);
                    coun.setAlpha_3(tempAlpha3);

                    statement = counCon.insertCountry(coun);

                    if (statement == true) {
                        status = String.valueOf(1);
                    } else if (statement == false) {
                        status = String.valueOf(2);
                    }

                    JsonObject JsonStat = new JsonObject();
                    JsonStat.addProperty("status", status);
                    PrintWriter out = response.getWriter();
                    out.print(JsonStat);

                } catch (Exception e) {
                    System.out.println("Not able to insert contact");
                }
            }

            if (action.equals("updateCountry")) {

                try {
                    countryArrayJson = new JsonArray();
                    countryArrayJson = rootObj.getAsJsonArray("country");
                    countryJson = new JsonObject();
                    countryJson = countryArrayJson.get(0).getAsJsonObject();
                    tempid = countryJson.get("id").toString();
                    tempName = countryJson.get("name").getAsString();
                    tempAlpha2 = countryJson.get("alpha_2").getAsString();
                    tempAlpha3 = countryJson.get("alpha_3").getAsString();

                } catch (Exception e) {
                    countryArrayJson = null;
                    countryJson = null;
                }

                try {
                    boolean statement;
                    counCon = new CountryController();
                    coun = new Country();
                    Long tempIdLong = Long.parseLong(tempid);
                    coun.setName(tempName);
                    coun.setAlpha_2(tempAlpha2);
                    coun.setAlpha_3(tempAlpha3);

                    statement = counCon.updateCountry(coun, tempIdLong);

                    if (statement == true) {
                        status = String.valueOf(1);
                    } else if (statement == false) {
                        status = String.valueOf(2);
                    }

                    JsonObject JsonStat = new JsonObject();
                    JsonStat.addProperty("status", status);
                    PrintWriter out = response.getWriter();
                    out.print(JsonStat);

                } catch (Exception e) {
                    System.out.println("Not able to update contact");
                }
            }

            if (action.equals("deleteCountry")) {

                try {
                    countryArrayJson = new JsonArray();
                    countryArrayJson = rootObj.getAsJsonArray("country");
                    countryJson = new JsonObject();
                    countryJson = countryArrayJson.get(0).getAsJsonObject();
                    tempid = countryJson.get("id").toString();

                } catch (Exception e) {
                    countryArrayJson = null;
                    countryJson = null;
                }

                try {
                
                   
                        boolean statement;
                        Long tempIdLong = Long.parseLong(tempid);
                        counCon = new CountryController();
                        coun = new Country();

                        statement = counCon.deleteCountry(tempIdLong);

                        if (statement == true) {
                            status = String.valueOf(1);
                        } else if (statement == false) {
                            status = String.valueOf(2);
                        }

                        JsonObject JsonStat = new JsonObject();
                        JsonStat.addProperty("status", status);
                        PrintWriter out = response.getWriter();
                        out.print(JsonStat);
                 

                } catch (Exception e) {

                }

            }

            //
            //out.write("A new user " + user + " has been created.");
            out.flush();
            out.close();

        } catch (Exception ex) {
            Logger.getLogger(JSPLogin.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
