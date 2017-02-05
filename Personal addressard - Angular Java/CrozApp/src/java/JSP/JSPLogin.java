/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JSP;

import DbControllers.CrozUserController;
import DbModels.Crozuser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.Enumeration;
import java.util.UUID;
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
@WebServlet({"/JSPLogin"})
public class JSPLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private CrozUserController usrCon;
    private Crozuser user = null;
    private UUID tokenGen;
    private String token;
    private JSONObject jsonToken;

    public JSPLogin() {
        usrCon = new CrozUserController();

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

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

            JSONParser parser = new JSONParser();
            JSONObject joUser = null;

            try {
                joUser = (JSONObject) parser.parse(sb.toString());
                System.out.println("User" + joUser);
            } catch (ParseException e) {
                e.printStackTrace();
                token = null;
            }

            //
            String username = (String) joUser.get("username");
            System.out.println(username);
            String password = (String) joUser.get("password");
            System.out.println(password);
            user = new Crozuser();
            usrCon = new CrozUserController();
            user = usrCon.loginUser(username, password);

            if (user != null) {
                System.out.println("User= " + user.getUserName());
                token = tokenGen.randomUUID().toString();
                jsonToken = new JSONObject();
                jsonToken.put("token", token);
              
                PrintWriter out = response.getWriter();

                out.print(jsonToken);

            } else {
                token = null;

                jsonToken = new JSONObject();
                jsonToken.put("token", token);

                PrintWriter out = response.getWriter();
                //response.sendError(HttpServletResponse.SC_NOT_FOUND);

                response.setContentType("application/json");

                out.print(jsonToken);
                //response.getWriter().write(token);
            }

            //out.write("A new user " + user + " has been created.");
            out.flush();
            out.close();
        } catch (Exception ex) {
            Logger.getLogger(JSPLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

/**
 * Returns a short description of the servlet.
 *
 * @return a String containing servlet description
 */
