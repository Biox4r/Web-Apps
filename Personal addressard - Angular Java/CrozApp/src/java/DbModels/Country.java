/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbModels;

/**
 *
 * Country model of database model containing attributes defined in database
 */
public class Country extends Entity {
    
    private String name;
    private String alpha_2;
    private String alpha_3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha_2() {
        return alpha_2;
    }

    public void setAlpha_2(String alpha_2) {
        this.alpha_2 = alpha_2;
    }

    public String getAlpha_3() {
        return alpha_3;
    }

    public void setAlpha_3(String alpha_3) {
        this.alpha_3 = alpha_3;
    }
    
    
    
    
}
