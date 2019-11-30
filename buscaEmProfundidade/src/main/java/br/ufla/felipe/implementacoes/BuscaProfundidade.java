package br.ufla.felipe.implementacoes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufla.felipe.entidades.Aresta;
import br.ufla.felipe.entidades.CorGrafo;
import br.ufla.felipe.entidades.Vertice;

public class BuscaProfundidade {

	static Vertice todosVertices[];
	
    static Map<Integer, List<Vertice>> grafosDesconexos = new HashMap<Integer, List<Vertice>>();
    static int contador = 0;
    
   	List<Aresta> bridges = new ArrayList<Aresta>();
    
    public Map<Integer, List<Vertice>> iniciar(Map<String, Integer> referenciaClasse, Map<Integer, Set<Integer>> adjacencias) {
    	
        todosVertices = new Vertice[referenciaClasse.size()];
        
        int index = 0;
        //preenche o vertice e suas adjacencias
        for(String valor : referenciaClasse.keySet()) {
        	index = referenciaClasse.get(valor);
        	todosVertices[index] = new Vertice(valor, index);
        	todosVertices[index].setAdjacentes(adjacencias.get(index));
        }
        
        for(Vertice vertice : todosVertices) {
        	if( CorGrafo.BRANCO.equals(vertice.getCor())) {
        		time = 0;
	        	buscaEmProfundidade(vertice);
	        	if(grafosDesconexos.containsKey(contador)) {
	        		contador++;
	        	}
        	}
        }
        
        //exibição do resultado
        for(Integer key : grafosDesconexos.keySet()) {
        	System.out.println("Microsserviço "+key+":");
        	for(Vertice vert : grafosDesconexos.get(key)) {
        		System.out.print(vert.getNome() + " : "+vert.getValor());
        		if(vert.isArticulationPoint()) {
        			System.out.print(" Articulação ");
        		}
        		System.out.println("");
        	}
        	System.out.println("----------------");
        }
        
        System.out.println("Fim da busca em profundidade");
        
        return grafosDesconexos;
    }
    
    static int time = 0;
    
    public void buscaEmProfundidade(Vertice vertice) {
        
        int children = 0; 
        
    	vertice.setCor(CorGrafo.CINZA);
    	
    	time += 1;
    	vertice.setDisc(time);
    	vertice.setLow(time);
    	
    	Vertice verticeAdjacente;
		
		for(Integer adjacente: vertice.getAdjacentes()) {
			
			verticeAdjacente = todosVertices[adjacente];
			
			if(verticeAdjacente.getCor().equals(CorGrafo.BRANCO)) {
					
				children++;
				
				verticeAdjacente.setVerticePai(vertice.getValor());
				
    			buscaEmProfundidade(verticeAdjacente);
    			
    			vertice.setLow(Math.min(vertice.getLow(), verticeAdjacente.getLow())); 
    			
    			// se o menor vertice encontrado na sub arvore abaixo de v 
    			// e esta antes de u, u-v é uma ponte
                if (verticeAdjacente.getLow() > vertice.getDisc()) {
                    System.out.println(vertice.getValor()+" "+verticeAdjacente.getValor()); 
                	bridges.add(new Aresta(vertice.getValor(),verticeAdjacente.getValor(), true));
                }
                
                // (1) u é root de DFS e tem dois ou mais filhos
                if (vertice.getVerticePai() == -1 && children > 1) {
                    vertice.setArticulationPoint(true); 
                }
                
                // se o vertice não é root e o menor valor de um dos vertices adjacentes e maior ou igual do que o valor descoberto.
                if (vertice.getVerticePai() != -1 && verticeAdjacente.getLow() >= vertice.getDisc()) {
                	vertice.setArticulationPoint(true); 
                }
			}
			// Atualiza o menor valor de u para chamadas do valor pai
            else if (verticeAdjacente.getValor() != vertice.getVerticePai()) {
                vertice.setLow(Math.min(vertice.getLow(), verticeAdjacente.getDisc()));
            }
		}
		vertice.setCor(CorGrafo.PRETO);
		
		List<Vertice> graph = grafosDesconexos.get(contador);
		if(graph == null) {
			graph = new ArrayList<Vertice>();
		}
		graph.add(vertice);
		grafosDesconexos.put(contador, graph);
    	
    }

	public List<Aresta> getBridges() {
		return bridges;
	}

	public void setBridges(List<Aresta> bridges) {
		this.bridges = bridges;
	}
    
}
