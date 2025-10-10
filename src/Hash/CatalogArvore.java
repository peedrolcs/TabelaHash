package Hash;

import java.io.*; // importa classes para entrada/saída (FileReader, BufferedReader, IOException, etc.)
import java.util.*; // importa utilitários como Scanner, List, ArrayList, Queue, LinkedList, etc.
import java.util.concurrent.ArrayBlockingQueue; // importa ArrayBlockingQueue (não usado diretamente, herdado do template)

/**
 * CatalogSystem.java
 * Sistema de Catálogo de Livros usando BST e AVL
 * Segue as orientações da Prática 02 (Estrutura de Dados II).
 *
 * Formato de arquivo esperado: ID;Título;Autor
 *
 * Compilar: javac CatalogSystem.java
 * Executar: java CatalogSystem [caminho_para_arquivo]
 */
public class CatalogArvore { // declara a classe pública principal do programa

    /* ----------------------------- Book ----------------------------- */
    static class Book { // declara a classe interna estática Book que representa um livro
        int id; // campo inteiro que armazena o ID do livro (chave)
        String title; // campo para o título do livro
        String author; // campo para o autor do livro

        Book(int id, String title, String author) { // construtor da classe Book
            this.id = id; // atribui o parâmetro id ao campo id do objeto
            this.title = title; // atribui o parâmetro title ao campo title do objeto
            this.author = author; // atribui o parâmetro author ao campo author do objeto
        }

        @Override // anotação que indica que o método toString sobrescreve o da superclasse
        public String toString() { // método que retorna uma representação em texto do livro
            return String.format("[%d] %s — %s", id, title, author); // formata e retorna ID, título e autor
        }
    }

    /* ----------------------------- BST ----------------------------- */
    static class BST { // declara a classe interna estática BST para a árvore binária de busca
        static class Node { // classe interna que representa um nó na BST
            Book book; // campo que armazena o livro associado ao nó
            Node left, right; // referências para filhos esquerdo e direito
            Node(Book b) { this.book = b; } // construtor que define o livro do nó
        }

        Node root; // referência para a raiz da BST
        // Counters for approximate cost
        long insertComparisons = 0; // contador de comparações durante inserções (aproximação de custo)
        long searchComparisons = 0; // contador de comparações durante buscas
        long removeComparisons = 0; // contador de comparações durante remoções

        public void resetCounters() { // método para zerar os contadores
            insertComparisons = searchComparisons = removeComparisons = 0; // zera todos os contadores
        }

        public void insert(Book b) { // método público para inserir um livro na BST
            root = insertRec(root, b); // chama o método recursivo começando pela raiz e atualiza a raiz
        }

        private Node insertRec(Node node, Book b) { // método recursivo que realiza a inserção real
            if (node == null) { // se o nó atual é nulo, significa posição livre
                return new Node(b); // cria e retorna um novo nó com o livro
            }
            insertComparisons++; // incrementa contador de comparações (visitando nó)
            if (b.id < node.book.id) { // se o id do livro é menor que o do nó atual
                node.left = insertRec(node.left, b); // insere recursivamente na subárvore esquerda
            } else if (b.id > node.book.id) { // se o id é maior que o do nó atual
                node.right = insertRec(node.right, b); // insere recursivamente na subárvore direita
            } else { // se os IDs são iguais
                // IDs devem ser únicos: atualiza dados se mesmo ID
                node.book = b; // atualiza o livro no nó (substitui informações)
            }
            return node; // retorna o nó (possivelmente modificado) para ligação com o pai
        }

        public Book search(int id) { // método público para buscar um livro por ID
            return searchRec(root, id); // chama o método recursivo iniciando pela raiz
        }

        private Book searchRec(Node node, int id) { // método recursivo de busca
            if (node == null) return null; // se nó nulo, não encontrado -> retorna null
            searchComparisons++; // incrementa o contador de comparações de busca
            if (id == node.book.id) return node.book; // se encontrou, retorna o livro
            if (id < node.book.id) return searchRec(node.left, id); // se menor, busca à esquerda
            else return searchRec(node.right, id); // se maior, busca à direita
        }

