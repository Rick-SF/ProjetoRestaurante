import java.util.Scanner;

public class Main {
    static boolean voltarGarcons = false;                          // Variável pública para voltar o menu de mostrar Garçons
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in);                                    // Cria Scanner
        Login login = new Login();                                                  // Instância da Classe Login
        Admin admin = new Admin();                                                  // Instância da Classe Admin
        GarcomUsuario garcomUsuario = new GarcomUsuario();
        Auxilios auxilios = new Auxilios();                                         // Instância da Classe Auxilios
        boolean inicio = true;                                                      // Inicio do Loop
        
        while (inicio) {
            boolean MenuAdmin = false, MenuAdminAtualizacao = false, logoutAdmin = false;     // Variáveis de Loops
            boolean MenuGarcom = false, logoutGarcom = false;
            auxilios.limparTerminal(0);
            login.LoginOpcoes();                                                         // Exibe as Opções de Login
            int opcao = leitor.nextInt();                                                // Recebe a opção escolhida
            leitor.nextLine();                                                           // Limpa o Buffer
            auxilios.limparTerminal(500);                                          // Limpa o terminal
            
            switch (opcao) {                                
                case 1:// (Menu Login) Caso opção "Garçom" escolhida
                    System.out.printf("\nUsuário: ");       // Usuário insere o Usuário de Garçom
                    String usuarioGarcom = leitor.nextLine();      // Armazena a entrada Usuário

                    System.out.printf("Senha: ");           // Usuário insere a Senha de Garçom
                    String senhaGarcom = leitor.nextLine();        // Armazena a entrada Senha
                    auxilios.limparTerminal(500);

                    // Faz a Verificação de Usuário e senha pelo Método LoginGarcom
                    if (login.LoginGarcom(usuarioGarcom, senhaGarcom)) {     // True, o Login é confirmado
                        System.out.println("Login de Garçom Feito!");
                        auxilios.limparTerminal(1000);
                        MenuGarcom = true;
                    } else {                                                // False, o Login é impedido
                        System.out.println("Login Inválido.");
                        auxilios.limparTerminal(1000);
                        continue;                                       // Volta para a tela de Login        
                    }
                    // Menu principal do Garçom
                    while (MenuGarcom) {
                        garcomUsuario.MenuGarcom();                         // Mostra o Menu de Opções
                        int garcomOpcao = leitor.nextInt();                 // Recebe a opção
                        auxilios.limparTerminal(500);
                        leitor.nextLine();

                        switch (garcomOpcao) {
                            case 1: // (Menu Garçom) Anotar Pedido
                                boolean AnotarPedido = true;
                                System.out.printf("Número da Mesa: ");
                                int numMesa = leitor.nextInt();                 // Recebe o número da Mesa
                                leitor.nextLine();                              // Limpar Buffer
                                while (AnotarPedido) {
                                    if(garcomUsuario.MesaExistente(numMesa)){       // Testa se a mesa já tinha algum pedido
                                        if (garcomUsuario.Cardápio()) {             // Mostra o Cardápio
                                            System.out.printf("--------------------\nPrato Escolhido: ");
                                            String pratoPedido = leitor.nextLine();
                                            // Anota esse outro pedido
                                            garcomUsuario.AnotarPedidoNovamente(numMesa ,pratoPedido, usuarioGarcom);
                                            auxilios.limparTerminal(500);
                                            System.out.println("Pedido na mesa "+ numMesa +" Feito!");
                                            System.out.println("1: Anotar outro Pedido");
                                            System.out.println("2: Imprimir Comanda");
                                            System.out.println("3: Voltar");
                                            System.out.print("--> ");
                                            int Opcoes = leitor.nextInt();
                                            leitor.nextLine();
                                            if (Opcoes == 1) {
                                                auxilios.limparTerminal(100);
                                                continue;
                                            }else if (Opcoes == 2) {
                                                auxilios.limparTerminal(1000);
                                                garcomUsuario.ImprimirComanda(numMesa);
                                                System.out.println("1: Continuar");
                                                System.out.printf("--> ");
                                                leitor.nextInt();
                                                AnotarPedido = false;
                                                auxilios.limparTerminal(300);
                                                System.out.println("Voltando...");
                                                auxilios.limparTerminal(1000);
                                                continue;
                                            }else if (Opcoes == 3) {
                                                auxilios.limparTerminal(100);
                                                System.out.println("Voltando...");
                                                auxilios.limparTerminal(500);
                                                break;
                                            }
                                        } else{ // se false no método Cardápio, informa que não há pratos 
                                            auxilios.limparTerminal(500);
                                            System.out.println("Não há pratos cadastrados.");
                                            auxilios.limparTerminal(2000);
                                            break;
                                        }
                                        continue;
                                    }
                                    // Novo pedido
                                    System.out.printf("Pessoas na mesa: ");
                                    int ocupacao = leitor.nextInt();
                                    leitor.nextLine();
                                    auxilios.limparTerminal(500);
                                    if(garcomUsuario.Cardápio()){           // Se true, mostra cardápio e opção de Anotar o prato
                                        System.out.printf("--------------------\nPrato Escolhido: ");
                                        String pratoPedido = leitor.nextLine();
                                        // Anota o novo pedido
                                        garcomUsuario.AnotarNovoPedido(numMesa, ocupacao, pratoPedido, usuarioGarcom);
                                        auxilios.limparTerminal(500);
                                        System.out.println("Pedido na mesa "+ numMesa+" Feito!");
                                        System.out.println("1: Anotar outro Pedido");
                                        System.out.println("2: Imprimir Comanda");
                                        System.out.println("3: Voltar");
                                        System.out.printf("--> ");
                                        int Opcoes = leitor.nextInt();
                                        leitor.nextLine();
                                        if (Opcoes == 1) {
                                            auxilios.limparTerminal(100);
                                            continue;
                                        }else if (Opcoes == 2) {
                                            auxilios.limparTerminal(500);
                                            garcomUsuario.ImprimirComanda(numMesa);
                                            System.out.println("1: Continuar");
                                            System.out.printf("--> ");
                                            leitor.nextInt();
                                            AnotarPedido = false;
                                            auxilios.limparTerminal(500);
                                            System.out.println("Voltando...");
                                            auxilios.limparTerminal(1000);
                                            continue;
                                        }else if (Opcoes == 3) {
                                            auxilios.limparTerminal(100);
                                            System.out.println("Voltando...");
                                            auxilios.limparTerminal(500);
                                            break;
                                        }
                                        auxilios.limparTerminal(2500);
                                    }if (!garcomUsuario.Cardápio()) {
                                        // se false no método, informa que não há pratos 
                                        auxilios.limparTerminal(500);
                                        System.out.println("Não há pratos cadastrados.");
                                        auxilios.limparTerminal(2000);
                                        break;    
                                    }
                                }
                                continue;
                            case 2: // (Menu Garçom) Cardápio
                                if (admin.PratosCadastrados()){                   // Se der true no método, mostra
                                    System.out.printf("--------------------\n1: Voltar\n--> ");
                                } else{
                                    System.out.println("Não há Pratos cadastrados.");  // Se der false, informa
                                    auxilios.limparTerminal(2000);
                                    continue;
                                }
                                int voltar = leitor.nextInt();                      // Opção voltar
                                auxilios.limparTerminal(500);
                                if (voltar == 1) {
                                    System.out.printf("Voltando...");
                                    auxilios.limparTerminal(1000);
                                    continue;                                      // Volta para o Menu
                                }else{
                                    System.out.println("opção inválida");
                                }
                            case 3: // (Menu Garçom) Pedidos
                                garcomUsuario.ConsultarPedidos();
                                System.out.printf("--------------------\n1: Voltar\n--> ");
                                voltar = leitor.nextInt();                      // Opção voltar
                                auxilios.limparTerminal(500);
                                if (voltar == 1) {
                                    System.out.printf("Voltando...");
                                    auxilios.limparTerminal(1000);
                                    continue;                                      // Volta para o Menu
                                }else{
                                    System.out.println("Voltando...");
                                    auxilios.limparTerminal(1000);
                                    continue;
                                }
                            case 4: // (Menu Garçom) Cancelar Pedido
                                auxilios.limparTerminal(300);
                                System.out.printf("Número da mesa onde será cancelado os pedidos: ");
                                numMesa = leitor.nextInt();
                                garcomUsuario.CancelarPedido(numMesa);
                                System.out.println("Pedidos Deletados da mesa número: " + numMesa);
                                auxilios.limparTerminal(3000);
                                continue;
                            case 5: // (Menu Garçom) Imprimir Comanda
                                System.out.printf("Número da Mesa: ");
                                numMesa = leitor.nextInt();
                                auxilios.limparTerminal(500);
                                garcomUsuario.ImprimirComanda(numMesa);
                                System.out.println("1: Continuar");
                                System.out.printf("--> ");
                                leitor.nextInt();
                                auxilios.limparTerminal(500);
                                System.out.println("Voltando...");
                                auxilios.limparTerminal(1000);
                                break;
                            case 6: // (Menu Garçom) Pagar Conta
                                System.out.printf("Mesa a pagar: ");
                                int mesa = leitor.nextInt();
                                auxilios.limparTerminal(500);
                                if(garcomUsuario.pagarConta(mesa)){
                                    // Pagamento Realizado com Sucesso
                                }else{ // Falha no Pagamento
                                    auxilios.limparTerminal(500);
                                    System.out.println("Erro ao fazer Pagamento.");
                                    auxilios.limparTerminal(3000);
                                    break;            
                                }
                                auxilios.limparTerminal(500);
                                continue;                             
                            case 7: // (Menu Garçom) Deslogar
                                System.out.println("Deslogando...");
                                MenuGarcom = false;                               // False para sair do Menu do Admin
                                logoutGarcom = true;                                   // True para voltar ao Menu Login
                                auxilios.limparTerminal(1000);
                                continue;
                        }
                    }
                    // Volta pra tela de login se der Logout no Garçom
                    if (logoutGarcom == true) {
                        break;
                    }
                case 2:// (Menu Login) Caso opção "Admin" escolhida
                    System.out.printf("\nUsuário: ");       // Usuário Insere o Usuário de Admin
                    String usuarioAdmin = leitor.nextLine();       // Armazena a entrada Usuário

                    System.out.printf("Senha: ");           // Usuário Insere a Senha de Admin
                    String senhaAdmin = leitor.nextLine();         // Armazena a entrada Senha
                    auxilios.limparTerminal(500);

                    // Faz a Verificação de Usuário e senha de Admin pelo Método LoginAdmin
                    if (login.LoginAdmin(usuarioAdmin, senhaAdmin)) {        // True, o Login é confirmado e procede
                        System.out.println("Login de Admin Feito!");        
                        auxilios.limparTerminal(1000);                        // Limpa o terminal
                        MenuAdmin = true;
                    } else {
                        System.out.println("Login Inválido.");         // Retornando False, o Login é impedido.
                        auxilios.limparTerminal(1000);
                        break;
                    }
                    // Menu principal do Admin
                    while (MenuAdmin) {
                        admin.MenuAdmin();                              // Exibe o Menu Opções de Admin
                        int AdminOpcao = leitor.nextInt();             // Recebe a opção de admin escolhida
                        auxilios.limparTerminal(500);                   // Limpa o terminal
                        leitor.nextLine();                           // Limpa o Buffer

                        switch (AdminOpcao) {
                            case 1: // (Menu Admin) Cadastrar Pratos
                                System.out.printf("Nome do Prato a ser Cadastrado: "); // Entrada para Registrar novo Prato
                                String nomePrato = leitor.nextLine();                      // Armazena novo Prato
                                System.out.printf("Valor do Prato: ");             // Entrada para Registrar Valor do Prato
                                double valorPrato = leitor.nextDouble();                 // Armazena o Valor

                                // Adiciona um novo prato com os parâmetros informados
                                admin.CadastrarPratos(nomePrato, valorPrato);        // Método para Adicionar os novos pratos
                                auxilios.limparTerminal(3000);
                                continue;
                            case 2: // (Menu Admin) Atualizações de Pratos
                                MenuAdminAtualizacao = true;                    // Boolean para Loop no menu Atualizar Pratos 
                                while (MenuAdminAtualizacao) {
                                    // Menu do Admin localizado na classe admin para Atualizações de prato
                                    admin.MenuAdminAtualizar();
                                    int AtualiOpcao = leitor.nextInt();          // Armazena a opção de atualização escolhida
                                    auxilios.limparTerminal(500);          // Limpa o terminal
                                    if (AtualiOpcao == 4) {                      // Opção de voltar escolhida
                                        MenuAdminAtualizacao = false;            // False no Menu para sair do Loop
                                        continue;
                                    }else{                                      // Else para as outras opções de atualização
                                        admin.AtualizarPratos(AtualiOpcao);     // Método para atualizar Pratos
                                        auxilios.limparTerminal(2000);
                                    }
                                }
                                continue;
                            case 3: // (Menu Admin) Mostrar Pratos Cadastrados
                                if (admin.PratosCadastrados()){                   // Se der true no método, mostra
                                    System.out.printf("--------------------\n1: Voltar\n--> ");
                                } else{
                                    System.out.println("Não há Pratos cadastrados.");  // Se der false, informa
                                    auxilios.limparTerminal(2000);
                                    continue;
                                }
                                int voltar = leitor.nextInt();                      // Opção voltar
                                auxilios.limparTerminal(500);
                                if (voltar == 1) {
                                    System.out.printf("Voltando...");
                                    auxilios.limparTerminal(1000);
                                    continue;                                      // Volta para o Menu
                                }else{
                                    System.out.println("Voltando...");
                                    auxilios.limparTerminal(1000);
                                    continue;
                                }
                            case 4: // (Menu Admin) Deletar Pratos Cadastrados
                                System.out.printf("Nome do Prato a ser Deletado: ");       
                                nomePrato = leitor.nextLine();                   // Armazena prato escolhido
                                
                                if (admin.VerificarPrato(nomePrato)) {   // Verifica se o Prato Existe, se sim, continua
                                } else {
                                    System.out.println("Prato não cadastrado ou não existe.");
                                    auxilios.limparTerminal(2000);
                                    continue;
                                }

                                admin.DeletarPratos(nomePrato);                  // Deleta o Prato
                                auxilios.limparTerminal(2000);             // Limpa o terminal
                                continue;
                            case 5: // (Menu Admin) Administrar Garçons
                                boolean menuAdminGarcons = true;                    
                                while (menuAdminGarcons) {           // Loop menu de administrar garçons para sair e voltar
                                    admin.MenuAdminGarcom();                         // Exibe menu de Administrar Garçons
                                    int opcaoAdminGarcom = leitor.nextInt();         // Recebe a opção escolhida
                                    if (opcaoAdminGarcom == 4) {           // Se escolhida opção voltar, menu fica false e sai
                                        menuAdminGarcons = false;
                                        auxilios.limparTerminal(500);
                                        System.out.println("Voltando...");
                                        auxilios.limparTerminal(500);
                                        continue;
                                    } else {
                                        auxilios.limparTerminal(500);
                                        admin.AdministrarGarcons(opcaoAdminGarcom);  // Opção é decidida no método da classe Admin
                                        if (voltarGarcons) {                         // Voltar para o menu Administrar Garçons
                                            continue;
                                        }
                                    }
                                }
                                continue;
                            case 6: // (Menu Admin) Deslogar Admin
                                System.out.println("Deslogando...");
                                MenuAdmin = false;                               // False para sair do Menu do Admin
                                logoutAdmin = true;                                   // True para voltar ao Menu Login
                                auxilios.limparTerminal(1000);
                                continue;
                            default: // (Menu Admin) Se escolhida uma opção além das ofertadas no menu de admin
                                System.out.println("Opção Inválida");
                                auxilios.limparTerminal(1000);
                                continue;
                        }
                    }
                case 3: // (Menu Login) Encerra Programa
                    if (logoutAdmin == true) {
                        continue;
                    }else{
                        System.out.println("Saindo...");
                        auxilios.limparTerminal(500);
                        inicio = false;
                        break;
                    }
                default:
                    System.out.println("Opção Inválida.");       // Mensagem de erro para opção de Login inválida
            }
        }
        conectiondb.fecharConexao(null);
        leitor.close();
        System.out.println("Programa Encerrado.");
    }
}