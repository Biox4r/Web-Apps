/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSP;

import DbControllers.AddressController;
import DbControllers.ContactController;
import DbControllers.CountryController;
import DbControllers.CrozUserController;
import DbControllers.GenderController;
import DbModels.Address;
import DbModels.Contact;
import DbModels.Country;
import DbModels.Gender;
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
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Deamon
 */
@WebServlet(name = "JSPContact", urlPatterns = {"/JSPContact"})
public class JSPContact extends HttpServlet {

    private ContactController contactCon;
    private List listOfCountries;
    private Contact contact;
    private Address address;
    private Gender gender;
    private String status;
    private String tempid;
    private String objCountry;
    private String tempName;
    private String tempFirstName;
    private String tempLastName;
    private String tempPhone;
    private String tempEmail;
    private String tempStreet;
    private String tempStreetNo;
    private String tempAddressId2;
    private String tempContactAddressId;
    private String tempNewCityId;
    private String tempOldCityId;
    private Long tempAddressId;
    private String tempNewGenderId;
    private String tempOldGenderId;
    private String action;
    private JsonArray contactArrayJson = null;
    private JsonObject contactJson = null;
    private JsonObject contactAddressJson = null;
    private JsonObject contactGenderJson = null;

    JsonObject rootObj = null;
    JsonParser parser = null;

    private ContactController conCon;
    private AddressController addCon;
    private GenderController genCon;
    private List listOfContacts;

    private List<Object> contactDetails;

    public JSPContact() {
        conCon = new ContactController();
    }

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
            out.println("<title>Servlet JSP2</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JSP2 at " + request.getContextPath() + "</h1>");
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
//parser for newly recived data
            JsonParser parser = new JsonParser();
//getting the main JSON objcet
            rootObj = parser.parse(sb.toString()).getAsJsonObject();
//getting the type of action from http call 
            action = rootObj.get("action").getAsString();

