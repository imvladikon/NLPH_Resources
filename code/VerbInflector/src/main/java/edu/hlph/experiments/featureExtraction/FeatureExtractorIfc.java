package edu.hlph.experiments.featureExtraction;

import java.util.List;

import edu.hlph.experiments.classificationSampleTypes.ClassificationSampleIfc;

import edu.hlph.experiments.features.Feature;

public interface FeatureExtractorIfc {

	public List<Feature> getWordFeatures(ClassificationSampleIfc sample);
}
