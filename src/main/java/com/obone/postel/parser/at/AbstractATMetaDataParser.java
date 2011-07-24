/**
 * 
 */
package com.obone.postel.parser.at;



import java.util.List;
import java.util.Map;

import com.obone.postel.parser.AbstractParser;
import com.obone.postel.parser.at.comandi.Comando;

/**
 * @author ddefrancesco
 *
 */
public abstract class AbstractATMetaDataParser<K,V> extends AbstractParser{
	static List<Comando> comandi;
	protected abstract Map<K,V> getMetaDataMap(String[] fileLines);
	protected abstract Comando parseComando(String riga);
	protected List<Comando> getComandi(Comando comando){
		
		comandi.add(comando);
		return comandi;
	}
	
}
