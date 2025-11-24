package com.curadoria.view;

import com.curadoria.model.Usuario;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import javax.swing.*;

public class PerfilPanel extends JPanel {

    public PerfilPanel(Usuario usuario) {
        setLayout(new BorderLayout());
        setBackground(UtilMaterial.BG); 

        // Título
        JLabel title = UtilMaterial.h1("Meu Perfil de Usuário");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Painel de Informações
        JPanel infoPanel = new JPanel();
        infoPanel.setBackground(UtilMaterial.BG);
        infoPanel.setLayout(new GridLayout(0, 1, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        String tipoPerfil = usuario.admin ? "Administrador" : "Usuário Comum";
        
        // Exibição formatada com HTML básico para cores
        JLabel lblTipo = new JLabel("<html><b>Tipo de Perfil:</b> <span style='color: " + 
                (usuario.admin ? "#247BA0" : "#13293D") + ";'>" + tipoPerfil + "</span></html>");
        lblTipo.setFont(UtilMaterial.PLAIN);
        infoPanel.add(lblTipo);
        
        infoPanel.add(new JSeparator()); // Linha separadora

        JLabel lblNome = new JLabel("<html><b>Nome Completo:</b> " + usuario.nome + "</html>");
        lblNome.setFont(UtilMaterial.PLAIN);
        infoPanel.add(lblNome);

        JLabel lblUser = new JLabel("<html><b>Username (Login):</b> " + usuario.username + "</html>");
        lblUser.setFont(UtilMaterial.PLAIN);
        infoPanel.add(lblUser);

        JLabel lblIdade = new JLabel("<html><b>Idade:</b> " + usuario.idade + " anos</html>");
        lblIdade.setFont(UtilMaterial.PLAIN);
        infoPanel.add(lblIdade);

        JLabel lblStatus = new JLabel("<html><b>Status da Conta:</b> <span style='color: " + 
                (usuario.ativo ? "green" : "red") + ";'>" + (usuario.ativo ? "Ativa" : "Inativa") + "</span></html>");
        lblStatus.setFont(UtilMaterial.PLAIN);
        infoPanel.add(lblStatus);
        
        infoPanel.add(new JSeparator());

        // Interesses
        JLabel lblInteressesTitle = new JLabel("<html><b>Interesses Principais:</b></html>");
        lblInteressesTitle.setFont(UtilMaterial.PLAIN);
        infoPanel.add(lblInteressesTitle);

        JLabel lblI1 = new JLabel("<html>&emsp;• Interesse 1: " + usuario.interesse1 + "</html>");
        lblI1.setFont(UtilMaterial.PLAIN);
        infoPanel.add(lblI1);
        
        if (usuario.interesse2 != null && !usuario.interesse2.isEmpty()) {
            JLabel lblI2 = new JLabel("<html>&emsp;• Interesse 2: " + usuario.interesse2 + "</html>");
            lblI2.setFont(UtilMaterial.PLAIN);
            infoPanel.add(lblI2);
        }

        // Centraliza o painel de info
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(UtilMaterial.BG);
        centerPanel.add(infoPanel);

        add(centerPanel, BorderLayout.CENTER);
    }
}