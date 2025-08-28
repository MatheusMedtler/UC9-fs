import Controller.PokemonController;
import Controller.TreinadorController;
import Model.Pokemon;
import Model.Treinador;
import View.ListaPokemonsPanel;
import View.ListaTreinadoresPanel;
import View.PokemonForm;
import View.TreinadorForm;

import javax.swing.*;

public class Main extends JFrame {

    private JDesktopPane desktopPane;
    private PokemonController pokemonController;
    private TreinadorController treinadorController;

    public Main() {
        super("Sistema de Gerenciamento de Pokémons");

        this.pokemonController = new PokemonController();
        this.treinadorController = new TreinadorController();

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Pokémons
        JMenu menuPokemons = new JMenu("Pokémons");
        JMenuItem itemCadastrarPokemon = new JMenuItem("Cadastrar Pokémon");
        JMenuItem itemListarPokemons = new JMenuItem("Listar Pokémons");

        itemCadastrarPokemon.addActionListener(e -> openPokemonForm(null));
        itemListarPokemons.addActionListener(e -> openListaPokemonPanel());

        menuPokemons.add(itemCadastrarPokemon);
        menuPokemons.add(itemListarPokemons);
        menuBar.add(menuPokemons);

        // Menu Treinadores
        JMenu menuTreinadores = new JMenu("Treinadores");
        JMenuItem itemCadastrarTreinador = new JMenuItem("Cadastrar Treinador");
        JMenuItem itemListarTreinadores = new JMenuItem("Listar Treinadores");

        itemCadastrarTreinador.addActionListener(e -> openTreinadorForm(null));
        itemListarTreinadores.addActionListener(e -> openListaTreinadorPanel());

        menuTreinadores.add(itemCadastrarTreinador);
        menuTreinadores.add(itemListarTreinadores);
        menuBar.add(menuTreinadores);

        // Menu Sair
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.addActionListener(e -> System.exit(0));
        menuSair.add(itemSair);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void openTreinadorForm(Integer idTreinador) {
        TreinadorForm treinadorForm = new TreinadorForm(treinadorController, idTreinador);
        desktopPane.add(treinadorForm);
        treinadorForm.setVisible(true);
        treinadorForm.toFront();
    }

    private void openListaTreinadorPanel() {
        ListaTreinadoresPanel listaTreinador = new ListaTreinadoresPanel(treinadorController);
        desktopPane.add(listaTreinador);
        listaTreinador.setVisible(true);
        listaTreinador.toFront();
    }

    private void openPokemonForm(Integer idPokemon) {
        PokemonForm pokemonForm = new PokemonForm(pokemonController, idPokemon);
        desktopPane.add(pokemonForm);
        pokemonForm.setVisible(true);
        pokemonForm.toFront();
    }

    private void openListaPokemonPanel() {
        ListaPokemonsPanel listaPokemon = new ListaPokemonsPanel(pokemonController);
        desktopPane.add(listaPokemon);
        listaPokemon.setVisible(true);
        listaPokemon.toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
