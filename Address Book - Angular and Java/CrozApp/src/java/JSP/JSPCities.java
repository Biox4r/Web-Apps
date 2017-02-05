/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSP;

import DbControllers.CityController;
import DbControllers.CountryController;
import DbModels.City;
import DbModels.Country;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Deamon
 */
@WebServlet(name = "JSPCities", urlPatterns = {"/JSPCities"})
public class JSPCities extends HttpServlet {

    private CityController citCon;
    private List listOfCities;
    private List detailsOfCities;
    private City city;

    private String status;

    private String tempid;
    private String objCountry;
    private String tempName;
    private String tempZip_code;
    private String tempCountry_id;
    private String newTempCountry_id;

    private String action;
    private JsonArray cityArrayJson = null;
    private JsonObject cityJson = null;
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
            out.println("<title>Servlet JSPCities</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JSPCities at " + request.getContextPath() + "</h1>");
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

            if (action.equals("allCities")) {
                try {
                    citCon = new CityController();
                    listOfCities = new ArrayList<>();
                    listOfCities = citCon.getAllCities();

                    Gson gson = new Gson();
                    String json = gson.toJson(listOfCities);
                    //listOfContacts = (List) new JSONObject();
                    PrintWriter out = response.getWriter();

                    out.print(json);

                } catch (Exception e) {
                    System.out.println("Not able to retrive all contacts");
                }
            }

            if (action.equals("getCity")) {
                try {
                    citCon = new CityController();
                    city = new City();
                    city = citCon.searchCity(tempid);
                    detailsOfCities = new ArrayList<>();
                    detailsOfCities.add(city);
                    Gson gson = new Gson();
                    String json = gson.toJson(detailsOfCities);
                    //listOfContacts = (List) new JSONObject();
                    PrintWriter out = response.getWriter();

                    out.print(json);

                } catch (Exception e) {
                    System.out.println("Not able to retrive all contacts");
                }
            }

            if (action.equals("insertCity")) {

                try {
                    tempName = rootObj.get("name").getAsString();
                    tempZip_code = rootObj.get("zip_code").getAsString();
                    tempCountry_id = rootObj.get("country_id").getAsString();

                } catch (Exception e) {
                    rootObj = null;
                }

                try {
                    boolean statement;
                    citCon = new CityController();
                    city = new City();
                    city.setName(tempName);
                    city.setZip_code(tempZip_code);
                    city.setCountry_id(Long.parseLong(tempCountry_id));

                    statement = citCon.insertCity(city);

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
            if (action.equals("updateCity")) {

                try {
                    cityArrayJson = new JsonArray();
                    cityArrayJson = rootObj.getAsJsonArray("city");
                    cityJson = new JsonObject();
                    cityJson = cityArrayJson.get(0).getAsJsonObject();
                    tempid = cityJson.get("id").toString();
                    tempName = cityJson.get("name").getAsString();
                    tempZip_code = cityJson.get("zip_code").getAsString();
                    tempCountry_id = cityJson.get("country_id").getAsString();
                    newTempCountry_id = action = rootObj.get("country_id").getAsString();

                } catch (Exception e) {
                    cityArrayJson = null;
                    cityJson = null;
                }

                try {
                    boolean statement;
                    citCon = new CityController();
                    city = new City();
                    Long tempIdLong = Long.parseLong(tempid);
                    city.setName(tempName);
                    city.setZip_code(tempZip_code);

                    if (newTempCountry_id != null) {
                        city.setCountry_id(Long.parseLong(newTempCountry_id));
                    } else {
                        city.setCountry_id(Long.parseLong(tempCountry_id));
                    }

                    statement = citCon.updateCity(city, tempIdLong);

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

            if (action.equals("deleteCity")) {

                try {
                    cityArrayJson = new JsonArray();
                    cityArrayJson = rootObj.getAsJsonArray("city");
                    cityJson = new JsonObject();
                    cityJson = cityArrayJson.get(0).getAsJsonObject();
                    tempid = cityJson.get("id").toString();

                } catch (Exception e) {
                    cityArrayJson = null;
                    cityJson = null;
                }

                try {
                    boolean idCheck;
                    boolean statement;
                    citCon = new CityController();
                    idCheck = citCon.checkForCityId(Long.valueOf(tempid));

                    if (idCheck == false) {
                        citCon = new CityController();
                        city = new City();

                        statement = citCon.deleteCity(Long.valueOf(tempid));

                        if (statement == true) {
                            status = String.valueOf(1);
                        } else if (statement == false) {
                            status = String.valueOf(2);
                        }
                    } else if (idCheck == true) {
                        status = String.valueOf(3);
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
