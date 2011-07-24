/**
 * 
 */
package com.obone.postel.parser.at.comandi;

/**
 * @author ddefrancesco
 *
 */
public class Comando {
	private String comando;
	private Integer parametro;
	private Integer riga;
	public String getComando() {
		return comando;
	}
	public void setComando(String comando) {
		this.comando = comando;
	}
	public Integer getParametro() {
		return parametro;
	}
	public void setParametro(Integer parametro) {
		this.parametro = parametro;
	}
	public Integer getRiga() {
		return riga;
	}
	public void setRiga(Integer riga) {
		this.riga = riga;
	}
}
