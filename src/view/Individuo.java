package view;

import java.util.Random;

public class Individuo{
    private int fitness;
    private int caminho[] = new int[20];
    private Random rand = new Random();
    
    public Individuo(){ 
    }
    
    public void GeraIndividuo(Individuo individuo, int inicio, int fim){
        caminho[0]=inicio; // rato sempre pos 0 
        caminho[19]=fim; // queijo sempre pos 19
        for(int i = 1; i < 19; i++){
                caminho[i] = rand.nextInt(20); // gera posições random no meio da matriz de 1 a 18
            } 
        individuo.caminho = caminho;
    }
    
    public void Mutacao(){
        caminho[rand.nextInt(17)+1]=rand.nextInt(19);
    }
    
    public void CalculaFitness(Individuo individuo, int[][] obstaculos){
        individuo.setFitness(0);
        int aux;
        int fit=0; // inicia com valor zero
        for(int i = 0; i < 20-1; i++){
            aux = Math.abs((int)individuo.caminho[i] - (int)individuo.caminho[i+1]); // calculo do fitnes, percorre o caminho, e= pa+pp+1
            // math modulo caminho de [i] - caminho de [i+1] 
            if(obstaculos[i][individuo.caminho[i]]==1) // + 10 caso ache um abstaculo
            {
                aux+=10;
            }
            
            fit += aux; // fit recebe aux
        }
        
      
        individuo.setFitness(fit);
    } //1
    
    public void ImprimeIndividuo(Individuo individuo){
        //individuo.setFitness(0);
        for(int i=0;i<20;i++){
            System.out.print(individuo.caminho[i]+",");
        }
        System.out.print("Fitness "+ individuo.getFitness());
        System.out.println(" ");
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
    
    public Individuo Cruza(Individuo ind1, Individuo ind2){
        Individuo ind = new Individuo();
        
        ind.caminho[0] = ind1.caminho[0];
        ind.caminho[19] = ind1.caminho[19];
        
        for(int i = 1; i<10; i++)
            ind.caminho[i] = ind1.caminho[i]; // pega metade do primeiro
        
        for(int i = 10; i<19; i++)
            ind.caminho[i] = ind2.caminho[i]; // pega a segunda parte do segundo
        
        return ind;
    }
}

