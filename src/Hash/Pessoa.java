package Hash;

public class Pessoa {
    private String nome;
    private String cpf;
    private int idade;

    public Pessoa(){

    }
    public Pessoa(String nome, String cpf, int idade){
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }
    public String getNome(){
        return nome;
    }
    public int getIdade(){
        return idade;
    }
    public void setNome (String nome){
        this.nome = nome;
    }
    public void setCpf(String cpf){
        this.cpf = cpf;
    }
    public void setIdade(int idade){
        this.idade = idade;
    }

    @Override
    public String toString(){
        return "Nome: " + nome + " - cpf: " + cpf + " - Idade: " + idade;
    }
}