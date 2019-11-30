package br.ufla.felipe.entidades;

import java.util.Set;

public class Vertice {

	private String nome;
	private Integer valor;
	
	private CorGrafo cor = CorGrafo.BRANCO;
	//Armazena o proximo nó a ser percorrido
	private Set<Integer> adjacentes;
	
	private int disc = 0; 
	private int low = 0; 
	private int verticePai = -1; 
	private boolean articulationPoint;
	
	
	public Vertice(String nome, Integer valor) {
		this.nome = nome;
		this.valor = valor;
	}
	public Vertice(Integer valor) {
		this.valor = valor;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	
	public CorGrafo getCor() {
		return cor;
	}
	public void setCor(CorGrafo cor) {
		this.cor = cor;
	}
	
	public Set<Integer> getAdjacentes() {
		return adjacentes;
	}
	public void setAdjacentes(Set<Integer> adjacentes) {
		this.adjacentes = adjacentes;
	}

	public int getDisc() {
		return disc;
	}
	public void setDisc(int disc) {
		this.disc = disc;
	}
	
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	
	public int getVerticePai() {
		return verticePai;
	}
	public void setVerticePai(int parent) {
		this.verticePai = parent;
	}
	
	public boolean isArticulationPoint() {
		return articulationPoint;
	}
	public void setArticulationPoint(boolean articulationPoint) {
		this.articulationPoint = articulationPoint;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertice other = (Vertice) obj;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	
}
