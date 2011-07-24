/**
 * 
 */
package com.obone.postel.parser.model.billing;

/**
 * @author ddefrancesco
 *
 */

public class Dettaglio {
	private String codiceProdotto;
	private String descrizione;
	private String periodoRiferimento;
	private String quantita;
	private String aliquotaIva;
	private String importo;
	private String prezzo;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getPeriodoRiferimento() {
		return periodoRiferimento;
	}
	public void setPeriodoRiferimento(String periodoRiferimento) {
		this.periodoRiferimento = periodoRiferimento;
	}
	public String getQuantita() {
		return quantita;
	}
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	public String getAliquotaIva() {
		return aliquotaIva;
	}
	public void setAliquotaIva(String aliquotaIva) {
		this.aliquotaIva = aliquotaIva;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	public String getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(String prezzo) {
		this.prezzo = prezzo;
	}
	public String getCodiceProdotto() {
		return codiceProdotto;
	}
	public void setCodiceProdotto(String codiceProdotto) {
		this.codiceProdotto = codiceProdotto;
	}
}
