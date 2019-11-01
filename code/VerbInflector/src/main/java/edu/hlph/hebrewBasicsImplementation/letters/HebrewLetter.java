package edu.hlph.hebrewBasicsImplementation.letters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.util.EnumSet;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.EnumSet.of;

@AllArgsConstructor
@NoArgsConstructor
public enum HebrewLetter {

    ALEF("ALEF", Literals.ALEF),
    BET("BET", Literals.BET),
    GIMEL("GIMEL", Literals.GIMEL),
    JIMEL("JIMEL", Literals.GIMEL, of(NikkudEnum.QUOTE)),
    DALET("DALET", Literals.DALET),
    HEY("HEY", Literals.HEY),
    VAV("VAV", Literals.VAV),
    ZAIN("ZAIN", Literals.ZAIN),
    HET("HET", Literals.HET),
    TET("TET", Literals.TET),
    YOD("YOD", Literals.YOD),
    KAF("KAF", Literals.KAF),
    KAF_SOFIT("KAF_SOFIT", Literals.KAF_SOFIT, true),
    LAMED("LAMED", Literals.LAMED),
    MEM("MEM", Literals.MEM),
    MEM_SOFIT("MEM_SOFIT", Literals.MEM_SOFIT, true),
    NUN("NUN", Literals.NUN),
    NUN_SOFIT("NUN_SOFIT", Literals.NUN_SOFIT, true),
    SAMECH("SAMECH", Literals.SAMECH),
    AIN("AIN", Literals.AIN),
    PEH("PEH", Literals.PEH),
    PEH_SOFIT("PEH_SOFIT", Literals.PEH_SOFIT, true),
    TSADI("TSADI", Literals.TSADI),
    TSADI_SOFIT("TSADI_SOFIT", Literals.TSADI_SOFIT, true),
    CHADI("CHADI", Literals.TSADI, of(NikkudEnum.QUOTE)),
    CHADI_SOFIT("CHADI_SOFIT", Literals.TSADI_SOFIT, true, of(NikkudEnum.QUOTE)),
    KUF("KUF", Literals.KUF),
    REISH("REISH", Literals.REISH),
    SHIN("SHIN", Literals.SHIN),
    SIN("SIN", Literals.SHIN + Literals.SHIN_DOT),
    TAV("TAV", Literals.TAV);

    String name;
    @Getter
    String letter;
    @Getter
    boolean finLetter;
    NikkudEnum vocalization = NikkudEnum.EMPTY;
    NikkudEnum dagesh = NikkudEnum.EMPTY;
    NikkudEnum mapik = NikkudEnum.EMPTY;
    NikkudEnum quote = NikkudEnum.EMPTY;

    HebrewLetter(String name, String letter) {
        this(name, letter, NikkudEnum.EMPTY);
    }

    HebrewLetter(String name, String letter, NikkudEnum vocalization) {
        this(name, letter, false, vocalization);
    }

    HebrewLetter(String name, String letter, boolean finLetter) {
        this(name, letter, finLetter, NikkudEnum.EMPTY);
    }

    HebrewLetter(String name, String letter, EnumSet<NikkudEnum> nikkuds) {
        this(name, letter, false, nikkuds);
    }

    HebrewLetter(String name, String letter, boolean finLetter, EnumSet<NikkudEnum> nikkuds) {
        if (nikkuds.contains(NikkudEnum.QUOTE)) this.quote = NikkudEnum.QUOTE;
        if (nikkuds.contains(NikkudEnum.MAPIK)) this.quote = NikkudEnum.MAPIK;
        if (nikkuds.contains(NikkudEnum.DAGESH)) this.quote = NikkudEnum.DAGESH;
        this.name = name;
        this.letter = letter;
        this.finLetter = finLetter;
    }

    HebrewLetter(String name, String letter, boolean finLetter, NikkudEnum vocalization) {
        this.name = name;
        this.letter = letter;
        this.finLetter = finLetter;
        this.vocalization = vocalization;
    }

    public boolean isQuoted() {
        return quote == NikkudEnum.QUOTE;
    }

