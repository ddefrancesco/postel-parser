/**
 * 
 */
package com.obone.postel.parser.model.billing;

import java.util.SortedMap;

import com.obone.postel.parser.model.AbstractDocumento;



/**
 * @author ddefrancesco
 *
 */
public class FatturaImpl extends AbstractDocumento implements Fattura{
	
//	private TreeMap<Integer, String> _tmap  = getDataMap();
	
	/* (non-Javadoc)
	 * @see it.matrix.aod.postel.model.Fattura#getComunicazioni()
	 */
	@Override
	public Comunicazioni[] getComunicazioni() {
		SortedMap<Integer, String> _map = getParser().buildComunicazioniDataMap(getDataMap());
		return getParser().getRigheComunicazioni(_map);
	}

	/* (non-Javadoc)
	 * @see it.matrix.aod.postel.model.Fattura#getDettagli()
	 */
	@Override
	public Dettaglio[] getDettagli() {
		SortedMap<Integer, String> _map = getParser().buildDetailsDataMap(getDataMap());
		return getParser().getRigheDettaglio(_map);
	}

	/* (non-Javadoc)
	 * @see it.matrix.aod.postel.model.Fattura#getImponibiliIva()
	 */
	@Override
	public ImponibileIva[] getImponibiliIva() {
		SortedMap<Integer, String> _map = getParser().buildIvaDataMap(getDataMap());
		return getParser().getRigheImponibiliIva(_map);
	}

	/* (non-Javadoc)
	 * @see it.matrix.aod.postel.model.Fattura#getSconti()
	 */
	@Override
	public TotaleSconti getSconti() {
		SortedMap<Integer, String> _map = getParser().buildScontiDataMap(getDataMap());
		return getParser().getTotaleSconti(_map);
	}

	/* (non-Javadoc)
	 * @see it.matrix.aod.postel.model.Fattura#getTestata()
	 */
	@Override
	public TestataFattura getTestata() {
		
		SortedMap<Integer, String> _map = getParser().buildTestataDataMap(getDataMap());
		return getParser().getTestata(_map);
	}

	/* (non-Javadoc)
	 * @see it.matrix.aod.postel.model.Fattura#getTotale()
	 */
	@Override
	public TotaleFattura getTotale() {
		
		SortedMap<Integer, String> _map = getParser().buildTotaliDataMap(getDataMap());
		return getParser().getTotaleFattura(_map);
	}

}
