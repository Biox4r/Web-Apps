/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbControllers;

import DbConnection.ConnectionSingl;
import DbModels.Address;
import DbModels.City;
import DbModels.Contact;
import DbModels.Country;
import DbModels.Gender;
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
public class ContactController extends ConnectionSingl {

    private Long tempId;

    private ArrayList<Object> contactDetails;
    
    
    public ContactController() {
    }

    public List<Contact> getAllContacts() {

        List<Contact> listOfContacts = new ArrayList<>();
        String query = " Select * from CONTACT";
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query);
            rs = (OracleResultSet) prSt.executeQuery();

            while (rs.next()) {
                Contact contact = new Contact();
                contact.setId(rs.getLong("id"));
                contact.setFirst_name(rs.getString("first_name"));
                contact.setLast_name(rs.getString("last_name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setSex_id(rs.getLong("sex_id"));
                contact.setAdress_id(rs.getLong("adress_id"));
                listOfContacts.add(contact);
            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listOfContacts;
    }

    public List getContactDetails(String id) {

        String query1 = " Select a.*,b.*,b.id as id_1, c.*,c.id as id_2,d.*,d.id as id_3,d.name as name_1,e.id as id_4,e.name as name_2 from contact a left join address b on b.id = a.adress_id left join city c on b.city_id=c.id left join country d on c.country_id=d.id left join sex e on e.id=a.sex_id where a.id = '" +id + "'";
        
        try {
            prSt = (OraclePreparedStatement) connection.prepareStatement(query1);
            rs = (OracleResultSet) prSt.executeQuery();
            while (rs.next()) {
                contactDetails = new ArrayList<>();
                Contact contact = new Contact();
                City city = new City();
                Country country = new Country();
                Gender sex = new Gender();
                Address address = new Address();
                contact.setId(rs.getLong("id"));
                contact.setFirst_name(rs.getString("first_name"));
                contact.setLast_name(rs.getString("last_name"));
                contact.setPhone(rs.getString("phone"));
                contact.setEmail(rs.getString("email"));
                contact.setSex_id(rs.getLong("sex_id"));
                contact.setAdress_id(rs.getLong("adress_id"));
                contactDetails.add(contact);
                address.setId(rs.getLong("id_1"));
                address.setStreet(rs.getString("street"));
                address.setStreet_no(rs.getString("street_no"));
                address.setCity_id(rs.getLong("city_id"));
                contactDetails.add(address);
                city.setId(rs.getLong("id_2"));
                city.setName(rs.getString("name"));
                city.setZip_code(rs.getString("zip_code"));
                city.setCountry_id(rs.getLong("country_id"));
                contactDetails.add(city);
                country.setId(rs.getLong("id_3"));
                country.setName(rs.getString("name_1"));
                country.setAlpha_2(rs.getString("alpha_2"));
                country.setAlpha_3(rs.getString("alpha_3"));
                contactDetails.add(country);
                sex.setId(rs.getLong("id_4"));
                sex.setName(rs.getString("name_2"));
                contactDetails.add(sex);

            }
            rs.close();
            prSt.close();
        } catch (Exception e) {
            System.out.println("Something went wrong with the execution of search method");
            e.printStackTrace();

        }

        return contactDetails;
    }

    public boolean insertContact(Contact contact) {

        try {
            String query = "Insert into CONTACT (first_name, last_name, phone, email,sex_id,adress_id) values (?,?,?,?,?,?)";

            prSt = (OraclePreparedStatement) connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getValuesOfContact(contact);

            prSt.executeUpdate();
            rs = (OracleResultSet) prSt.getGeneratedKeys();
            rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean updateContact(Contact contact) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement("update CONTACT set first_name=?,last_name=?,phone=?,email=?,sex_id=?,adress_id=? where ID='" + contact.getId() + "'");
                getValuesOfContact(contact);
                rs = (OracleResultSet) prSt.executeQuery();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteContact(Contact contact) {
        try {
            synchronized (this) {
                prSt = (OraclePreparedStatement) connection.prepareStatement(
                        "delete CONTACT where ID='" + contact.getId() + "'");
                rs = (OracleResultSet) prSt.executeQuery();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void getValuesOfContact(Contact contact) throws SQLException {
        prSt.setString(1, contact.getFirst_name());
        prSt.setString(2, contact.getLast_name());
        prSt.setString(3, contact.getPhone());
        prSt.setString(4, contact.getEmail());        
        prSt.setLong(5, contact.getSex_id());
        prSt.setLong(6, contact.getAdress_id());

    }
}
