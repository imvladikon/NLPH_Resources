package edu.hlph.syllableDismantlingImplementation.syllableDismantling;

import java.util.ArrayList;

import edu.hlph.hebrewBasicsImplementation.letters.HebrewLetter;

import static edu.hlph.hebrewBasicsImplementation.letters.HebrewLetter.*;
import static edu.hlph.hebrewBasicsImplementation.letters.NikkudEnum.*;

public class SyllableSegmentationByHurristic {

	public ArrayList<HebrewLetter> _word;
	
	public SyllableSegmentationByHurristic(){
		
	}
	
	public SyllabledWord makeSyllabledWordByHuristic(ArrayList<HebrewLetter> w){
		this._word = new ArrayList<>();
		this._word.addAll(w);
		int pointer = 0;
		ArrayList<Integer> sylArrList = new ArrayList<Integer>();
		sylArrList.add(0);
		//System.out.println("word: "+this._word);
		while(pointer<this._word.size()-1){
			
			if(this.isVocalizetionSmallVowel(pointer)){
				//System.out.println("!"+pointer);
				pointer = this.smallStartSyllable(pointer, sylArrList);
			}
			else if(this.isVocalizetionBigVowel(pointer)) {
				//System.out.println("!!"+pointer);
				pointer = this.bigStartSyllable(pointer, sylArrList);
			}
			else if(this._word.get(pointer).isWith(EMPTY)) {
				//System.out.println("!!!"+pointer);
				pointer = this.nonVocStartSyllable(pointer, sylArrList);
			}
			else if(this._word.get(pointer).isWith(SHVA)||this._word.get(pointer).isWith(HATAF)){
				//System.out.println("!!!!"+pointer);
				pointer = this.shva_HatafStartSyllable(pointer, sylArrList);
			}
			else{
				pointer++;
			}
		}	
		int[] sylArr = new int[sylArrList.size()];
		for (int i=0; i<sylArr.length;i++){
			sylArr[i] = sylArrList.get(i);
		}
		return new SyllabledWord(_word,sylArr);
	}
	
	// First letter in the current syllable is vocalized by a small vowel
	private int smallStartSyllable(int pointer, ArrayList<Integer> sylArrList){
		if (	((pointer+3<this._word.size()-1)&&
				(this._word.get(pointer+1).isWith(SHVA)))&&
				(this._word.get(pointer+1).is(VAV))&&
				(this._word.get(pointer+2).is(VAV))&&
				(this._word.get(pointer+2).isWith(EMPTY))){
			pointer++;													// Small-VavShva(Nach)-VavUnvocalized~
			pointer++;
			pointer++;
			sylArrList.add(pointer);
		}
		else if (	((pointer+2<this._word.size()-1)&&(this._word.get(pointer+1).isWith(SHVA)))||
				((pointer+2==this._word.size()-1)&&(this._word.get(pointer+2).isWith(KAMATS)))){
			pointer++;													// Small-Shva(Nach)~
			pointer++;
			sylArrList.add(pointer);
		}
		else if (	(pointer+1<this._word.size()-1)&&					// Small~Vowel or Small~Hataf 
					(	(this._word.get(pointer+1).isWith(VOWELS))||
						(this._word.get(pointer+1).isWith(HATAF)))){
			pointer++;
			sylArrList.add(pointer);
		}
		else if (	(pointer+2<this._word.size())&&						// Small~Unvocalized
					(this._word.get(pointer+1).isWith(EMPTY))&&
					(this._word.get(pointer+2).is(VAV))&&
					(this._word.get(pointer+2).isWith(SHURUK)||(this._word.get(pointer+2).isWith(HOLAM_M)))){
			pointer++;
			sylArrList.add(pointer);
		}
		else if (	(pointer+3<this._word.size())&&						// VavSmall-UnvocalizedVav-Yod~
				(this._word.get(pointer).is(VAV))&&
				(this._word.get(pointer+1).is(VAV))&&
				(this._word.get(pointer+1).isWith(EMPTY))&&
				(this._word.get(pointer+2).is(YOD))&&
				(this._word.get(pointer+2).isWith(EMPTY))&&
				(	!this._word.get(pointer+3).isWith(EMPTY)||
					(	(pointer+4<this._word.size())&&
						(((this._word.get(pointer+4).is(VAV))&&(this._word.get(pointer+4).isWith(HOLAM_M)))||
						((this._word.get(pointer+4).is(VAV))&&(this._word.get(pointer+4).isWith(SHURUK))))))){
			pointer++;
			pointer++;
			pointer++;
			sylArrList.add(pointer);
		}
		else if (	(pointer+3<this._word.size())&&						// Small~Unvocalized
				(this._word.get(pointer+1).isWith(EMPTY))&&
				(this._word.get(pointer+2).is(VAV))&&
				!(this._word.get(pointer+2).isWith(SHURUK)||(this._word.get(pointer+2).isWith(HOLAM_M)))){
			System.out.println("ERROR: Small-UnVoc-NonVavSuruk_NonVavHolam_M.");
			System.out.println("	word:" + this._word + " pointer:" + pointer);
		}
		else {															// End Of Word syllable
			pointer = _word.size();
		}
		return pointer;
	}
	
