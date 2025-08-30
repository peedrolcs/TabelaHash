import Hash.HashTable;
import Hash.Pessoa;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashTable tabela = new HashTable(10);

        while (true){
            System.out.println("----- MENU -----");
            System.out.println("1 - Cadastrar Pessoa");
            System.out.println("2 - Buscar Pessoa por CPF");
            System.out.println("3 - Mostrar Todos");
            System.out.println("4 - Sair");
            System.out.println("Opção: ");
            int op = sc.nextInt();
            sc.nextLine();

            if (op == 1) {
                System.out.println("Digite o CPF: ");
                String cpf = sc.nextLine();
                System.out.println("Digite o Nome: ");
                String nome = sc.nextLine();
                System.out.println("Digite a Idade: ");
                int idade = sc.nextInt();

                Pessoa p = new Pessoa(nome, cpf, idade);
                tabela.inserir(p);
                System.out.println("Pessoa cadastrada com Sucesso!");

            }else if (op == 3) {
                tabela.mostrar();
            } else if (op == 4) {
                System.out.println("Encerrando...");
                break;
            }else {
                System.out.println("Opção inválida!");
            }
        }
        sc.close();
    }
}