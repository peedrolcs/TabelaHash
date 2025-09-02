package Hash;

import java.util.Objects;

public class HashTable {
    private Pessoa[] tabela;
    private int tamanho;

    public HashTable(int tamanho){
        this.tamanho = tamanho;
        tabela = new Pessoa[tamanho];
    }

    public int hash(String cpf){
        return Math.abs(cpf.hashCode() % tamanho);
    }

    public void inserir(Pessoa p){
        int pos = hash(p.getCpf());

        while (tabela[pos] != null){
            if (tabela[pos].getCpf().equals(p.getCpf())){
                System.out.println("CPF, já cadastrado.");
                return;
            }
            pos = (pos + 1) % tamanho;
        }
        tabela[pos] = p;

    }
    public Pessoa buscar(String cpf){
        int pos = hash(cpf);
        int start = pos;

        while (tabela[pos] != null){
            if (tabela[pos].getCpf().equals(cpf)){
                return tabela[pos];
            }
            pos = (pos + 1) % tamanho;
            if (pos == start) break;
        }
        return null;
    }
    public void mostrar(){
        for (Pessoa p : tabela){
            if (p != null){
                System.out.println(p);
            }
        }
    }

    public void excluir (String cpf){
        int pos = hash(cpf);
        int start = pos;

        while (tabela[pos] != null){
            if (tabela[pos].getCpf().equals(cpf)){
                tabela[pos] = null;
                System.out.println("Cadastro removido!");

                reorganizar(pos);
                return;
            }
            pos = (pos + 1) % tamanho;
            if (pos == start) break;
        }
        System.out.println("Cadastro não encotrado.");
    }

    private void reorganizar(int posRemovido){
        int pos = (posRemovido + 1) % tamanho;

        while (tabela[pos] != null){
            Pessoa p = tabela[pos];
            tabela[pos] = null;
            inserir(p);
            pos = (pos + 1) % tamanho;
        }
    }

}