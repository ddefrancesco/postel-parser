package com.obone.postel.parser.billing;



import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.obone.postel.parser.ParserConstants;


public class SiTinATParser extends BaseTinAtParser{

	@Override
	public SortedMap<Integer, String> buildDetailsDataMap(
			TreeMap<Integer, String> rawMap) {
		SortedMap<Integer, String> _sm = new TreeMap<Integer, String>();
		SortedMap<Integer, String> _smtmp = new TreeMap<Integer, String>();
		Iterator<Entry<Integer, String>> mit = rawMap.entrySet().iterator();
		
		Integer[] fromKey = new Integer[15]; //max 15 pagine, è sufficiente Nicò? :-))
		Integer[] toKey = new Integer[15];
		int i = 0;
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			if (entry.getValue().startsWith("!SPA -100")) {
				fromKey[i] = entry.getKey();
				i++;
			} 
			
			if (entry.getValue().contains("!SPA 2;INL 0")) {
				toKey[i] = entry.getKey();
				
			}
//				if((entry.getValue().startsWith("!NEW")&& entry.getKey() > 0)){
//					
//				}
			
		}
		for(int j= 0;j<i;j++){
			//mit = rawMap.entrySet().iterator();
			_smtmp = rawMap.subMap(fromKey[j]+1, fromKey[j]+ ParserConstants.SITIN_DETAILS_PAG_SIZE);
			_sm.putAll(_smtmp) ;
		}
		return  _sm;

	}

}
