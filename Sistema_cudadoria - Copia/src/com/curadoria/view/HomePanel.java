package com.curadoria.view;

import com.curadoria.util.UtilMaterial;
import java.awt.*;
import javax.swing.*;

public class HomePanel extends JPanel {

    public HomePanel() {

        // Configuração de Layout e Cores
        setLayout(new BorderLayout(0, 10)); 
        setBackground(UtilMaterial.BG);

        // --- Título Superior ---
        // Usa o método h1() do seu UtilMaterial
        JLabel title = UtilMaterial.h1("Página Inicial — Sistema de Curadoria"); 
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // --- Conteúdo Central (Texto Informativo) ---
        JTextArea info = new JTextArea(
                "O Sistema de Curadoria de Recursos Educacionais permite cadastrar, pesquisar e visualizar " +
                "conteúdos focados em temas críticos da Tecnologia.\n\n" +
                "Funcionalidades disponíveis:\n" +
                "• Cadastrar novos Recursos (Artigos, Cursos, Vídeos ou Podcasts)\n" +
                "• Visualizar e pesquisar conteúdos cadastrados\n" +
                "• Foco em: IA Responsável, Cibersegurança e Ética Digital\n" +
                "• Administradores: Acesso à Gestão de Usuários e Exclusão de Recursos\n\n" +
                "Use o menu lateral esquerdo para navegar e interagir com o sistema."
        );
        
        // Estilização do Texto
        info.setEditable(false);
        info.setBackground(UtilMaterial.BG);
        info.setFont(UtilMaterial.PLAIN); 
        info.setForeground(UtilMaterial.PRIMARY_DARK);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        
        // Margens internas do texto
        info.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Painel para centralizar o texto na tela
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setBackground(UtilMaterial.BG);
        center.add(info);
        
        add(center, BorderLayout.CENTER);

        // --- Rodapé ---
        JPanel footer = new JPanel();
        footer.setBackground(UtilMaterial.BG);
        
        JLabel version = new JLabel("© 2025 Sistema de Curadoria e Compartilhamento");
        version.setFont(new Font("Segoe UI", Font.ITALIC, 12)); // Ajustei para Segoe UI para manter padrão
        
        footer.add(version);
        add(footer, BorderLayout.SOUTH);
    }
}