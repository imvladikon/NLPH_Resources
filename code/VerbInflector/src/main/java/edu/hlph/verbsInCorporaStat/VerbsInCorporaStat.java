package edu.hlph.verbsInCorporaStat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.hlph.fileReadersAndWriters.nonVocalizedWordsFileReader.VerbCSVReader;

import edu.hlph.fileReadersAndWriters.corporaFileReaders.MorphologyAnalyzedCorporaReader;


import edu.hlph.hebrewBasicsImplementation.letters.HebrewLetter;
import edu.hlph.hebrewBasicsImplementation.letters.Literals;


public class VerbsInCorporaStat {
	
	private int totalVerbsInCorpora = 0;
	private int knownVerbsInCorpora = 0;
	private HashMap<String, Integer> corporaVerbs;
	private List<String> knownVerbs;
	
	public VerbsInCorporaStat(String verbCountsFile, String theVerbIndexFile) {
		MorphologyAnalyzedCorporaReader r = new MorphologyAnalyzedCorporaReader();
		r.readVerbCountsFromFile(verbCountsFile);
		this.corporaVerbs = r.getVerbCounts();
		VerbCSVReader vr = new VerbCSVReader(theVerbIndexFile);
		String[] inflectionsExtendedToStrings = vr.toStringExtended().split("\n");
		this.knownVerbs = new ArrayList<String>();
		
		
		for (String inf:inflectionsExtendedToStrings){
			//List<String> knownKeys = new ArrayList<String>();
			String[] splittedInf = inf.split(",");
			String vocalizedInf = splittedInf[2];
			
			String nonVocalizedInf = vocalizedInf.replaceAll(Literals.DAGESH, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.H_KAMATS, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.H_PATAH, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.H_SEGOL, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.HIRIK, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.HOLAM_H, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.HOLAM_M, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.KAMATS, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.KAMATS_KATAN, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.KUBUTS, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.MAPIK, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.PATAH, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.SEGOL, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.SHIN_DOT, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.SHURUK, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.SHVA, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.SIN_DOT, "");
			nonVocalizedInf = nonVocalizedInf.replaceAll(Literals.TSERE, "");
			
			//String[] morphAttrs = splittedInf[3].split("+");
			this.knownVerbs.add(nonVocalizedInf);
			
			
		}
		
		
		for (String k : this.corporaVerbs.keySet()){
			//System.out.println("key=" + k + ", value=" + this.corporaVerbs.get(k));
			int value = this.corporaVerbs.get(k);
			String kWitoutMorph = k.split(",")[0];
			this.totalVerbsInCorpora += value;
			if (this.knownVerbs.contains(kWitoutMorph)){
				this.knownVerbsInCorpora += value;
			}
		}
	}
	
	public void printStats(){
		System.out.println("Known verbs: " + (double)this.knownVerbsInCorpora/this.totalVerbsInCorpora + 
				" (" + this.knownVerbsInCorpora + "/" + this.totalVerbsInCorpora + ")");
	}
	

}
