package br.ufla.felipe.entidades;

public class Aresta {

	private Integer vertice1;
	private Integer vertice2;
	private boolean ponte;
	
	public Aresta(Integer vertice1, Integer vertice2, boolean ponte) {
		this.vertice1 = vertice1;
		this.vertice2 = vertice2;
		this.ponte = ponte;
	}
	public Integer getVertice1() {
		return vertice1;
	}
	public void setVertice1(Integer vertive1) {
		this.vertice1 = vertive1;
	}
	public Integer getVertice2() {
		return vertice2;
	}
	public void setVertice2(Integer vertice2) {
		this.vertice2 = vertice2;
	}
	public boolean isPonte() {
		return ponte;
	}
	public void setPonte(boolean ponte) {
		this.ponte = ponte;
	}
	
	
	
}
