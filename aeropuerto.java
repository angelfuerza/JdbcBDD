/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author epsaeropuerto
 */

package app;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class aeropuerto {

    public static void main(String[] args) {

        try {

        String url = "jdbc:mysql://localhost/aeropuerto";
        String usuario = "judias";
        String contrasenia = "123localhost";
        Connection conexion =
        DriverManager.getConnection("jdbc:mysql://localhost/aeropuerto?user=judias&password=1234localhost&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");
              //OBTENER INFO DE LA BD ? solo con las tab
            
            DatabaseMetaData bdMetaDatos = conexion.getMetaData();
            String bdNombre = bdMetaDatos.getDatabaseProductName();   
            System.out.println("Base de datos: " + bdNombre);
            String bdVersion = bdMetaDatos.getDatabaseProductVersion();
            System.out.println("Versión: " + bdVersion);
            
            ResultSet tabla1 = bdMetaDatos.getTables(null, null, "vuelo", null);
            ResultSet tabla2 = bdMetaDatos.getTables(null, null, "avion", null);
            
            
            if (tabla1.next()) {
                    // Comprobamos si las tablas se han cargado correctamente.
                    System.out.println("La tabla vuelos existe");   
             }
            
              if (tabla2.next()) {
                System.out.println("La tabla avion existe");
            }
            
           //CREAR OBJETO STATMENT
            Statement stm = conexion.createStatement();
            
            //EJECUTAR SQL CONSULTA GENÉRICA
           System.out.println("ConsultaGenerica Obtencion de todos los datos de la tabla vuelo: ");
            String consulta1 = "SELECT * FROM vuelo"; 
            ResultSet rs = stm.executeQuery(consulta1);
            
            //RECORRER EL RESULSET E IMPRIMIR EL RESULTADO POR PANTALLA
            
            while(rs.next()){
                System.out.println(rs.getString("id_vuelo") + " " + rs.getString("idavionv") 
                        + " " + rs.getString("llegada_terminal")+ " " + rs.getString("timestamp_llegadas")
                        + " " + rs.getString("salida_terminal") + " " + rs.getString("timestamp_salidas")
                        + " " + rs.getString("inicio_puerta")+ " " + rs.getString("timestamp_inicia")
                        + " " + rs.getString("destino_puerta")+ " " + rs.getString("timestamp_acaba"));
            }
            
            List<vuelo> lista1 = new ArrayList<>();
            
            List<avion> lista2 = new ArrayList<>();
            
            //CONSULTA 1 Datos de los vuelos cuyo modelo de avión es un boeing
            
            //PREPARAR CONSULTA
                
                System.out.println("EJECUCIÓN CONSULTA 1");

                PreparedStatement stm1=conexion.prepareStatement("SELECT id_vuelo, idavionv, inicio_puerta, destino_puerta FROM vuelo WHERE salida_terminal=? ");
                
                    //Establecemos parametros de consulta
                    
                    stm1.setInt(1 , 2 );
                    
                    //ejecutar y recorrer consulta
                    ResultSet rs1 = stm1.executeQuery(consulta1);
                    rs1 = stm1.executeQuery();
                    
                    while(rs1.next()){
                        int id_vuelo = rs1.getInt(1);
                        int idavionv = rs1.getInt(2);
                        int inicio_puerta = rs1.getInt(3);
                        int destino_puerta = rs1.getInt(4);
                        
                        vuelo vuelo=new vuelo(id_vuelo,idavionv,inicio_puerta,destino_puerta);
                        lista1.add(vuelo);
                                                                 
                        System.out.println(id_vuelo + " " + idavionv + " " + inicio_puerta + " " + destino_puerta);
                  
                    }
                    rs1.close();
                   
                    
                    String outputFilePath = "C:/tmp/consulta1.html";
                    BufferedWriter writer1 = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outputFilePath), "UTF-8"));
            
                  String header1 = "<!DOCTYPE html>\n" +                                              // Mas adelante concatenaremos las 3 partes del html
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Listado de vuelos</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>vuelos con modelo de avion boeing</h2>\n" +
                "<!-- Ejemplo de tabla -->\n" +
                "<table style=\"border: 5px solid blue;\">\n" +
                "<tr style=\"background-color: #FFFF00;\">\n" +
                "<th>id_vuelo</th>\n" +
                "<th>id_avion</th>\n" +
                "<th>inicio_puerta</th>\n" +
                "<th>destino_puerta</th>\n" +
                "</tr>\n";
            
                  String end1 =  "</table>\n" +
                "</body>\n" +
                "</html>";
            
            String body1 = "";
            
            for ( int  L=0; L<lista1.size(); L++ ) {                                             //En cada iteracion se encuentran las referencias a cada instancia de la clase. Ahi se encuentran los resultados de la consulta
                vuelo e = lista1.get(L);
                
                body1 +=                                                                        //Para cada resultado se va concatenando con la plantilla html
                "<tr>\n" +
                "<td>"+e.id_vuelo+"</td>\n"+
                "<td>"+e.idavionv+"</td>\n" +
                "<td>"+e.inicio_puerta+"</td>\n" +
                "<td>"+e.destino_puerta+"</td>\n" +
                "</tr>\n";    
            }
            
            writer1.write(header1 + body1 + end1 +"\n");                                        // Concatenamos las 3 partes  html y escribimos en el fichero
            writer1.close();            
            
            System.out.println("\n");
                   

            //CONSULTA 2 Numero de vuelos que salen de Madrid y tienen como avion un modelo Boeing

                 //PREPARAR CONSULTA
                    /*SELECT COUNT(vuelo.id_vuelo)
                    FROM vuelo, avion
                    WHERE avion.id_avion=vuelo.idavionv AND
                    vuelo.salida_terminal=1 
                    GROUP BY avion.modelo
                    HAVING avion.modelo='boeing';*/ 
                 
                 System.out.println("EJECUCIÓN  CONSULTA 2");
                 PreparedStatement stm2=conexion.prepareStatement(" SELECT COUNT(vuelo.id_vuelo) FROM vuelo, avion WHERE avion.id_avion=vuelo.idavionv AND vuelo.salida_terminal= ?  GROUP BY avion.modelo HAVING avion.modelo= ?;");

                //Establecemos parametros de consulta
                stm2.setInt(1,1);
                stm2.setString(2,"boeing");

                ResultSet rs2 = stm2.executeQuery(consulta1);
                //ejecutar y recorrer consulta
                rs2 = stm2.executeQuery();

                while(rs2.next()){
                        int id_vuelo1 = rs2.getInt(1);
                        System.out.println(id_vuelo1);
                        
                        avion avion=new avion(id_vuelo1);
                        lista2.add(avion);
                    }
                    
                
                    rs2.close();
                                        
                    String outputFilePath2 = "C:/tmp/consulta2.html";
                    BufferedWriter writer2 = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outputFilePath2), "UTF-8"));
            
                  String header2 = "<!DOCTYPE html>\n" +                                              // Mas adelante concatenaremos las 3 partes del html
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Listado de vuelos</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h2>numero de vuelos que salen de madrid con modelo boeing</h2>\n" +
                "<!-- Ejemplo de tabla -->\n" +
                "<table style=\"border: 5px solid blue;\">\n" +
                "<th>num_total</th>\n";
            
                  String end2 =  "</table>\n" +
                "</body>\n" +
                "</html>";
            
            String body2 = "";
            
            for ( int  L=0; L<lista2.size(); L++ ) {                                             //En cada iteracion se encuentran las referencias a cada instancia de la clase. Ahi se encuentran los resultados de la consulta
                avion e2 = lista2.get(L);
                
                body2 +=                                                                        //Para cada resultado se va concatenando con la plantilla html
                "<tr>\n" +
                "<td>"+e2.id_vuelo1+"</td>\n"+
                "</tr>\n";    
            }
            
            writer2.write(header2 + body2 + end2 +"\n");                                        // Concatenamos las 3 partes  html y escribimos en el fichero
            writer2.close();            
            
            System.out.println("\n");
                    
        }
        
        catch (Exception e) {

            e.printStackTrace();

        }

    }
}