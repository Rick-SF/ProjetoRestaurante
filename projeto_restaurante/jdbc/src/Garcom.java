public class Garcom {
    private String nome;
    private int idade;
    private String usuarioGarcom;
    private String senhaGarcom;

    // Classe criada para criar um Garçom
    public Garcom(String nome, int idade, String usuarioGarcom, String senhaGarcom) {
        this.nome = nome;
        this.idade = idade;
        this.usuarioGarcom = usuarioGarcom;
        this.senhaGarcom = senhaGarcom;
    }

    public String getUsuarioGarcom() {
        return usuarioGarcom;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setUsuarioGarcom(String usuarioGarcom) {
        this.usuarioGarcom = usuarioGarcom;
    }

    public void setSenhaGarcom(String senhaGarcom) {
        this.senhaGarcom = senhaGarcom;
    }

    public String getSenhaGarcom() {
        return senhaGarcom;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }
}