	// First letter in the current syllable is vocalized by a big vowel
	private int bigStartSyllable(int pointer, ArrayList<Integer> sylArrList){
		if (	(	(pointer+1<this._word.size()-1)||					// Big~Vowel or Big~Shva or Big~Hataf
					((pointer+1==this._word.size()-1)&&(this._word.get(pointer+1).isWith(KAMATS))) &&
				(	(this._word.get(pointer+1).isWith(VOWELS))||
					(this._word.get(pointer+1).isWith(SHVA))||
					(this._word.get(pointer+1).isWith(HATAF))))){
			pointer++;
			sylArrList.add(pointer);
		}
		else if (	(	(pointer+2<this._word.size()-1)||
						((pointer+2==this._word.size()-1)&&(this._word.get(pointer+2).isWith(KAMATS)))&&
					(this._word.get(pointer).isWith(HIRIK))&&
					(this._word.get(pointer+1).is(YOD))&&(this._word.get(pointer+1).isWith(EMPTY)))){
				pointer++;												// Hirik-Yod~
				pointer++;
				sylArrList.add(pointer);
		}
		else if (	(	(pointer+2<this._word.size()-1)||
						((pointer+2==this._word.size()-1)&&(this._word.get(pointer+2).isWith(KAMATS)))&&
				(this._word.get(pointer).isWith(TSERE))&&(this._word.get(pointer+1).is(YOD)))){
				pointer++;												// Tsere-Yod~
				pointer++;
				sylArrList.add(pointer);
		}
		else if (	(	(pointer+2<this._word.size()-1)||
						((pointer+2==this._word.size()-1)&&(this._word.get(pointer+2).isWith(KAMATS))))&&
					(this._word.get(pointer).isWith(SEGOL))&&(this._word.get(pointer+1).is(YOD))){
		pointer++;														// Segol-Yod~
		pointer++;
		sylArrList.add(pointer);
		}
		else if(	(	(pointer+2<this._word.size()-1)	||				// Big-Alef_Rafa~
						((pointer+2==this._word.size()-1)&&(this._word.get(pointer+2).isWith(KAMATS))))&&
					(this._word.get(pointer+1).isWith(EMPTY))&&
					(this._word.get(pointer+1).is(ALEF))&&
					((!((this._word.get(pointer+2).is(VAV))&&(this._word.get(pointer+2).isWith(HOLAM_M))))||
					(!((this._word.get(pointer+2).is(VAV))&&(this._word.get(pointer+2).isWith(SHURUK)))))){
				pointer++;
				pointer++;
				sylArrList.add(pointer);
		}
		else if(	(	(pointer+2<this._word.size()-1)||					// Big~Unvocalized-(VavHolam_M or vavShuruk)
						((pointer+2==this._word.size()-1)&&(this._word.get(pointer+2).isWith(KAMATS))))&&
					(this._word.get(pointer+1).isWith(EMPTY))&&
					(((this._word.get(pointer+2).is(VAV))&&(this._word.get(pointer+2).isWith(HOLAM_M)))||
					((this._word.get(pointer+2).is(VAV))&&(this._word.get(pointer+2).isWith(SHURUK))))){
			pointer++;
			sylArrList.add(pointer);
		}
		else if (	(pointer+3<this._word.size())&&						// VavBig-UnvocalizedVav-Yod~
				(this._word.get(pointer).is(VAV))&&
				(this._word.get(pointer+1).is(VAV))&&
				(this._word.get(pointer+1).isWith(EMPTY))&&
				(this._word.get(pointer+2).is(YOD))&&
				(this._word.get(pointer+2).isWith(EMPTY))&&
				(	!this._word.get(pointer+3).isWith(EMPTY)||
					(	(pointer+4<this._word.size())&&
						(((this._word.get(pointer+4).is(VAV))&&(this._word.get(pointer+4).isWith(HOLAM_M)))||
						((this._word.get(pointer+4).is(VAV))&&(this._word.get(pointer+4).isWith(SHURUK))))))){
			pointer++;
			pointer++;
			pointer++;
			sylArrList.add(pointer);
		}
		else{
			pointer = _word.size();
		}
		return pointer;
	}
	
