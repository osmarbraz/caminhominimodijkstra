/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplina: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 *
 * Baseado nos slides 63 da aula do dia 27/10/2017 
 * Baseado no algoritmo página 479 Thomas H. Cormen 3a Ed 
 *
 * Caminho mínimos de fonte única, Algoritmo de Dijkstra
 *
 * O algoritmo de Dijkstra recebe um grafo orientado ponderado(G,w) (sem 
 * arestas de peso negativo) e um vértice s de G. Menor caminho de um vértice,
 * para todos os outros vértices.
 */

/**
 * @author Osmar de Oliveira Braz Junior
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Principal {

    //Vetor dos pais de um vértice
    static int[] pi;
    //Vetor das distâncias
    static int[] d;
    //Armazena os vértices visitados
    static int[] cor;

    /**
     * Troca um número que representa a posição pela vértice do grafo.
     *
     * @param i Posição da letra
     * @return Uma String com a letra da posição i
     */
    public static String trocar(int i) {
        String letras = "stxyz";
        //String letras = "srwtuv";
        if ((i >= 0) && (i <= letras.length())) {
            return letras.charAt(i) + "";
        } else {
            return "-";
        }
    }

    /**
     * Troca a letra pela posição na matriz de adjacência
     *
     * @param v Letra a ser troca pela posição
     * @return Um inteiro com a posição da letra no grafo
     */
    public static int destrocar(char v) {
        String letras = "stxyz";
        int pos = -1;
        for (int i = 0; i < letras.length(); i++) {
            if (letras.charAt(i) == v) {
                pos = i;
            }
        }
        return pos;
    }
   
    /**
     * Gera uma lista dos vértices.
     *
     * @param G Matriz de adjacência do grafo
     * @return Um vetor dos vértices.
     */
    public static List getVertices(int[][] G) {
        int n = G.length;
        List vertices = new LinkedList();
        for (int i = 0; i < n; i++) {            
           //Cria uma lista com os vértices
           vertices.add(i);            
        }
        return vertices;
    }
      
    /**
     * Retorna o vértice com o menor peso da aresta.
     *
     * Complexidade O(V log V)
     *
     * @param Q Lista dos vértices a ser pesquisados
     * @return O menor vértice
     */
    public static int extrairMenor(List Q) {
        //Usa o maior valor de inteiro como menor
        int menorValor = Integer.MAX_VALUE;        
        int indiceMenor = -1;        
        int apagar = -1;
        for (int i = 0; i < Q.size(); i++) {
            //Recupera o vértice da lista
            int v = (int) Q.get(i);
            
            //Verifica se é menor
            if (d[v] < menorValor) {
                //Guarda o menor valor
                menorValor = d[v];
                //Guarda o vértice
                indiceMenor = v;
                //Guarda o elemento da lista a ser apagadp
                apagar = i;
            }
        }
        //Remove o menor da lista
        Q.remove(apagar);
           
        return indiceMenor;
    }
              
    /**
     * Retorna a lista dos vértices getAdjacentes de u.
     *
     * Complexidade O(V log V)
     *
     * @param G Matriz de adjacência do grafo
     * @param u Vértices a ser localiza os getAdjacentes
     * @return O menor vértice
     */
    public static List getAdjacentes(int[][] G, int u) {
        int n = G.length;
        List vertices = new LinkedList();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                //Com pesos
                if (G[i][j] != 0) {
                    //Adiciona somente quando for de uma mesma origem
                    if (i == u) {
                        //Cria um vetor de 3 elementos para conter                     
                        //[0]=v(destino), [1]=w(peso)
                        vertices.add(new int[]{j, G[i][j]});

                    }
                }
            }
        }
        return vertices;
    }

    /**
     * Inicializa as estimativas de caminhos mínimos e predecessores.
     *
     * @param G Grafo a ser inicializado
     * @param s Vértice inicial
     */
    public static void inicializaFonteUnica(int[][] G, int s) {
        //Quantidade de vértices do grafo G
        int V = G.length;
        //Instancia os vetores
        d = new int[V];
        pi = new int[V];
        cor = new int[V];
        for (int v = 0; v < G.length; v++) {
            d[v] = Integer.MAX_VALUE;
            pi[v] = -1;            
        }
        d[s] = 0;
        pi[s] = 0;
    }

    /**
     * Testa se pode ser melhorado o caminho mínimo de u até v.
     *
     * @param u Vértice de origem.
     * @param v Vértice de destino
     * @param w Peso do caminho u até v.
     */
    private static void relaxamento(int u, int v, int w) {
        if (d[v] > d[u] + w) {
            d[v] = d[u] + w;
            pi[v] = u;
        }
    }

    /**
     * Exibe o caminho a ser percorrido no Grafo e o custo
     *
     * @param S Lista a ser percorrido para mostrar o caminho e o custo
     */
    public static void mostrarCaminho(List S) {
        //Quantidade de vértices da lista
        int n = S.size();
        //Percorre os vértices a partir de S        
        System.out.println("Caminho mínimo :");
        for (int i = 1; i < n; i++) {
            //Recupera o vértice
            int v = (int) S.get(i);
            System.out.println(trocar(pi[v]) + " -> " + trocar(v) + " custo: " + d[v]);
        }
    }
    
    /**
     * Executa o algoritmo de Dijkstra para Caminhos Mínimos de fonte única.
     *
     * Encontra a distância mais curta de s para todos os outros vértices.
     *
     * Complexidade: O(V log V + E)
     *
     * @param G Matriz de adjacência da árvore
     * @param s Vértice de início
     * @return Vetor com a lista dos vértices de menor custo
     */
    public static List dijkstra(int[][] G, int s) {

        //Realiza a inicialização das estimativas
        inicializaFonteUnica(G, s);

        //Instância o conjunto de retorno                     
        List S = new LinkedList();
                
        //Fila de prioridade mínima dos vértices
        List Q = getVertices(G);
        
        //Até existir vértices em Q
        while (Q.size() != 0) {

            //extranctMin retorna e remove o vértice com a menor distância de Q
            int u = extrairMenor(Q);
            
            //Adiciona o vértice do menor(x) a lista a saída
            S.add(u);
            
            //Retorna a lista dos vértices adjacentes do menor(u)
            List Adj = getAdjacentes(G, u);
            
            //Percorre os vértices adjacentes do menor(u)
            for (int i = 0; i < Adj.size(); i++) {
                //Recupera o vértice adjacente i de u para realizar o relaxamento
                int[] vertice = (int[]) Adj.get(i);                
                int v = vertice[0];
                int w = vertice[1];
                
                //Faz o relaxamento para o vertice(u,v,w)                
                relaxamento(u, v, w);
            }
        }
        return S;
    }

    public static void main(String args[]) {

//       //Grafo Slide 143 de 20/10/2017
        //descomente a linha a seguir para usar este grafo
//       int G[][] =
//               //s  r  w  t  u  v 
//               {{0,10, 0, 0, 5, 0}, //s
//                {0, 0, 1, 0, 2, 0}, //r
//                {0, 0, 0, 3, 0, 4}, //w                
//                {0, 0, 0, 0, 0, 0}, //t
//                {0, 3, 9, 0, 0, 2}, //u
//                {7, 0, 6, 5, 0, 0}}; //v

        //Grafo da página 659 Thomas H. Cormen 3 ed
        //descomente a linha a seguir para usar este grafo
        int G[][]
                = //s   t  x  y  z    
                {{0, 10, 0, 5, 0}, //s
                {0, 0, 1, 2, 0}, //t
                {0, 0, 0, 0, 2}, //x
                {0, 3, 9, 0, 2}, //y
                {7, 0, 6, 0, 0}};//z

        System.out.println(">>> Caminho mínimos de fonte única, Algoritmo de Dijkstra <<<");

        //Executa o algoritmo
        int s = destrocar('s');

        List S = dijkstra(G, s);
        
        mostrarCaminho(S);  

        System.out.println("\nMostrando todos dados:");
        for (int i = 0; i < G.length; i++) {
            System.out.println(trocar(pi[i]) + " -> " + trocar(i) + " custo: " + d[i]);
        }
    }
}
