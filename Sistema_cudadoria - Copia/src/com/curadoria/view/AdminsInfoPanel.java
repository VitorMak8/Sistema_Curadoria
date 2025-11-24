package com.curadoria.view;

import com.curadoria.dao.RepositorioUsuarios;
import com.curadoria.model.Usuario;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;

public class AdminsInfoPanel extends JPanel {

    public AdminsInfoPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UtilMaterial.BG); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = UtilMaterial.h1("Informações e Funções de Administrador");
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UtilMaterial.BG);

        // --- Seção 1: Lista de Administradores ---
        JLabel lblAdminsTitle = new JLabel("<html><h2 style='color: " + UtilMaterial.PRIMARY_DARK.getRGB() + ";'>Administradores Ativos:</h2></html>");
        lblAdminsTitle.setFont(UtilMaterial.H1);
        lblAdminsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(lblAdminsTitle);
        content.add(Box.createVerticalStrut(10));

        // MUDANÇA: Busca do banco e filtra
        List<Usuario> admins = RepositorioUsuarios.buscarTodos().stream()
                .filter(u -> u.admin && u.ativo)
                .collect(Collectors.toList());

        if (admins.isEmpty()) {
            JLabel noAdmin = new JLabel("Nenhum administrador ativo encontrado.");
            noAdmin.setFont(UtilMaterial.PLAIN);
            content.add(noAdmin);
        } else {
            JList<String> adminList = new JList<>(admins.stream()
                    .map(u -> "Nome: " + u.nome + " | Username: " + u.username)
                    .toArray(String[]::new));
            adminList.setFont(UtilMaterial.PLAIN);
            adminList.setFixedCellHeight(25);
            adminList.setBorder(BorderFactory.createLineBorder(UtilMaterial.PRIMARY, 1));
            
            JScrollPane scroll = new JScrollPane(adminList);
            scroll.setMaximumSize(new Dimension(800, 150));
            scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
            content.add(scroll);
        }
        
        content.add(Box.createVerticalStrut(30));

        // --- Seção 2: Texto Explicativo ---
        JLabel lblFuncTitle = new JLabel("<html><h2 style='color: " + UtilMaterial.PRIMARY_DARK.getRGB() + ";'>Funções Exclusivas de Administrador:</h2></html>");
        lblFuncTitle.setFont(UtilMaterial.H1);
        lblFuncTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(lblFuncTitle);
        content.add(Box.createVerticalStrut(10));

        String funcoes = "<html>" +
                "<p style='margin-bottom: 15px; font-family: Segoe UI; font-size: 14pt; color: " + UtilMaterial.TEXT.getRGB() + ";'>O perfil Administrador possui controle total, incluindo:</p>" +
                "<ul>" +
                "<li><b>Gestão de Usuários:</b> Cadastro, Edição e <u>Controle de Status</u> (Ativar/Inativar).</li>" +
                "<li><b>Exclusão de Conteúdo:</b> Remoção de recursos cadastrados por qualquer usuário.</li>" +
                "<li><b>Configurações:</b> Visualização rápida dos demais administradores.</li>" +
                "</ul>" +
                "</html>";

        JLabel lblFuncoes = new JLabel(funcoes);
        lblFuncoes.setFont(UtilMaterial.PLAIN);
        lblFuncoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(lblFuncoes);

        add(new JScrollPane(content), BorderLayout.CENTER);
    }
}