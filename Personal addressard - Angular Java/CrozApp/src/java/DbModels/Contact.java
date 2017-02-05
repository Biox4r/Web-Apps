/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbModels;

/**
 *
 * @author Deamon
 */
public class Contact {
    private Long id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private Long sex_id;
    private Long adress_id;

    public Contact() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getSex_id() {
        return sex_id;
    }

    public void setSex_id(Long sex_id) {
        this.sex_id = sex_id;
    }

    public Long getAdress_id() {
        return adress_id;
    }

    public void setAdress_id(Long adress_id) {
        this.adress_id = adress_id;
    }

    @Override
    public String toString() {
        return "first_name=" + first_name + ", last_name=" + last_name + ", phone=" + phone + ", email=" + email + ", sex_id=" + sex_id + ", adress_id=" + adress_id;
    }
    
    

    
    
    
}
