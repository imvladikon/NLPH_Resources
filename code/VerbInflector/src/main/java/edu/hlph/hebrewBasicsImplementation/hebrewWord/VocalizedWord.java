package edu.hlph.hebrewBasicsImplementation.hebrewWord;

import edu.hlph.hebrewBasicsImplementation.hebrewVerb.VerbInflection;

import java.util.ArrayList;
import java.util.Arrays;

import edu.hlph.hebrewBasicsImplementation.letters.NikkudEnum;
import edu.hlph.syllableDismantlingImplementation.stringTools.EditDistanceCalculator;
import edu.hlph.syllableDismantlingImplementation.syllableDismantling.SyllableDismantlerByBaseForm;
import edu.hlph.syllableDismantlingImplementation.syllableDismantling.SyllableSegmentationByHurristic;
import edu.hlph.syllableDismantlingImplementation.syllableDismantling.SyllabledWord;
import edu.hlph.hebrewBasicsImplementation.letters.HebrewLetter;

import static edu.hlph.hebrewBasicsImplementation.letters.HebrewLetter.*;
import static edu.hlph.hebrewBasicsImplementation.letters.NikkudEnum.*;

public class VocalizedWord {

    protected ArrayList<HebrewLetter> _word;

    public VocalizedWord(VerbInflection w) {
        _word = new ArrayList<>();
        ArrayList<HebrewLetter> letters = w.getLetters();
        _word.addAll(letters);
    }

    public VocalizedWord(VocalizedWord w) {
        _word = new ArrayList<>();
        ArrayList<HebrewLetter> letters = w.getLetters();
        _word.addAll(letters);
    }

    public VocalizedWord(HebrewLetter[] arr) {
        _word = new ArrayList<HebrewLetter>();
        _word.addAll(Arrays.asList(arr));
    }

    private HebrewLetter makeVocalizedLetter(String letter, String diacritics) {
        HebrewLetter l = HebrewLetter.makeLetterFromString(letter);
        for (int k = 0; k < diacritics.length(); k++) {
            NikkudEnum c = from(diacritics.substring(k, k + 1));
            if (c.is(SHURUK) && l.is(VAV)) {
                l.withNikkud(SHURUK);
            } else if (c.is(DAGESH) && !l.isHaacheaa() && !l.is(VAV)) {
                l.withDagesh();
            } else if (c.is(H_KAMATS)) {
                l.withNikkud(H_KAMATS);
            } else if (c.is(H_PATAH)) {
                l.withNikkud(H_PATAH);
            } else if (c.is(H_SEGOL)) {
                l.withNikkud(H_SEGOL);
            } else if (c.is(HIRIK)) {
                l.withNikkud(HIRIK);
            } else if (c.is(HOLAM_M) && l.is(VAV)) {
                l.withNikkud(HOLAM_M);
            } else if (c.is(HOLAM_H)) {
                l.withNikkud(HOLAM_H);
            } else if (c.is(KAMATS)) {
                l.withNikkud(KAMATS);
            } else if (c.is(KAMATS_KATAN)) {
                l.withNikkud(KAMATS_KATAN);
            } else if (c.is(KUBUTS)) {
                l.withNikkud(KUBUTS);
            } else if (c.is(MAPIK) && l.is(HEY)) {
                l.withMapik();
            } else if (c.is(PATAH)) {
                l.withNikkud(PATAH);
            } else if (c.is(SEGOL)) {
                l.withNikkud(SEGOL);
            } else if (c.is(SHVA)) {
                l.withNikkud(SHVA);
            } else if (c.is(TSERE)) {
                l.withNikkud(TSERE);
            }
        }
        return l;
    }

    public VocalizedWord(String vWord) {
        this._word = new ArrayList<>();
        // the loop goes once for each letter
        for (int i = 0; i < vWord.length(); ) {
            String currentLetter = vWord.substring(i, i + 1);
            if (!HebrewLetter.isHebrewLetter(currentLetter)) {
                System.out.println("the word " + vWord + " is not a valid Hebrew word");
                break;
            }
            String currentDiacritics = new String("");
            int j = i + 1;
            // the loop goes once for each diacritic sign
            while (j < vWord.length() && !HebrewLetter.isHebrewLetter(vWord.substring(j, j + 1))) {
                String c = vWord.substring(j, j + 1);
                NikkudEnum n = NikkudEnum.from(vWord.substring(j, j + 1));
                if (n.is(SIN_DOT) && currentLetter.equals(SHIN.getLetter())) {
                    currentLetter += QUOTE.getLetter();
                } else if (n.is(QUOTE) && (currentLetter.equals(TSADI.getLetter()) || currentLetter.equals(HebrewLetter.GIMEL.getLetter()))) {
                    currentLetter += QUOTE.getLetter();
                } else {
                    currentDiacritics += c;
                }
                j++;
            }
            i = j;
            HebrewLetter l = this.makeVocalizedLetter(currentLetter, currentDiacritics);
            if (l.getLetter().equals(EMPTY.getLetter())) {
                System.out.println("the word " + vWord + " is not a valid Hebrew word");
                break;
            }
            this._word.add(l);
        }
    }

    public String toString() {
        String ret = "";
		for (HebrewLetter hebrewLetter : _word) {
			ret += hebrewLetter.toString();
		}
        return ret;
    }

    public SyllabledWord makeSyllabledWordByHuristic() {
        return (new SyllableSegmentationByHurristic()).makeSyllabledWordByHuristic(this._word);
    }

    public SyllabledWord makeSyllabledWordByBaseForm(VocalizedWord baseForm, EditDistanceCalculator calc) {
        return (new SyllableDismantlerByBaseForm(calc)).makeSyllabledWordByBaseForm(baseForm, this);
    }

    public ArrayList<HebrewLetter> getLetters() {
        return this._word;
    }
}
