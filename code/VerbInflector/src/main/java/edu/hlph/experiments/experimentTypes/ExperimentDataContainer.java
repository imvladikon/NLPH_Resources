package edu.hlph.experiments.experimentTypes;

import java.util.List;

import edu.hlph.experiments.classificationSampleTypes.ClassificationSampleIfc;

public class ExperimentDataContainer {

	protected double training;
	protected List<ClassificationSampleIfc> samples;
	
	public ExperimentDataContainer(double training, List<ClassificationSampleIfc> sampleList){
		this.training = training;
		this.samples = sampleList;
	}
	
	public double getTraning(){
		return this.training;
	}
	
	public List<ClassificationSampleIfc> getSamples(){
		return this.samples;
	}
}
