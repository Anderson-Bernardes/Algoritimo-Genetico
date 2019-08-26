package view;

import java.util.ArrayList;
import java.util.Random;


public class Populacao { //
    //if rank ordenar o array pelo fitness/copiar os x melhores para array auxiliar x=pop*90
    //if rank + torneio ordena pelo fitness/ copiar 10% de x para o array auxiliar /90% de x por torneio

    private int pRato;
    private int pQueijo;
    private int tPop;
    private int mutacao;
    private int nGeracoes;
    private String selecao;
    ArrayList<Individuo> populacao;
    private ArrayList<Individuo> candidatos;
    public int[][] matrizObstaculos; // vetor da matriz de obstaculos, calculo:
    //Na matriz tem um obstáculo (1) na posição 5 das linhas pares e na posição 15 das linhas ímpares
    Random rand;

    public Populacao(int pRato, int pQueijo, int tPop, int mutacao, int nGeracoes, String selecao) {
        this.pRato = pRato;
        this.pQueijo = pQueijo;
        this.tPop = tPop;
        this.mutacao = mutacao;
        this.nGeracoes = nGeracoes;
        this.selecao = selecao;
    } // part 1 estanciar 

    public void Cruzamento() {
        Individuo individuo = new Individuo(); //array list de indivuo
       int n1, n2;
        for (int i = 1; i < candidatos.size(); i++) { // seleciona os candidatos para o cruzamento
            n1=rand.nextInt(tPop);
            n2=rand.nextInt(tPop);
            
            populacao.add(individuo.Cruza(candidatos.get(n1), candidatos.get(n2)));
            populacao.add(individuo.Cruza(candidatos.get(n2), candidatos.get(n1)));
            
            //populacao.add(individuo.Cruza(candidatos.get(i - 1), candidatos.get(i)));
            //populacao.add(individuo.Cruza(candidatos.get(i), candidatos.get(i - 1)));
        } // troca a ultima parte pela primeira, cruzamento acontece na class indiviuo
        System.out.println("Cruzamento realizado com sucesso, numero atual da populacao "+populacao.size());
    }

    public void GeraPopInicial() { // gera pop inicial
        populacao = new ArrayList<>(); // gerando um arraylist de pop inicial

        for (int i = 0; i < tPop; i++) { // tp tapanho da pop
            Individuo individuo = new Individuo(); // 
            individuo.GeraIndividuo(individuo, pRato, pQueijo); // manda p inicio e o fim da array
            individuo.CalculaFitness(individuo, matrizObstaculos); // calculo do fitnes com a matriz de obstaculos
            populacao.add(individuo); // add pop em inviduo 
        }
        System.out.println("População incial gerada com sucesso");
    } //

    public void GeraProximaGerancao() {
        ArrayList<Individuo> auxPop = new ArrayList<>(); // add inviduo na variavel aux

        Individuo individuo = new Individuo();
        for (int i = 0; i < populacao.size(); i++) { // emquanto poptamanho for menor q i=0 faça i++ 
            individuo.CalculaFitness(populacao.get(i), matrizObstaculos); // daí esse método escolhe os melhores pra ser a próxima população
        }

        Ordena(populacao);

        for (int i = 0; i < tPop; i++) {
            auxPop.add(populacao.get(i)); // nova pop
        }

        populacao.clear();

        populacao = auxPop; // gera nova pop
    }

    public void ImprimePop() {
        for (int i = 0; i < populacao.size(); i++) {
            Individuo ind = populacao.get(i);
            ind.ImprimeIndividuo(ind);
        }
    } // imprime array da pop

    public void FazMutacao() {
        for (int i = 0; i < mutacao; i++) { // numero de mutação q a pessoa escolher na interface
            System.out.println("Fazendo mutacao");
            populacao.get(rand.nextInt(populacao.size())).Mutacao(); // escolhe um numero aleatorio dentro do array pop para fazer a mutação 
        }

        System.out.println("Mutação feita com sucesso");
    } //  

    public void Rank() {
        Ordena(populacao);
        rand = new Random();
        candidatos = new ArrayList<>(); // candaidados
        int qtdRank = 90 * tPop / 100;  //pre definido, 90* tamanhp da pop / 100 p dar os 90% q podem cruzar
        
        for (int i = 0; i < qtdRank; i++) { 
            candidatos.add(populacao.get(i));
        } 

        System.out.println("Rank feito com sucesso");
    }

    public void RankTorneio(Populacao pop) {
        candidatos = new ArrayList<>();
        rand = new Random();
        int qtdRank = 10 * tPop / 100; // 10% do rank p cruzar com 90% do torneio
        int qtdTorneio = 90 * tPop / 100; 
        int n1, n2;
        Ordena(pop.populacao); //ordenar pelo fitness; pega os 90 melhores e add candidatos

        for (int i = 0; i < qtdRank; i++) { // pega os 10% escolhidos  
            candidatos.add(pop.populacao.get(i));
        } 

        for (int j = 0; j < qtdTorneio; j++) { //  
            n1 = rand.nextInt(tPop); //  pega os 90% e escolhem dois aleatorios p fazer seleção
            n2 = rand.nextInt(tPop);

            if (pop.populacao.get(n1).getFitness() >= pop.populacao.get(n2).getFitness()) { // se n1 for maior= n2 add na pop 
                candidatos.add(pop.populacao.get(n1));
            } else {
                candidatos.add(pop.populacao.get(n2)); // se n2 for maior q n1 add pop
            }
        }

        System.out.println("Rank+Torneio realizado com sucesso");
    }

    public void MatrizDeObstaculos() {
        matrizObstaculos = new int[20][20]; // cria uma matriz 20x20
        for (int i = 0; i < 20; i++) { // percorre a matriz
            for (int j = 0; j < 20; j++) { // percorre a matriz
                if (i % 2 == 0) { // acha par
                    if (j == 5) { // 5 muda as posições nas linhas pares 
                        matrizObstaculos[i][j] = 1;
                    }
                } else if (j == 15) { // 15 muda as posições impares
                    matrizObstaculos[i][j] = 1;
                }
            }
        }
        System.out.println("Matriz de obstaculos gerada com sucesso");
    }

    public void Ordena(ArrayList<Individuo> populacao) {
        Individuo ind;

        for (int i = 0; i < populacao.size() - 1; i++) {
            for (int j = 0; j < populacao.size() - i - 1; j++) {
                if (populacao.get(j).getFitness() > populacao.get(j + 1).getFitness()) {
                    ind = populacao.get(j);
                    populacao.set(j, populacao.get(j + 1));
                    populacao.set(j + 1, ind);
                }
            }
        }
//        for (int i = 0; i < populacao.size(); i++) {
//            System.out.println(populacao.get(i).getFitness());
//        }
    }
}
