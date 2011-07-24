/**
 * 
 */
package com.obone.postel.parser.billing;


import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.obone.postel.parser.ParserConstants;
import com.obone.postel.parser.at.ATParserImpl;
import com.obone.postel.parser.model.billing.Comunicazioni;
import com.obone.postel.parser.model.billing.Dettaglio;
import com.obone.postel.parser.model.billing.ImponibileIva;
import com.obone.postel.parser.model.billing.TestataFattura;
import com.obone.postel.parser.model.billing.TotaleScadenza;
import com.obone.postel.parser.model.billing.TotaleSconti;

/**
 * @author ddefrancesco
 *
 */
public class DahliaATParser extends ATParserImpl {
	
	private int linesNumber = 70;
	
	public DahliaATParser(){
		this.setLinesNumber(linesNumber);
	}
	
	public DahliaATParser(int linesNumber){
		
		this.setLinesNumber(linesNumber);
		
	}
	public DahliaATParser(int linesNumber,String fileName){
		this.setLinesNumber(linesNumber);
		this.setFileName(fileName);
	}
	public DahliaATParser(String fileName,String template){
		this.setTemplate(template);
		this.setFileName(fileName);

		
	}
	public DahliaATParser(int linesNumber,String fileName,String template){
		this.setLinesNumber(linesNumber);
		this.setTemplate(template);
		this.setFileName(fileName);

	}
	/**
	 * @author ddefrancesco
	 * @param rawMap
	 * @return TreeMap<Integer, String> _tm 
	 * @category Utility method. 
	 * 
	 * This method builds the generic part of footer that embeds 
	 * IVA descriptions, totals both net and gross.
	 * The resulting map will be used by buildIvaDataMap(TreeMap<Integer, String> rawMap)
	 * and buildTotaliDataMap(TreeMap<Integer, String> rawMap).
	 */
	
	private TreeMap<Integer, String> buildFooterDataMap(TreeMap<Integer, String> rawMap){
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		Integer fromKey = 0;
		Integer toKey = 0;
		Integer spa1Key= 0;
		Integer spa2Key= 0;
		String line="";
		TreeMap<Integer, String> _tm = new TreeMap<Integer, String>();
		
		int k = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if (entry.getValue().startsWith("!SPA 2;INL 0")){
				fromKey = entry.getKey();
				line = rawMap.get(fromKey + 1);
			}
			if (entry.getValue().startsWith("!SPA 1") && entry.getKey()>12)
				spa1Key = entry.getKey();
			if (entry.getValue().startsWith("!SPA 2") && !entry.getValue().contains(";INL 0") && entry.getKey()>12)
				spa2Key = entry.getKey();
				
		}
		_tm.put(new Integer(0), line);	
		
		_tm.put(new Integer(1), rawMap.get(spa1Key -1));	
		