        public void remove(int id) { // método público para remover um livro por ID
            root = removeRec(root, id); // chama recursivamente e atualiza a raiz
        }

        private Node removeRec(Node node, int id) { // método recursivo de remoção
            if (node == null) return null; // se nó nulo, nada a remover
            removeComparisons++; // incrementa contador de comparações durante remoção
            if (id < node.book.id) { // se id buscado é menor que o do nó atual
                node.left = removeRec(node.left, id); // remove na subárvore esquerda
            } else if (id > node.book.id) { // se id maior
                node.right = removeRec(node.right, id); // remove na subárvore direita
            } else { // encontrado o nó a ser removido
                // encontrado
                if (node.left == null) return node.right; // se só tem filho direito (ou nenhum), retorna direito
                else if (node.right == null) return node.left; // se só tem filho esquerdo, retorna esquerdo
                else { // se tem dois filhos
                    // 2 filhos: pegar sucessor (mínimo na direita)
                    Node min = minNode(node.right); // encontra o menor da subárvore direita
                    node.book = min.book; // substitui o livro do nó pelo sucessor
                    node.right = removeRec(node.right, min.book.id); // remove o sucessor na subárvore direita
                }
            }
            return node; // retorna o nó (ajustado) para ligar ao pai
        }

        private Node minNode(Node node) { // método auxiliar para encontrar o nó mínimo de uma subárvore
            Node cur = node; // começa pelo nó recebido
            while (cur.left != null) cur = cur.left; // desce sempre à esquerda até o menor
            return cur; // retorna o nó mínimo encontrado
        }

        /* Traversals */
        public void inorder(PrintStream out) { inorderRec(root, out); } // inicia travessia em-ordem pela raiz
        private void inorderRec(Node n, PrintStream out) { // recursivo para em-ordem
            if (n == null) return; // se nulo, retorna
            inorderRec(n.left, out); // visita subárvore esquerda
            out.println(n.book); // processa (imprime) o nó atual
            inorderRec(n.right, out); // visita subárvore direita
        }

        public void preorder(PrintStream out) { preorderRec(root, out); } // inicia pré-ordem
        private void preorderRec(Node n, PrintStream out) { // recursivo para pré-ordem
            if (n == null) return; // se nulo, retorna
            out.println(n.book); // processa o nó atual
            preorderRec(n.left, out); // visita esquerda
            preorderRec(n.right, out); // visita direita
        }

        public void postorder(PrintStream out) { postorderRec(root, out); } // inicia pós-ordem
        private void postorderRec(Node n, PrintStream out) { // recursivo para pós-ordem
            if (n == null) return; // se nulo, retorna
            postorderRec(n.left, out); // visita esquerda
            postorderRec(n.right, out); // visita direita
            out.println(n.book); // processa o nó atual por último
        }

        public void breadthFirst(PrintStream out) { // método que executa travessia em largura (BFS)
            if (root == null) return; // se árvore vazia, retorna
            Queue<Node> q = new LinkedList<>(); // cria fila para visitar nós por níveis
            q.add(root); // adiciona a raiz na fila
            while (!q.isEmpty()) { // enquanto houver nós na fila
                Node n = q.poll(); // remove o próximo nó da fila
                out.println(n.book); // imprime o livro do nó
                if (n.left != null) q.add(n.left); // adiciona filho esquerdo se existir
                if (n.right != null) q.add(n.right); // adiciona filho direito se existir
            }
        }

        /* Utility: size */
        public int size() { return sizeRec(root); } // retorna o número de nós chamando recursivo
        private int sizeRec(Node n) { // recursivo que conta nós
            if (n == null) return 0; // se nulo, não conta
            return 1 + sizeRec(n.left) + sizeRec(n.right); // soma 1 (nó atual) + contagem das subárvores
        }
    }

