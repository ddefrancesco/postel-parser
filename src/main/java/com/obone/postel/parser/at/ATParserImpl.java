/**
 * 
 */
package com.obone.postel.parser.at;




import java.io.InputStream;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;

import com.obone.postel.parser.AbstractParser;
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

public abstract class ATParserImpl extends AbstractParser<Integer,String> implements ATParser {
	
	
	private int linesNumber;
	
	public ATParserImpl(){
		
		this.setLinesNumber(linesNumber);
	}	
	
	public ATParserImpl(int linesNumber){
		
		this.setLinesNumber(linesNumber);
	}
	public ATParserImpl(int linesNumber,String fileName){
		
		this.setLinesNumber(linesNumber);
		this.setFileName(fileName);
	}
	public ATParserImpl(String fileName,String template){
		
		this.setTemplate(template);
		this.setFileName(fileName);
	}	
	public ATParserImpl(int linesNumber,String fileName,String template){
		
		this.setLinesNumber(linesNumber);
		this.setFileName(fileName);
		this.setTemplate(template);
	}
	
	public int getLinesNumber() {
		return linesNumber;
	}

	public void setLinesNumber(int linesNumber) {
		this.linesNumber = linesNumber;
	}

	/**
	 * 
	 */
	@Override
	public String[] readFileByLines(String nomeFile){
		return super.readFileByLines(nomeFile);
	}
	/**
	 * @see it.matrix.aod.postel.at.ATParser#parse(java.lang.String)
	 */
	@Override
	public Document parse(String stream) {
		return null;
	}

	/**
	 * @see it.matrix.aod.postel.at.ATParser#parse(java.io.InputStream)
	 */
	@Override
	public org.dom4j.Document parse(InputStream is) throws DocumentException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#getRawDataMap(java.lang.String[])
	 */
	@SuppressWarnings("unchecked")
	public TreeMap<Integer, String> getRawDataMap(String[] fileLines){
		rawMap = new TreeMap<Integer, String>();
		Integer index = new Integer(1);
		//First Pass Compile
		for (int i = 0;i<fileLines.length;i++){
				if(rawMap.get(i)==null)
					rawMap.put(i, "");
				String trans = fileLines[i];
				if(trans == null)
					trans = "";
				
					
				if(trans.startsWith("!NEW")){
					
					int endValue = trans.indexOf(';');
					String value = trans.substring("!NEW".length(),endValue).trim();

					rawMap.put(index, value);
					index++;
				}
				else if(trans.startsWith("@?43")){
					
					
					String value = trans.substring("@?43".length()).trim();

					rawMap.put(index, value);
					index++;
				}
				else if(trans.startsWith("@O")){
					
					int endValue = trans.indexOf("@o");
					String value = trans.substring("@O".length(),endValue).trim();
					rawMap.put(index, value);
					index++;
				}
					

				else if(trans.startsWith("@?13")){
					
					if(trans.startsWith("@?13@>@+")){
						int endValue = trans.indexOf("@<@-");
						String value = trans.substring("@?13@>@+".length(),endValue).trim();

						rawMap.put(index, value);
						index++;
					}
					else if(trans.startsWith("@?13@>")){
						int endValue = trans.indexOf("@<");
						String value = trans.substring("@?13@>".length(),endValue).trim();
						rawMap.put(index, value);
						index++;
					}
					else{
						String value = trans.substring("@?13".length()).trim();
						rawMap.put(index, value);
						index++;
					}
				}
				
				else if(trans.startsWith("!TOP")||trans.startsWith("!TEX")){
					rawMap.put(index, trans);
					index++;
				}				/*...*/

				
				else {
					if(!trans.contains("@?35")){
						rawMap.put(index, trans.trim());
					}else{
						int endValue = trans.indexOf("@?35");
						String value = trans.substring(0,endValue).trim();
						rawMap.put(index, value);
					}
					
					index++;
				}
				
		}
		
		
		return rawMap;
	}

