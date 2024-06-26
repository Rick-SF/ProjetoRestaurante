import java.sql.*;
import java.util.Scanner;

public class Admin {
    Scanner leitor = new Scanner(System.in);
    Auxilios auxilios = new Auxilios();
    Login login = new Login();
    Garcom garcom;
    
    // Método para exibir opções de Admin
    public void MenuAdmin(){
        System.out.println("----------Opções de Admin----------");
        System.out.println("1: Cadastrar Pratos");
        System.out.println("2: Atualizar Pratos");
        System.out.println("3: Pratos Cadastrados");
        System.out.println("4: Deletar Pratos");
        System.out.println("5: Administrar Garçons");
        System.out.println("6: Logout");
        System.out.printf("--> ");
    }

    
    // Método para exibir opções de Atualizar Pratos
    public void MenuAdminAtualizar(){
        System.out.println("\n----------Atualização----------");
        System.out.println("1: Mudar Nome de um Prato");
        System.out.println("2: Mudar Valor de um Prato");
        System.out.println("3: Mudar Disponibilidade de um Prato");
        System.out.println("4: Voltar");
        System.out.printf("--> ");
    }
    
    // Método para exibir opções de Administrar Garçons
    public void MenuAdminGarcom(){
        System.out.println("----------Garçons----------");
        System.out.println("1: Cadastrar novo Garçom");
        System.out.println("2: Excluir Garçom");
        System.out.println("3: Garçons Cadastrados");
        System.out.println("4: Voltar");
        System.out.printf("--> ");
    }
    
    // Método para Cadastrar novos Pratos
    public void CadastrarPratos(String nomePrato, double valorPrato){
        String sql = "INSERT INTO prato (nome, valor, disponivel) VALUES (?, ?, ?)";
    
        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);
            stmt.setDouble(2, valorPrato);
            stmt.setString(3, "sim");
            stmt.executeUpdate();

