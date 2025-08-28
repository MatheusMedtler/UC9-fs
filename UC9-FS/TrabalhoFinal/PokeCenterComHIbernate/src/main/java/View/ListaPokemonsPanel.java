package View;
import Controller.PokemonController;
import Json.LerJson;
import Model.Pokemon;

import javax.swing.*;
        import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

    public class ListaPokemonsPanel extends JInternalFrame {

        private PokemonController controller;
        private JTable tabelaPokemons;
        private DefaultTableModel tableModel;
        private JButton btnAtualizar, btnRemover, btnBuscar, btnEditar, btnCurarTodos, btnAjustarNivel, btnInserirEMLOTEEE;
        private JTextField txtBuscaNome;

        public ListaPokemonsPanel(PokemonController controller) {
            super("Lista de Pokémons", true, true, true, true);
            this.controller = controller;

            setSize(1000, 1000);
            setLayout(new BorderLayout());

            String[] colunas = {"ID", "Nome", "Tipo Primário", "Tipo Secundário", "Nível", "HP Máximo","HP Atual"};
            tableModel = new DefaultTableModel(colunas, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tabelaPokemons = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(tabelaPokemons);
            add(scrollPane, BorderLayout.CENTER);

            JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
            txtBuscaNome = new JTextField(20);
            btnBuscar = new JButton("Buscar por Nome");
            btnAtualizar = new JButton("Atualizar Tabela");
            btnRemover = new JButton("Remover Selecionado");
            btnEditar = new JButton("Editar Selecionado");
            btnInserirEMLOTEEE = new JButton("Ineserir Lista");
            btnCurarTodos = new JButton("Curar Todos");
            btnAjustarNivel = new JButton("Ajustar Nível por Tipo");


            panelAcoes.add(new JLabel("Nome:"));
            panelAcoes.add(txtBuscaNome);
            panelAcoes.add(btnBuscar);
            panelAcoes.add(btnAtualizar);
            panelAcoes.add(btnRemover);
            panelAcoes.add(btnEditar);
            panelAcoes.add(btnInserirEMLOTEEE);
            panelAcoes.add(btnCurarTodos);
            panelAcoes.add(btnAjustarNivel);

            add(panelAcoes, BorderLayout.NORTH);

            btnAtualizar.addActionListener(e -> carregarPokemonsNaTabela());
            btnRemover.addActionListener(e -> removerPokemonSelecionado());
            btnBuscar.addActionListener(e -> buscarPokemonsPorNome());
            btnEditar.addActionListener(e -> editarPokemonSelecionado());
            btnInserirEMLOTEEE.addActionListener(e-> inserirLOTE());
            btnCurarTodos.addActionListener(e -> curarTodosPokemons());
            btnAjustarNivel.addActionListener(e ->trocarLVL());

            carregarPokemonsNaTabela();
        }

        private void carregarPokemonsNaTabela() {
            txtBuscaNome.setText("");
            tableModel.setRowCount(0);
            List<Pokemon> pokemons = controller.listarTodosPokemon();
            for (Pokemon pokemon : pokemons) {
                tableModel.addRow(new Object[]{
                        pokemon.getId_pokemon(),
                        pokemon.getNome(),
                        pokemon.getTipo_primario(),
                        pokemon.getTipo_secundario() != null ? pokemon.getTipo_secundario() : "",
                        pokemon.getNivel(),
                        pokemon.getHpMaximo(),
                        pokemon.getHpAtual()
                });
            }
        }

        private void removerPokemonSelecionado() {
            int selectedRow = tabelaPokemons.getSelectedRow();
            if (selectedRow >= 0) {
                int idPokemon = (int) tableModel.getValueAt(selectedRow, 0);

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Tem certeza que deseja remover o Pokémon ID: " + idPokemon + "?",
                        "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        controller.removerPokemon(idPokemon);
                        JOptionPane.showMessageDialog(this, "Pokémon removido com sucesso!");
                        carregarPokemonsNaTabela();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Erro ao remover Pokémon: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um Pokémon para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }

        private void buscarPokemonsPorNome() {
            String nomeBusca = txtBuscaNome.getText().trim();
            tableModel.setRowCount(0);

            List<Pokemon> pokemons = controller.buscarPokemonPorNome(nomeBusca);

            if (pokemons.isEmpty() && !nomeBusca.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum Pokémon encontrado com o nome: '" + nomeBusca + "'", "Busca", JOptionPane.INFORMATION_MESSAGE);
            }

            for (Pokemon pokemon : pokemons) {
                tableModel.addRow(new Object[]{
                        pokemon.getId_pokemon(),
                        pokemon.getNome(),
                        pokemon.getTipo_primario(),
                        pokemon.getTipo_secundario() != null ? pokemon.getTipo_secundario() : "",
                        pokemon.getNivel(),
                        pokemon.getHpMaximo(),
                        pokemon.getHpAtual()
                });
            }
        }

        private void editarPokemonSelecionado() {
            int selectedRow = tabelaPokemons.getSelectedRow();
            if (selectedRow >= 0) {
                int idPokemon = (int) tableModel.getValueAt(selectedRow, 0);

                PokemonForm pokemonForm = new PokemonForm(controller, idPokemon);
                this.getDesktopPane().add(pokemonForm);
                pokemonForm.setVisible(true);
                pokemonForm.toFront();

                pokemonForm.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                    @Override
                    public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                        carregarPokemonsNaTabela();
                    }
                });

            } else {
                JOptionPane.showMessageDialog(this, "Selecione um Pokémon para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }

        private boolean verificarLOTE = false;
        private void inserirLOTE() {
            if (verificarLOTE) {
                JOptionPane.showMessageDialog(this, "A lista de Pokémons já foi inserida Meu PARE!");
                return;
            }
            try {
                JOptionPane.showMessageDialog(this, "Carga de Pokémons concluída com sucesso!");
                List<Pokemon> pokemonsParaInserir = LerJson.lerPokemonsDoJson();
                controller.cadastrarListaPokemon(pokemonsParaInserir);
                  System.out.println("SSSSSSucessoooooooooooooo!");
                verificarLOTE = true;
            } catch (IOException e) {
                System.err.println("Erro ao carregar o arquivo JSON: " + e.getMessage());
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        private void curarTodosPokemons() {
            controller.curarTodosPokemons();
            carregarPokemonsNaTabela();
            JOptionPane.showMessageDialog(this, "Pokémons curados!");
        }

        public void trocarLVL() {
            String tipo = JOptionPane.showInputDialog(this, "Informe o tipo do Pokémon:");
            String nivelStr = JOptionPane.showInputDialog(this, "Informe o novo nível:");
            if (tipo != null && nivelStr != null) {
                if(!tipo.matches("[a-zA-Z\\s]+")) {
                    JOptionPane.showMessageDialog(this, "Tipo inválido! Use apenas letras e espaços.");
                    return;
                }
                int novoNivel = Integer.parseInt(nivelStr);
                if(novoNivel < 1 || novoNivel > 100) {
                    JOptionPane.showMessageDialog(this, "Nível deve estar entre 1 e 100.");
                    return;
                }
                try {
                    controller.ajustarNiveisPorTipo(tipo, novoNivel);
                    carregarPokemonsNaTabela(); // Atualiza a tabela após ajuste
                    JOptionPane.showMessageDialog(this, "Pokémons do tipo " + tipo + " atualizados para nível " + novoNivel);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Nível inválido!");
                }
            }
        }
    }

