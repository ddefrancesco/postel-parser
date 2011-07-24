package com.obone.postel.parser.bill.test;
/**
 * 
 */


import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import com.obone.postel.parser.billing.DahliaATParser;
import com.obone.postel.parser.model.billing.Dettaglio;
import com.obone.postel.parser.model.billing.ImponibileIva;
import com.obone.postel.parser.model.billing.TestataFattura;
import com.obone.postel.parser.model.billing.TotaleSconti;




/**
 * @author ddefrancesco
 *
 */
@SuppressWarnings("deprecated")
public class ATHandler {
	static private DahliaATParser atp ;
	static int linesNumber = 57;
	static String file;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		atp = new DahliaATParser(linesNumber);
		file = "/home/ddefrancesco/workspace/aod-web/AOD/testaod/la7fatt.txt";		
		
		String[] lines = atp.readFileByLines(file);
		TreeMap<Integer, String> data = atp.getRawDataMap(lines);
		
		Iterator<Entry<Integer, String>> mit = data.entrySet().iterator();
		while(mit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)mit.next();
			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
		}
		
		System.out.println("Sottomappa dettagli: \n");
		
		SortedMap<Integer, String> details = atp.buildDetailsDataMap(data);
		Iterator<Entry<Integer, String>> smit = details.entrySet().iterator();
		while(smit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)smit.next();
			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
		}
		
		System.out.println("Sottomappa sconti: \n");
		SortedMap<Integer, String> scontiMap = atp.buildScontiDataMap(data);
		Iterator<Entry<Integer, String>> ssmit = scontiMap.entrySet().iterator();
		while(ssmit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)ssmit.next();
			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
		}		
//		
		System.out.println("Sottomappa testata: \n");
		SortedMap<Integer, String> testataMap = atp.buildTestataDataMap(data);
		Iterator<Entry<Integer, String>> tssmit = testataMap.entrySet().iterator();
		while(tssmit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)tssmit.next();
			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
		}	
//		
//		System.out.println("Sottomappa comunicazioni: \n");
//		SortedMap<Integer, String> commMap = atp.buildComunicazioniDataMap(data);
//		Iterator<Entry<Integer, String>> csmit = commMap.entrySet().iterator();
//		while(csmit.hasNext()){
//			Entry<Integer, String> entry = (Entry<Integer, String>)csmit.next();
//			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
//		}	
//		
//		System.out.println("Sottomappa iva: \n");
//		SortedMap<Integer, String> ivaMap = atp.buildIvaDataMap(data);
//		Iterator<Entry<Integer, String>> ismit = ivaMap.entrySet().iterator();
//		while(ismit.hasNext()){
//			Entry<Integer, String> entry = (Entry<Integer, String>)ismit.next();
//			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
//		}
//		
//		
//		
//		
//		System.out.println("Sottomappa scadenza e totale: \n");
//		SortedMap<Integer, String> stMap = atp.buildTotScadenzaDataMap(data);
//		Iterator<Entry<Integer, String>> stsmit = stMap.entrySet().iterator();
//		while(stsmit.hasNext()){
//			Entry<Integer, String> entry = (Entry<Integer, String>)stsmit.next();
//			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
//		}
//		
		System.out.println("Oggetto array dettagli: \n");
		
		
		Dettaglio[] dettagli = atp.getRigheDettaglio(details);
		for(int i = 0;i < dettagli.length;i++){
			Dettaglio dettaglio = dettagli[i];
			System.out.println("Descrizione " + i + ": "+ dettaglio.getDescrizione()+" \n");
			System.out.println("Periodo Validita' " + i + ": "+ dettaglio.getPeriodoRiferimento()+" \n");
			System.out.println("Prezzo " + i + ": "+ dettaglio.getPrezzo()+" \n");
			System.out.println("Quantita' " + i + ": "+ dettaglio.getQuantita()+" \n");
			System.out.println("Riferimento IVA " + i + ": "+ dettaglio.getAliquotaIva()+" \n");
			System.out.println("Importo " + i + ": "+ dettaglio.getImporto()+" \n");
		}
		
		System.out.println("Oggetto testata: \n");
		
		TestataFattura testata = atp.getTestata(testataMap);
		System.out.println("Cod. Cliente : "+ testata.getCodiceCliente()+" \n");
		System.out.println("Cod. Fiscale : "+ testata.getCodiceFiscale()+" \n");
		System.out.println("Prezzo : "+ testata.getDataEmissione()+" \n");
		System.out.println("Modalita' Pagamento : "+ testata.getModalitaPagamento()+" \n");
		System.out.println("Numero Documento : "+ testata.getNumeroDocumento()+" \n");
		System.out.println("Indirizzo : "+ testata.getPrimaRigaIndirizzo()+" \n");
		System.out.println("Indirizzo : "+ testata.getSecondaRigaIndirizzo()+" \n");
		System.out.println("Indirizzo : "+ testata.getTerzaRigaIndirizzo()+" \n");
		System.out.println("Indirizzo : "+ testata.getQuartaRigaIndirizzo()+" \n");
		System.out.println("Valuta : "+ testata.getValuta()+" \n");
		
		System.out.println("Totale : "+ testata.getTotaleScadenza().getTotale()+" \n");
		System.out.println("Scadenza : "+ testata.getTotaleScadenza().getScadenza()+" \n");
