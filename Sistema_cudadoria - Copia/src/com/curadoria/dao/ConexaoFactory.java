package com.curadoria.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {
    
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_curadoria";
    private static final String USER = "root";
    private static final String PASS = ""; 

    public static Connection conectar() {
        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver"); 
                       
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver do MySQL não encontrado! Adicione a biblioteca ao projeto.");
            return null;
        } catch (SQLException e) {
            System.err.println("❌ Erro de Conexão: " + e.getMessage());
            return null;
        }
    }
}