import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);            // Cria Scanner
        Login login = new Login();                          // Instância da Classe Login
        Admin admin = new Admin();                          // Instância da Classe Admin
        
        while (true) {
            // Exibe as Opções de Login
            login.LoginOpcoes();
            int opcao = leitor.nextInt();
            leitor.nextLine();                      // Limpa o Buffer
            
            switch (opcao) {
                case 1:
                    System.out.printf("\nUsuário: ");       // Usuário insere o Usuário de Garçom
                    String GarcomUsuario = leitor.nextLine();      // Armazena a entrada Usuário

                    System.out.printf("Senha: ");           // Usuário insere a Senha de Garçom
                    String GarcomSenha = leitor.nextLine();        // Armazena a entrada Senha

                    // Faz a Verificação de Usuário e senha pelo Método LoginGarcom
                    if (login.LoginGarcom(GarcomUsuario, GarcomSenha)) {    
                        System.out.println("Login de Garçom Feito!");        // Retornando True, o Login é confirmado
                    } else {
                        System.out.println("Login Inválido.");          // Retornando False, o Login é impedido
                    }
                    break;
                case 2:
                    System.out.printf("\nUsuário: ");       // Usuário Insere o Usuário de Admin
                    String AdminUsuario = leitor.nextLine();       // Armazena a entrada Usuário

                    System.out.printf("Senha: ");           // Usuário Insere a Senha de Admin
                    String AdminSenha = leitor.nextLine();         // Armazena a entrada Senha

                    // Faz a Verificação de Usuário e senha pelo Método LoginAdmin
                    if (login.LoginAdmin(AdminUsuario, AdminSenha)) {   
                        System.out.println("Login de Admin Feito!");        // Retornando True, o Login é confirmado e procede

                        admin.MenuAdmin();                              // Exibe o Menu de Admin
                        int adminopcao = leitor.nextInt();

                        leitor.nextLine();                          // Limpa o Buffer

                        switch (adminopcao) {
                            case 1:
                                System.out.printf("Nome do Prato: ");    
                                String nomePrato = leitor.nextLine();
                                System.out.printf("Valor do Prato: ");
                                double valorPrato = leitor.nextDouble();
                                leitor.nextLine();                              // Limpa o Buffer

                                // Adiciona um novo prato com os parâmetros informados
                                admin.InserirPratos(nomePrato, valorPrato); 
                                
                                break;
                            case 2:
                                admin.AtualizarPratos();                        // Método para atualizar Pratos
                                break;
                            case 3:
                                System.out.println("Nome do Prato a ser Deletado: ");
                                nomePrato = leitor.nextLine();

                                admin.DeletarPratos(nomePrato);
                                break;
                            case 4:
                                continue;
                            default:
                                System.out.println("Opção Inválida");
                                break;
                        }
                    
                    } else {
                        System.out.println("Login Inválido.");              // Retornando False, o Login é impedido.
                        break;
                    }
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção Inválida.");   // Mensagem de erro para opção inválida
            }
            
            leitor.close();
        }
    }
}
