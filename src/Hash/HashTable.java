package Hash;

import java.util.Objects;

import java.util.LinkedList;

public class HashTable {
    private LinkedList<Pessoa>[] tabela;
    private int tamanho;
    private int colissoes;
    private int comparacoesBusca;

    public HashTable(int tamanho) {
        this.tamanho = tamanho;
        tabela = new LinkedList[tamanho];
        for (int i = 0; i < tamanho; i++) {
            tabela[i] = new LinkedList<>();
        }
        colissoes = 0;
        comparacoesBusca = 0;
    }

    private int hash(String cpf) {
        return Math.abs(cpf.hashCode()) % tamanho;
    }

    public void inserir(Pessoa pessoa) {
        int pos = hash(pessoa.getCpf());

        if (pessoa.validacaoCPF() == false) {

        } else {
            for (Pessoa p : tabela[pos]) {
                if (p.getCpf().equals(pessoa.getCpf())) {
                    System.out.println("CPF já cadastrado, colisão à vista!");
                }
            }
            if (!tabela[pos].isEmpty()) {
                colissoes++;
            }
            tabela[pos].add(pessoa);
            System.out.println("Cadastro realizado!");
        }

    }

    public Pessoa buscar(String cpf) {


        long inicio = System.nanoTime();
        int pos = hash(cpf);
        for (Pessoa p : tabela[pos]) {
            comparacoesBusca++;
            if (p.getCpf().equals(cpf)) {
                long fim = System.nanoTime();
                System.out.println("Tempo de busca: " + (fim - inicio) + " ns");
                return p;
            }
        }
        long fim = System.nanoTime();
        System.out.println("Tempo de busca: " + (fim - inicio) + " ns");
        return null;
    }

    public boolean remover(String cpf) {
        int pos = hash(cpf);
        return tabela[pos].removeIf(p -> p.getCpf().equals(cpf));
    }

    public void imprimirTabela() {
        for (int i = 0; i < tamanho; i++) {
            System.out.print("Posição " + i + ": ");
            if (tabela[i].isEmpty()) {
                System.out.println("vazio");
            } else {
                for (Pessoa p : tabela[i]) {
                    System.out.print(p + " | ");
                }
                System.out.println();
            }
        }
        System.out.println("Número de colisões: " + colissoes);
        System.out.println("Total de comparações em buscas: " + comparacoesBusca);
    }
}