    public boolean isBegedkefet() {
        return asList(HebrewLetter.BET.letter, HebrewLetter.GIMEL.letter,
                HebrewLetter.DALET.letter, HebrewLetter.KAF.letter,
                HebrewLetter.PEH.letter, HebrewLetter.TAV.letter,
                HebrewLetter.KAF_SOFIT.letter, HebrewLetter.PEH_SOFIT.letter).contains(this.letter);
    }

    public boolean isHaacheaa() {
        return asList(HebrewLetter.HEY.letter, HebrewLetter.ALEF.letter,
                HebrewLetter.HET.letter, HebrewLetter.REISH.letter,
                HebrewLetter.AIN.letter).contains(this.letter);
    }

    public HebrewLetter withNikkud(NikkudEnum vocalization) {
        this.vocalization = vocalization;
        return this;
    }

    public HebrewLetter withDagesh() {
        this.dagesh = NikkudEnum.DAGESH;
        return this;
    }

    public HebrewLetter withDageshBegedKefet() {
        if (this.isBegedkefet()) {
            this.dagesh = NikkudEnum.DAGESH;
        }
        return this;
    }

    public HebrewLetter withMapik() {
        if (this.is(HEY)) {
            this.mapik = NikkudEnum.MAPIK;
        }
        return this;
    }

    public static boolean isHebrewLetter(String l) {
        return Stream.of(HebrewLetter.values()).anyMatch(h -> h.letter.equals(l));
    }

    public HebrewLetter finalizeLetter() {
        if (this.equals(KAF)) {
            return KAF_SOFIT.with(this);
        }
        if (this.equals(MEM)) {
            return MEM_SOFIT.with(this);
        }
        if (this.equals(NUN)) {
            return NUN_SOFIT.with(this);
        }
        if (this.equals(PEH)) {
            return PEH_SOFIT.with(this);
        }
        if (this.equals(TSADI)) {
            return TSADI_SOFIT.with(this);
        }
        if (this.equals(CHADI)) {
            return CHADI_SOFIT.with(this);
        }
        return this;
    }

    private HebrewLetter with(HebrewLetter hl) {
        this.dagesh = hl.dagesh;
        this.mapik = hl.mapik;
        this.vocalization = hl.vocalization;
        return this;
    }

    //TODO: problem
    public HebrewLetter unfinalizeLetter() {
        if (this.equals(KAF_SOFIT)) {
            return KAF.with(this);
        }
        if (this.equals(MEM_SOFIT)) {
            return MEM.with(this);
        }
        if (this.equals(NUN_SOFIT)) {
            return NUN.with(this);
        }
        if (this.equals(PEH_SOFIT)) {
            return PEH.with(this);
        }
        if (this.equals(TSADI_SOFIT)) {
            return TSADI.with(this);
        }
        if (this.equals(CHADI_SOFIT)) {
            return CHADI.with(this);
        }
        return this;
    }

    public HebrewLetter withoutNikkud() {
        this.vocalization = NikkudEnum.EMPTY;
        return this;
    }

    public HebrewLetter withoutDagesh() {
        this.dagesh = NikkudEnum.EMPTY;
        return this;
    }

    public HebrewLetter withoutMapik() {
        this.mapik = NikkudEnum.EMPTY;
        return this;
    }

    //TODO: fix it, to usual override method
    public boolean equals(HebrewLetter l) {
        return (this.letter.equals(l.letter));
    }

    public boolean is(HebrewLetter l) {
        return (this.letter.equals(l.letter));
    }

    public boolean isWith(NikkudEnum nikkud) {
        return this.vocalization.is(nikkud);
    }

    public boolean isWith(EnumSet<NikkudEnum> nikkuds) {
        return this.vocalization.is(nikkuds);
    }

    @SneakyThrows
    public static HebrewLetter makeLetterFromString(String l) {
        return Stream.of(HebrewLetter.values())
                .filter(h -> h.letter.equals(l))
                .findFirst()
                .orElseThrow(() -> new Exception("Warning - created empty letter (may indicate encoding problem... try to convert file to \"UTF8 without BOM\" by ntpd++)"));
    }

    public static HebrewLetter makeLetterFromLetterName(String l) {
        return HebrewLetter.valueOf(l);
    }

    @Override
    public String toString() {
        return this.letter + this.dagesh.getLetter() + this.mapik.getLetter() + this.vocalization.getLetter() + this.quote.getLetter();
    }

}