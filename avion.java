/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

/**
 *
 * @author Ruben Pascual Sanchez y Ángel De Lara Lafuerza
 */
class avion {
    int id_vuelo1;
    
    
    public avion(int id_vuelo1) {
    this.id_vuelo1 = id_vuelo1;
    
    }
    
    public int getid_vuelo() {
        return this.id_vuelo1;
    }
    
    public  int toint(){return this.id_vuelo1 ;}
}
