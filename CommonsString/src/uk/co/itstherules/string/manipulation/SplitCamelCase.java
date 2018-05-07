package uk.co.itstherules.string.manipulation;

import java.util.ArrayList;
import java.util.List;

public final class SplitCamelCase implements StringManipulator {

	private final List<Character> caps;
	private final List<Character> numbers;
	private final String substitute;

	public SplitCamelCase() {
		this(" ");
	}

	public SplitCamelCase(String substitute) {
		this.substitute = new StringBuffer(substitute).reverse().toString();
		
		caps = new ArrayList<Character>();
		numbers = new ArrayList<Character>();
		caps.add('A');
		caps.add('B');
		caps.add('C');
		caps.add('D');
		caps.add('E');
		caps.add('F');
		caps.add('G');
		caps.add('H');
		caps.add('I');
		caps.add('J');
		caps.add('K');
		caps.add('L');
		caps.add('M');
		caps.add('N');
		caps.add('O');
		caps.add('P');
		caps.add('Q');
		caps.add('R');
		caps.add('S');
		caps.add('T');
		caps.add('U');
		caps.add('V');
		caps.add('W');
		caps.add('X');
		caps.add('Y');
		caps.add('Z');
		numbers.add('0');
		numbers.add('1');
		numbers.add('2');
		numbers.add('3');
		numbers.add('4');
		numbers.add('5');
		numbers.add('6');
		numbers.add('7');
		numbers.add('8');
		numbers.add('9');
	}
	
	public String manipulate(String text) {
		char[] textArray = text.toCharArray();
		StringBuffer buffer = new StringBuffer();
		for (int i = textArray.length-1; i > -1; i--) {
			char character = textArray[i];
			buffer.append(character);
			if((isUpper(character) && i != 0) || isNumber(character) && (i!=0 && !isNumber(textArray[i-1]))) {
				buffer.append(substitute);
			}
		}
		return buffer.reverse().toString();
	}

	private boolean isNumber(char character) {
		return numbers.contains(character);
	}

	private boolean isUpper(char c) {
		return caps.contains(c);
	}


}
