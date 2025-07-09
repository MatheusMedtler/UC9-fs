package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PImysql {


        // Informações de conexão com o MySQL
        private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/Pi";
        private static final String MYSQL_USUARIO = "root";
        private static final String MYSQL_SENHA = "root";

        // Método para estabelecer a conexão com o banco MySQL
        public static Connection conectarMySQL() {
            Connection conexao = null;
            try {
                conexao = DriverManager.getConnection(MYSQL_URL, MYSQL_USUARIO, MYSQL_SENHA);
                System.out.println("Conexão com MySQL estabelecida com sucesso!");
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao MySQL: " + e.getMessage());
            }
            return conexao;
        }

        // Método para inserir um novo usuário no banco de dados
        public static void setUsuario(int ID_Usuario,String nome, String cpf, String telefone, String email, String senha, String dataCadastro) {
            String sql = "INSERT INTO Usuario (ID_Usuario,Nome_Completo_Usuario, CPF_Usuario, Telefone_USuario, Email_Usuario, Senha_Usuario, Data_Cadastro) VALUES (?,?, ?, ?, ?, ?, ?)";
            Connection conexao = conectarMySQL();
            PreparedStatement stmt = null;

            try {
                if (conexao != null) {
                    stmt = conexao.prepareStatement(sql);
                    stmt.setInt(1,ID_Usuario);
                    stmt.setString(2, nome);
                    stmt.setString(3, cpf);
                    stmt.setString(4, telefone);
                    stmt.setString(5, email);
                    stmt.setString(6, senha);
                    stmt.setString(7, dataCadastro);

                    int linhasAfetadas = stmt.executeUpdate();

                    if (linhasAfetadas > 0) {
                        System.out.println("Usuário " + nome + " inserido no BD com sucesso!");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Erro ao inserir usuário no banco: " + e.getMessage());
            } finally {
                try {
                    if (stmt != null) stmt.close();
                    if (conexao != null) conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar recursos após inserção: " + e.getMessage());
                }
            }
        }

        // Método para exibir todos os usuários cadastrados no banco
        public static void getTodosUsuarios() {
            String sql = "SELECT * FROM Usuario";
            Connection conexao = conectarMySQL();
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                if (conexao != null) {
                    stmt = conexao.prepareStatement(sql);
                    rs = stmt.executeQuery();

                    System.out.println("\n--- Todos os Usuários Cadastrados ---");
                    while (rs.next()) {
                        int id = rs.getInt("ID_Usuario");
                        String nome = rs.getString("Nome_Completo_Usuario");
                        String cpf = rs.getString("CPF_Usuario");
                        String telefone = rs.getString("Telefone_USuario");
                        String email = rs.getString("Email_Usuario");
                        String senha = rs.getString("Senha_Usuario");
                        String dataCadastro = rs.getString("Data_Cadastro");

                        System.out.println("ID: " + id + ", Nome: " + nome + ", CPF: " + cpf + ", Telefone: " + telefone + ", Email: " + email + ", Senha: " + senha + ", Data de Cadastro: " + dataCadastro);
                    }
                }
            } catch (SQLException e) {
                System.err.println("Erro ao consultar usuários no banco: " + e.getMessage());
            } finally {
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conexao != null) conexao.close();
                } catch (SQLException e) {
                    System.err.println("Erro ao fechar recursos após consulta: " + e.getMessage());
                }
            }
        }

        // Método main para rodar a aplicação
        public static void main(String[] args) {
            // Inserir dados de exemplo
            setUsuario(2,"pedro silva", "12345678901", "11987654321", "joao@gmail.com", "senha123", "2025-07-04");
            setUsuario(3,"gabriel guerreiro", "10987654321", "11976543210", "maria@gmail.com", "senha456", "2025-07-05");
            setUsuario(4,"arthur vargas", "11223344556", "11965432109", "pedro@gmail.com", "senha789", "2025-07-06");

            // Exibir todos os usuários cadastrados
            getTodosUsuarios();
        }
    }

