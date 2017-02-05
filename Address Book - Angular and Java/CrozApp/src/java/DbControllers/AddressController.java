/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbControllers;

import DbConnection.ConnectionSingl;
import DbModels.Address;
import DbModels.Contact;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Deamon
 */
public class AddressController extends ConnectionSingl {

    private Long tempId;
    private Contact contac;

    public Long insertAddress(Address adress) {
        String generatedColumns[] = {"ID"};
        try {
            String query = "Insert into Address (street, street_no,city_id) values (?,?,?)";

            prSt = (OraclePreparedStatement) connection.prepareStatement(query,generatedColumns);

            getValuesOfAddress(adress);

            prSt.executeQuery();
            rs = (OracleResultSet) prSt.getGeneratedKeys();
            if (rs.next()) {
                tempId = rs.getLong(1);
            }

            prSt.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return tempId;
    }

    public boolean updateAddress(Address address) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement("update Address set street=?,street_no=?,city_id=? where ID='" + address.getId() + "'");
                getValuesOfAddress(address);
                rs = (OracleResultSet) prSt.executeQuery();
            }
            prSt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteAddress(Address address) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(
                        "delete Address where ID='" + address.getId() + "'");
                rs = (OracleResultSet) prSt.executeQuery();
            }
            prSt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void getValuesOfAddress(Address address) throws SQLException {
        prSt.setString(1, address.getStreet());
        prSt.setString(2, address.getStreet_no());
        prSt.setLong(3, address.getCity_id());

    }

}
