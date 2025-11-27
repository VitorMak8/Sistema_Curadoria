package com.curadoria.dao;

import com.curadoria.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepositorioUsuarios {

    // --- 1. LOGIN (Busca por usuário e senha ativos) ---
    public static Usuario login(String user, String senha) {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND senha = ? AND ativo = TRUE";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user);
            stmt.setString(2, senha);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return instanciarUsuario(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- 2. ADICIONAR (INSERT) ---
    public static boolean adicionar(Usuario u) {
        String sql = "INSERT INTO usuarios (username, senha, nome, idade, tipo, ativo, interesse1, interesse2) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, u.username);
            stmt.setString(2, u.senha);
            stmt.setString(3, u.nome);
            stmt.setInt(4, u.idade);
            // Converte o boolean admin para o ENUM do banco ('ADMIN' ou 'COMUM')
            stmt.setString(5, u.admin ? "ADMIN" : "COMUM");
            stmt.setBoolean(6, u.ativo);
            stmt.setString(7, u.interesse1);
            stmt.setString(8, u.interesse2); // Pode ser null
            
            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar usuário: " + e.getMessage());
            return false;
        }
    }

    // --- 3. BUSCAR TODOS (SELECT) ---
    public static List<Usuario> buscarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(instanciarUsuario(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // --- 4. ATUALIZAR DADOS (UPDATE) ---
    public static void atualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nome=?, idade=?, tipo=?, interesse1=?, interesse2=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, u.nome);
            stmt.setInt(2, u.idade);
            stmt.setString(3, u.admin ? "ADMIN" : "COMUM");
            stmt.setString(4, u.interesse1);
            stmt.setString(5, u.interesse2);
            stmt.setInt(6, u.id); // Importante: Usa o ID para achar o registro
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 5. ALTERAR STATUS (Ativar/Inativar) ---
    public static void alterarStatus(int id, boolean ativo) {
        String sql = "UPDATE usuarios SET ativo=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, ativo);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para transformar a linha do banco em Objeto Java
    private static Usuario instanciarUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getString("senha"),
            rs.getString("nome"),
            rs.getInt("idade"),
            "ADMIN".equalsIgnoreCase(rs.getString("tipo")), // Converte String 'ADMIN' para boolean true
            rs.getBoolean("ativo"),
            rs.getString("interesse1"),
            rs.getString("interesse2")
        );
    }
}