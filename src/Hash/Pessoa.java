package Hash;

public class Pessoa implements Validacao {
    private String cpf;
    private String nome;
    private int idade;

    public Pessoa(String cpf, String nome, int idade) {
        this.cpf = cpf;
        this.nome = nome;
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    @Override
    public boolean validacaoCPF() {
        if (cpf != null && cpf.length() == 11) {
            return true;
        } else {
            System.out.println("CPF inválido. Verifique a quantidade de dígitos.");
            return false;
        }

    }
    @Override
    public String toString() {
        return "CPF: " + cpf + ", Nome: " + nome + ", Idade: " + idade;
    }

}