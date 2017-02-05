/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbControllers;

import DbConnection.ConnectionSingl;
import Util.PassGen;
import DbModels.Crozuser;
import java.math.BigInteger;
import java.sql.SQLException;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Deamon
 */
public class CrozUserController extends ConnectionSingl {

    //prepareing new Employee
    private Crozuser usr = null;

    private PassGen passg;
    private String userName;
    //private TempID getId;

    public CrozUserController() {
        //calling for ConnectionWorkOut class and connection methods
        super();
        passg = new PassGen();
        //getId = new TempID();
    }

    public Crozuser loginUser(String username, String password) throws SQLException, Exception {
        String temp;
        //query for employee selection based on username and password
        try {
            String sql = "select * from CROZUSER where USERNAME= '" + username + "'";
            prSt = (OraclePreparedStatement) connection.prepareStatement(sql);
            //prSt.setString(1, username);
            // prSt.setString(2, password);
            //prSt.setString(2, password);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    usr = new Crozuser();
                    if (!passg.check(password, rs.getString("PASSWORD"))) {
                        System.out.println("Wrong username or password");
                        usr = null;
                    }

                    usr.setId(rs.getLong("ID"));
                    usr.setName(rs.getString("NAME"));
                    
                    usr.setPassword(rs.getString("PASSWORD"));
                }
            }

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usr;
    }

    public boolean chekUserName(String username) {
        boolean postoji = false;
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement("select ID from CROZUSER where username=?");
            prSt.setString(1, username);
            synchronized (this) {
                rs = (OracleResultSet) prSt.executeQuery();
                while (rs.next()) {
                    postoji = true;
                }

            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return postoji;
    }

}
