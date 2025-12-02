package com.curadoria.view;

import com.curadoria.model.Usuario;
import com.curadoria.util.UtilMaterial;
import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel cards;
    private Usuario usuario;
    private HomePanel home;
    private CadastroUsuarioPanel cadUserPanel;
    private CadastroRecursoPanel cadRec;
    private ListagemUsuariosPanel listUsers;

    // NOVOS campos para os painéis que faltavam
    private ListagemConteudosPanel listRec;         // listar recursos
    private PerfilPanel perfilPanel;               // perfil
    private PainelExcluirRecurso painelExcluir;    // exclusão de recursos (admin)
    private AdminsInfoPanel adminsInfo;            // info admin

    public TelaPrincipal(Usuario usuario) {
        this.usuario = usuario;

        setTitle("Sistema de Curadoria — " + usuario.nome + (usuario.admin ? " (Admin)" : ""));
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- 1.MENU LATERAL ---
        JPanel side = new JPanel();
        side.setBackground(UtilMaterial.PRIMARY_DARK);
        side.setPreferredSize(new Dimension(240, 700));
        side.setLayout(new GridLayout(0, 1, 10, 10));
        side.setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));

        // Info do Usuário
        JLabel lblUser = new JLabel(
            "<html><b style='color:white;'>Perfil: " + (usuario.admin ? "Admin" : "Comum") +
            "</b><br/><span style='color:"+ UtilMaterial.BG_HEX +"; font-size: 10pt;'>" +
            usuario.nome + "</span></html>"
        );
        lblUser.setFont(UtilMaterial.PLAIN);
        lblUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        side.add(lblUser);

        JButton bHome = UtilMaterial.materialButton("Página Inicial");
        JButton bPerfil = UtilMaterial.materialButton("Meu Perfil");
        JButton bCadRec = UtilMaterial.materialButton("Cadastrar Recurso");
        JButton bListRec = UtilMaterial.materialButton("Visualizar Recursos");

        side.add(bHome);
        side.add(bPerfil);
        side.add(bCadRec);
        side.add(bListRec);

        // Botões de Admin
        JButton bCadUser = null;
        JButton bListUsers = null;
        JButton bExcluir = null;
        JButton bAdmins = null;

        if (usuario.admin) {
            bCadUser = UtilMaterial.materialButton("Cadastrar Usuário");
            bListUsers = UtilMaterial.materialButton("Gestão de Usuários");
            bExcluir = UtilMaterial.materialButton("Excluir Recurso");
            bAdmins = UtilMaterial.materialButton("Informações Admin");

            side.add(bCadUser);
            side.add(bListUsers);
            side.add(bExcluir);
            side.add(bAdmins);
        }

        side.add(new JLabel(""));
        JButton bSair = UtilMaterial.materialButton("Sair");
        side.add(bSair);

        add(side, BorderLayout.WEST);

        // --- 2. ÁREA CENTRAL (CARDS) ---
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        home = new HomePanel();
        cadRec = new CadastroRecursoPanel();

        // cria painel de listagem de recursos (faltava)
        listRec = new ListagemConteudosPanel();

        // cria painel de perfil (passa o usuário logado)
        perfilPanel = new PerfilPanel(usuario);

        // Painel de exclusão (admin) e info admin (se usuário for admin)
        painelExcluir = new PainelExcluirRecurso();
        adminsInfo = new AdminsInfoPanel();

        // Callback para atualizar lista de usuários após cadastro
        cadUserPanel = new CadastroUsuarioPanel(() -> {
            if (usuario.admin && listUsers != null) {
                listUsers.atualizarLista();
            }
        });

        listUsers = new ListagemUsuariosPanel();

        // Adicionando ao CardLayout — incluí os painéis que faltavam
        cards.add(home, "home");
        cards.add(cadRec, "cadRec");
        cards.add(listRec, "listRec");          // LISTA DE RECURSOS
        cards.add(perfilPanel, "perfil");       // PERFIL
        cards.add(cadUserPanel, "cadUser");
        cards.add(listUsers, "listUsers");
        cards.add(painelExcluir, "excluirRec"); // EXCLUIR RECURSO (admin)
        cards.add(adminsInfo, "adminsInfo");    // INFORMAÇÕES ADMIN

        add(cards, BorderLayout.CENTER);

        // --- 3. AÇÕES DOS BOTÕES (NAVEGAÇÃO) ---
        bHome.addActionListener(e -> cardLayout.show(cards, "home"));

        bCadRec.addActionListener(e -> cardLayout.show(cards, "cadRec"));

        // MOSTRA A LISTA DE RECURSOS e força atualização
        bListRec.addActionListener(e -> {
            listRec.atualizarLista();
            cardLayout.show(cards, "listRec");
        });

        // Mostrar perfil
        bPerfil.addActionListener(e -> {
            // Se quiser atualizar dinamicamente, recrie o painel ou atualize seus campos aqui
            cardLayout.show(cards, "perfil");
        });

        bSair.addActionListener(e -> {
            new TelaLogin().setVisible(true);
            dispose();
        });

        if (usuario.admin) {
            bCadUser.addActionListener(e -> cardLayout.show(cards, "cadUser"));
            bListUsers.addActionListener(e -> {
                listUsers.atualizarLista();
                cardLayout.show(cards, "listUsers");
            });
            // excluir recurso: mostra painel para exclusão e atualiza a tabela
            bExcluir.addActionListener(e -> {
                painelExcluir.refreshList();
                cardLayout.show(cards, "excluirRec");
            });
            bAdmins.addActionListener(e -> {
                cardLayout.show(cards, "adminsInfo");
            });
        }

        // Mostra a home inicialmente
        cardLayout.show(cards, "home");
    }
}
