package com.curadoria.view;

import com.curadoria.dao.RepositorioUsuarios;
import com.curadoria.model.Usuario;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import javax.swing.*;

public class CadastroUsuarioPanel extends JPanel {
    
    private JTextField tfUser, tfNome;
    private JPasswordField pfSenha;
    private JSpinner spIdade;
    private JCheckBox cbAdmin;
    private JComboBox<String> cbI1, cbI2;

    // O Runnable 'afterSave' serve para atualizar a lista de usuários automaticamente após cadastrar
    public CadastroUsuarioPanel(Runnable afterSave) {
        setLayout(null);
        setBackground(UtilMaterial.BG);

        JLabel title = UtilMaterial.h1("Cadastro de Novo Usuário");
        title.setBounds(20, 10, 600, 30); 
        add(title);
        
        // Layout manual (Absolute Positioning)
        int x1 = 30;
        int x2 = 280;
        int yOffset = 60;
        int w = 220;
        int h = 30;
        int spacing = 45;

        // Username
        JLabel lUser = new JLabel("Username (Login):"); 
        lUser.setBounds(x1, yOffset, w, 20); lUser.setFont(UtilMaterial.PLAIN); add(lUser);
        tfUser = new JTextField(); 
        tfUser.setBounds(x1, yOffset + 20, w, h); tfUser.setFont(UtilMaterial.PLAIN); add(tfUser);
        
        // Senha
        JLabel lSenha = new JLabel("Senha:"); 
        lSenha.setBounds(x2, yOffset, w, 20); lSenha.setFont(UtilMaterial.PLAIN); add(lSenha);
        pfSenha = new JPasswordField(); 
        pfSenha.setBounds(x2, yOffset + 20, w, h); pfSenha.setFont(UtilMaterial.PLAIN); add(pfSenha);

        yOffset += spacing;

        // Nome
        JLabel lNome = new JLabel("Nome Completo:"); 
        lNome.setBounds(x1, yOffset, w*2+30, 20); lNome.setFont(UtilMaterial.PLAIN); add(lNome);
        tfNome = new JTextField(); 
        tfNome.setBounds(x1, yOffset + 20, w*2 + 30, h); tfNome.setFont(UtilMaterial.PLAIN); add(tfNome);

        yOffset += spacing;

        // Idade
        JLabel lIdade = new JLabel("Idade:"); 
        lIdade.setBounds(x1, yOffset, w, 20); lIdade.setFont(UtilMaterial.PLAIN); add(lIdade);
        spIdade = new JSpinner(new SpinnerNumberModel(18, 1, 150, 1)); 
        spIdade.setBounds(x1, yOffset + 20, w, h); spIdade.setFont(UtilMaterial.PLAIN); add(spIdade);
        
        // Checkbox Admin
        cbAdmin = new JCheckBox("É Administrador?"); 
        cbAdmin.setBounds(x2, yOffset + 20, w, h); 
        cbAdmin.setFont(UtilMaterial.PLAIN); 
        cbAdmin.setBackground(UtilMaterial.BG); 
        add(cbAdmin);

        yOffset += spacing;

        // Interesses
        String[] categorias = {"IA Responsável", "Cibersegurança", "Ética Digital"};

        JLabel lI1 = new JLabel("Interesse Principal 1:"); 
        lI1.setBounds(x1, yOffset, w, 20); lI1.setFont(UtilMaterial.PLAIN); add(lI1);
        cbI1 = new JComboBox<>(categorias); 
        cbI1.setBounds(x1, yOffset + 20, w, h); cbI1.setFont(UtilMaterial.PLAIN); add(cbI1);

        JLabel lI2 = new JLabel("Interesse Principal 2 (Opcional):"); 
        lI2.setBounds(x2, yOffset, w, 20); lI2.setFont(UtilMaterial.PLAIN); add(lI2);
        cbI2 = new JComboBox<>(categorias); 
        cbI2.setBounds(x2, yOffset + 20, w, h); cbI2.setFont(UtilMaterial.PLAIN); add(cbI2);
        
        cbI2.insertItemAt("", 0);
        cbI2.setSelectedIndex(0);

        yOffset += spacing + 20;

        // Botão Salvar
        JButton btn = UtilMaterial.materialButton("Finalizar Cadastro");
        btn.setBounds(x1, yOffset + 10, w*2 + 30, h + 5); 
        add(btn);

        // Ação do Botão
        btn.addActionListener(e -> {
            String u = tfUser.getText().trim();
            String s = new String(pfSenha.getPassword()).trim();
            String n = tfNome.getText().trim();
            int i = (int) spIdade.getValue();
            boolean a = cbAdmin.isSelected();
            String i1 = (String) cbI1.getSelectedItem();
            String i2 = (String) cbI2.getSelectedItem();
            
            if (i2.isEmpty()) i2 = null;

            if (u.isEmpty() || s.isEmpty() || n.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios.");
                return;
            }

            // Cria objeto Usuario (usando construtor sem ID, pois é novo)
            Usuario novo = new Usuario(u, s, n, i, a, true, i1, i2);

            // Tenta salvar no Banco
            if (RepositorioUsuarios.adicionar(novo)) {
                JOptionPane.showMessageDialog(this, "✅ Usuário cadastrado com sucesso!");
                // Limpa campos
                tfUser.setText(""); pfSenha.setText(""); tfNome.setText("");
                spIdade.setValue(18); cbAdmin.setSelected(false); cbI2.setSelectedIndex(0);
                
                // Atualiza a tela de listagem se o callback existir
                if (afterSave != null) afterSave.run(); 
            } else {
                JOptionPane.showMessageDialog(this, "❌ Erro: Username já existe.");
            }
        });
    }
}