package com.curadoria.dao;

import com.curadoria.model.Recurso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioConteudos {

    // --- 1. ADICIONAR RECURSO ---
    public static boolean adicionar(Recurso r) {
        String sql = "INSERT INTO recursos (titulo, autor, categoria, tipo, cadastradoPor) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, r.titulo);
            stmt.setString(2, r.autor);
            stmt.setString(3, r.categoria);
            stmt.setString(4, r.tipo);
            stmt.setString(5, r.cadastradoPor); // Deve bater com um username existente (ex: 'admin')
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false;
        }
    }

    // --- 2. LISTAR (Ordenado por Título) ---
    public static List<Recurso> todosOrdenadosPorTitulo() {
        List<Recurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM recursos ORDER BY titulo ASC";

        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Recurso(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getString("categoria"),
                    rs.getString("tipo"),
                    rs.getString("cadastradoPor")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // --- 3. EXCLUIR (Pelo ID) ---
    public static void excluir(int id) {
        String sql = "DELETE FROM recursos WHERE id=?";
        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}