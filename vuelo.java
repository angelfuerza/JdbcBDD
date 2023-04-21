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
class vuelo {
    
     int id_vuelo;
     int idavionv;
     int inicio_puerta;
     int destino_puerta;

    
    public vuelo(int id_vuelo, int idavionv, int inicio_puerta, int destino_puerta) { 
    this.id_vuelo = id_vuelo;
    this.idavionv = idavionv;
    this.inicio_puerta = inicio_puerta; 
    this.destino_puerta = destino_puerta;
    }
    
    public int getid_vuelo() {
        return this.id_vuelo;////acabar de rellenarlo con todas las variables que faltan de vuelo
    }
    
    public int getidavionv() {
        return this.idavionv;
    }
    
    public int getinicio_puerta() {
        return this.inicio_puerta;
    }
    
    public int getdestino_puerta() {
        return this.destino_puerta;
    }
    
  
    public  int toint(){return this.id_vuelo + this.idavionv + this.inicio_puerta + this.destino_puerta;}
}