    /* ----------------------------- AVL ----------------------------- */
    static class AVL { // declara a classe interna estática AVL para árvore AVL balanceada
        static class Node { // nó interno da AVL
            Book book; // livro armazenado no nó
            Node left, right; // filhos esquerdo e direito
            int height; // altura do nó (para cálculo de balanceamento)
            Node(Book b) { book = b; height = 1; } // construtor define livro e altura inicial 1
        }

        Node root; // raiz da árvore AVL
        long rotations = 0; // contador de rotações realizadas pela AVL
        long insertComparisons = 0; // contador de comparações em inserções
        long searchComparisons = 0; // contador de comparações em buscas
        long removeComparisons = 0; // contador de comparações em remoções

        public void resetCounters() { // zera todos os contadores da AVL
            rotations = insertComparisons = searchComparisons = removeComparisons = 0; // zera variáveis
        }

        private int height(Node n) { return n == null ? 0 : n.height; } // retorna altura do nó (0 se nulo)
        private int balanceFactor(Node n) { return n == null ? 0 : height(n.left) - height(n.right); } // calcula fator de balanceamento
        private void updateHeight(Node n) { n.height = 1 + Math.max(height(n.left), height(n.right)); } // atualiza altura do nó

        // Rotations
        private Node rightRotate(Node y) { // realiza rotação à direita em torno do nó y
            rotations++; // incrementa contador de rotações
            Node x = y.left; // x será novo topo após rotação (filho esquerdo de y)
            Node T2 = x.right; // T2 guarda a subárvore que mudará de posição
            x.right = y; // faz y virar filho direito de x
            y.left = T2; // reanexa T2 como filho esquerdo de y
            updateHeight(y); // atualiza altura de y após mudanças
            updateHeight(x); // atualiza altura de x após mudanças
            return x; // retorna novo topo da subárvore (x)
        }

        private Node leftRotate(Node x) { // realiza rotação à esquerda em torno do nó x
            rotations++; // incrementa contador de rotações
            Node y = x.right; // y será novo topo após rotação (filho direito de x)
            Node T2 = y.left; // T2 guarda a subárvore que será movida
            y.left = x; // faz x virar filho esquerdo de y
            x.right = T2; // reanexa T2 como filho direito de x
            updateHeight(x); // atualiza altura de x
            updateHeight(y); // atualiza altura de y
            return y; // retorna novo topo (y)
        }

        public void insert(Book b) { // método público para inserir na AVL
            root = insertRec(root, b); // chama recursivamente e atualiza raiz
        }

        private Node insertRec(Node node, Book b) { // recursivo que insere e re-balanceia
            if (node == null) return new Node(b); // se posição livre, cria e retorna novo nó
            insertComparisons++; // incrementa contador de comparações durante inserção
            if (b.id < node.book.id) node.left = insertRec(node.left, b); // insere à esquerda se id menor
            else if (b.id > node.book.id) node.right = insertRec(node.right, b); // insere à direita se id maior
            else { node.book = b; return node; } // se mesmo id, atualiza o livro e retorna

            updateHeight(node); // atualiza altura do nó após inserção nas subárvores
            int bf = balanceFactor(node); // calcula fator de balanceamento do nó

            // Left Left
            if (bf > 1 && b.id < node.left.book.id) return rightRotate(node); // caso LL -> rotação direita
            // Right Right
            if (bf < -1 && b.id > node.right.book.id) return leftRotate(node); // caso RR -> rotação esquerda
            // Left Right
            if (bf > 1 && b.id > node.left.book.id) { // caso LR -> rotação dupla (esquerda no filho, depois direita)
                node.left = leftRotate(node.left); // primeiro rotaciona à esquerda o filho esquerdo
                return rightRotate(node); // depois rotaciona à direita o nó atual
            }
            // Right Left
            if (bf < -1 && b.id < node.right.book.id) { // caso RL -> rotação dupla (direita no filho, depois esquerda)
                node.right = rightRotate(node.right); // primeiro rotaciona à direita o filho direito
                return leftRotate(node); // depois rotaciona à esquerda o nó atual
            }
            return node; // retorna nó (possivelmente re-balanceado)
        }