		_tm.put(new Integer(2), rawMap.get(spa2Key +1));


		
		return _tm;
			
}
	/**
	 * @author ddefrancesco
	 * @param rawMap
	 * @return TreeMap<Integer, String> _tm 
	 * @category Utility method. 
	 * 
	 * This method builds the generic part of footer that embeds 
	 * IVA descriptions, totals both net and gross.
	 * The resulting map will be used by buildIvaDataMap(TreeMap<Integer, String> rawMap)
	 * and buildTotaliDataMap(TreeMap<Integer, String> rawMap).
	 */
	
	private SortedMap<Integer, String> buildImponibiliIvaDataMap(TreeMap<Integer, String> rawMap){
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		Integer spa1Key= 0;
		Integer spa2Key= 0;
		
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if (entry.getValue().startsWith("!SPA 2") && !entry.getValue().contains(";INL 0") && entry.getKey()>12)
				spa2Key = entry.getKey();
			if (entry.getValue().startsWith("!SPA 1") && entry.getKey()>12)
				spa1Key = entry.getKey();

		}
		SortedMap<Integer, String> _tm = rehashedMap(rawMap.subMap(spa2Key+1, spa1Key-2));
		
		
		return _tm;
		
	}

		
	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#buildComunicazioniDataMap(java.util.TreeMap)
	 */
	@Override
	public SortedMap<Integer, String> buildComunicazioniDataMap(TreeMap<Integer, String> rawMap){
			Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
			Integer fromKey = 0;
			while(mit.hasNext()){
				Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
				if (entry.getValue().startsWith("!SPA 1")&& entry.getKey()>12){
					fromKey = entry.getKey();
				}
			}
			return rawMap.tailMap(fromKey,false);
	}

	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#buildDetailsDataMap(java.util.TreeMap)
	 */
	@Override
	public SortedMap<Integer, String> buildDetailsDataMap(
			TreeMap<Integer, String> rawMap) {
		
		SortedMap<Integer, String> _sm = new TreeMap<Integer, String>();
		SortedMap<Integer, String> _smtmp = new TreeMap<Integer, String>();

		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();

		Integer[] fromKey = new Integer[15]; //max 15 pagine, è sufficiente Nicò? :-))		
		Integer[] toKey = new Integer[15];
		int i = 0;
//		Integer fromKey = 0;
//		Integer toKey = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if (entry.getValue().startsWith("!SPA -100")) {
				fromKey[i] = entry.getKey();
				i++;
			} 
			
 			if (entry.getValue().contains("!SPA 2;INL 0")) {
				toKey[i] = entry.getKey();
				
			}
			
			
		}

		for(int j= 0;j<i;j++){
			//mit = rawMap.entrySet().iterator();
			_smtmp = rawMap.subMap(fromKey[j]+1, fromKey[j]+ ParserConstants.DAHLIA_DETAILS_PAG_SIZE);
			_sm.putAll(_smtmp) ;
		}
		
		return  _sm;
	}

	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#buildIvaDataMap(java.util.TreeMap)
	 */
	@Override
	public SortedMap<Integer, String> buildIvaDataMap(
			TreeMap<Integer, String> rawMap) {

		
		SortedMap<Integer, String> footerMap = buildImponibiliIvaDataMap(rawMap);
		
		String firstLine = footerMap.get(0);
		footerMap.put(0, firstLine.substring(0, 68));
		
		return footerMap.subMap(0, footerMap.lastKey()); 

	}

	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#buildScontiDataMap(java.util.TreeMap)
	 */
	@Override
	public SortedMap<Integer, String> buildScontiDataMap(
			TreeMap<Integer, String> rawMap) {
		SortedMap<Integer, String> sm = new TreeMap<Integer, String>();
		sm.put(new Integer(0), rawMap.get(50));
		return sm;
	}

	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#buildTestataDataMap(java.util.TreeMap)
	 */
	@Override
	public SortedMap<Integer, String> buildTestataDataMap(
			TreeMap<Integer, String> rawMap) {
		SortedMap<Integer, String> sm = new TreeMap<Integer, String>();
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		
		Integer fromKey = 0;
		Integer toKey = 0;
		int i = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			
			if (entry.getKey() >= 2 && entry.getKey() <= 26) {
				
				sm.put(new Integer(i++), entry.getValue());
				
			} 
			if (entry.getKey() == 2) fromKey = entry.getKey(); 
			if (entry.getKey() == 26) toKey = entry.getKey();
		}
		
		
		//sm = rawMap.subMap(fromKey, toKey);
		
		return sm;

	}

	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#buildTotScadenzaDataMap(java.util.TreeMap)
	 */
	@Override
	public SortedMap<Integer, String> buildTotScadenzaDataMap(
			TreeMap<Integer, String> rawMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#buildTotaliDataMap(java.util.TreeMap)
	 */
	@Override
	public SortedMap<Integer, String> buildTotaliDataMap(
			TreeMap<Integer, String> rawMap) {

		TreeMap<Integer, String> footerMap = buildFooterDataMap(rawMap);
//		SortedMap<Integer, String> _sm = footerMap.subMap(footerMap.lastKey(),footerMap.lastKey()+1);

		SortedMap<Integer, String> _sms = new TreeMap<Integer, String>();
		//int k = 0;
	

			String value=footerMap.get(0);
			String s = value;
			
			s=value.substring(0, value.indexOf('-')).trim();		
			_sms.put(new Integer(1), s);
			
//			s=value.substring(value.indexOf('-')+1).trim();
//			s=s.substring(0,s.indexOf(' ')).trim();
//			_sms.put(new Integer(1), s);
			String totValue = footerMap.get(1);
			totValue = totValue.substring(totValue.lastIndexOf(' ')).trim();
			_sms.put(new Integer(2), totValue);
			String ivaTot =footerMap.get(2).substring(0,footerMap.get(2).lastIndexOf(' ') ).trim();
			ivaTot = ivaTot.substring(ivaTot.lastIndexOf(' ')).trim();
			//String ivaMap = 
			_sms.put(new Integer(0), ivaTot);
		
		return _sms;
	}

	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#getRigheDettaglio(java.util.SortedMap)
	 */
	@Override
	public Dettaglio[] getRigheDettaglio(SortedMap<Integer, String> _map) {
		Dettaglio[] righeDettaglio = new Dettaglio[_map.size()];
		Iterator<Entry<Integer, String>> mit = _map.entrySet().iterator();
		int i = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			String value = entry.getValue();
			String record = value;
			Dettaglio rigaDettaglio = new Dettaglio();
			rigaDettaglio.setPeriodoRiferimento("");
			if(!record.equals("") && record.length() >= 55){
				
				rigaDettaglio.setDescrizione(value.substring(0, 55));
				value = value.substring(rigaDettaglio.getDescrizione().length()).trim();
				
				if(record.charAt(61) != ' '){
					rigaDettaglio.setPrezzo(value.substring(0, value.indexOf(' ')));
					value = value.substring(rigaDettaglio.getPrezzo().length()).trim();
				}else{
					rigaDettaglio.setPrezzo("");
					
				}
				if(record.charAt(68) != ' '){
					rigaDettaglio.setQuantita(value.substring(0, value.indexOf(' ')));
					value = value.substring(rigaDettaglio.getQuantita().length()).trim();
				}else{
					rigaDettaglio.setQuantita("");
					
				}
				if(record.charAt(70) != ' '){
					rigaDettaglio.setAliquotaIva(value.substring(0, value.indexOf(' ')));
					value = value.substring(rigaDettaglio.getQuantita().length()).trim();
				}else{
					rigaDettaglio.setAliquotaIva(value.substring(0, value.indexOf(' ')));
					
				}				
				
				value = value.substring(rigaDettaglio.getAliquotaIva().length()).trim();
				rigaDettaglio.setImporto(value);
				
				
				
			}else if(!record.equals("") && record.length() < 55){
				rigaDettaglio.setAliquotaIva("");
				rigaDettaglio.setImporto("");
				rigaDettaglio.setPrezzo ("");
				rigaDettaglio.setPeriodoRiferimento("");
				rigaDettaglio.setQuantita("");
				rigaDettaglio.setDescrizione(record.trim());

			}else{
				rigaDettaglio.setAliquotaIva("");
				rigaDettaglio.setDescrizione("");
				rigaDettaglio.setImporto("");
				rigaDettaglio.setPrezzo ("");
				rigaDettaglio.setPeriodoRiferimento("");
				rigaDettaglio.setQuantita("");
			}
			righeDettaglio[i] = rigaDettaglio;
			i++;
		}
		
		
		
		return righeDettaglio;

	}

	/**
	 * @see it.matrix.aod.postel.at.ATParserImpl#getTestata(java.util.SortedMap)
	 */
	@Override
	public TestataFattura getTestata(SortedMap<Integer, String> _map) {
		TestataFattura testata= new TestataFattura();
		testata.setPrimaRigaIndirizzo(_map.get(0));
		testata.setSecondaRigaIndirizzo(_map.get(1));	
		testata.setTerzaRigaIndirizzo(_map.get(2));
		testata.setQuartaRigaIndirizzo(_map.get(3));
		testata.setPrimaRigaIndirizzoSedeLegale(_map.get(5));
		testata.setSecondaRigaIndirizzoSedeLegale(_map.get(6));
		testata.setTerzaRigaIndirizzoSedeLegale(_map.get(7));
		testata.setTipoDocumento(_map.get(9));
		testata.setNumeroDocumento(_map.get(11));
		testata.setDataEmissione(_map.get(13));
		
		testata.setCodiceCliente(_map.get(17));
		testata.setCodiceFiscale(_map.get(19));
		String modalitaPagamento = _map.get(21).substring(_map.get(21).indexOf("@?11@+")+ "@?11@+".length(), _map.get(21).indexOf("@-"))+
		_map.get(22).substring(_map.get(22).indexOf("@?11@+")+ "@?11@+".length(), _map.get(22).indexOf("@-"));
		testata.setModalitaPagamento(modalitaPagamento);
		testata.setRiferimentoFattura(_map.get(24));
		testata.setValuta("");
		
		TotaleScadenza totaleScadenza = new TotaleScadenza();
//		SortedMap<Integer, String> _tmap = buildTotScadenzaDataMap(rawMap);

		totaleScadenza.setTotale("");
		totaleScadenza.setScadenza(_map.get(15));
		testata.setTotaleScadenza(totaleScadenza);
			
		
		
		return testata;		
	}

