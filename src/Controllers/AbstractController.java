/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.e
 */
package Controllers;

import java.util.List;

/**
 *
 * @author John Linus
 */
abstract class AbstractController {

    public void create(Object o){
        
    }

    public <T> void printList(List<T> t){
        
    }
    public void displayAll(){
    };
    public void findByName(String name){
        
    }

    public Object findById(Long id){
        return null;
        
    }

    public boolean delete(Long id){
        return false;
        
    }

    public boolean update(Object o){
    
    return false;
    }
}