        public Book search(int id) { return searchRec(root, id); } // busca pública que chama recursivo
        private Book searchRec(Node node, int id) { // recursivo da busca na AVL
            if (node == null) return null; // se nulo, não encontrado
            searchComparisons++; // incrementa contador de comparações
            if (id == node.book.id) return node.book; // se encontrou, retorna o livro
            if (id < node.book.id) return searchRec(node.left, id); // se menor, vai para esquerda
            else return searchRec(node.right, id); // se maior, vai para direita
        }

        public void remove(int id) { root = removeRec(root, id); } // método público para remover por ID

        private Node removeRec(Node node, int id) { // recursivo que remove e re-balanceia
            if (node == null) return null; // se nulo, nada a remover
            removeComparisons++; // incrementa contador de comparações durante remoção
            if (id < node.book.id) node.left = removeRec(node.left, id); // se menor, remove à esquerda
            else if (id > node.book.id) node.right = removeRec(node.right, id); // se maior, remove à direita
            else { // encontrado nó a remover
                // found
                if (node.left == null || node.right == null) { // se tem no máximo um filho
                    Node temp = node.left != null ? node.left : node.right; // pega o filho existente (ou null)
                    if (temp == null) { node = null; } // sem filhos: torna nulo (remove)
                    else node = temp; // um filho: substitui nó pelo filho
                } else { // dois filhos
                    // two children: get min of right subtree
                    Node min = minNode(node.right); // encontra o sucessor (mínimo à direita)
                    node.book = min.book; // copia dados do sucessor para o nó atual
                    node.right = removeRec(node.right, min.book.id); // remove o sucessor na subárvore direita
                }
            }
            if (node == null) return null; // se após remoção o nó ficou nulo, retorna nulo

            updateHeight(node); // atualiza altura após remoção
            int bf = balanceFactor(node); // recalcula fator de balanceamento

            // LL
            if (bf > 1 && balanceFactor(node.left) >= 0) return rightRotate(node); // caso LL -> rotação direita
            // LR
            if (bf > 1 && balanceFactor(node.left) < 0) { // caso LR -> rotação dupla
                node.left = leftRotate(node.left); // rotaciona esquerda no filho
                return rightRotate(node); // rotaciona direita no nó
            }
            // RR
            if (bf < -1 && balanceFactor(node.right) <= 0) return leftRotate(node); // caso RR -> rotação esquerda
            // RL
            if (bf < -1 && balanceFactor(node.right) > 0) { // caso RL -> rotação dupla
                node.right = rightRotate(node.right); // rotaciona direita no filho
                return leftRotate(node); // rotaciona esquerda no nó
            }
            return node; // retorna nó re-balanceado
        }

        private Node minNode(Node n) { // encontra o menor nó (mais à esquerda) em uma subárvore
            Node cur = n; // começa pelo nó dado
            while (cur.left != null) cur = cur.left; // segue à esquerda até não haver mais
            return cur; // retorna o nó mínimo
        }

        /* Traversals mirror BST methods */
        public void inorder(PrintStream out) { inorderRec(root, out); } // inicia em-ordem na AVL
        private void inorderRec(Node n, PrintStream out) { // recursivo em-ordem
            if (n == null) return; // se nulo, retorna
            inorderRec(n.left, out); // visita esquerda
            out.println(n.book); // imprime o livro no nó atual
            inorderRec(n.right, out); // visita direita
        }

        public void preorder(PrintStream out) { preorderRec(root, out); } // inicia pré-ordem
        private void preorderRec(Node n, PrintStream out) { // recursivo pré-ordem
            if (n == null) return; // se nulo retorna
            out.println(n.book); // imprime o nó atual primeiro
            preorderRec(n.left, out); // visita esquerda
            preorderRec(n.right, out); // visita direita
        }

        public void postorder(PrintStream out) { postorderRec(root, out); } // inicia pós-ordem
        private void postorderRec(Node n, PrintStream out) { // recursivo pós-ordem
            if (n == null) return; // se nulo retorna
            postorderRec(n.left, out); // visita esquerda
            postorderRec(n.right, out); // visita direita
            out.println(n.book); // imprime o nó por último
        }