	/**
	 * @see it.matrix.aod.postel.at.ATParser#buildDetailsDataMap(java.util.TreeMap)
	 */
	@SuppressWarnings("null")
	public abstract SortedMap<Integer, String> buildDetailsDataMap(TreeMap<Integer, String> rawMap);
	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#buildScontiDataMap(java.util.TreeMap)
	 */
	public abstract SortedMap<Integer, String> buildScontiDataMap(TreeMap<Integer, String> rawMap);
	/**
	 * @see it.matrix.aod.postel.at.ATParser#buildTestataDataMap(java.util.TreeMap)
	 */
	public abstract SortedMap<Integer, String> buildTestataDataMap(TreeMap<Integer, String> rawMap);
	/**
	 * @see it.matrix.aod.postel.at.ATParser#buildComunicazioniDataMap(java.util.TreeMap)
	 */
	public abstract SortedMap<Integer, String> buildComunicazioniDataMap(TreeMap<Integer, String> rawMap);
	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#buildIvaDataMap(java.util.TreeMap)
	 */
	public abstract SortedMap<Integer, String> buildIvaDataMap(TreeMap<Integer, String> rawMap);
	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#buildTotaliDataMap(java.util.TreeMap)
	 */
	public abstract SortedMap<Integer, String> buildTotaliDataMap(TreeMap<Integer, String> rawMap);
	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#buildTotScadenzaDataMap(java.util.TreeMap)
	 */
	public abstract SortedMap<Integer, String> buildTotScadenzaDataMap(TreeMap<Integer, String> rawMap);
	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#getRigheDettaglio(java.util.SortedMap)
	 */
	@SuppressWarnings("deprecation")
	public abstract Dettaglio[] getRigheDettaglio(SortedMap<Integer, String> _map);
	/**
	 * @see it.matrix.aod.postel.at.ATParser#getTestata(java.util.SortedMap)
	 */
	@SuppressWarnings("deprecation")
	public abstract TestataFattura getTestata(SortedMap<Integer, String> _map);
	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#getRigheImponibiliIva(java.util.SortedMap)
	 */
	public abstract ImponibileIva[] getRigheImponibiliIva(SortedMap<Integer, String> _map);
	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#getTotaleSconti(java.util.SortedMap)
	 */
	public abstract TotaleSconti getTotaleSconti(SortedMap<Integer, String> _map);
	/**
	 * @see it.matrix.aod.postel.at.ATParser#getTotaleFattura(java.util.SortedMap)
	 */
	public TotaleFattura getTotaleFattura (SortedMap<Integer, String> _map){
		
		TotaleFattura totaleFattura = new TotaleFattura();
		
		
		totaleFattura.setTotaleIva(_map.get(0));
		totaleFattura.setTotaleFattura(_map.get(1));
		totaleFattura.setTotaleGenerale(_map.get(2));
		
		return totaleFattura;
	}	
	/**
	 * @see it.matrix.aod.postel.at.ATParser#getRigheComunicazioni(java.util.SortedMap)
	 */
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
	public TreeMap<Integer, String> filterBullettin(TreeMap<Integer, String> rawMap){
		SortedMap<Integer, String> _sm = null;
		
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		Integer toKey = rawMap.lastKey();
		
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if(entry.getValue().startsWith("CEETIN")||entry.getValue().startsWith("CCPTIN")||entry.getValue().startsWith("CPETIN")){
				toKey = entry.getKey();
			}
			if(entry.getValue().startsWith("!NEW") && entry.getKey() > 0){
				toKey = entry.getKey();
			}
			
		}
		_sm = rawMap.subMap(0, toKey+1);
//		_sm = this.rehashedMap(_sm);
		TreeMap<Integer, String> _tm = new TreeMap<Integer, String>(_sm);
		return _tm;
		
	}
	public TreeMap<Integer, String> filterMIL(TreeMap<Integer, String> rawMap){
		
		SortedMap<Integer, String> _sm = null;
		
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		Integer toKey = rawMap.lastKey();
		
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();

			if(entry.getValue().startsWith("!TOP;TEX") && entry.getKey() > 0){

					toKey = entry.getKey();
			}
			
		}
		_sm = rawMap.subMap(0, toKey);
		_sm.put(_sm.lastKey(), "");//Correzione per le Comunicazioni
//		_sm = this.rehashedMap(_sm);
		TreeMap<Integer, String> _tm = new TreeMap<Integer, String>(_sm);
		return _tm;

	}
}
