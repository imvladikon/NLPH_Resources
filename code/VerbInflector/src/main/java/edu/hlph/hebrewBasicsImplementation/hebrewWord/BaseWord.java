package edu.hlph.hebrewBasicsImplementation.hebrewWord;

import java.util.ArrayList;
import java.util.Arrays;

import edu.hlph.hebrewBasicsImplementation.letters.*;

public class BaseWord {
	
	protected ArrayList<HebrewLetter> _word;
	
	
	public BaseWord(HebrewLetter[] letterArr){
		_word = new ArrayList<>();
		_word.addAll(Arrays.asList(letterArr));
	}
	
	public BaseWord(BaseWord iw){
		_word = new ArrayList<>();
		ArrayList<HebrewLetter> letters = iw.getLetters();
		_word.addAll(letters);
	}
	
	public ArrayList<HebrewLetter> getLetters(){
		return _word;
	}
	
	public String toString(){
		String ret="";
		for(int i=0; i<_word.size();i++){
			ret += _word.get(i).toString();
		}
		return ret;
	}
}
