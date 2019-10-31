package edu.hlph.experiments.featureExtraction;

import edu.hlph.hebrewBasicsImplementation.hebrewWord.BaseWord;

import java.util.ArrayList;
import java.util.List;

import edu.hlph.experiments.classificationSampleTypes.BaseWordSample;
import edu.hlph.experiments.classificationSampleTypes.ClassificationSampleIfc;
import edu.hlph.hebrewBasicsImplementation.letters.HebrewLetter;
import edu.hlph.experiments.features.Feature;
import edu.hlph.experiments.features.GronitAtFeature;
import edu.hlph.experiments.features.LettersAtFeature;
import edu.hlph.experiments.features.WordSizeFeature;

public class BasicFeatureExtractor implements FeatureExtractorIfc{

	private int topFeatureSize;
	
	public BasicFeatureExtractor(int topFeatureSize){
		this.topFeatureSize = topFeatureSize;
	}
	
	public List<Feature> getWordFeatures(ClassificationSampleIfc bwo){
		BaseWord bw = ((BaseWordSample)bwo).getBaseWord();
		List<Feature> ret = new ArrayList<Feature>();
		ret.add(this.extractWordFeatures_wordLength(bw));
		ret.addAll(this.extractWordFeatures_wordGronitPossition(bw));
		ret.addAll(this.extractWordFeatures_wordLetterPossition(bw));
		return ret;
	}
	
	protected List<Feature> extractWordFeatures_wordGronitPossition(BaseWord bw) {
		List<Feature> ret = new ArrayList<Feature>();
		List<HebrewLetter> wordLetters = bw.getLetters();
		int length = wordLetters.size();
		for(int i=0; i<length; i++){
			if(wordLetters.get(i).isHaacheaa()){
				ret.add(new GronitAtFeature(i));
			}
		}
		return ret;
	}

	protected WordSizeFeature extractWordFeatures_wordLength(BaseWord bw){
		int length = bw.getLetters().size();
		return new WordSizeFeature(length);
	}
	
	protected List<Feature> extractWordFeatures_wordLetterPossition(BaseWord bw){
		List<Feature> ret = new ArrayList<Feature>();
		List<HebrewLetter> wordLetters = bw.getLetters();
		int length = wordLetters.size();
		for (int i=0; i<this.topFeatureSize; i++){ 
			for (int j=0; j<length-i; j++){
				HebrewLetter[] featureLetters = new HebrewLetter[i+1];
				for (int k=0; k<i+1; k++){
					HebrewLetter l = wordLetters.get(k+j);
					featureLetters[k] = l;
				}
				Feature feature = new LettersAtFeature(j,featureLetters);
				ret.add(feature);
			}
		}
		return ret;
	}
}