            try {
                tempid = rootObj.get("id").getAsString();
            } catch (Exception e) {
                tempid = null;
            }
//action to get all contacts from database
            if (action.equals("allContacts")) {
                try {
                    contactCon = new ContactController();
                    listOfContacts = new ArrayList<>();
                    listOfContacts = contactCon.getAllContacts();

                    Gson gson = new Gson();
                    String json = gson.toJson(listOfContacts);
                    //listOfContacts = (List) new JSONObject();
                    PrintWriter out = response.getWriter();

                    out.print(json);

                } catch (Exception e) {
                    System.out.println("Not able to retrive all contacts");
                }
            }
//Action that pulls out contact details from database
            if (action.equals("getContact")) {
                try {
                    contactCon = new ContactController();
                    contactDetails = new ArrayList<>();
                    contactDetails = contactCon.getContactDetails(tempid);

                    Gson gson = new Gson();
                    String json = gson.toJson(contactDetails);
                    //listOfContacts = (List) new JSONObject();
                    PrintWriter out = response.getWriter();

                    out.print(json);

                } catch (Exception e) {
                    System.out.println("Not able to retrive all contacts");
                }
            }
//checking the action for contact insertion.
            if (action.equals("insertContact")) {
//Setting the data from recived JSON object.
                try {
                    tempFirstName = rootObj.get("first_name").getAsString();
                    tempLastName = rootObj.get("last_name").getAsString();
                    tempPhone = rootObj.get("phone").getAsString();
                    tempEmail = rootObj.get("email").getAsString();
                    tempStreet = rootObj.get("street").getAsString();
                    tempStreetNo = rootObj.get("street_no").getAsString();

                } catch (Exception e) {
                    System.out.println("Not able to get details from JSON in JSPContact");
                }

                try {
                    tempNewCityId = rootObj.get("city_id").getAsString();
                } catch (Exception e) {
                    tempNewCityId = null;
                }

                try {
                    tempNewGenderId = rootObj.get("gender_id").getAsString();
                } catch (Exception e) {
                    tempNewGenderId = null;
                }

                //Checking if user have filled some of the address parameters.
                //If street, street number or city are choosen for contact details, the provided logic below will insert that data in the database and it will create new address details.
                if (tempStreet != null | tempStreetNo != null | tempNewCityId != null) {
                    try {
                        address = new Address();
                        addCon = new AddressController();
                        address.setStreet(tempStreet);
                        address.setStreet_no(tempStreetNo);
                        address.setCity_id(Long.valueOf(tempNewCityId));
                        //the method (insertAddress) for address insertion will return its newly generated primary key which we will use for the insertion in the table contact (address_id)
                        //tempAddressId is temporary address_id
                        tempAddressId = addCon.insertAddress(address);
                    } catch (Exception e) {
                        System.out.println("Problem with address insert in JSPContact");
                    }
                }

//insertion of the contact
                try {
                    contact = new Contact();
                    contact.setFirst_name(tempFirstName);
                    contact.setLast_name(tempLastName);
                    contact.setEmail(tempEmail);
                    contact.setPhone(tempPhone);
                    contact.setAdress_id(tempAddressId);
                    contact.setSex_id(Long.valueOf(tempNewGenderId));
//if we recive the true statement 
                    boolean statement = conCon.insertContact(contact);

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
//Checking the action for contact update
            if (action.equals("updateContact")) {

//The nested JSON array is composed of 5 JSON objects and we are pulling out the data only from some of them
                contactArrayJson = new JsonArray();
                contactArrayJson = rootObj.getAsJsonArray("contact");
                contactJson = new JsonObject();
//Contact details
                contactJson = contactArrayJson.get(0).getAsJsonObject();
                tempid = contactJson.get("id").toString();
                tempFirstName = contactJson.get("first_name").getAsString();
                tempLastName = contactJson.get("last_name").getAsString();
                tempPhone = contactJson.get("phone").getAsString();
                tempEmail = contactJson.get("email").getAsString();
//Address details
                contactAddressJson = new JsonObject();
                contactAddressJson = contactArrayJson.get(1).getAsJsonObject();
                tempAddressId2 = contactAddressJson.get("id").getAsString();
                tempStreet = contactAddressJson.get("street").getAsString();
                tempStreetNo = contactAddressJson.get("street_no").getAsString();
                tempOldCityId = contactAddressJson.get("city_id").getAsString();
//Gender details
                contactGenderJson = new JsonObject();
                contactGenderJson = contactArrayJson.get(4).getAsJsonObject();
                tempOldGenderId = contactGenderJson.get("id").getAsString();
//Checking if user have entered new city ID
                try {
                    tempNewCityId = rootObj.get("city_id").getAsString();
                } catch (Exception e) {
                    tempNewCityId = null;
                }
//Checking if user have entered new Sex ID            
                try {
                    tempNewGenderId = rootObj.get("gender_id").getAsString();
                } catch (Exception e) {
                    tempNewGenderId = null;
                }

                {

                    //Based on the enterd data we are updateing address table/details 
                    try {
                        address = new Address();
                        addCon = new AddressController();
                        address.setId(Long.valueOf(tempAddressId2));
                        address.setStreet(tempStreet);
                        address.setStreet_no(tempStreetNo);
//checking if we have new City ID
//If there is a new one we will set it as new city id, otherwise we will use previous city id 
                        if (tempNewCityId != null) {
                            address.setCity_id(Long.valueOf(tempNewCityId));
                        } else if (tempNewCityId == null) {
                            address.setCity_id(Long.valueOf(tempOldCityId));
                        }
//this method will trigger the update process on address table
                        addCon.updateAddress(address);
                    } catch (Exception e) {
                        System.out.println("There was a problem with addres insertion in JSPContact on Contact update");
                    }

                }
//after successful update of address we will update the contact table
                try {
                    boolean statement;
                    contact = new Contact();
                    contact.setId(Long.valueOf(tempid));
                    contact.setFirst_name(tempFirstName);
                    contact.setLast_name(tempLastName);
                    contact.setEmail(tempEmail);
                    contact.setPhone(tempPhone);
                    contact.setAdress_id(Long.valueOf(tempAddressId2));
//checking if we have new Sex ID
//If there is a new one we will set it as new sex id, otherwise we will use previous sex id 
                    if (tempNewGenderId != null) {
                        contact.setSex_id(Long.valueOf(tempNewGenderId));
                    } else if (tempNewGenderId == null) {
                        contact.setSex_id(Long.valueOf(tempOldGenderId));
                    }
//The method below will update the contact and return to us true or false statement
                    statement = conCon.updateContact(contact);

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
//Cheking the action for contact delete
            if (action.equals("deleteContact")) {
//Fetching the values from JSON objects
                try {
                    contactArrayJson = new JsonArray();
                    contactArrayJson = rootObj.getAsJsonArray("contact");
                    contactJson = new JsonObject();
                    contactJson = contactArrayJson.get(0).getAsJsonObject();
                    tempid = contactJson.get("id").toString();

                    contactAddressJson = new JsonObject();
                    contactAddressJson = contactArrayJson.get(1).getAsJsonObject();
                    tempAddressId2 = contactAddressJson.get("id").getAsString();

                } catch (Exception e) {
                    contactArrayJson = null;
                    contactJson = null;
                }
//Triggering the delete process of contact
                try {
                    boolean statement;
                    contact = new Contact();
                    contact.setId(Long.valueOf(tempid));;
//The method below will delete the contact and return us true or false statement
//We are deleteing contact first, otherwise we cant delete the address (we would get constraint violation)
                    statement = conCon.deleteContact(contact);

                    address = new Address();
                    addCon = new AddressController();
                    address.setId(Long.valueOf(tempAddressId2));
                    addCon.deleteAddress(address);

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
