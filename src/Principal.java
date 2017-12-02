/*
 * Universidade Federal de Santa Catarina - UFSC
 * Departamento de Informática e Estatística - INE
 * Programa de Pós-Graduação em Ciências da Computação - PROPG
 * Disciplina: Projeto e Análise de Algoritmos
 * Prof Alexandre Gonçalves da Silva 
 *
 * Baseado nos slides 63 da aula do dia 27/10/2017 
 *
 * Página 479 Thomas H. Cormen 3a Ed 
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
import java.util.LinkedList;
import java.util.List;

public class Principal {

    final static int BRANCO = 0;//Vértice não visitado. Inicialmente todos os vértices são brancos
    final static int CINZA = 1; //Vértice visitado 
    
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
        if ((i >=0) && (i<=letras.length())) {
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
     * Gera um vetor de arestas e pesos.
     *
     * @param G Matriz de adjacência do grafo
     * @return Um vetor de arestas e pesos.
     */
    public static List getMatrizVertices(int[][] G) {
        int n = G.length;
        List vertices = new LinkedList();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {                
                if (G[i][j] != 0) {
                    //Cria um vetor de 3 elementos para conter                     
                    //[0]=u(origem), [1]=v(destino), [2]=w(peso)
                    vertices.add(new int[]{i, j, G[i][j]});
                }
            }
        }
        return vertices;
    }
    
    /**
     * Retorna o índice do vértice com o menor peso da aresta ainda não visitado.
     *
     * Complexidade O(V log V)
     * 
     * @param n Quantidade de vértices a ser pesquisados
     * @return O índice do menor vértice
     */
    public static int extrairMenor(int n) {
        int menorValor = Integer.MAX_VALUE;
        int indiceMenor = -1;
        for (int i = 0; i < n; i++) {
            if (cor[i] == BRANCO && d[i] < menorValor) {
                indiceMenor = i;
                menorValor = d[i];
            }
        }
        return indiceMenor;
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
            cor[v] = BRANCO;            
        }
        d[s] = 0;
        pi[s] = 0;               
    }

    /**
     * Teste se pode ser melhorado o caminho mínimo de u até v.
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
    public static void mostrarCaminho(int[] S) {
        //Quantidade de vértices da lista
        int n = S.length;        
        //Percorre os vértices a partir de S        
        System.out.println("Caminho mínimo :");
        for (int v = 1; v < n; v++) {
            System.out.println(trocar(pi[S[v]]) + " -> " + trocar(S[v]) + " custo: " + d[S[v]]);            
        }        
    }
    
    /**
     * Executa o algoritmo de Dijkstra para Caminhos Mínimos de fonte única.
     *
     * Encontra a distância mais curta de s para todos os outros vértices.   
     * 
     * Complexidade: O(V log V + E)
     *
     * @param G Matriz de indicência da árvore
     * @return Vetor com a lista das arestas de menor custo
     */
    public static int[] algoritmoDijkstra(int[][] G, int s) {

        //Quantidade de vértices do grafo G
        int V = G.length;

        //Instância o vetor de retorno
        int[] S = new int[V];
        
        //Converte a matriz em uma lista de arestas
        List arestas = getMatrizVertices(G);

        //Quantidade de arestas do grafo
        int E = arestas.size();

        //Realiza a inicialização das estimativas
        inicializaFonteUnica(G, s);

        //Percorre todos os vértice do grafo
        for (int i = 0; i < V; i++) {
            //extranctMin remove o vértice com a menor distância de Q
            int x = extrairMenor(V);
            //Marca como visitado
            cor[x] = CINZA;
            S[i]=x;                        
            //Percorre todas as arestas do grafo
            for (int j = 0; j < E; j++) {
                int[] vertice = (int[]) arestas.get(j);
                int u = vertice[0];
                int v = vertice[1];
                int w = vertice[2];
                //Faz p relaxamento para o vertice retirado de V
                if (u == x) {                
                    relaxamento(u, v, w);
                }
            }
        }  
        return S;
    }

    public static void main(String args[]) {

//       //Grafo Slide 143 de 20/10/2017
         //descomente a linha 39 para usar este grafo
//       int G[][] =
//               //s  r  w  t  u  v 
//               {{0,10, 0, 0, 5, 0}, //s
//                {0, 0, 1, 0, 2, 0}, //r
//                {0, 0, 0, 3, 0, 4}, //w                
//                {0, 0, 0, 0, 0, 0}, //t
//                {0, 3, 9, 0, 0, 2}, //u
//                {7, 0, 6, 5, 0, 0}}; //v
               
        
        //Grafo da página xx Thomas H. Cormen 3 ed
        //descomente a linha 38 para usar este grafo
        int G[][]
             = //s   t  x  y  z    
               {{0, 10, 0, 5, 0}, //s
                {0,  0, 1, 2, 0}, //t
                {0,  0, 0, 0, 2}, //x
                {0,  3, 9, 0, 2}, //y
                {7,  0, 6, 0, 0}};//z

        System.out.println(">>> Caminho mínimos de fonte única, Algoritmo de Dijkstra <<<");

        //Executa o algoritmo
        int s = destrocar('s');
        int[] S = algoritmoDijkstra(G, s);

        //Mostra o menor caminho
        mostrarCaminho(S);  
        
        System.out.println("\nMostrando todos dados:");
        for (int i = 0; i < G.length; i++) {
            System.out.println(trocar(pi[i]) + " -> " +  trocar(i) + " custo: " + d[i]);
        }
    }
}
