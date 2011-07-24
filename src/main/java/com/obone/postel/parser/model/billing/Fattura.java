package com.obone.postel.parser.model.billing;



import java.util.TreeMap;

import com.obone.postel.parser.at.ATParser;

public interface Fattura {
	
	public void setParser(ATParser parser);
	
	public void setDataMap(TreeMap<Integer , String> map);
	/**
	 * @see it.matrix.aod.postel.model.AbstractDocumento#getComunicazioni()
	 */
	public Comunicazioni[] getComunicazioni();

	/**
	 * @see it.matrix.aod.postel.model.AbstractDocumento#getDettagli()
	 */
	public Dettaglio[] getDettagli();

	/**
	 * @see it.matrix.aod.postel.model.AbstractDocumento#getImponibiliIva()
	 */
	public ImponibileIva[] getImponibiliIva();

	/**
	 * @see it.matrix.aod.postel.model.AbstractDocumento#getSconti()
	 */
	public TotaleSconti getSconti();

	/**
	 * @see it.matrix.aod.postel.model.AbstractDocumento#getTestata()
	 */
	public TestataFattura getTestata();

	/**
	 * @see it.matrix.aod.postel.model.AbstractDocumento#getTotale()
	 */
	public TotaleFattura getTotale();

}