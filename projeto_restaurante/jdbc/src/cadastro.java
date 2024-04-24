import java.sql.*;

public class cadastro {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        conectiondb.conectar();

        conectiondb.fecharConexao(null);
    }
}
