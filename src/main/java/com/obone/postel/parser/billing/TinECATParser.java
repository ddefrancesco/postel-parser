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
import com.obone.postel.parser.model.billing.Dettaglio;
import com.obone.postel.parser.model.billing.ImponibileIva;
import com.obone.postel.parser.model.billing.TestataFattura;
import com.obone.postel.parser.model.billing.TotaleScadenza;
import com.obone.postel.parser.model.billing.TotaleSconti;

/**
 * @author ddefrancesco
 *
 */
public class TinECATParser extends ATParserImpl {

	public TinECATParser(){}
	
	public TinECATParser(int linesNumber) {
			this.setLinesNumber(linesNumber);
		}
	public TinECATParser(int linesNumber,String fileName){
		this.setLinesNumber(linesNumber);
		this.setFileName(fileName);
	}
	public TinECATParser(String fileName,String template){
		this.setTemplate(template);
		this.setFileName(fileName);
	}
	public TinECATParser(int linesNumber,String fileName,String template){
		this.setLinesNumber(linesNumber);
		this.setTemplate(template);
		this.setFileName(fileName);
	}
	

	public SortedMap<Integer, String> buildDetailsDataMap(TreeMap<Integer, String> rawMap){
			
			
		SortedMap<Integer, String> _sm = new TreeMap<Integer, String>();
		SortedMap<Integer, String> _smtmp = new TreeMap<Integer, String>();
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		
		Integer[] fromKey = new Integer[15]; //max 15 pagine, è sufficiente Nicò? :-))
		Integer toKey = 0;
		int i = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if (entry.getValue().startsWith("!SPA -100")) {
				fromKey[i] = entry.getKey();
				i++;
			} 
			
			if (entry.getValue().contains("!SPA 2;INL 0")) {
				toKey = entry.getKey();
			}
			
			
		}
		for(int j= 0;j<i;j++){
			//mit = rawMap.entrySet().iterator();
			_smtmp = rawMap.subMap(fromKey[j]+1, fromKey[j]+ ParserConstants.TIN_DETAILS_PAG_SIZE);
			_sm.putAll(_smtmp) ;
		}
		
