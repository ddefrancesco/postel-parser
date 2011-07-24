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
public class FatturaDahlia extends AbstractDocumento {

	@Override
	public Comunicazioni[] getComunicazioni() {
		SortedMap<Integer, String> _map = getParser().buildComunicazioniDataMap(getDataMap());
		return getParser().getRigheComunicazioni(_map);

		
	}

	@Override
	public Dettaglio[] getDettagli() {
		SortedMap<Integer, String> _map = getParser().buildDetailsDataMap(getDataMap());
		return getParser().getRigheDettaglio(_map);
	}

	@Override
	public ImponibileIva[] getImponibiliIva() {
		SortedMap<Integer, String> _map = getParser().buildIvaDataMap(getDataMap());
		return getParser().getRigheImponibiliIva(_map);
	}

	@Override
	public TotaleSconti getSconti() {
		SortedMap<Integer, String> _map = getParser().buildScontiDataMap(getDataMap());
		return getParser().getTotaleSconti(_map);
	}

	@Override
	public TestataFattura getTestata() {
		SortedMap<Integer, String> _map = getParser().buildTestataDataMap(getDataMap());
		return getParser().getTestata(_map);
	}

	@Override
	public TotaleFattura getTotale() {
		SortedMap<Integer, String> _map = getParser().buildTotaliDataMap(getDataMap());
		return getParser().getTotaleFattura(_map);

	}

}
