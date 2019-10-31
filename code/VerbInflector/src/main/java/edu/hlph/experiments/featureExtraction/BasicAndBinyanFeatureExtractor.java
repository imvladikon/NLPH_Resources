package edu.hlph.experiments.featureExtraction;

import java.util.ArrayList;
import java.util.List;
import edu.hlph.experiments.features.BinyanFeature;
import edu.hlph.experiments.features.Feature;
import edu.hlph.experiments.classificationSampleTypes.ClassificationSampleIfc;

public class BasicAndBinyanFeatureExtractor extends BasicFeatureExtractor{

	
	public BasicAndBinyanFeatureExtractor(int topFeatureSize) {
		super(topFeatureSize);
	}
	
	public List<Feature> getWordFeatures(ClassificationSampleIfc bwo){
		List<Feature> ret = new ArrayList<Feature>();
		ret.addAll(super.getWordFeatures(bwo));
		String binyan = bwo.getClassName().substring(0, 1);
		for(Feature f : ret){
			f.setAdditionalToStriongArg(binyan);
		}
		Feature bf = new BinyanFeature(binyan);
		ret.add(bf);
		return ret;
	}

}
