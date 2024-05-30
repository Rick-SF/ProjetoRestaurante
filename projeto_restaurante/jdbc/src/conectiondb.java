import java.sql.*;

public class conectiondb {
    //Informações de conexão
    private static final String url = "jdbc:mysql://127.0.0.1:3308/restaurante";
    private static final String user = "root";
    private static final String password = "root";
    
    private static Connection conexao = null;

    //Método para estabelecer conexão com o Banco
    public static Connection conectar(){
        try {
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //Método para fechar a conexão com o banco
    public static void fecharConexao(Connection conexao){
        if (conexao != null) {
            try {
                System.out.println("Conexão encerrada.");
                conexao.close();
            } catch (Exception e) {
                System.out.println("Erro no encerramento da conexão." + e.getMessage());
            }
        }
    }
}