	// First letter in the current syllable is non-vocalized
	private int nonVocStartSyllable(int pointer, ArrayList<Integer> sylArrList){
		if(		(pointer+2<this._word.size()-1)&&						// Unvocalized-(VavHolam_M or vavShuruk)~
				(((this._word.get(pointer+1).is(VAV))&&(this._word.get(pointer+1).isWith(HOLAM_M)))||
				((this._word.get(pointer+1).is(VAV))&&(this._word.get(pointer+1).isWith(SHURUK))))){
		pointer++;
		pointer++;
		sylArrList.add(pointer);
		}
		else if(pointer+2<this._word.size()-1){
			System.out.println("ERROR: Syllable starts with an unvocalized letter and no VavHolam_M or vavShuruk afterwords.");
			System.out.println("	word:" + this._word + " pointer:" + pointer);
			pointer=this._word.size(); //??
		}
		else{
			pointer++;
			pointer++;
		}
		return pointer;
	}
	
	// First letter in the current syllable is vocalized by shva or hataf
	private int shva_HatafStartSyllable(int pointer, ArrayList<Integer> sylArrList){
		pointer++;
		if(this.isVocalizetionSmallVowel(pointer)){
			//System.out.println("$"+pointer);
			pointer = this.smallStartSyllable(pointer, sylArrList);
		}
		else if(this.isVocalizetionBigVowel(pointer)) {
			//System.out.println("$$"+pointer);
			pointer = this.bigStartSyllable(pointer, sylArrList);
		}
		else if(this._word.get(pointer).isWith(EMPTY)) {
			//System.out.println("$$$"+pointer);
			pointer = this.nonVocStartSyllable(pointer, sylArrList);
		}
		return pointer;
	}
	
	private boolean isVocalizetionSmallVowel(int pointer){
		return (	(this._word.get(pointer).isWith(HIRIK))&&(pointer+1<this._word.size())&&
						(!(this._word.get(pointer+1).is(YOD)&&this._word.get(pointer+1).isWith(EMPTY))))||
					//(this._word.get(pointer).isVocalizetionKamats_Katan())||
					(this._word.get(pointer).isWith(PATAH))||
					(this._word.get(pointer).isWith(SEGOL))&&(pointer<this._word.size()-1)&&
							!(this._word.get(pointer+1).is(YOD)&&this._word.get(pointer+1).isWith(EMPTY))&&
							!(	(this._word.get(pointer+1).isWith(EMPTY))&&
								(pointer+1==this._word.size()-1))&&
							!(	(this._word.get(pointer+1).isWith(EMPTY))&&
								(pointer+1<this._word.size()-1)&&
									(	this._word.get(pointer+2).isWith(HOLAM_M)||
										this._word.get(pointer+2).isWith(SHURUK)))||
					(this._word.get(pointer).isWith(KUBUTS));
	}
	
	private boolean isVocalizetionBigVowel(int pointer){
		return (	(this._word.get(pointer).isWith(HIRIK)&&(pointer+1<this._word.size())&&
						this._word.get(pointer+1).is(YOD)&&this._word.get(pointer+1).isWith(EMPTY))||
					(this._word.get(pointer).isWith(SEGOL)&&(pointer+1<this._word.size())&&
						this._word.get(pointer+1).is(YOD)&&this._word.get(pointer+1).isWith(EMPTY))||
					(this._word.get(pointer).isWith(HOLAM_H))||
					(this._word.get(pointer).isWith(HOLAM_M))||
					(this._word.get(pointer).isWith(KAMATS))||
					(this._word.get(pointer).isWith(TSERE))||
					(this._word.get(pointer).isWith(SHURUK)));
	}
}
