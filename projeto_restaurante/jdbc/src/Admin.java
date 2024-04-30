import java.sql.*;
import java.util.Scanner;

public class Admin {
    Scanner leitor = new Scanner(System.in);
    LimparTerminal terminal = new LimparTerminal();
    
    // Método para exibir opções de Admin
    public void MenuAdmin(){
        System.out.println("\n----------Opções de Admin----------");
        System.out.println("1: Cadastrar Pratos");
        System.out.println("2: Atualizar Pratos");
        System.out.println("3: Deletar Pratos");
        System.out.println("4: Logout");
        System.out.printf("--> ");
    }
    
    // Método para inserir pratos novos
    public void InserirPratos(String nomePrato, double valorPrato){
        String sql = "INSERT INTO prato (nome, valor, disponivel) VALUES (?, ?, ?)";
    
        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);
            stmt.setDouble(2, valorPrato);
            stmt.setString(3, "1");

            int LinhasAfetadas = stmt.executeUpdate();
            System.out.println("Prato "+ nomePrato +" pelo valor de R$"+ valorPrato +" cadastrado.");
            System.out.println("Linhas Afetadas: "+ LinhasAfetadas);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Método para atualizar pratos existentes
    public void AtualizarPratos(int AtualiOpcao){
        switch (AtualiOpcao) {
            case 1:
                System.out.printf("Prato no qual deseja mudar o nome: ");
                String antigoNome = leitor.nextLine();
                System.out.printf("Insira o Novo Nome: ");
                String novoNome = leitor.nextLine();

                String sql = "UPDATE prato SET nome = ? WHERE nome = ?";

                try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
    
                    stmt.setString(1, novoNome);
                    stmt.setString(2, antigoNome);
    
                    int LinhasAfetadas = stmt.executeUpdate();
                    System.out.println("Nome do Prato "+ antigoNome +" atualizado para " + novoNome +".");
                    System.out.println("Linhas afetadas: "+ LinhasAfetadas);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case 2:
                System.out.printf("Prato no qual deseja mudar o preço: ");
                String Prato = leitor.nextLine();
                System.out.printf("Novo preço do prato: ");
                String NovoPreco = leitor.nextLine();

                sql = "UPDATE prato SET valor = ? WHERE nome = ?";

                try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
    
                    stmt.setString(1, NovoPreco);
                    stmt.setString(2, Prato);
    
                    int LinhasAfetadas = stmt.executeUpdate();
                    System.out.println("Preço do Prato " + Prato +" atualizado para R$" + NovoPreco +".");
                    System.out.println("Linhas afetadas: "+ LinhasAfetadas);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case 3:
                ////////////// mudar disponibilidade
                break;
            default:
                break;
        }
    }  

    // Método para deletar pratos existentes
    public void DeletarPratos(String nomePrato){
        String sql = "DELETE FROM prato WHERE nome = ?";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);

            int LinhasAfetadas = stmt.executeUpdate();
            System.out.println("Prato "+ nomePrato +" Deletado.");
            System.out.println("Linhas Afetadas: " + LinhasAfetadas);
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}