        public void breadthFirst(PrintStream out) { // travessia em largura da AVL
            if (root == null) return; // se vazia, retorna
            Queue<Node> q = new LinkedList<>(); // cria fila para BFS
            q.add(root); // adiciona raiz
            while (!q.isEmpty()) { // enquanto houver nós na fila
                Node n = q.poll(); // remove próximo da fila
                out.println(n.book); // imprime o livro do nó
                if (n.left != null) q.add(n.left); // adiciona esquerdo se existir
                if (n.right != null) q.add(n.right); // adiciona direito se existir
            }
        }

        /* Utility: size */
        public int size() { return sizeRec(root); } // retorna tamanho chamando recursivo
        private int sizeRec(Node n) { // recursivo que conta nós na AVL
            if (n == null) return 0; // se nulo, retorna 0
            return 1 + sizeRec(n.left) + sizeRec(n.right); // soma recursivamente
        }
    }

    /* ----------------------------- Main / UI ----------------------------- */
    public static void main(String[] args) { // método principal que inicia a aplicação
        Scanner sc = new Scanner(System.in); // cria Scanner para ler entrada do usuário (teclado)
        BST bst = new BST(); // instancia uma BST vazia
        AVL avl = new AVL(); // instancia uma AVL vazia

        // If a filepath provided, try to load books
        if (args.length >= 1) { // se passou argumento na linha de comando
            String path = args[0]; // pega o primeiro argumento como caminho do arquivo
            try { // tenta carregar o arquivo
                List<Book> books = readFromFile(path); // chama função que lê o arquivo e retorna lista de livros
                for (Book b : books) { // para cada livro lido
                    bst.insert(b); // insere na BST
                    avl.insert(b); // insere na AVL
                }
                System.out.printf("Arquivo carregado (%d livros) de: %s%n", books.size(), path); // informa sucesso
            } catch (Exception e) { // captura exceção caso ocorra erro ao ler arquivo
                System.err.println("Erro ao carregar arquivo: " + e.getMessage()); // imprime mensagem de erro
            }
        }

        while (true) { // loop principal do menu que se repete até o usuário sair
            System.out.println("\n--- Sistema de Catálogo de Livros ---"); // cabeçalho do menu
            System.out.println("1. Inserir livro manualmente"); // opção 1
            System.out.println("2. Carregar livros de arquivo"); // opção 2
            System.out.println("3. Buscar livro por ID"); // opção 3
            System.out.println("4. Remover livro por ID"); // opção 4
            System.out.println("5. Listar (In-ordem)"); // opção 5
            System.out.println("6. Listar (Pré-ordem)"); // opção 6
            System.out.println("7. Listar (Pós-ordem)"); // opção 7
            System.out.println("8. Listar (Em largura)"); // opção 8
            System.out.println("9. Comparar BST x AVL (tamanhos, rotações, contadores)"); // opção 9
            System.out.println("0. Sair"); // opção 0 para sair
            System.out.print("Opcao: "); // pede ao usuário para digitar a opção
            String opt = sc.nextLine().trim(); // lê a linha digitada e remove espaços em volta, armazenando em opt
            try { // bloco try para tratar possíveis exceções nas operações do menu
                switch (opt) { // estrutura de seleção com base no valor de opt
                    case "1": // caso o usuário escolha "1" (inserir manualmente)
                        Book b = promptBook(sc); // chama função que pede dados do livro via console e retorna Book
                        // BST insert with timing
                        long t0 = System.nanoTime(); // registra tempo inicial para inserção na BST em ns
                        bst.insert(b); // insere o livro na BST
                        long t1 = System.nanoTime(); // registra tempo final para BST
                        // AVL insert with timing
                        long t2 = System.nanoTime(); // registra tempo inicial para AVL
                        avl.insert(b); // insere o livro na AVL
                        long t3 = System.nanoTime(); // registra tempo final para AVL
                        System.out.printf("Inserido: %s%nBST tempo(ns)= %d, AVL tempo(ns)= %d%n",
                                b, (t1 - t0), (t3 - t2)); // imprime resumo com tempos gastos
                        break; // encerra o case 1
                    case "2": // caso "2" (carregar livros de arquivo)
                        System.out.print("Caminho do arquivo: "); // solicita o caminho do arquivo
                        String path = sc.nextLine().trim(); // lê caminho informado e remove espaços
                        List<Book> books = readFromFile(path); // lê o arquivo e retorna lista de livros
                        long bstStart = System.nanoTime(); // registra tempo inicial para carregar na BST
                        for (Book bb : books) bst.insert(bb); // insere todos os livros na BST
                        long bstEnd = System.nanoTime(); // tempo final para BST
                        long avlStart = System.nanoTime(); // tempo inicial para AVL
                        for (Book bb : books) avl.insert(bb); // insere todos os livros na AVL
                        long avlEnd = System.nanoTime(); // tempo final para AVL
                        System.out.printf("Arquivo carregado: %d livros%nBST tempo(ns)= %d%nAVL tempo(ns)= %d%n",
                                books.size(), (bstEnd - bstStart), (avlEnd - avlStart)); // imprime estatísticas de tempo
                        break; // encerra case 2
                    case "3": // caso "3" (buscar por ID)
                        System.out.print("ID para busca: "); // pede o ID ao usuário
                        int idSearch = Integer.parseInt(sc.nextLine().trim()); // lê e converte para int
                        long s0 = System.nanoTime(); // tempo inicial BST busca
                        Book foundBst = bst.search(idSearch); // busca na BST
                        long s1 = System.nanoTime(); // tempo após BST
                        Book foundAvl = avl.search(idSearch); // busca na AVL (sem medir tempo intermediário antes)
                        long s2 = System.nanoTime(); // tempo final após AVL
                        System.out.printf("BST: %s (tempo ns=%d, comps=%d)%n",
                                foundBst == null ? "NAO ENCONTRADO" : foundBst,
                                (s1 - s0), bst.searchComparisons); // imprime resultado BST com tempo e comparações
                        System.out.printf("AVL: %s (tempo ns=%d, comps=%d)%n",
                                foundAvl == null ? "NAO ENCONTRADO" : foundAvl,
                                (s2 - s1), avl.searchComparisons); // imprime resultado AVL com tempo e comparações
                        break; // encerra case 3
                    case "4": // caso "4" (remover por ID)
                        System.out.print("ID para remover: "); // solicita ID para remoção
                        int idRem = Integer.parseInt(sc.nextLine().trim()); // lê e converte para int
                        long r0 = System.nanoTime(); // tempo inicial BST remoção
                        bst.remove(idRem); // realiza remoção na BST
                        long r1 = System.nanoTime(); // tempo final BST
                        long r2 = System.nanoTime(); // tempo inicial AVL (separado)
                        avl.remove(idRem); // realiza remoção na AVL
                        long r3 = System.nanoTime(); // tempo final AVL
                        System.out.printf("Remoção solicitada: %d%nBST tempo(ns)=%d (comps=%d)%nAVL tempo(ns)=%d (rotações=%d, comps=%d)%n",
                                idRem, (r1 - r0), bst.removeComparisons, (r3 - r2), avl.rotations, avl.removeComparisons); // imprime resumo da remoção
                        break; // encerra case 4
                    case "5": // caso "5" (listar in-ordem)
                        System.out.println("BST In-ordem:"); // indica que será impressa a BST em-ordem
                        bst.inorder(System.out); // chama método de travessia em-ordem da BST, enviando saída para System.out
                        break; // encerra case 5
                    case "6": // caso "6" (listar pré-ordem)
                        System.out.println("BST Pré-ordem:"); // indica pré-ordem
                        bst.preorder(System.out); // imprime pré-ordem da BST
                        break; // encerra case 6
                    case "7": // caso "7" (listar pós-ordem)
                        System.out.println("BST Pós-ordem:"); // indica pós-ordem
                        bst.postorder(System.out); // imprime pós-ordem da BST
                        break; // encerra case 7
                    case "8": // caso "8" (listar em largura)
                        System.out.println("BST Em largura:"); // indica travessia em largura
                        bst.breadthFirst(System.out); // imprime BFS da BST
                        break; // encerra case 8
                    case "9": // caso "9" (comparar estatísticas)
                        System.out.println("Comparação (estatísticas atuais):"); // cabeçalho da comparação
                        System.out.printf("BST — tamanho: %d, insert comps: %d, search comps: %d, remove comps: %d%n",
                                bst.size(), bst.insertComparisons, bst.searchComparisons, bst.removeComparisons); // imprime estatísticas da BST
                        System.out.printf("AVL — tamanho: %d, rotações: %d, insert comps: %d, search comps: %d, remove comps: %d%n",
                                avl.size(), avl.rotations, avl.insertComparisons, avl.searchComparisons, avl.removeComparisons); // imprime estatísticas da AVL
                        break; // encerra case 9
                    case "0": // caso "0" (sair)
                        System.out.println("Saindo..."); // mensagem de saída
                        sc.close(); // fecha o Scanner para liberar recursos
                        return; // encerra o método main (e, portanto, o programa)
                    default: // caso de opção inválida
                        System.out.println("Opcao invalida."); // informa que a opção não é reconhecida
                }
            } catch (Exception e) { // captura qualquer exceção que ocorra dentro do switch
                System.err.println("Erro: " + e.getMessage()); // imprime mensagem de erro no stderr
            }
        }
    }