            System.out.printf("\nPrato '%s' pelo valor de R$%.2f cadastrado.\n",nomePrato, valorPrato);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    // Método para atualizar pratos existentes
    public void AtualizarPratos(int AtualiOpcao){
        switch (AtualiOpcao) {
            case 1: // Mudar nome de um Prato
                System.out.printf("Prato no qual deseja mudar o nome: ");
                String antigoNome = leitor.nextLine();

                String nomePrato = antigoNome;
                if(VerificarPrato(nomePrato)){         // Verifica se o prato existe
                    // Se sim, continua...
                } else{                                             
                    System.out.println("Prato não cadastrado ou não existe.");
                    auxilios.limparTerminal(2000);                                      // Senão, Informa e não continua
                    break;
                }
                
                System.out.printf("Insira o Novo Nome: ");
                String novoNome = leitor.nextLine();

                String sql = "UPDATE prato SET nome = ? WHERE nome = ?";

                try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
    
                    stmt.setString(1, novoNome);
                    stmt.setString(2, antigoNome);
                    stmt.executeUpdate();

                    System.out.printf("\nNome do Prato '%s' atualizado para '%s'.\n", antigoNome, novoNome);
                    auxilios.limparTerminal(3000);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case 2: // Mudar Valor de um Prato
                System.out.printf("Prato no qual deseja mudar o preço: ");
                nomePrato = leitor.nextLine();
                System.out.printf("Novo preço do prato: ");
                double NovoPreco = leitor.nextDouble();

                if (VerificarPrato(nomePrato)) {            // Verifica se o Prato Existe
                    // Se sim, continua...
                }else{
                    System.out.println("Prato não cadastrado ou não existe.");
                    auxilios.limparTerminal(2000);                                      // Senão, Informa e não continua
                    break;
                }

                sql = "UPDATE prato SET valor = ? WHERE nome = ?";                  // Query para mudar preço de um Prato

                try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
    
                    stmt.setDouble(1, NovoPreco);
                    stmt.setString(2, nomePrato);
                    stmt.executeUpdate();

                    System.out.printf("Preço do Prato '%s' atualizado para R$%.2f.", nomePrato, NovoPreco);
                }catch(SQLException e){
                    e.printStackTrace();
                }
                break;
            case 3: // Mudar Disponibilidade de um Prato
                System.out.printf("Prato no qual deseja mudar a disponibilidade: ");
                nomePrato = leitor.nextLine();                          // Armazena o Prato a ser atualizado

                if (VerificarPrato(nomePrato) == true) {                // Verifica se o Prato Existe
                    // Se sim, continua...
                }else{                                              
                    System.out.println("Prato não existe ou não cadastrado.");
                    auxilios.limparTerminal(2000);                                      // Senão, informa e não continua
                    break;
                }

                System.out.printf("Disponivel('1') ou Indisponivel('0'): ");
                int disponibilidade = leitor.nextInt();                     // armazena opção de disponibilidade escolhida
                leitor.nextLine();                                          // Limpa o Buffer

                sql = "UPDATE prato SET disponivel = ? WHERE nome = ?";             // Query para Atualizar disponibilidade

                if (disponibilidade == 1) {
                    try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
                        
                    // Muda na tabela prato a disponibilidade do prato caso "Disponível" escolhido
                    stmt.setString(1, "sim");
                    stmt.setString(2, nomePrato);
                    stmt.executeUpdate();
                    
                    System.out.println("Disponibilidade do Prato '"+ nomePrato +"' atualizado para Disponivel.");
                    auxilios.limparTerminal(3000);

                    }catch(SQLException e){
                    e.printStackTrace();
                    }
                } else if(disponibilidade == 0) {
                    try (Connection conexao = conectiondb.conectar();
                    PreparedStatement stmt = conexao.prepareStatement(sql)){
                    
                    // Muda na tabela prato a disponibilidade do prato caso "Indisponível" escolhido
                    stmt.setString(1, "nao");
                    stmt.setString(2, nomePrato);
                    stmt.executeUpdate();

                    System.out.println("Disponibilidade do Prato '"+ nomePrato +"' atualizado para Indisponivel.");
                    auxilios.limparTerminal(3000);

                    }catch(SQLException e){
                    e.printStackTrace();
                    }
                }else {
                    System.out.println("Opção Inválida");
                    auxilios.limparTerminal(1000);
                    break;
                }
                break;
            default:
                break;
        }
    }

    // Método para retornar os pratos cadastrado no Banco
    public boolean PratosCadastrados(){
        String sql = "SELECT nome, valor, disponivel FROM prato";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){
            
            ResultSet rs = stmt.executeQuery();

            int count = 0;    // variável para testar se há pratos cadastrados
            
            while (rs.next()) {
                String prato = rs.getString("nome");
                String disponivel = rs.getString("disponivel");
                double valor = rs.getDouble("valor");
                System.out.printf("--------------------\nPrato: %s\nValor: R$%.2f\nDisponivel: %s\n",prato, valor, disponivel);
                count++;
            }

            if (count == 0) {       // Testa se foi encontrado Pratos cadastrados
                return false;       // Retorna False, se não encontrado Pratos
            }
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return true;                // Retorna True, para se encontrado Pratos
    }

    // Método para deletar pratos existentes
    public void DeletarPratos(String nomePrato){
        String sql = "DELETE FROM prato WHERE nome = ?";

        // Estabelecendo Conexão e preparando os parâmetros das QUERIES
        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);
            stmt.executeUpdate();

            System.out.println("Prato "+ nomePrato +" Deletado.");
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void AdministrarGarcons(int opcaoAdminGarcom){
        switch (opcaoAdminGarcom) {
            case 1: // Cria novo Garçom
                garcom = new Garcom(null, 0, null, null);       // Criando novo garçom
                garcom.setNome(auxilios.StringNaoVazia("Nome do Garçom: "));              // Define nome do Garçom
                System.out.printf("Idade do Garçom: ");                                    // Define a idade do garçom
                garcom.setIdade(leitor.nextInt());
                leitor.nextLine();                                                               // Limpa o buffer
                garcom.setUsuarioGarcom(auxilios.StringNaoVazia("Usuario do Garçom: ")); // Define o usuário de Login
                garcom.setSenhaGarcom(auxilios.StringNaoVazia("Senha do Garçom: ")); // Define a senha de Login
                auxilios.limparTerminal(500);
                // Manda os dados do novo garçom como parâmetro para o método de Criar Garçom na classe Login
                login.CriarGarcom(garcom.getNome(), garcom.getIdade(), garcom.getUsuarioGarcom(), garcom.getSenhaGarcom());
                auxilios.limparTerminal(3000);
                break;
            case 2: // Deletar um Garçom
                System.out.printf("Insira o nome do Garçom para deletar: ");
                String nomeGarcom = leitor.nextLine();

                // Preparando as QUERIES
                String sqlGarcomID = "SELECT id FROM garcom WHERE nome = ?";
                String sqlLogin = "DELETE FROM login WHERE id_garcom = ?";
                String sqlGarcom = "DELETE FROM garcom WHERE id = ?";

                // Estabelecendo Conexão e preparando os parâmetros das QUERIES
                try (Connection conexao = conectiondb.conectar();
                PreparedStatement stmt1 = conexao.prepareStatement(sqlGarcomID);
                PreparedStatement stmt2 = conexao.prepareStatement(sqlLogin);
                PreparedStatement stmt3 = conexao.prepareStatement(sqlGarcom)){

                    // Obter o ID do Garçom
                    stmt1.setString(1, nomeGarcom);
                    ResultSet rs = stmt1.executeQuery();

                    if (rs.next()) {
                        int GarcomID = rs.getInt("id");

                        // Deletar credencias de Login do Garçom da tabela login através do ID obtido
                        stmt2.setInt(1, GarcomID);
                        stmt2.executeUpdate();

                        // Deletar Garçom da tabela garcom através do mesmo ID
                        stmt3.setInt(1, GarcomID);
                        stmt3.executeUpdate();


                        // Executar as operações caso dê tudo certo
                        System.out.println("Garçom "+nomeGarcom+" Deletado com sucesso.");
                        auxilios.limparTerminal(3000);
                        break;
                    } else {
                        throw new SQLException("Garçom não encontrado com o nome: "+ nomeGarcom);
                    }
                } catch(SQLException e){
                    System.out.println("Erro ao Deletar Dados. "+ e.getMessage());
                }
                auxilios.limparTerminal(2000);
                break;
            case 3: // Garçons Cadastrados
                String sql = "SELECT nome, idade FROM garcom";
                
                // Estabelecendo Conexão e preparando os parâmetros da QUERIE sql
                try (Connection conexao = conectiondb.conectar();
                PreparedStatement stmt = conexao.prepareStatement(sql)){

                    ResultSet rs = stmt.executeQuery();

                    int count = 0;    // variável para testar se há garçons cadastrados
            
                    while (rs.next()) {
                        String nome = rs.getString("nome");
                        double idade = rs.getInt("idade");
                        System.out.printf("--------------------\nNome do Garçom: %s\nIdade: %.0f\n", nome, idade);
                        count++;
                    }
                    
                    if (count == 0) {       // Testa se foi encontrado Garçons cadastrados
                        System.out.println("Não há Garçons cadastrados.");;
                    }

                } catch (SQLException e){
                    System.out.println("Erro ao consultar dados. " + e.getMessage());
                }
                // Opção Voltar após mostrar os resultados
                System.out.println("--------------------\n1: Voltar");
                System.out.printf("--> ");
                int voltar = leitor.nextInt();
                if (voltar == 1) {
                    Main.voltarGarcons = true;                             // Volta para o Menu
                    auxilios.limparTerminal(300);
                    System.out.printf("Voltando...");
                    auxilios.limparTerminal(500);
                    break;                                               
                }else{                                                     // Volta para o Menu
                    Main.voltarGarcons = true;                             
                    auxilios.limparTerminal(300);
                    System.out.printf("Voltando...");
                    auxilios.limparTerminal(500);
                    break;
                }
            default:
                System.out.println("Opção inválida.");
                auxilios.limparTerminal(500);
                break;
            
        }
    }

    // Método para verificar se Prato existe no Banco
    public boolean VerificarPrato(String nomePrato){
        String sql = "SELECT COUNT(*) FROM prato WHERE nome = ?";

        try (Connection conexao = conectiondb.conectar();
            PreparedStatement stmt = conexao.prepareStatement(sql)){

            stmt.setString(1, nomePrato);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    return true;
                } else{
                    return false;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}