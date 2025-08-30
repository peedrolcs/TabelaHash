package Hash;

public class Pessoa {
    private String name;
    private String CPF;
    private int idade;

    public Pessoa() {
        this.name = name;
        this.CPF = CPF;
        this.idade = idade;
    }

    public Pessoa(String name, String CPF, int idade) {
        this.name = name;
        this.CPF = CPF;
        this.idade = idade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    @Override
    public String toString(){
        return "Nome: " + name + " CPF: " + CPF + " Idade: " + idade;
    }
}