    /* ----------------------------- Auxiliares ----------------------------- */

    // Lê livros de um arquivo com linhas "ID;Título;Autor"
    static List<Book> readFromFile(String path) throws IOException { // método que lê arquivo e retorna lista de Book
        List<Book> list = new ArrayList<>(); // cria lista vazia para armazenar livros
        BufferedReader br = new BufferedReader(new FileReader(path)); // cria BufferedReader para leitura eficiente do arquivo
        String line; // variável que guarda cada linha lida
        int lineNo = 0; // contador de número da linha (para mensagens de erro)
        while ((line = br.readLine()) != null) { // enquanto houver linhas no arquivo
            lineNo++; // incrementa o número da linha atual
            line = line.trim(); // remove espaços em branco no início e fim da linha
            if (line.isEmpty()) continue; // pula linhas em branco
            String[] parts = line.split(";", 3); // divide a linha em no máximo 3 partes usando ';' como separador
            if (parts.length < 3) { // se não houve 3 partes, o formato é inválido
                System.err.printf("Linha %d ignorada por formato invalido: %s%n", lineNo, line); // avisa no stderr e ignora
                continue; // pula para próxima linha
            }
            try { // tenta converter e criar objeto Book
                int id = Integer.parseInt(parts[0].trim()); // parse do ID convertendo string para inteiro
                String title = parts[1].trim(); // pega título (segundo campo) e remove espaços
                String author = parts[2].trim(); // pega autor (terceiro campo) e remove espaços
                list.add(new Book(id, title, author)); // adiciona o novo Book à lista
            } catch (NumberFormatException ex) { // captura erro se ID não for um inteiro válido
                System.err.printf("Linha %d: ID invalido: %s%n", lineNo, parts[0]); // imprime aviso sobre ID inválido
            }
        }
        br.close(); // fecha o BufferedReader para liberar recursos
        return list; // retorna a lista de livros lida do arquivo
    }

    static Book promptBook(Scanner sc) { // método auxiliar que solicita dados do livro via console
        System.out.print("ID (inteiro): "); // pede ID
        int id = Integer.parseInt(sc.nextLine().trim()); // lê e converte para inteiro
        System.out.print("Título: "); // pede título
        String title = sc.nextLine().trim(); // lê título e remove espaços
        System.out.print("Autor: "); // pede autor
        String author = sc.nextLine().trim(); // lê autor e remove espaços
        return new Book(id, title, author); // retorna novo objeto Book com os dados fornecidos
    }
}
