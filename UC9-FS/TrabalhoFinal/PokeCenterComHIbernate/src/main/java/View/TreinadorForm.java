package View;

import Controller.TreinadorController;
import Model.Treinador;

import javax.swing.*;
import java.awt.*;

public class TreinadorForm extends JInternalFrame {

    private TreinadorController controller;
    private JTextField txtId, txtNome, txtCidade;
    private JButton btnSalvar, btnBuscar;
    private Integer treinadorIdParaEdicao;

    public TreinadorForm(TreinadorController controller, Integer treinadorId) {
        super("Cadastro de Treinador", true, true, true, true);
        this.controller = controller;
        this.treinadorIdParaEdicao = treinadorId;

        setSize(600, 400);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 45, 5, 45);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        int row = 0;

        // Campo ID
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = row;
        txtId = new JTextField(10);
        txtId.setEditable(false);
        add(txtId, gbc);
        gbc.gridx = 2; gbc.gridy = row;

        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarTreinador());
        add(btnBuscar, gbc);
        row++;



        // Nome
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        txtNome = new JTextField(25);
        add(txtNome, gbc);
        row++;

        // Cidade
        gbc.gridx = 0; gbc.gridy = row;
        add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1; gbc.gridy = row; gbc.gridwidth = 2;
        txtCidade = new JTextField(25);
        add(txtCidade, gbc);
        row++;



        // Botão Salvar
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 3;
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarTreinador());
        add(btnSalvar, gbc);

        if (treinadorIdParaEdicao != null) {
            carregarTreinadorParaEdicao(treinadorIdParaEdicao);
            txtId.setText(String.valueOf(treinadorIdParaEdicao));
            btnBuscar.setEnabled(false);
        }
    }

    private void buscarTreinador() {
        String idStr = JOptionPane.showInputDialog(this, "Digite o ID do Treinador para buscar:");
        if (idStr != null && !idStr.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                carregarTreinadorParaEdicao(id);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido. Por favor, digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void carregarTreinadorParaEdicao(int id) {
        try {
            Treinador treinador = controller.buscarTreinadorPorId(id);
            if (treinador != null) {
                txtId.setText(String.valueOf(treinador.getId_treinador()));
                txtNome.setText(treinador.getNome());
                txtCidade.setText(treinador.getCidade());

            } else {
                JOptionPane.showMessageDialog(this, "Treinador com ID " + id + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                limparCampos();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar Treinador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarTreinador() {
        try {
            String nome = txtNome.getText().trim();
            String Cidade = txtCidade.getText().trim();




            if (treinadorIdParaEdicao == null) {
                Treinador novo = new Treinador();
                System.out.println(novo);
                novo.setNome(nome);
                novo.setCidade(Cidade);

                TreinadorController po = new TreinadorController();

                po.cadastrarTreinador(novo);

                JOptionPane.showMessageDialog(this, "Treinador cadastrado com sucesso!");
            } else {
                Treinador novo = new Treinador();
                novo.setId_treinador(treinadorIdParaEdicao);
                System.out.println(novo.getId_treinador());
                novo.setNome(nome);
                novo.setCidade(Cidade);
                controller.atualizarTreinador(novo);
                JOptionPane.showMessageDialog(this, "Treinador atualizado com sucesso!");
            }
            this.dispose();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nível ou HP Máximo inválido. Por favor, insira valores numéricos válidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) { // Captura exceções do Model (validações de Nível/HP)
            JOptionPane.showMessageDialog(this, "Erro de validação (Model): " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) { // Captura exceções do Controller
            JOptionPane.showMessageDialog(this, "Erro ao salvar Pokémon: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCidade.setText("");


        treinadorIdParaEdicao = null;
        btnBuscar.setEnabled(true);
    }

}
