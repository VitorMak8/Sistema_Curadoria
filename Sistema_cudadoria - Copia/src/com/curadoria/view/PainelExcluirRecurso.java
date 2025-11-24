package com.curadoria.view;

import com.curadoria.dao.RepositorioConteudos;
import com.curadoria.model.Recurso;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PainelExcluirRecurso extends JPanel {

    private JTable tabela;
    private DefaultTableModel model;

    public PainelExcluirRecurso() {
        setLayout(new BorderLayout());
        setBackground(UtilMaterial.BG);

        JLabel titulo = UtilMaterial.h1("Exclusão de Recursos (Acesso Admin)");
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Título", "Autor", "Tipo", "Categoria", "Cadastrado Por"}, 0);
        tabela = new JTable(model);
        tabela.setFont(UtilMaterial.PLAIN);
        tabela.setRowHeight(25);
        tabela.setAutoCreateRowSorter(true); 

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        JButton btnExcluir = UtilMaterial.materialButton("Excluir Recurso Selecionado");
        btnExcluir.addActionListener(e -> excluirRecurso());

        JPanel bottom = new JPanel();
        bottom.setBackground(UtilMaterial.BG);
        bottom.add(btnExcluir);

        add(bottom, BorderLayout.SOUTH);

        atualizarLista();
    }

    private void atualizarLista() {
        model.setRowCount(0);
        List<Recurso> lista = RepositorioConteudos.todosOrdenadosPorTitulo();
        for (Recurso r : lista) { 
            model.addRow(new Object[]{r.titulo, r.autor, r.tipo, r.categoria, r.cadastradoPor});
        }
    }
    
    public void refreshList() {
        atualizarLista();
    }

    private void excluirRecurso() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um recurso na tabela para excluir.");
            return;
        }
        
        // Pega os dados da linha selecionada
        int modelRow = tabela.convertRowIndexToModel(row); 
        String titulo = model.getValueAt(modelRow, 0).toString();
        String autor = model.getValueAt(modelRow, 1).toString();
        String tipo = model.getValueAt(modelRow, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o recurso: " + titulo + "?",
                "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Busca o ID no banco correspondente a este recurso visual
            List<Recurso> todos = RepositorioConteudos.todosOrdenadosPorTitulo();
            Recurso toRemove = todos.stream()
                .filter(r -> r.titulo.equals(titulo) && r.autor.equals(autor) && r.tipo.equalsIgnoreCase(tipo))
                .findFirst()
                .orElse(null);
            
            if (toRemove != null) {
                // Remove pelo ID
                RepositorioConteudos.excluir(toRemove.id); 
                atualizarLista();
                JOptionPane.showMessageDialog(this, "✅ Recurso excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Erro: Recurso não encontrado no banco.");
            }
        }
    }
}