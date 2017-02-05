/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DbModels;

import java.math.BigInteger;

/**
 *
 * @author Deamon
 */
//abstract class  called entity - presnt in all tables as ID   
//Long as type of data for ID is used as equivalent to Biginteger 
public abstract class Entity {

    private Long id;

    public Entity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
}
