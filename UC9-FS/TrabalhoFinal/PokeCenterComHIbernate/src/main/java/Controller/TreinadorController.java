package Controller;

import Model.Treinador;
import org.hibernate.Session;
import org.hibernate.Transaction;
import Util.HibernateUtil;
import org.hibernate.query.Query;

import java.util.List;

public class TreinadorController {

    public TreinadorController() {}

    public void cadastrarTreinador(Treinador treinador) throws Exception {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Validações
            if (treinador.getNome() == null || treinador.getNome().trim().isEmpty()) {
                throw new Exception("O nome do treinador é obrigatório.");
            }

            /*if (!pokemon.getNome().matches("[a-zA-Z\\s]+")) {
                throw new Exception("O nome não pode conter números ou caracteres especiais.");
            }*/

            if (treinador.getCidade() == null || treinador.getCidade().trim().isEmpty()) {
                throw new Exception("Você deve inserir a Cidade do treinador!");
            }

            session.persist(treinador);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new Exception("Erro ao cadastrar o Treinador: " + e.getMessage(), e);
        }
    }

    public List<Treinador> listarTodosTreinadores() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Treinador> query = session.createQuery("FROM Treinador", Treinador.class);
            return query.getResultList();
        }
    }


    public Treinador buscarTreinadorPorId(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Treinador.class, id);
        }
    }

    public void atualizarTreinador(Treinador pokemon) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(pokemon);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Erro ao atualizar o Treinador", e);
        }
    }

    public void removerTreinador(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Treinador treinador = session.get(Treinador.class, id);
            if (treinador != null) {
                session.remove(treinador);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw new RuntimeException("Erro ao remover Treinador", e);
        }
    }

    public List<Treinador> buscarTreinadorPorNome(String nome) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Treinador> query = session.createQuery(
                    "FROM Treinador WHERE lower(nome) LIKE :nome", Treinador.class
            );
            query.setParameter("nome", "%" + nome.toLowerCase() + "%");
            return query.getResultList();
        }
    }
}
