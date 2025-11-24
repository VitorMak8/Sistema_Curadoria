package com.curadoria.view;

import com.curadoria.dao.RepositorioUsuarios;
import com.curadoria.model.Usuario;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import javax.swing.*;

public class TelaLogin extends JFrame {

    private JTextField tfUser;
    private JPasswordField pf;
    private boolean mostrarSenha = false;

    public TelaLogin() {
        setTitle("Acesso ao Sistema de Curadoria");
        setSize(430, 480); 
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Color BG = UtilMaterial.BG;
        Color PRIMARY = UtilMaterial.PRIMARY;

        JPanel root = new JPanel(null);
        root.setBackground(BG);
        setContentPane(root);

        // Ícone e Título
        JLabel iconCenter = new JLabel("📚", SwingConstants.CENTER); 
        iconCenter.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 70));
        iconCenter.setBounds(0, 30, 430, 70);
        root.add(iconCenter);
        
        JLabel title = new JLabel("Sistema de Curadoria", SwingConstants.CENTER);
        title.setFont(UtilMaterial.H1);
        title.setForeground(UtilMaterial.TEXT);
        title.setBounds(0, 100, 430, 30);
        root.add(title);
        
        // Campos
        JLabel lUser = new JLabel("Username:");
        lUser.setFont(UtilMaterial.PLAIN); lUser.setBounds(80, 150, 280, 20); root.add(lUser);
        
        tfUser = new JTextField();
        tfUser.setBounds(80, 175, 280, 35);
        tfUser.setFont(UtilMaterial.PLAIN);
        tfUser.setBorder(BorderFactory.createLineBorder(PRIMARY, 1));
        root.add(tfUser);

        JLabel lSenha = new JLabel("Senha:");
        lSenha.setFont(UtilMaterial.PLAIN); lSenha.setBounds(80, 220, 280, 20); root.add(lSenha);
        
        pf = new JPasswordField();
        pf.setBounds(80, 245, 280, 35);
        pf.setFont(UtilMaterial.PLAIN);
        pf.setBorder(BorderFactory.createLineBorder(PRIMARY, 1));
        root.add(pf);

        // Botão Olho (Mostrar Senha)
        JButton btnMostrar = new JButton("👁");
        btnMostrar.setBounds(335, 245, 25, 35); 
        btnMostrar.setBorder(null);
        btnMostrar.setBackground(Color.WHITE);
        btnMostrar.addActionListener(e -> {
            mostrarSenha = !mostrarSenha;
            pf.setEchoChar(mostrarSenha ? (char) 0 : '•');
        });
        root.add(btnMostrar);
        
        // Botão Entrar
        JButton btn = UtilMaterial.materialButton("Entrar");
        btn.setBounds(80, 340, 280, 45); 
        root.add(btn);

        btn.addActionListener(e -> {
            String u = tfUser.getText().trim();
            String s = new String(pf.getPassword()).trim();
            
            // CONEXÃO COM O BANCO DE DADOS
            Usuario user = RepositorioUsuarios.login(u, s); 

            if (user == null) {
                JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
                return;
            }

            // Abre a Tela Principal passando o usuário logado
            new TelaPrincipal(user).setVisible(true);
            dispose();
        });
    }

    // Método Main para rodar o projeto por aqui
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}