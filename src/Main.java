import Hash.HashTable;
import Hash.Pessoa;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashTable tabela = new HashTable(5);

        while (true){
            System.out.println("---------MENU---------");
            System.out.println("1 - Cadastrar Pessoa:");
            System.out.println("2 - Buscar pessoa pelo CPF:");
            System.out.println("3 - Mostrar todos casdastros:");
            System.out.println("4 - Excluir cadastro:");
            System.out.println("5 - Sair:");
            System.out.println("Opção: ");

            int op = sc.nextInt();
            sc.nextLine();

            if (op < 1 || op > 5){
                System.out.println("Opção inválida:");
                continue;
            }

            else if (op == 1){
                System.out.println("Informe o nome:");
                String nome = sc.nextLine();
                System.out.println("Informe o número do CPF:");
                String cpf = sc.nextLine();
                System.out.println("Informe a idade:");
                int idade = sc.nextInt();

                Pessoa p = new Pessoa(nome, cpf, idade);
                tabela.inserir(p);
                System.out.println("Cadastro realizado!");

            }else if (op == 2){
                System.out.println("Qual número de CPF deseja buscar?");
                String cpfBusca = sc.nextLine();
                Pessoa p = tabela.buscar(cpfBusca);
                if (p != null){
                    System.out.println("Cadastro encontrado: " + p);
                }

            } else if (op == 3){
                tabela.mostrar();

            } else if (op == 4) {
                System.out.println("Informe o número do CPF para exclusão.");
                String cpfExc = sc.nextLine();
                tabela.excluir(cpfExc);

            }else if (op == 5) {
                System.out.println("Encerrado.");
                break;
            }
        }
        sc.close();
    }
}