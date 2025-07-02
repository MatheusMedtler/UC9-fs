package Conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexaoDB {

    //Variaveis de conexao
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection conectar() {
        Connection conexao = null;

        try {

                conexao = DriverManager.getConnection(URL,DB_USER,DB_PASSWORD);
                System.out.println("Conectado ao Banco");

        } catch (SQLException error) {//Capturar a excessao
            System.out.println(error.getMessage());
        }
        return conexao;
    }

    public static void fecharConexao(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
                System.out.println("Conexao com o bd fechado!");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexao com o bd");
            }
        }
    }

    public static void main (String[] args) {
        Connection testeConexao = ConexaoDB.conectar();
        if (testeConexao != null) {
            ConexaoDB.fecharConexao(testeConexao);
        }
    }


}
