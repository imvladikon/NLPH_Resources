package edu.hlph.hebrewBasicsImplementation.hebrewVerb;


import edu.hlph.hebrewBasicsImplementation.hebrewWord.BaseWord;

import java.util.ArrayList;

import edu.hlph.hebrewBasicsImplementation.letters.*;


public class HebrewVerb {

	public enum Time {E,PAST,PRESENT,BEINONI,FUTURE,IMPERATIVE,INFINITIVE }
	public enum Gender {E,M,F,MF}
	public enum Person {E,FIRST,SECOND,THIRD}
	public enum Number {E, SINGULAR, PLURAL}
	public enum Spelling {E,MISSING,COMPLETE}

	protected ArrayList<HebrewLetter> _word;
	protected int _pointer;
	protected Time _time;
	protected Gender _gender;
	protected Person _person;
	protected Number _number;
	protected Spelling _spelling;

	public HebrewVerb(BaseWord iw){
		_word = new ArrayList<>();
		_word.addAll(iw.getLetters());
		_pointer = 0;
		_time = Time.E;
		_gender = Gender.E;
		_person = Person.E;
		_number = Number.E;
		_spelling = Spelling.E;
	}

	public HebrewVerb(HebrewVerb w){
		_word = new ArrayList<>();
		for (int i=0; i<w.length();i++){
			_word.add(w.getLetter(i));
		}
		_pointer = 0;
		_time = w.getTime();
		_gender = w.getGender();
		_person = w.getPerson();
		_number = w.getNumber();
		_spelling = w.getSpelling();
	}

	/*
	 * Morphology setters and getters
	 */
	public Time getTime(){
		return _time;
	}

	public Gender getGender() {
		return _gender;
	}

	public Person getPerson() {
		return _person;
	}

	public Number getNumber() {
		return _number;
	}

	public Spelling getSpelling() {
		return _spelling;
	}

	public HebrewLetter getLetter(){
		return _word.get(_pointer);
	}

	public void setMorphology(Time t, Gender s, Person b,Number q, Spelling wr){
		_time = t;
		_gender = s;
		_person = b;
		_number = q;
		_spelling = wr;
	}

	/*
	 * Letter reference and pointer movers
	 */
	public HebrewLetter getLetter(int i){
		return _word.get(i);
	}

	public void movePointerRight() {
		_pointer = _pointer - 1;
	}

	public void movePointerLeft() {
		_pointer = _pointer + 1;
	}

	public void movePointer(int pointerChange){
		_pointer += pointerChange;
	}

	public ArrayList<HebrewLetter> getHebrewLetters(){
		return _word;
	}

	public void addLetterAfter(HebrewLetter l){
		if (_pointer == this.length()) {
			_word.add(l);
		}
		else {
			_word.add(_pointer+1, l);
		}
	}

	public void addLetterBefore(HebrewLetter l){
		_word.add(_pointer, l);
	}

	public void removeLetter(){
		_word.remove(_pointer);
	}

	/*
	 * Vocalization manipulators
	 */

	/*
	 * Getters
	 */
	public ArrayList<HebrewLetter> getLetters(){
		return _word;
	}
	
//	public String getVocalization(){
//		return _word.get(_pointer).getVocalization();
//	}
//
//	public String getDagesh(){
//		return _word.get(_pointer).getDagesh();
//	}
//
//	public String getMapik(){
//		return _word.get(_pointer).getMapik();
//	}
//
//	public boolean getFinLetter(){
//		return _word.get(_pointer).getFinLetter();
//	}

	/*
	 * Setters
	 */
	
	// All non last letters are unfinalized+MAPIK removed, the last letter is finalized.  
	public void finalizeWord(){
		for(int i=0; i<_word.size()-1; i++){
			HebrewLetter letter = _word.get(i).unfinalizeLetter();
			if(!letter.equals(_word.get(i))) {
				_word.set(i, letter);
			}
			_word.get(i).withoutMapik();
		}
		HebrewLetter letter = _word.get(_word.size() - 1).finalizeLetter();
		if(!letter.equals(_word.get(_word.size() - 1))) {
			_word.set(_word.size() - 1, letter);
		}
	}


	// Vocalization setters

	public void withNikkud(NikkudEnum vocalization) {
		_word.get(_pointer).withNikkud(vocalization);
	}

	public void withDageshBegedKefet(){
		_word.get(_pointer).withDageshBegedKefet();
	}

	// Vocalization removers

	public void withoutNikkud() {
		_word.get(_pointer).withoutNikkud();
	}

	public void withoutDagesh() {
		_word.get(_pointer).withoutDagesh();
	}

	public void withDagesh() {
		_word.get(_pointer).withDagesh();
	}

	public void withoutMapik() {
		_word.get(_pointer).withoutMapik();
	}

	public boolean isFinLetter(){
		return _word.get(_pointer).isFinLetter();
	}

	/*
	 * General methods
	 */

	public int getPointer(){
		return _pointer;
	}

	public String toString(){
		String ret="";
		for(int i=0; i<_word.size();i++){
			ret += _word.get(i).toString();
		}
		return ret;
	}

	public String toStringMorphology(){
		return _time.toString()+"+"+_person.toString()+"+"+_gender.toString()+"+"+_number.toString()+"+"+_spelling.toString();
	}

	public int length(){
		return _word.size();
	}
}
