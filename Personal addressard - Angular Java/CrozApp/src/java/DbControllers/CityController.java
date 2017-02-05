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
public class CityController extends ConnectionSingl {

    public CityController() {
    }

    private Long tempId;

    public List<City> getAllCities() {
       
        List<City> listOfCities = new ArrayList<>();
        String query = " Select * from City";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            rs = (OracleResultSet) prSt.executeQuery();
            
            while (rs.next()) {
                City city = new City();
                city.setId(rs.getLong("id"));
                city.setName(rs.getString("name"));
                city.setZip_code(rs.getString("zip_code"));
                city.setCountry_id(rs.getLong("country_id"));
               
                listOfCities.add(city);
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfCities;
    }
    
    
    public City searchCity(String id) {
 
        City city = new City();
        String query = " Select * from CITY where id = '" + id + "'";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            rs = (OracleResultSet) prSt.executeQuery();
            while (rs.next()) {
                city.setName(rs.getString("name"));
                city.setZip_code(rs.getString("zip_code"));
                city.setId(rs.getLong("id"));
                city.setCountry_id(rs.getLong("country_id"));
            }
     
            rs.close();
            prSt.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the execution of search method");
            e.printStackTrace();

        }

        return city;
    }

    public boolean insertCity(City city) {

        try {
            String query = "Insert into CITY (name, zip_code, country_id) values (?,?,?)";

            prSt = (OraclePreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getValuesOfCity(city);

            prSt.executeQuery();
            rs = (OracleResultSet) prSt.getGeneratedKeys();
            rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateCity(City city,Long id) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement("update City set name=?,zip_code=? ,country_id=? where ID='" + id + "'");
                getValuesOfCity(city);
                prSt.executeQuery();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteCity(Long id) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(
                        "delete City where ID='" + id + "'");
                prSt.executeQuery();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    public boolean checkForCityId(Long id) {
        City city = new City();
        boolean cityIdExistance = false;
        String checkQuery = "select city_id from address where city_id='" + id + "'";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(checkQuery);
            //prSt.setLong(1, city.getCountry_id());
            rs = (OracleResultSet) prSt.executeQuery();
            while (rs.next()) {
                cityIdExistance = true;
                break;
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityIdExistance;
    }

    private void getValuesOfCity(City city) throws SQLException {
        prSt.setString(1, city.getName());
        prSt.setString(2, city.getZip_code());
        prSt.setLong(3, city.getCountry_id());


    }

}
