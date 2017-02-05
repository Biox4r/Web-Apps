/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbControllers;

import DbConnection.ConnectionSingl;
import DbModels.City;
import DbModels.Country;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;

/**
 *
 * @author Deamon
 */
public class CountryController extends ConnectionSingl {

    public CountryController() {
    }

    private Long tempId;

    public List<Country> getAllCountries() {

        List<Country> listOfCountries = new ArrayList<>();
        String query = " Select * from Country";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            rs = (OracleResultSet) prSt.executeQuery();

            while (rs.next()) {
                Country country = new Country();
                country.setId(rs.getLong("id"));
                country.setName(rs.getString("name"));
                country.setAlpha_2(rs.getString("alpha_2"));
                country.setAlpha_3(rs.getString("alpha_3"));
                listOfCountries.add(country);
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfCountries;
    }

    public Country searchCountry(Long id) {
        Country country = new Country();

        String query = " Select * from COUNTRY where id = '" + id + "'";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            rs = (OracleResultSet) prSt.executeQuery();
            while (rs.next()) {
                country.setId(rs.getLong("id"));
                country.setName(rs.getString("name"));
                country.setAlpha_2(rs.getString("alpha_2"));
                country.setAlpha_3(rs.getString("alpha_3"));

            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the execution of search method");
            e.printStackTrace();

        }

        return country;
    }

    public boolean insertCountry(Country country) {

        try {
            String query = "Insert into Country (name, alpha_2,alpha_3) values (?,?,?)";

            prSt = (OraclePreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getValuesOfCountry(country);

            prSt.executeQuery();
            rs = (OracleResultSet) prSt.getGeneratedKeys();
            rs.next();
            prSt.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateCountry(Country country, Long id) {
        String query = "update COUNTRY set name=?,alpha_2=?,alpha_3=? where ID= '" + id + "'";

        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            getValuesOfCountry(country);
            rs = (OracleResultSet) prSt.executeQuery();

            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean checkForCountryId(Long id) {
        City city = new City();
        boolean countryIdExistance = false;
        String checkQuery = "select country_id from city where country_id='" + id + "'";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(checkQuery);
            //prSt.setLong(1, city.getCountry_id());
            rs = (OracleResultSet) prSt.executeQuery();
            while (rs.next()) {
                countryIdExistance = true;
                break;
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryIdExistance;
    }

    public boolean deleteCountry(Long id) {

        String query = "delete Country where ID='" + id + "'";
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(query);
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

    private void getValuesOfCountry(Country country) throws SQLException {

        prSt.setString(1, country.getName());
        prSt.setString(2, country.getAlpha_2());
        prSt.setString(3, country.getAlpha_3());

    }

}