public TotaleSconti getTotaleSconti(SortedMap<Integer, String> _map){
		
		TotaleSconti sconti = new TotaleSconti();
		String value = _map.get(0);
		sconti.setTotaleImportoLordoSconti(value.substring(0, value.indexOf('-')));
		value = value.substring(value.indexOf('-')+1).trim();
		sconti.setMargineMasterDealer(value.substring( 0, value.indexOf(' ')));
		value = value.substring(value.indexOf(' ')).trim();
		sconti.setTotaleSconti(value.substring( 0, value.indexOf(' ')));
		value = value.substring(value.indexOf(' ')).trim();
		sconti.setBolli(value);
		
		return sconti;
	}

public Comunicazioni[] getRigheComunicazioni(SortedMap<Integer, String> _map){
	
		Comunicazioni[] comunicazioni = new Comunicazioni[_map.size()];
		Iterator<Entry<Integer, String>> mit = _map.entrySet().iterator();
		int i = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
	
			Comunicazioni comunicazione = new Comunicazioni();
			
			comunicazione.setId(""+i);
			comunicazione.setDescrizione(entry.getValue());
			
			comunicazioni[i] = comunicazione;
			i++;
		}
		
		return comunicazioni;
	}

public ImponibileIva[] getRigheImponibiliIva(SortedMap<Integer, String> _map){
	
	ImponibileIva[] righeImponibiliIva = new ImponibileIva[_map.size()];
	
	Iterator<Entry<Integer, String>> mit = _map.entrySet().iterator();

	while(mit.hasNext()){
		Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
		String value = entry.getValue();
		
		ImponibileIva imponibileIva = new ImponibileIva();
		imponibileIva.setIndice(entry.getKey());
		if(!value.equals("")){
			
			imponibileIva.setImponibile(value.substring(0, value.indexOf(' ')));
			value = value.substring(imponibileIva.getImponibile().length()).trim();
			imponibileIva.setAliquota(value.substring(0, value.indexOf(' ')));
			value = value.substring(imponibileIva.getAliquota().length()).trim();
			imponibileIva.setDescrizione(value);
		}else{
			
			imponibileIva.setImponibile("");
			imponibileIva.setAliquota("");
			imponibileIva.setDescrizione("");
		}
		righeImponibiliIva[entry.getKey()] = imponibileIva;
	}
	return righeImponibiliIva;
}

@Override
public TreeMap<Integer, String> filterBullettin(TreeMap<Integer, String> rawMap) {
	return rawMap;
}

/**
 * @author ddefrancesco
 * @return _rawDataMap TreeMap<Integer, String> Raw map parsed from the file.
 * @category Mandatory method
 * 
 * {@code  ATParser parser = new ATParserImpl();
			parser.setFileName(file);
			TreeMap<Integer , String> map = parser.getParsedRawDataMap();
			String _logo = parser.getLogotipo();}
 */
public TreeMap<Integer, String> getParsedRawDataMap() {
	String[] fileLines = readFileByLines(getFileName());
	
	TreeMap<Integer, String> _rawDataMap = getRawDataMap(fileLines);
	_rawDataMap = filterBullettin(_rawDataMap);
	//_rawDataMap = filterMIL(_rawDataMap);
	setLogotipo(_rawDataMap.get(1));
	return _rawDataMap;
}

}
