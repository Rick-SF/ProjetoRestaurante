import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);                                    // Cria Scanner
        Login login = new Login();                                                  // Instância da Classe Login
        Admin admin = new Admin();                                                  // Instância da Classe Admin
        LimparTerminal terminal = new LimparTerminal();                             // Instância da Classe LimparTerminal
        boolean inicio = true;                                                      // Inicio do Loop
        
        while (inicio) {
            boolean MenuAdmin = false, MenuAdminAtualizacao = false, logout = false;     // Variáveis de Loops
            login.LoginOpcoes();                    // Exibe as Opções de Login
            int opcao = leitor.nextInt();           // Recebe a opção escolhida
            leitor.nextLine();                      // Limpa o Buffer
            terminal.limpar(500);             // Limpa o Terminal
            
            switch (opcao) {                                
                case 1:// Caso opção "Garçom" escolhida
                    System.out.printf("\nUsuário: ");       // Usuário insere o Usuário de Garçom
                    String GarcomUsuario = leitor.nextLine();      // Armazena a entrada Usuário

                    System.out.printf("Senha: ");           // Usuário insere a Senha de Garçom
                    String GarcomSenha = leitor.nextLine();        // Armazena a entrada Senha

                    // Faz a Verificação de Usuário e senha pelo Método LoginGarcom
                    if (login.LoginGarcom(GarcomUsuario, GarcomSenha)) {     // True, o Login é confirmado
                        System.out.println("Login de Garçom Feito!");        
                    } else {                                                // False, o Login é impedido
                        System.out.println("Login Inválido.");
                        terminal.limpar(1000);
                        continue;                                       // Volta para a tela de Login        
                    }
                case 2:// Caso opção "Admin" escolhida
                    System.out.printf("\nUsuário: ");       // Usuário Insere o Usuário de Admin
                    String AdminUsuario = leitor.nextLine();       // Armazena a entrada Usuário

                    System.out.printf("Senha: ");           // Usuário Insere a Senha de Admin
                    String AdminSenha = leitor.nextLine();         // Armazena a entrada Senha

                    // Faz a Verificação de Usuário e senha de Admin pelo Método LoginAdmin
                    if (login.LoginAdmin(AdminUsuario, AdminSenha)) {        // True, o Login é confirmado e procede
                        System.out.println("Login de Admin Feito!");        
                        terminal.limpar(1000);                        // Limpa o terminal
                        MenuAdmin = true;
                    } else {
                        System.out.println("Login Inválido.");         // Retornando False, o Login é impedido.
                        break;
                    }
                    while (MenuAdmin) {
                        admin.MenuAdmin();                              // Exibe o Menu Opções de Admin
                        int AdminOpcao = leitor.nextInt();             // Recebe a opção de admin escolhida
                        terminal.limpar(500);                   // Limpa o terminal
                        leitor.nextLine();                           // Limpa o Buffer

                        switch (AdminOpcao) {
                            case 1:
                                System.out.printf("Nome do Prato a ser Cadastrado: "); // Entrada para Registrar novo Prato
                                String nomePrato = leitor.nextLine();                      // Armazena novo Prato
                                System.out.printf("Valor do Prato: ");             // Entrada para Registrar Valor do Prato
                                double valorPrato = leitor.nextDouble();                 // Armazena o Valor

                                // Adiciona um novo prato com os parâmetros informados
                                admin.InserirPratos(nomePrato, valorPrato);        // Método para Adicionar os novos pratos
                                terminal.limpar(3000);
                                continue;
                            case 2:
                                MenuAdminAtualizacao = true;
                                while (MenuAdminAtualizacao) {
                                    // Menu do Admin para Atualizações no Banco
                                    System.out.println("\n----------Atualização----------");
                                    System.out.println("1: Mudar Nome de um Prato");
                                    System.out.println("2: Mudar Valor de um Prato");
                                    System.out.println("3: Mudar Disponibilidade de um Prato");
                                    System.out.println("4: Voltar");
                                    System.out.printf("--> ");
                                    int AtualiOpcao = leitor.nextInt();          // Armazena a opção de atualização escolhida
                                    leitor.nextLine();                          // Limpa o Buffer
                                    terminal.limpar(500);
                                    if (AtualiOpcao == 4) {
                                        MenuAdminAtualizacao = false;
                                        continue;
                                    }else{
                                        admin.AtualizarPratos(AtualiOpcao);     // Método para atualizar Pratos
                                    }
                                }
                                continue;
                            case 3:
                                //////////// pratos cadastrados
                                admin.PratosCadastrados();
                                continue;
                            case 4:
                                System.out.printf("Nome do Prato a ser Deletado: ");       
                                nomePrato = leitor.nextLine();                   // Armazena prato escolhido
                                
                                if (admin.VerificarPrato(nomePrato) == true) {      // Verifica se o Prato Existe
                                } else {
                                    System.out.println("Prato não cadastrado ou não existe.");
                                    terminal.limpar(2000);
                                    continue;
                                }

                                admin.DeletarPratos(nomePrato);                  // Deleta o Prato
                                terminal.limpar(2000);                     // Limpa o terminal
                                continue;
                            case 5:
                                System.out.println("Deslogando...");
                                MenuAdmin = false;
                                logout = true;
                                terminal.limpar(1000);
                                continue;
                            default:
                                System.out.println("Opção Inválida");
                                terminal.limpar(1000);
                                continue;
                        }
                    }
                case 3:
                    if (logout == true) {
                        continue;
                    }else{
                        System.out.println("Saindo...");
                        terminal.limpar(500);
                        inicio = false;
                        break;
                    }
                default:
                    System.out.println("Opção Inválida.");             // Mensagem de erro para opção de Login inválida
            }
        }
        conectiondb.fecharConexao(null);
        leitor.close();
        System.out.println("Programa Encerrado.");
    }
}
