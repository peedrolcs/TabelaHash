import Hash.HashTable;
import Hash.Pessoa;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        HashTable tabela = new HashTable(10);
        int opcao;


        for (int i = 00; i < 30; i++) {
            String cpf = String.format("%011d", i);
            System.out.println("                ");
            System.out.println("CPF " + i + " de teste: " + cpf +".");
            tabela.inserir(new Pessoa(cpf, "Pessoa" + i, 20 + (i % 10)));

        }

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1 - Inserir pessoa");
            System.out.println("2 - Buscar pessoa");
            System.out.println("3 - Remover pessoa");
            System.out.println("4 - Imprimir tabela");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.print("Digite o CPF: ");
                String cpf = scanner.nextLine();
                System.out.print("Digite o Nome: ");
                String nome = scanner.nextLine();
                System.out.print("Digite a Idade: ");
                int idade = scanner.nextInt();

                scanner.nextLine();
                tabela.inserir(new Pessoa(cpf, nome, idade));
                
            } else if (opcao == 2) {
                System.out.print("Informe o CPF para busca: ");
                String buscaCpf = scanner.nextLine();
                Pessoa p = tabela.buscar(buscaCpf);
                if (p != null && p.validacaoCPF() == true) {
                    System.out.println(p);
                } else {
                    System.out.println("Não encontrado. Verifique se o cadastro existe ou se a quantidade de dígitos está correta.");
                }
            } else if (opcao == 3) {
                System.out.print("Informe o CPF para remover: ");
                String removeCpf = scanner.nextLine();
                boolean removido = tabela.remover(removeCpf);
                if (removido == true) {
                    System.out.println("CPF removido com sucesso!");
                } else {
                    System.out.println("CPF não encontrado, digite um CPF já cadastrado ou verifique a quantidade de dígitos!");
                }
            } else if (opcao == 4) {
                tabela.imprimirTabela();
            } else if (opcao == 0) {
                System.out.println("Saindo...");
                break;
            }
            else {
                System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}