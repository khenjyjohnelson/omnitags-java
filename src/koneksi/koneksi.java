/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author khenj
 */
public class koneksi {
    private Connection kff;
    
    public Connection konek(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Berhasil konek");
        } catch (ClassNotFoundException ex) {
            System.out.println("Gagal Koneksi " + ex);
        }
        
        String url = "jdbc:mysql://localhost:3306/kff";
        try {
            kff = DriverManager.getConnection(url, "root", "");
            System.out.println("Berhasil Konek Database");
        } catch (SQLException ex){
            System.out.println("Gagal Konek Database " + ex);
        }
        return kff;
    }
}
