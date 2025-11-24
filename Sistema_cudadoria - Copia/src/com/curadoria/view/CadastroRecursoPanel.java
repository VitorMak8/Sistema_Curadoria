package com.curadoria.view;

import com.curadoria.dao.RepositorioConteudos;
import com.curadoria.model.Recurso;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import javax.swing.*;

public class CadastroRecursoPanel extends JPanel {
    
    private JTextField tfTitulo, tfAutor;
    private JComboBox<String> cbCategoria, cbTipo;

    public CadastroRecursoPanel() {
        setLayout(null);
        setBackground(UtilMaterial.BG);

        JLabel title = UtilMaterial.h1("Cadastro de Novo Recurso de Conteúdo");
        title.setBounds(20, 10, 600, 30); 
        add(title);

        int x1 = 30;
        int x2 = 280;
        int yOffset = 60;
        int w = 220;
        int h = 30;
        int spacing = 45;

        // Campos
        JLabel lTit = new JLabel("Título do Recurso:"); 
        lTit.setBounds(x1, yOffset, w*2+30, 20); lTit.setFont(UtilMaterial.PLAIN); add(lTit);
        tfTitulo = new JTextField(); 
        tfTitulo.setBounds(x1, yOffset + 20, w*2 + 30, h); tfTitulo.setFont(UtilMaterial.PLAIN); add(tfTitulo);

        yOffset += spacing;

        JLabel lAut = new JLabel("Autor ou Fonte:"); 
        lAut.setBounds(x1, yOffset, w*2+30, 20); lAut.setFont(UtilMaterial.PLAIN); add(lAut);
        tfAutor = new JTextField(); 
        tfAutor.setBounds(x1, yOffset + 20, w*2 + 30, h); tfAutor.setFont(UtilMaterial.PLAIN); add(tfAutor);
        
        yOffset += spacing;

        String[] categorias = {"IA Responsável", "Cibersegurança", "Ética Digital"};
        JLabel lCat = new JLabel("Área de Foco:"); 
        lCat.setBounds(x1, yOffset, w, 20); lCat.setFont(UtilMaterial.PLAIN); add(lCat);
        cbCategoria = new JComboBox<>(categorias); 
        cbCategoria.setBounds(x1, yOffset + 20, w, h); cbCategoria.setFont(UtilMaterial.PLAIN); add(cbCategoria);

        String[] tipos = {"Artigo", "Curso", "Vídeo", "Podcast"};
        JLabel lTipo = new JLabel("Formato:"); 
        lTipo.setBounds(x2, yOffset, w, 20); lTipo.setFont(UtilMaterial.PLAIN); add(lTipo);
        cbTipo = new JComboBox<>(tipos); 
        cbTipo.setBounds(x2, yOffset + 20, w, h); cbTipo.setFont(UtilMaterial.PLAIN); add(cbTipo);
        
        yOffset += spacing + 20;

        // Botão
        JButton btn = UtilMaterial.materialButton("Cadastrar Recurso");
        btn.setBounds(x1, yOffset + 10, w*2 + 30, h + 5);
        add(btn);

        btn.addActionListener(e -> {
            String t = tfTitulo.getText().trim();
            String a = tfAutor.getText().trim();
            String c = (String) cbCategoria.getSelectedItem();
            String tipo = (String) cbTipo.getSelectedItem();
            
           
            String cadastradoPor = "admin"; 

            if (t.isEmpty() || a.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha Título e Autor.");
                return;
            }

            Recurso novo = new Recurso(t, a, c, tipo, cadastradoPor);

            if (RepositorioConteudos.adicionar(novo)) { 
                JOptionPane.showMessageDialog(this, "✅ Recurso cadastrado com sucesso!");
                tfTitulo.setText(""); tfAutor.setText("");
                cbCategoria.setSelectedIndex(0); cbTipo.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Erro ao salvar recurso.");
            }
        });
    }
}