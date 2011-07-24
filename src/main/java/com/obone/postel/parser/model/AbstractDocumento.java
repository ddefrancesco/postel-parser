/**
 * 
 */
package com.obone.postel.parser.model;

import java.util.TreeMap;

import com.obone.postel.parser.at.ATParser;
import com.obone.postel.parser.model.billing.Comunicazioni;
import com.obone.postel.parser.model.billing.Dettaglio;
import com.obone.postel.parser.model.billing.ImponibileIva;
import com.obone.postel.parser.model.billing.TestataFattura;
import com.obone.postel.parser.model.billing.TotaleFattura;
import com.obone.postel.parser.model.billing.TotaleSconti;



/**
 * @author ddefrancesco
 *
 */
public abstract class AbstractDocumento {
	private TreeMap<Integer, String> dataMap;
	private ATParser parser;
//	private int linesNumber;
	private String nomeFile;
	
	private TestataFattura testata;
	private Dettaglio[] dettagli;
	private ImponibileIva[] imponibiliIva;
	private TotaleSconti sconti;
	private TotaleFattura totale;
	private Comunicazioni[] comunicazioni;
	

	public abstract TestataFattura getTestata() ;
	public void setTestata(TestataFattura testata) {
		this.testata = testata;
	}
	public abstract Dettaglio[] getDettagli();
	public void setDettagli(Dettaglio[] dettagli) {
		this.dettagli = dettagli;
	}
	public abstract ImponibileIva[] getImponibiliIva() ;
	public void setImponibiliIva(ImponibileIva[] imponibiliIva) {
		this.imponibiliIva = imponibiliIva;
	}
	public abstract TotaleSconti getSconti();
	public void setSconti(TotaleSconti sconti) {
		this.sconti = sconti;
	}
	public abstract TotaleFattura getTotale();
	public void setTotale(TotaleFattura totale) {
		this.totale = totale;
	}
	public abstract Comunicazioni[] getComunicazioni() ;
	public void setComunicazioni(Comunicazioni[] comunicazioni) {
		this.comunicazioni = comunicazioni;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}
	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public TreeMap<Integer, String> getDataMap() {
		return dataMap;
	}
	public void setDataMap(TreeMap<Integer, String> dataMap) {
		this.dataMap = dataMap;
	}
	public ATParser getParser() {
		return parser;
	}
	public void setParser(ATParser parser) {
		this.parser = parser;
	}

}