		return  _sm;
		}
		
	public SortedMap<Integer, String> buildScontiDataMap(TreeMap<Integer, String> rawMap){
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		
		while(mit.hasNext()){
			
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			String value = entry.getValue();
			if (entry.getValue().startsWith("@?42") && !entry.getValue().contains("@?32")){
				String[] values = new String[4];
	
					values[0] = value.substring(0, value.indexOf(' '));
					value = value.substring(values[0].length()).trim();
					//map.put(new Integer(0), values[0]);
					values[1] = value.substring(0, value.indexOf(' '));
					value = value.substring(values[1].length()).trim();
					map.put(new Integer(0), values[1]);
					values[2] = value.substring(0, value.indexOf(' '));
					value = value.substring(values[2].length()).trim();
					map.put(new Integer(1), values[2]);
					values[3]=value;
					map.put(new Integer(2), values[3]);
			}
		}
		
		SortedMap<Integer, String> sm = map;
		
		return sm;
	}
	
	public SortedMap<Integer, String> buildTestataDataMap(TreeMap<Integer, String> rawMap){
		
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
	
	public SortedMap<Integer, String> buildComunicazioniDataMap(TreeMap<Integer, String> rawMap){
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		Integer fromKey = 0;
		Integer toKey = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if (entry.getValue().startsWith("!SPA 2")){
				fromKey = entry.getKey();
			}
			
		}
		toKey = rawMap.size();
		return  rawMap.subMap(fromKey+1,toKey+1);
	}
	
	public SortedMap<Integer, String> buildIvaDataMap(TreeMap<Integer, String> rawMap){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		
		int i = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if(entry.getValue().contains("@?17") && entry.getValue().contains("@?01")){
				map.put(new Integer(i),entry.getValue());
				i++;
			}
			
		}
		
		SortedMap<Integer, String> sm = map;
		
		return  sm;
	}
	
	public SortedMap<Integer, String> buildTotaliDataMap(TreeMap<Integer, String> rawMap){
		
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		
		int i = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			String value = entry.getValue();
//			if(entry.getValue().contains("@?61")){
//				map.put(new Integer(0),value.trim().substring(0, value.indexOf(' ')));
//				value = value.substring(map.get(0).length()).trim();
//				map.put(new Integer(1),value.trim().substring(0, value.indexOf("@?61")));
//				value = value.substring(value.indexOf("@?61")+ "@?61".length()).trim();
//				map.put(new Integer(2),value);
//			}
			if (entry.getValue().startsWith("@?42") && entry.getValue().contains("@?32")){
				String[] values = new String[2];
	
					values[0] = value.substring(value.indexOf("@?42")+"@?42".length(), value.indexOf("@?32")).trim();
					value = value.substring(values[0].length()).trim();
					map.put(new Integer(0), values[0]);
					values[1] = value.substring(value.indexOf("@?32")+"@?32".length(), value.indexOf(' '));
					value = value.substring(values[1].length()).trim();
					map.put(new Integer(1), values[1]);
//					values[2] = value.substring(value.indexOf("@?32")+1, value.indexOf(' '));
//					value = value.substring(values[2].length()).trim();
//					map.put(new Integer(1), values[2]);
//					values[3]=value;
//					map.put(new Integer(2), values[3]);
			}			
		}
		
		SortedMap<Integer, String> sm = map;
		
		return  sm;
	}	
	
	public SortedMap<Integer, String> buildTotScadenzaDataMap(TreeMap<Integer, String> rawMap){
		rawMap = filterBullettin(rawMap);
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if(entry.getValue().contains("@?52")){
				map.put(new Integer(0),entry.getValue().substring(0, entry.getValue().indexOf("@?52")));
				if(entry.getValue().endsWith("@?01"))
					map.put(new Integer(1),entry.getValue().substring(entry.getValue().indexOf("@?52")+"@?52".length(),entry.getValue().indexOf("@?01")));
				else
					map.put(new Integer(1),entry.getValue().substring(entry.getValue().indexOf("@?52")+"@?52".length()));
			}
			
		}
		
		SortedMap<Integer, String> sm = map;
		
		return  sm;
	}	

	public Dettaglio[] getRigheDettaglio(SortedMap<Integer, String> _map){
		Dettaglio[] righeDettaglio = new Dettaglio[_map.size()];
		Iterator<Entry<Integer, String>> mit = _map.entrySet().iterator();
		int i = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			String value = entry.getValue();
			String record = value;
			Dettaglio rigaDettaglio = new Dettaglio();
			
			if(!record.equals("")){
				rigaDettaglio.setDescrizione(value.substring(0, entry.getValue().indexOf("   ")));
				value = value.substring(rigaDettaglio.getDescrizione().length()).trim();
				
				if(record.charAt(37) != ' '){
					rigaDettaglio.setPeriodoRiferimento(value.substring(0, value.indexOf(' ')));
					value = value.substring(rigaDettaglio.getPeriodoRiferimento().length()).trim();
				}else{
					rigaDettaglio.setPeriodoRiferimento("");
					
				}
				if(record.charAt(67) != ' '){
				rigaDettaglio.setPrezzo(value.substring(0, value.indexOf(' ')));
				value = value.substring(rigaDettaglio.getPrezzo().length()).trim();
				}else{
					rigaDettaglio.setPrezzo("");
				}
				if(record.charAt(78) != ' '){
					rigaDettaglio.setQuantita(value.substring(0, value.indexOf(' ')));
					value = value.substring(rigaDettaglio.getQuantita().length()).trim();
				}else{
					rigaDettaglio.setQuantita("");
					
				}
				rigaDettaglio.setAliquotaIva(value.substring(0, value.indexOf(' ')));
				value = value.substring(rigaDettaglio.getAliquotaIva().length()).trim();
				rigaDettaglio.setImporto(value);
				
				
				
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

	
	public TestataFattura getTestata(SortedMap<Integer, String> _map){
		
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
		testata.setCodiceCliente(_map.get(15));
		testata.setCodiceFiscale(_map.get(17));
		testata.setModalitaPagamento(_map.get(19));
		testata.setRiferimentoFattura(_map.get(21));
		testata.setValuta(_map.get(23));
		
		TotaleScadenza totaleScadenza = new TotaleScadenza();
		//TreeMap<Integer, String> _tmp = new TreeMap<Integer, String>(_map);
		SortedMap<Integer, String> _tmap = buildTotScadenzaDataMap(rawMap);

		totaleScadenza.setTotale(_tmap.get(0));
		totaleScadenza.setScadenza(_tmap.get(1));
		testata.setTotaleScadenza(totaleScadenza);
			
		
		
		return testata;
	}
	
public TotaleSconti getTotaleSconti(SortedMap<Integer, String> _map){
		
		TotaleSconti sconti = new TotaleSconti();
		
		sconti.setTotaleImportoLordoSconti(_map.get(0));
		sconti.setTotaleSconti(_map.get(1));
		sconti.setBolli(_map.get(2));
		
		return sconti;
	}

public ImponibileIva[] getRigheImponibiliIva(SortedMap<Integer, String> _map){
	
	ImponibileIva[] righeImponibiliIva = new ImponibileIva[_map.size()];
	
	Iterator<Entry<Integer, String>> mit = _map.entrySet().iterator();
//	int i = 0;
	while(mit.hasNext()){
		Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
		String value = entry.getValue();
		String record = value;
		ImponibileIva imponibileIva = new ImponibileIva();
		
		if(!record.startsWith("@?17")){
			imponibileIva.setIndice(entry.getKey());
			imponibileIva.setImponibile(value.substring(0, value.indexOf(' ')));
			value = value.substring(imponibileIva.getImponibile().length()).trim();
			imponibileIva.setAliquota(value.substring(0, value.indexOf("@?17")));
			imponibileIva.setDescrizione(value.substring(value.indexOf("@?17")+"@?17".length(),value.indexOf("@?01")));
		}else{
			imponibileIva.setIndice(entry.getKey());
			imponibileIva.setImponibile("");
			imponibileIva.setAliquota("");
			imponibileIva.setDescrizione("");
		}
		righeImponibiliIva[entry.getKey()] = imponibileIva;
	}
	return righeImponibiliIva;
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
	//_rawDataMap = filterBullettin(_rawDataMap);
	//_rawDataMap = filterMIL(_rawDataMap);
	setLogotipo(_rawDataMap.get(1));
	return _rawDataMap;
}
}
