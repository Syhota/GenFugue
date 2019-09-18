package org.genfugue;

import java.util.StringJoiner;
import java.util.Vector;

import org.jfugue.pattern.Pattern;
import org.jfugue.theory.Chord;
import org.jfugue.theory.Note;

/**
 * Operations for converting JFugue data types into strings and back
 * @author Connor J Stephe, 2418377
 *
 */
public class StringOperators {

	/**
	 * Converts a string into a pattern
	 * @param str the string to turn into a pattern
	 * @return the converted pattern
	 */
	static Pattern stringToPattern(String str) {
		Pattern pattern = new Pattern();
		pattern.add(str);
		return pattern;
	}

	/**
	 * Converts strings into a string array
	 * @param str string to become an array
	 * @return the mutated string
	 */
	static String[] stringToStringArr(String str) {
		String[] string = str.split(" ");
		return string;
	}

	/**
	 * Changes a chord into a string array
	 * @param ch Chord to change into a string array
	 * @return Chord into a string array
	 */
	static String chordArrToString(Chord[] ch) {
		String str = "";
		for (int i = 0; i < ch.length; i++) {
			if (str == "") {
				str = ch[i].toString();
			}
			str = str + " " + ch[i].toString();
		}
		return str;
	}
	
	/**
	 * Converts a chord to a string for the purposes of getting the key
	 * @param ch the chord
	 * @return string array of the chord
	 */
	static String chordToString(Chord ch) {
		String str = ch.toString();
		return str;
	}
	
	String patternToString(Pattern pt) {
		String str = pt.toString();
		return str;
	}

	/**
	 * String builder for converting string arrays into regular strings
	 * @param strA the string array
	 * @return a string made from the array
	 */
	static String stringBuilder(String[] strA) {
		StringJoiner sb = new StringJoiner(" ");
		for (String st: strA)
			sb.add(st);
		String str = sb.toString();
		return str;
	}
	
	static String[] noteArrToStringArr(Note[] notes) {
		String note = null;
		for (int i = 0; i < notes.length; i++) {
			if (note == null) {
				note = notes[i].toString();
			}
			note = note + " " + notes[i].toString();
		}
		String[] noteArr = stringToStringArr(note);
		return noteArr;
	}
	
	/**
	 * Fixes the key so that the chord of the tonic note doesn't have numbers attached. Better for finding fitness
	 * @param ch Chord array to get the notes from
	 * @return string of the tonic chord
	 */
	static String chordFixer(Chord[] ch) {
		Vector<String[]> key = new Vector<String[]>();
		Vector<String> sKey = new Vector<String>();
		for (int i = 0; i < ch.length; i ++) {
			Note[] notes = ch[i].getNotes();
			key.add(noteArrToStringArr(notes));
		}
		String str = "";
		for (int i = 0; i < key.get(0).length; i++) {
			for (int j = 0; j < key.get(0).length; j++) {
				str = key.get(i)[j];
				str = str.substring(0, Math.min(str.length(), 1));
			}
			sKey.add(str);
		}
		str = "";
		for (int i = 0; i < sKey.size(); i++) {
			if (str == "") {
				str = sKey.get(i);
			}
			str = str + " " + sKey.get(i);
		}
		return str;
	}
}