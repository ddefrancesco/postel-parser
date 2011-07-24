package com.obone.postel.parser.at;



import java.io.InputStream;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;

import com.obone.postel.parser.model.billing.Comunicazioni;
import com.obone.postel.parser.model.billing.Dettaglio;
import com.obone.postel.parser.model.billing.ImponibileIva;
import com.obone.postel.parser.model.billing.TestataFattura;
import com.obone.postel.parser.model.billing.TotaleFattura;
import com.obone.postel.parser.model.billing.TotaleSconti;

public interface ATParser {

	public String getLogotipo();
	
	public String getTemplate();
	
	public void setTemplate(String template);
	
	public void setFileName(String fileName);
	
	public String getFileName();

	public Document parse(String stream);

	public org.dom4j.Document parse(InputStream is) throws DocumentException;

	public TreeMap<Integer, String> getRawDataMap(String[] fileLines);
	
	public TreeMap<Integer, String> getParsedRawDataMap();
	
	public TreeMap<Integer, String> filterBullettin(TreeMap<Integer, String> rawMap);

	@SuppressWarnings("null")
	public SortedMap<Integer, String> buildDetailsDataMap(
			TreeMap<Integer, String> rawMap);

	public SortedMap<Integer, String> buildScontiDataMap(
			TreeMap<Integer, String> rawMap);

	public SortedMap<Integer, String> buildTestataDataMap(
			TreeMap<Integer, String> rawMap);

	public SortedMap<Integer, String> buildComunicazioniDataMap(
			TreeMap<Integer, String> rawMap);

	public SortedMap<Integer, String> buildIvaDataMap(
			TreeMap<Integer, String> rawMap);

	public SortedMap<Integer, String> buildTotaliDataMap(
			TreeMap<Integer, String> rawMap);

	public SortedMap<Integer, String> buildTotScadenzaDataMap(
			TreeMap<Integer, String> rawMap);

	@SuppressWarnings("deprecation")
	public Dettaglio[] getRigheDettaglio(SortedMap<Integer, String> _map);

	@SuppressWarnings("deprecation")
	public TestataFattura getTestata(SortedMap<Integer, String> _map);

	public ImponibileIva[] getRigheImponibiliIva(SortedMap<Integer, String> _map);

	public TotaleSconti getTotaleSconti(SortedMap<Integer, String> _map);

	public TotaleFattura getTotaleFattura(SortedMap<Integer, String> _map);

	public Comunicazioni[] getRigheComunicazioni(SortedMap<Integer, String> _map);
	
	public String[] readFileByLines(String nomeFile);
	

}