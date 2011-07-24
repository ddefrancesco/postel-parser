/**
 * 
 */
package com.obone.postel.parser.model.billing;

/**
 * @author ddefrancesco
 *
 */
public class TotaleFattura {
	private String totaleIva;
	private String totaleFattura;
	private String totaleGenerale;
	public String getTotaleIva() {
		return totaleIva;
	}
	public void setTotaleIva(String totaleIva) {
		this.totaleIva = totaleIva;
	}
	public String getTotaleFattura() {
		return totaleFattura;
	}
	public void setTotaleFattura(String totaleFattura) {
		this.totaleFattura = totaleFattura;
	}
	public String getTotaleGenerale() {
		return totaleGenerale;
	}
	public void setTotaleGenerale(String totaleGenerale) {
		this.totaleGenerale = totaleGenerale;
	}
	
}
