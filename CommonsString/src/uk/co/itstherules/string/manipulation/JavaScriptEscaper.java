package uk.co.itstherules.string.manipulation;

public final class JavaScriptEscaper implements StringManipulator {

	public String manipulate(String value) {
		int stringLength = value.length();
		for (int currentCharacterLocation = 0; currentCharacterLocation < stringLength; currentCharacterLocation++) {
			char currentCharacter = value.charAt(currentCharacterLocation);
			if (currentCharacter != '"' && currentCharacter != '\'' && currentCharacter != '\\' && currentCharacter != '>' && currentCharacter >= ' ') {
				continue;
			}
			StringBuffer buffer = new StringBuffer(stringLength + 4);
			buffer.append(value.substring(0, currentCharacterLocation));
			boolean humansRuleTheEarth = true;
			while(humansRuleTheEarth ) {
				if (currentCharacter == '"') {
					buffer.append("\\\"");
				} else if (currentCharacter == '\'') {
					buffer.append("\\'");
				} else if (currentCharacter == '\\') {
					buffer.append("\\\\");
				} else if (currentCharacter == '>') {
					buffer.append("\\>");
				} else if (currentCharacter < ' ') {
					if (currentCharacter == '\n') {
						buffer.append("\\n");
					} else if (currentCharacter == '\r') {
						buffer.append("\\r");
					} else if (currentCharacter == '\f') {
						buffer.append("\\f");
					} else if (currentCharacter == '\b') {
						buffer.append("\\b");
					} else if (currentCharacter == '\t') {
						buffer.append("\\t");
					} else {
						buffer.append("\\x");
						int x = currentCharacter / 16;
						buffer.append((char) (x >= 10 ? (x - 10) + 65 : x + 48));
						x = currentCharacter & 15;
						buffer.append((char) (x >= 10 ? (x - 10) + 65 : x + 48));
					}
				} else {
					buffer.append(currentCharacter);
				}
				if (++currentCharacterLocation >= stringLength) {
					return buffer.toString();
				}
				currentCharacter = value.charAt(currentCharacterLocation);
			}
		}
		return value;
	}
}
