package Hash;

import java.util.Scanner;

public class ArvoreBinariaNaoRecursiva {
    // Definindo a classe que representará cada elemento (nó) da árvore binária
    private static class ARVORE {
        public int num;
        public ARVORE dir, esq;
    }

    // Definindo a classe que representará cada elemento da pilha auxiliar
    // A pilha é usada para os percursos não recursivos
    private static class PILHA {
        public ARVORE no; // Alterado de 'num' para 'no' para maior clareza
        public PILHA prox;
    }

    public static void main(String[] args) { // Corrigido 'String args[]' para 'String[] args' (convenção)
        Scanner entrada = new Scanner(System.in);

        // Variáveis da Árvore
        ARVORE raiz = null;
        ARVORE aux;
        ARVORE novo;

        // Variáveis da Pilha (usadas nos percursos)
        PILHA topo;
        PILHA aux_pilha;

        int op, achou, numero;

        do {
            System.out.println("\nMENU DE OPÇÕES");
            System.out.println("1 - Inserir na árvore");
            System.out.println("2 - Consultar um nó da árvore");
            System.out.println("3 - Consultar toda a árvore em Ordem");
            System.out.println("4 - Consultar toda a árvore em Pré-Ordem");
            System.out.println("5 - Consultar toda a árvore em Pós-Ordem");
            System.out.println("6 - Excluir um nó da árvore");
            System.out.println("7 - Esvaziar a árvore");
            System.out.println("8 - Sair");
            System.out.print("Digite sua opção: ");
            op = entrada.nextInt();

            if (op < 1 || op > 8) {
                System.out.println("Opção inválida!!");
                continue; // Volta para o início do loop
            }

            switch (op) {
                case 1: // Inserir
                    System.out.print("Digite o número a ser inserido na árvore: ");
                    novo = new ARVORE();
                    novo.num = entrada.nextInt();
                    novo.dir = null;
                    novo.esq = null;
                    if (raiz == null) {
                        raiz = novo;
                    } else {
                        aux = raiz;
                        // Loop infinito que só será quebrado quando a posição correta for encontrada
                        while (true) {
                            if (novo.num < aux.num) {
                                if (aux.esq == null) {
                                    aux.esq = novo;
                                    break; // Sai do loop
                                } else {
                                    aux = aux.esq;
                                }
                            } else { // Se for maior ou igual, vai para a direita
                                if (aux.dir == null) {
                                    aux.dir = novo;
                                    break; // Sai do loop
                                } else {
                                    aux = aux.dir;
                                }
                            }
                        }
                    }
                    System.out.println("Número inserido na árvore!!");
                    break;

                case 2: // Consultar
                    if (raiz == null) {
                        System.out.println("Árvore vazia!!");
                    } else {
                        System.out.print("Digite o elemento a ser consultado: ");
                        numero = entrada.nextInt();
                        achou = 0;
                        aux = raiz;
                        while (aux != null) {
                            if (aux.num == numero) {
                                achou = 1;
                                break; // Encontrou, pode sair do loop
                            } else if (numero < aux.num) {
                                aux = aux.esq;
                            } else {
                                aux = aux.dir;
                            }
                        }
                        // A verificação é feita fora do loop para não imprimir a mensagem várias vezes
                        if (achou == 1) {
                            System.out.println("Número encontrado na árvore!");
                        } else {
                            System.out.println("Número não encontrado na árvore!");
                        }
                    }
                    break;

                case 3: // Percurso em Ordem (Esquerda, Raiz, Direita)
                    if (raiz == null) {
                        System.out.println("Árvore vazia!!");
                    } else {
                        System.out.println("\nListando todos os elementos da árvore em Ordem:");
                        aux = raiz;
                        topo = null; // Esvazia a pilha
                        while (aux != null || topo != null) {
                            if (aux != null) {
                                aux_pilha = new PILHA();
                                aux_pilha.no = aux;
                                aux_pilha.prox = topo;
                                topo = aux_pilha;
                                aux = aux.esq;
                            } else {
                                aux_pilha = topo;
                                System.out.print(aux_pilha.no.num + " ");
                                aux = topo.no.dir;
                                topo = topo.prox;
                            }
                        }
                        System.out.println();
                    }
                    break;

                case 4: // Percurso em Pré-Ordem (Raiz, Esquerda, Direita)
                    if (raiz == null) {
                        System.out.println("Árvore vazia!!");
                    } else {
                        System.out.println("\nListando todos os elementos da árvore em Pré-Ordem:");
                        aux = raiz;
                        topo = null; // Esvazia a pilha

                        // Empilha a raiz para começar
                        aux_pilha = new PILHA();
                        aux_pilha.no = aux;
                        aux_pilha.prox = null;
                        topo = aux_pilha;

                        while (topo != null) {
                            // Desempilha um nó e o imprime
                            aux_pilha = topo;
                            topo = topo.prox;
                            System.out.print(aux_pilha.no.num + " ");

                            // Empilha o filho direito (se existir)
                            if (aux_pilha.no.dir != null) {
                                PILHA novo_topo = new PILHA();
                                novo_topo.no = aux_pilha.no.dir;
                                novo_topo.prox = topo;
                                topo = novo_topo;
                            }
                            // Empilha o filho esquerdo (se existir)
                            if (aux_pilha.no.esq != null) {
                                PILHA novo_topo = new PILHA();
                                novo_topo.no = aux_pilha.no.esq;
                                novo_topo.prox = topo;
                                topo = novo_topo;
                            }
                        }
                        System.out.println();
                    }
                    break;

                case 5: // Percurso em Pós-Ordem (Esquerda, Direita, Raiz)
                    if (raiz == null) {
                        System.out.println("Árvore vazia!!");
                    } else {
                        System.out.println("\nListando todos os elementos da árvore em Pós-Ordem:");
                        topo = null;
                        PILHA pilha2 = null;

                        // Empilha a raiz na primeira pilha
                        aux_pilha = new PILHA();
                        aux_pilha.no = raiz;
                        aux_pilha.prox = topo;
                        topo = aux_pilha;

                        // Processa a primeira pilha e empilha o resultado na segunda
                        while (topo != null) {
                            // Desempilha da primeira pilha
                            PILHA atual = topo;
                            topo = topo.prox;

                            // Empilha na segunda pilha
                            atual.prox = pilha2;
                            pilha2 = atual;

                            // Empilha filhos esquerdo e direito na primeira pilha
                            if (atual.no.esq != null) {
                                aux_pilha = new PILHA();
                                aux_pilha.no = atual.no.esq;
                                aux_pilha.prox = topo;
                                topo = aux_pilha;
                            }
                            if (atual.no.dir != null) {
                                aux_pilha = new PILHA();
                                aux_pilha.no = atual.no.dir;
                                aux_pilha.prox = topo;
                                topo = aux_pilha;
                            }
                        }

                        // Imprime todos os elementos da segunda pilha
                        while (pilha2 != null) {
                            System.out.print(pilha2.no.num + " ");
                            pilha2 = pilha2.prox;
                        }
                        System.out.println();
                    }
                    break;

                case 6: // Excluir
                    if (raiz == null) {
                        System.out.println("Árvore vazia!!");
                    } else {
                        System.out.print("Digite o número a ser excluído: ");
                        numero = entrada.nextInt();

                        ARVORE atual = raiz;
                        ARVORE pai = null;
                        achou = 0;

                        // 1. Encontrar o nó a ser removido
                        while (atual != null) {
                            if (atual.num == numero) {
                                achou = 1;
                                break;
                            }
                            pai = atual;
                            if (numero < atual.num) {
                                atual = atual.esq;
                            } else {
                                atual = atual.dir;
                            }
                        }

                        if (achou == 0) {
                            System.out.println("Número não encontrado para exclusão.");
                        } else {
                            // 2. Lógica de remoção baseada nos filhos do nó
                            // Caso 1: Nó é uma folha (não tem filhos)
                            if (atual.esq == null && atual.dir == null) {
                                if (atual == raiz) {
                                    raiz = null;
                                } else if (pai.esq == atual) {
                                    pai.esq = null;
                                } else {
                                    pai.dir = null;
                                }
                            }
                            // Caso 2: Nó tem apenas um filho (direito ou esquerdo)
                            else if (atual.esq == null) { // Apenas filho direito
                                if (atual == raiz) {
                                    raiz = atual.dir;
                                } else if (pai.esq == atual) {
                                    pai.esq = atual.dir;
                                } else {
                                    pai.dir = atual.dir;
                                }
                            } else if (atual.dir == null) { // Apenas filho esquerdo
                                if (atual == raiz) {
                                    raiz = atual.esq;
                                } else if (pai.esq == atual) {
                                    pai.esq = atual.esq;
                                } else {
                                    pai.dir = atual.esq;
                                }
                            }
                            // Caso 3: Nó tem dois filhos
                            else {
                                ARVORE sucessor = atual.dir;
                                ARVORE paiSucessor = atual;
                                while (sucessor.esq != null) {
                                    paiSucessor = sucessor;
                                    sucessor = sucessor.esq;
                                }
                                // Copia o valor do sucessor para o nó atual
                                atual.num = sucessor.num;

                                // Remove o nó sucessor (que agora é um caso 1 ou 2)
                                if (paiSucessor.esq == sucessor) {
                                    paiSucessor.esq = sucessor.dir;
                                } else {
                                    paiSucessor.dir = sucessor.dir;
                                }
                            }
                            System.out.println("Número removido com sucesso!");
                        }
                    }
                    break;

                case 7: // Esvaziar
                    raiz = null;
                    System.out.println("Árvore esvaziada!!");
                    break;

                case 8: // Sair
                    System.out.println("Encerrando o programa...");
                    break;

                default: // Caso a verificação inicial falhe
                    System.out.println("Opção inválida!!");
                    break;
            }

        } while (op != 8);

        entrada.close(); // Boa prática: fechar o Scanner ao final
}
}
