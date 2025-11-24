package com.curadoria.view;

import com.curadoria.dao.RepositorioConteudos;
import com.curadoria.model.Recurso;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListagemConteudosPanel extends JPanel {

    private JTable tabela;
    private DefaultTableModel model;

    public ListagemConteudosPanel() {
        setLayout(new BorderLayout());
        setBackground(UtilMaterial.BG);

        JLabel title = UtilMaterial.h1("Visualização de Recursos Cadastrados");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // Tabela
        model = new DefaultTableModel(new Object[]{"Título", "Autor", "Tipo", "Categoria", "Cadastrado Por"}, 0);
        tabela = new JTable(model);
        tabela.setFont(UtilMaterial.PLAIN);
        tabela.setRowHeight(25);
        tabela.setAutoCreateRowSorter(true); // Permite ordenar clicando no cabeçalho

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // Botão Atualizar
        JButton btnRefresh = UtilMaterial.materialButton("Atualizar Lista");
        btnRefresh.addActionListener(e -> atualizarLista());
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(UtilMaterial.BG);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        atualizarLista();
    }

    public void atualizarLista() {
        model.setRowCount(0);
        // Busca do Banco de Dados
        List<Recurso> recursos = RepositorioConteudos.todosOrdenadosPorTitulo(); 
        for (Recurso r : recursos) {
            model.addRow(new Object[]{r.titulo, r.autor, r.tipo, r.categoria, r.cadastradoPor});
        }
    }
    
    // Método auxiliar para ser chamado externamente se precisar
    public void refreshList() {
        atualizarLista();
    }
}