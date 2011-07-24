/**
 * 
 */
package com.obone.postel.parser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author ddefrancesco
 *
 */
public abstract class AbstractParser<K,V> {
	protected static Logger logger = Logger.getLogger( AbstractParser.class );
	protected TreeMap<K,V> rawMap = null;
	private int linesNumber;
	private String logotipo;
	private String fileName;
	private String filePath;
	private String template;
	
	public int getLinesNumber() {
		return linesNumber;
	}

	public void setLinesNumber(int linesNumber) {
		this.linesNumber = linesNumber;
	}	
	
	public String getLogotipo() {
		return logotipo;
	}
	public void setLogotipo(String logotipo) {
		this.logotipo = logotipo;
	}
	
	public String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}
	/**
	 * @param filePath
	 *            the name of the file to open. Not sure if it can accept URLs
	 *            or just filenames. Path handling could be better, and buffer
	 *            sizes are hardcoded
	 */
	protected String readFileAsString(String filePath)
			throws java.io.IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return fileData.toString();
	}
	
	public String[] readFileByLines(String nomeFile){
		
		String[] lines = new String[calcFileLines(nomeFile)];
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(nomeFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			int i = 0;
			
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				
				lines[i] = strLine;
				
				i++;
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return lines;
	}
	public List<String> readFileByLinesInList(String nomeFile){
		
		List<String> lines = new ArrayList<String>();
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(nomeFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {
				lines.add(strLine);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return lines;
	}	
	protected int calcFileLines(String nomeFile){
		List<String> lines = new ArrayList<String>();
		lines = readFileByLinesInList(nomeFile);
		return lines.size();
		
	}
	public SortedMap<Integer, V> rehashedMap(SortedMap<Integer, V> _map){
		Iterator<Entry<Integer,V>> mit = _map.entrySet().iterator();
		SortedMap<Integer, V> rehashMap = new TreeMap<Integer, V>();
		int k = 0;
		while(mit.hasNext()){
			Entry<Integer, V> entry = (Entry<Integer, V>)mit.next();
			rehashMap.put(new Integer(k), entry.getValue());
			k++;
		}
		return rehashMap;
		
	}
	public String getLogotipo(String nomeFile){
		String strLine="";
		
		try {
			// Open the file that is the first
			// command line parameter
			logger.debug("Worm Path: "+ filePath);
			
			FileInputStream fstream = new FileInputStream(getFilePath()+"/"+ nomeFile);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			// Read File Line By Line
			strLine = br.readLine();
			
			strLine = strLine.substring(strLine.indexOf("!NEW")+"!NEW".length(), strLine.indexOf(";")).trim();
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

		return strLine;
	}
	public abstract Document parse(String stream) ;
	public abstract org.dom4j.Document parse(InputStream is) throws DocumentException ;

	/**
	 * @see it.matrix.aod.postel.at.ATParser#setFileName(java.lang.String)
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
