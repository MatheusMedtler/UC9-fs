package Controller;

import Model.Pokemon;
import Util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PokemonController {


    public boolean confereGABRIEL(String texto){
        List<Character> letrasTexto = new ArrayList<>();

        for(int i = 0; i<texto.length(); i++){
            letrasTexto.add(texto.charAt(i));
        }

        for(Character letra: letrasTexto) {
            if (!Character.isLetter(letra)){
                return false;
            }
        }
        return true;
    }

    public void cadastrarPokemon(Pokemon pokemon) throws Exception {
        // ====== Validações antes de abrir sessão/transação ======
        if (pokemon.getNome() == null || pokemon.getNome().trim().isEmpty()) {
            throw new Exception("O nome do Pokemon é obrigatório");
        }

        if (!pokemon.getNome().matches("[a-zA-Z\\s'’♀♂-]+")) {
            throw new Exception("Só pode letras, espaços e alguns símbolos no nome");
        }

        if (pokemon.getTipo_primario() == null || pokemon.getTipo_primario().trim().isEmpty()) {
            throw new Exception("Tipo primário é obrigatório");
        }

        if (pokemon.getTipo_primario() != null) {
            for (char c : pokemon.getTipo_primario().toCharArray()) {
                if (!Character.isLetter(c)) {
                    throw new Exception("Só pode letras no tipo primário");
                }
            }
        }

        if (pokemon.getTipo_secundario() != null) {
            for (char c : pokemon.getTipo_secundario().toCharArray()) {
                if (!Character.isLetter(c)) {
                    throw new Exception("Só pode letras no tipo secundário");
                }
            }
        }

        if (pokemon.getTipo_primario().equalsIgnoreCase(pokemon.getTipo_secundario())) {
            throw new Exception("Não é permitido inserir o mesmo tipo primário e secundário");
        }

        if (pokemon.getNivel() <= 0 || pokemon.getNivel() > 100) {
            throw new Exception("O nível deve ser entre 1 e 100");
        }

        if (pokemon.getHpMaximo() <= 0) {
            throw new Exception("O HP Máximo deve ser maior que 0");
        }

        // ====== Inserção no banco ======
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(pokemon);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao cadastrar Pokemon", e);
        }
    }

    public List<Pokemon> listarTodosPokemon() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pokemon> query = session.createQuery("FROM Pokemon order by id", Pokemon.class);
            return query.getResultList();
        }
    }

    public Pokemon buscarPorID(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Pokemon.class, id); // Retorna objeto ou null
        }
    }

    public List<Pokemon> buscarPokemonPorNome(String nome) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pokemon> query = session.createQuery(
                    "FROM Pokemon p WHERE p.nome = :nome", Pokemon.class);
            query.setParameter("nome", nome);
            return query.getResultList();
        }
    }

    public void atualizarPokemon(Pokemon pokemon) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(pokemon);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao atualizar Pokemon", e);
        }
    }

    public void removerPokemon(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Pokemon pokemon = session.get(Pokemon.class, id);
            if (pokemon != null) {
                session.remove(pokemon);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Erro ao remover Pokemon", e);
        }
    }

    public void cadastrarListaPokemon(List<Pokemon> listaPokemon) throws Exception {

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            for (int i = 0; i < listaPokemon.size(); i++) {
                Pokemon p = listaPokemon.get(i);

                if (!p.getNome().matches("[a-zA-Z\\s'’♀♂-]+")) {
                    throw new Exception("Só pode letras, espaços e alguns símbolos no nome");
                }



                if (p.getTipo_primario() != null) {
                    for (char c : p.getTipo_primario().toCharArray()) {
                        if (!Character.isLetter(c)) {
                            throw new Exception("So pode Letras no tipo 1");
                        }
                    }
                }

                if (p.getTipo_secundario() != null) {
                    for (char c : p.getTipo_secundario().toCharArray()) {
                        if (!Character.isLetter(c)) {
                            throw new Exception("So pode Letras no tipo 2");
                        }
                    }
                }

                if (p.getTipo_primario() != null && p.getTipo_secundario() != null
                        && p.getTipo_primario().equalsIgnoreCase(p.getTipo_secundario())) {
                    throw new Exception("Não é permitido inserir o mesmo Tipo");
                }



                if (p.getNome() == null || p.getNome().trim().isEmpty()) {
                    throw new Exception("O nome do Pokemon é obrigatório");
                }
                if (String.valueOf(p.getNivel()).trim().isEmpty()) {
                    throw new Exception("Nivel obrigatório.");
                }
                if (p.getTipo_primario() == null || p.getTipo_primario().trim().isEmpty()) {
                    throw new Exception("Tipo primário é obrigatório");
                }
                if (p.getNivel() < 0 || p.getNivel() > 101) {
                    throw new Exception("O nivel deve ser maior que 0 e menor que 100");
                }

                if (p.getHpMaximo() <= 0) {
                    throw new IllegalArgumentException("O HP Máximo deve ser maior que 0.");
                }
                session.persist(p);
            }
            transaction.commit();
        } catch (Exception e){
            throw new RuntimeException("Erro ao cadastrar Pokemon", e);
        }
    }

    public void curarTodosPokemons() {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<?> query = session.createQuery("UPDATE Pokemon p SET p.hpAtual = p.hpMaximo");
            int atualizados = query.executeUpdate();
            System.out.println("A");
            System.out.println(atualizados);
            System.out.println("C");

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erro ao curar todos os Pokémons", e);
        }
    }


    public void ajustarNiveisPorTipo(String tipo, int novoNivel) {
        if (novoNivel < 0 || novoNivel > 100) {
            throw new IllegalArgumentException("O nível deve ser entre 1 e 100.");
        }

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Query<?> query = session.createQuery("UPDATE Pokemon p SET p.nivel = :novoNivel WHERE p.tipoPrimario = :tipo OR p.tipoSecundario = :tipo");
            query.setParameter("novoNivel", novoNivel);
            query.setParameter("tipo", tipo);
            int atualizados = query.executeUpdate();
            System.out.println("A");
            System.out.println(atualizados);
            System.out.println("C");

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            throw new RuntimeException("Erro ao ajustar níveis por tipo", e);
        }
    }

}
