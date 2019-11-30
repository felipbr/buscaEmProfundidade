package br.ufla.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufla.felipe.entidades.Aresta;
import br.ufla.felipe.entidades.Vertice;
import br.ufla.felipe.implementacoes.BuscaProfundidade;
import br.ufla.felipe.utilitarios.GraphViz;

public class Main {

	//Preenchimento de referencia inicial
	static Map<String, Integer> referenciaClasse = new HashMap<String, Integer>();
	static Map<Integer, Set<Integer>> adjacencias = new HashMap<Integer, Set<Integer>>();
	
	//Para exibir o resultado
	static Map<Integer, String> referenciaIdClasse = new HashMap<Integer, String>();
	
	static Map<String, Aresta> arestas = new HashMap<String, Aresta>();
	
	static GraphViz gv;
	
    public static void main( String[] args ) {
    	
    	//Inicializa grafo
    	gv = new GraphViz();
		gv.addln(gv.start_graph());
		gv.addln("\t ratio = fill;");
		gv.addln("\t node [style=filled shape=record];");
		
		
    	buscarGrafo(getPath("execucaoDinamica_teste.txt"));
    	
    	System.out.println("\n\n\n\n");
        BuscaProfundidade bp = new BuscaProfundidade();
        Map<Integer, List<Vertice>> grafosDesconexos = bp.iniciar(referenciaClasse, adjacencias);
        
        generateGraph(grafosDesconexos);
        
        //adiciona na listagem de arestas, quais foram as pontes encontradas
        for(Aresta a : bp.getBridges()) {
        	if(arestas.containsKey(a.getVertice1()+""+a.getVertice2())) {
        		arestas.get(a.getVertice1()+""+a.getVertice2()).setPonte(true);
        	} else if(arestas.containsKey(a.getVertice2()+""+a.getVertice1())) {
        		arestas.get(a.getVertice1()+""+a.getVertice2()).setPonte(true);
        	}
        }
        
        //preenche as arestas no GraphViz
        for(String key: arestas.keySet()) {
        	Aresta a = arestas.get(key);
        	gv.addln("\t \"" + referenciaIdClasse.get(a.getVertice1()) +
					"\" -> \"" + referenciaIdClasse.get(a.getVertice2()) +
					"\"");
        	if(a.isPonte()) {
        		gv.addln("[color=red penwidth=4]; ");
        	}
        }
        
        //Realiza a montagem da imagem do grafo
        String type = "png";
		String representationType="dot";
		final String NOME_ARQUIVO = "resultados//imagem//resultado_final.png";
		File saida = new File(getPath(NOME_ARQUIVO));
		gv.addln(gv.end_graph());
		gv.writeGraphToFile( gv.getGraph(gv.getDotSource(), type, representationType), saida );
        System.out.println("Fim");
        
	}
    
    private static void generateGraph(Map<Integer, List<Vertice>> grafosDesconexos) {
		
    	//adiciona os vertices existes, já colorindo as articulacoes e separando por microsserviços
		for(Integer key : grafosDesconexos.keySet()) {
			
			StringBuilder escrever = new StringBuilder("\t subgraph cluster_"+key+" {\n");
			escrever.append("\t\t style=filled;\n")
				.append("\t\t color=lightgrey;\n")
				.append("\t\t node [style=filled color=white shape=record];\n");

			StringBuilder valores = new StringBuilder();
			
			for(Vertice v : grafosDesconexos.get(key)) {
				
				if(v.isArticulationPoint()) {
					valores.append("\t\t \""+v.getNome()+"\" [color= yellow];\n");
				} else {
					valores.append("\t\t \""+v.getNome()+"\";\n");
				}
			}

			escrever.append("\t\t label = \" Microsserviço "+key+"\"\n");
			
			escrever.append(valores);
			escrever.append("\t };");

			gv.addln(escrever.toString());
		
		}

	}

	public static void buscarGrafo(final String caminho) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(caminho)));
			String line;
			
			int proximoItem = 0;
			Integer item1, item2;
			
			while((line = reader.readLine()) != null) {
				if( ! line.trim().equals("")){
					
					//Pega so a comunicacao(ignora o caso de uso a que veio)
					String comunicacao = line.split("#")[0].trim();
					
					//Pega o item1 e da a ele um numero
					String str = comunicacao.split("->")[0].trim().replace("\"", "");
				
					item1 = referenciaClasse.get(str);
					if( item1 == null) {
						item1 = proximoItem;
						referenciaClasse.put(str, proximoItem);
						referenciaIdClasse.put(proximoItem, str);
						proximoItem += 1;
					}
					
					//Pega o item2 e da a ele um numero
					str = comunicacao.split("->")[1].trim().replace("\"", "");
					
					item2 = referenciaClasse.get(str);
					if( item2 == null) {
						item2 = proximoItem;
						referenciaClasse.put(str, proximoItem);
						referenciaIdClasse.put(proximoItem, str);
						proximoItem += 1;
					}
					
					//Adiciona item2 como adjacenia do item1
					Set<Integer> adj = adjacencias.get(item1);
					if(adj == null) {
						adj = new HashSet<Integer>();
					}
					adj.add(item2);
					adjacencias.put(item1, adj);
					
					arestas.put(item1+""+item2, new Aresta(item1, item2, false));
					
					//Adiciona item1 como adjacenia do item2
					adj = adjacencias.get(item2);
					if(adj == null) {
						adj = new HashSet<Integer>();
					}
					adj.add(item1);
					adjacencias.put(item2, adj);
				}
			}
			reader.close();
			
		} catch (IOException e) { }
	}
    
    public static String getPath(String arquivo){
		File f1 = new File("/Users/fiocruz/Desktop/ArquivosLP");
		final String caminho = f1.getAbsolutePath() + "//" + arquivo;
		return caminho;
	}
}