//		
//		ImponibileIva[] imponibiliIva = atp.getRigheImponibiliIva(ivaMap);
//		for(int i = 0;i < imponibiliIva.length;i++){
//			ImponibileIva imponibile = imponibiliIva[i];
//			System.out.println("Imponibile " + i + ": "+ imponibile.getImponibile()+" \n");
//			System.out.println("Aliquota " + i + ": "+ imponibile.getAliquota() +" \n");
//			System.out.println("Descrizione " + i + ": "+ imponibile.getDescrizione()+" \n");
//			
//		}
//		
//		System.out.println("Sottomappa totali: \n");
//		SortedMap<Integer, String> totMap = atp.buildTotaliDataMap(data);
//		Iterator<Entry<Integer, String>> totsmit = totMap.entrySet().iterator();
//		while(totsmit.hasNext()){
//			Entry<Integer, String> entry = (Entry<Integer, String>)totsmit.next();
//			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
//		}
//		
//		TotaleFattura totaleFattura = atp.getTotaleFattura(totMap);
//		System.out.println("Totale IVA : "+ totaleFattura.getTotaleIva()+" \n");
//		System.out.println("Totale Fattura : "+ totaleFattura.getTotaleFattura()+" \n");
//		System.out.println("Totale da pagare : "+ totaleFattura.getTotaleGenerale()+" \n");
//		
//		System.out.println("Oggetto Comunicazioni.\n");
//		Comunicazioni[] comunicazioni = atp.getRigheComunicazioni(commMap);
//		for(int i = 0;i < comunicazioni.length;i++){
//			Comunicazioni com = comunicazioni[i];
//			System.out.println("Id " + i + ": "+ com.getId()+" \n");
//			System.out.println("Descrizione " + i + ": "+ com.getDescrizione()+" \n");
//			
//		}
//		
		System.out.println("Sottomappa sconti: \n");
		SortedMap<Integer, String> scMap = atp.buildScontiDataMap(data);
		TotaleSconti sconti = atp.getTotaleSconti(scMap);
		System.out.println("Totale Lordo Sconti : "+ sconti.getTotaleImportoLordoSconti()+" \n");
		System.out.println("Margini M.D. : "+ sconti.getMargineMasterDealer()+" \n");
		System.out.println("Totale sconti: "+ sconti.getTotaleSconti()+" \n");
		System.out.println("Bolli: "+ sconti.getBolli()+" \n");
		
		System.out.println("Sottomappa iva: \n");
		
		SortedMap<Integer, String> ivaMap = atp.buildIvaDataMap(data);
		Iterator<Entry<Integer, String>> ivasmit = ivaMap.entrySet().iterator();
		while(ivasmit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)ivasmit.next();
			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
		}		
		
		ImponibileIva[] imponibiliIva = atp.getRigheImponibiliIva(ivaMap);
			for(int i = 0;i < imponibiliIva.length;i++){
				ImponibileIva iva = imponibiliIva[i];
				System.out.println("Imponibile " + i + ": "+ iva.getImponibile()+" \n");
				System.out.println("Aliquota " + i + ": "+ iva.getAliquota()+" \n");
				System.out.println("Descrizione " + i + ": "+ iva.getDescrizione()+" \n");
	
		}		
//		TotaleSconti sconti = atp.getTotaleSconti(scMap);
//		System.out.println("Totale Lordo Sconti : "+ sconti.getTotaleImportoLordoSconti()+" \n");
//		System.out.println("Margini M.D. : "+ sconti.getMargineMasterDealer()+" \n");
//		System.out.println("Totale sconti: "+ sconti.getTotaleSconti()+" \n");
//		System.out.println("Bolli: "+ sconti.getBolli()+" \n");		
		
		System.out.println("Sottomappa totali: \n");
		SortedMap<Integer, String> totMap = atp.buildTotaliDataMap(data);
		Iterator<Entry<Integer, String>> totsmit = totMap.entrySet().iterator();
		while(totsmit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)totsmit.next();
			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
		}		
		
		System.out.println("Sottomappa comunicazioni: \n");
		SortedMap<Integer, String> comMap = atp.buildComunicazioniDataMap(data);
		Iterator<Entry<Integer, String>> comsmit = comMap.entrySet().iterator();
		while(comsmit.hasNext()){
			Entry<Integer, String> entry = (Entry<Integer, String>)comsmit.next();
			System.out.println("Key: "+entry.getKey()+"\tValue: "+entry.getValue());
		}		
	}

}
