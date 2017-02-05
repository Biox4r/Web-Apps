/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbControllers;

import DbConnection.ConnectionSingl;
import DbModels.Gender;
import java.sql.SQLException;
import java.sql.Statement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Deamon
 */
public class GenderController extends ConnectionSingl {

    private Long tempId;

    public Long insertGender(Gender gender) {

        try {
            String query = "Insert into Sex (name) values (?)";

            prSt = (OraclePreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getValuesOfGender(gender);

            prSt.executeQuery();
            rs = (OracleResultSet) prSt.getGeneratedKeys();
            if (rs.next()) {
                Long tempId = rs.getLong(1);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return tempId;
    }

    public boolean updateAdress(Gender gender) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement("update Gender set name=? where ID='" + gender.getId() + "'");
                getValuesOfGender(gender);
                prSt.executeQuery();
            }
            prSt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteAdress(Gender gender) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(
                        "delete Gender where ID='" + gender.getId() + "'");
                prSt.executeQuery();
            }
            prSt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void getValuesOfGender(Gender gender) throws SQLException {
        prSt.setString(1, gender.getName());

    }
}
