package com.curadoria.view;

import com.curadoria.dao.RepositorioUsuarios;
import com.curadoria.model.Usuario;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListagemUsuariosPanel extends JPanel {

    private JTable tabela;
    private DefaultTableModel model;
    private JTextField tfNome, tfIdade;
    private JComboBox<String> cbAdmin, cbInteresse1, cbInteresse2;
    private JButton btnInactivate; 
    private Usuario usuarioSelecionado = null; 

    public ListagemUsuariosPanel() {
        setLayout(new BorderLayout());
        setBackground(UtilMaterial.BG);

        JLabel title = UtilMaterial.h1("Gestão e Edição de Usuários");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Modelo da Tabela
        model = new DefaultTableModel(new Object[]{"Username", "Nome", "Idade", "Admin", "Ativo"}, 0);
        tabela = new JTable(model);
        tabela.setFont(UtilMaterial.PLAIN);
        tabela.setRowHeight(25);
        tabela.setAutoCreateRowSorter(true); 

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        
        // Evento de Seleção na Tabela
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                carregarParaEdicao(tabela.getSelectedRow());
            }
        });

        // --- Área de Edição (Parte Inferior) ---
        JPanel editPanel = new JPanel(new BorderLayout());
        editPanel.setBackground(UtilMaterial.PRIMARY.brighter()); 
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridLayout(2, 6, 10, 10));
        form.setBackground(UtilMaterial.PRIMARY.brighter());
        
        tfNome = new JTextField(15); tfNome.setFont(UtilMaterial.PLAIN);
        tfIdade = new JTextField(5); tfIdade.setFont(UtilMaterial.PLAIN);
        cbAdmin = new JComboBox<>(new String[]{"Comum", "Admin"}); cbAdmin.setFont(UtilMaterial.PLAIN);
        cbInteresse1 = new JComboBox<>(new String[]{"IA Responsável", "Cibersegurança", "Ética Digital"}); cbInteresse1.setFont(UtilMaterial.PLAIN);
        cbInteresse2 = new JComboBox<>(new String[]{"", "IA Responsável", "Cibersegurança", "Ética Digital"}); cbInteresse2.setFont(UtilMaterial.PLAIN); 
        
        form.add(new JLabel("Nome:")); form.add(tfNome);
        form.add(new JLabel("Idade:")); form.add(tfIdade);
        form.add(new JLabel("Tipo:")); form.add(cbAdmin);
        form.add(new JLabel("I. 1:")); form.add(cbInteresse1);
        form.add(new JLabel("I. 2:")); form.add(cbInteresse2);
        form.add(new JLabel("")); 
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(UtilMaterial.PRIMARY.brighter());
        
        JButton btnEdit = UtilMaterial.materialButton("Salvar Edição");
        btnInactivate = UtilMaterial.materialButton("Inativar/Ativar Conta"); 
        
        btnEdit.addActionListener(e -> editarUsuario());
        btnInactivate.addActionListener(e -> alternarStatusUsuario()); 
        
        btnPanel.add(btnEdit);
        btnPanel.add(btnInactivate);
        
        editPanel.add(form, BorderLayout.CENTER);
        editPanel.add(btnPanel, BorderLayout.SOUTH);

        add(editPanel, BorderLayout.SOUTH);

        atualizarLista();
    }
    
    // Carrega dados do banco para os campos de texto
    private void carregarParaEdicao(int viewRowIndex) {
        int modelRowIndex = tabela.convertRowIndexToModel(viewRowIndex);
        String username = model.getValueAt(modelRowIndex, 0).toString();
        
        // Busca do banco para garantir que temos o ID correto
        List<Usuario> lista = RepositorioUsuarios.buscarTodos();
        usuarioSelecionado = lista.stream() 
            .filter(u -> u.username.equals(username))
            .findFirst()
            .orElse(null);
            
        if (usuarioSelecionado != null) {
            tfNome.setText(usuarioSelecionado.nome);
            tfIdade.setText(String.valueOf(usuarioSelecionado.idade));
            cbAdmin.setSelectedItem(usuarioSelecionado.admin ? "Admin" : "Comum");
            cbInteresse1.setSelectedItem(usuarioSelecionado.interesse1);
            cbInteresse2.setSelectedItem(usuarioSelecionado.interesse2 != null ? usuarioSelecionado.interesse2 : "");
            
            btnInactivate.setText(usuarioSelecionado.ativo ? "Inativar Conta" : "Reativar Conta");
        }
    }

    // Salva alterações no Banco
    private void editarUsuario() {
        if (usuarioSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para editar.");
            return;
        }

        try {
            usuarioSelecionado.nome = tfNome.getText().trim();
            usuarioSelecionado.idade = Integer.parseInt(tfIdade.getText().trim());
            usuarioSelecionado.admin = cbAdmin.getSelectedItem().equals("Admin");
            usuarioSelecionado.interesse1 = (String) cbInteresse1.getSelectedItem();
            
            String i2 = (String) cbInteresse2.getSelectedItem();
            if (i2 != null && i2.isEmpty()) i2 = null;
            usuarioSelecionado.interesse2 = i2;
            
            // Atualiza no SQL
            RepositorioUsuarios.atualizar(usuarioSelecionado);
            
            atualizarLista();
            JOptionPane.showMessageDialog(this, "✅ Usuário editado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Erro: Idade inválida.");
        }
    }

    
    private void alternarStatusUsuario() {
        if (usuarioSelecionado == null) return;
        
        String acao = usuarioSelecionado.ativo ? "inativar" : "reativar";

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente " + acao + " a conta de: " + usuarioSelecionado.nome + "?",
                "Confirmação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Usa o ID para alterar status
            RepositorioUsuarios.alterarStatus(usuarioSelecionado.id, !usuarioSelecionado.ativo);
            
            atualizarLista();
            usuarioSelecionado = null; 
            JOptionPane.showMessageDialog(this, "Status alterado com sucesso!");
        }
    }

    // Busca todos do Banco e preenche a tabela
    public void atualizarLista() {
        model.setRowCount(0);
        List<Usuario> lista = RepositorioUsuarios.buscarTodos(); 
        for (Usuario u : lista) { 
            model.addRow(new Object[]{u.username, u.nome, u.idade, u.admin ? "Sim" : "Não", u.ativo ? "Sim" : "Não"});
        }
    }
}