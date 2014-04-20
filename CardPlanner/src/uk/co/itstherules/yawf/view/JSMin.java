/*
 *
 * Stolen from Crockford and ported to Java - simply awful code, and untested
 *
 * jsmin.c 2003-04-21
 * 
 * Copyright (c) 2002 Douglas Crockford (www.crockford.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * The Software shall be used for Good, not Evil.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package uk.co.itstherules.yawf.view;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.Writer;


public final class JSMin {

    private static final int EOF = -1;

    private int theA;
    private int theB;

    public JSMin(){}

    private static boolean isAlphanumeric(int c) {
        return ( (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') ||
                (c >= 'A' && c <= 'Z') || c == '_' || c == '$' || c == '\\' ||
                c > 126);
    }

    private int nextCharacter(PushbackReader reader) throws IOException {
        int c = reader.read();

        if (c >= ' ' || c == '\n' || c == EOF) {
            return c;
        }

        if (c == '\r') {
            return '\n';
        }

        return ' ';
    }



    private int peek(PushbackReader reader) throws IOException {
        int lookaheadChar = reader.read();
        reader.unread(lookaheadChar);
        return lookaheadChar;
    }

    private int next(PushbackReader reader) throws IOException, UnterminatedCommentException {
        int c = nextCharacter(reader);
        if (c == '/') {
            switch (peek(reader)) {
                case '/':
                    for (;;) {
                        c = nextCharacter(reader);
                        if (c <= '\n') {
                            return c;
                        }
                    }

                case '*':
                    nextCharacter(reader);
                    for (;;) {
                        switch (nextCharacter(reader)) {
                            case '*':
                                if (peek(reader) == '/') {
                                    nextCharacter(reader);
                                    return ' ';
                                }
                                break;
                            case EOF:
                                throw new UnterminatedCommentException();
                        }
                    }

                default:
                    return c;
            }

        }
        return c;
    }

    private void action(int d, PushbackReader reader, Writer writer) throws IOException, UnterminatedRegExpLiteralException,
            UnterminatedCommentException, UnterminatedStringLiteralException {
        switch (d) {
            case 1:
                writer.write(theA);
            case 2:
                theA = theB;

                if (theA == '\'' || theA == '"') {
                    for (;;) {
                        writer.write(theA);
                        theA = nextCharacter(reader);
                        if (theA == theB) {
                            break;
                        }
                        if (theA <= '\n') {
                            throw new UnterminatedStringLiteralException();
                        }
                        if (theA == '\\') {
                            writer.write(theA);
                            theA = nextCharacter(reader);
                        }
                    }
                }

            case 3:
                theB = next(reader);
                if (theB == '/' && (theA == '(' || theA == ',' || theA == '=' ||
                        theA == ':' || theA == '[' || theA == '!' ||
                        theA == '&' || theA == '|' || theA == '?' ||
                        theA == '{' || theA == '}' || theA == ';' ||
                        theA == '\n')) {
                    writer.write(theA);
                    writer.write(theB);
                    for (;;) {
                        theA = nextCharacter(reader);
                        if (theA == '/') {
                            break;
                        } else if (theA == '\\') {
                            writer.write(theA);
                            theA = nextCharacter(reader);
                        } else if (theA <= '\n') {
                            throw new UnterminatedRegExpLiteralException();
                        }
                        writer.write(theA);
                    }
                    theB = next(reader);
                }
        }
    }

    public void minify(Reader in, Writer out) throws IOException, UnterminatedRegExpLiteralException, UnterminatedCommentException, UnterminatedStringLiteralException {
        PushbackReader reader = new PushbackReader(in);
        theA = '\n';
        action(3, reader, out);
        while (theA != EOF) {
            switch (theA) {
                case ' ':
                    if (isAlphanumeric(theB)) {
                        action(1, reader, out);
                    } else {
                        action(2, reader, out);
                    }
                    break;
                case '\n':
                    switch (theB) {
                        case '{':
                        case '[':
                        case '(':
                        case '+':
                        case '-':
                            action(1, reader, out);
                            break;
                        case ' ':
                            action(3, reader, out);
                            break;
                        default:
                            if (isAlphanumeric(theB)) {
                                action(1, reader, out);
                            } else {
                                action(2, reader, out);
                            }
                    }
                    break;
                default:
                    switch (theB) {
                        case ' ':
                            if (isAlphanumeric(theA)) {
                                action(1, reader, out);
                                break;
                            }
                            action(3, reader, out);
                            break;
                        case '\n':
                            switch (theA) {
                                case '}':
                                case ']':
                                case ')':
                                case '+':
                                case '-':
                                case '"':
                                case '\'':
                                    action(1, reader, out);
                                    break;
                                default:
                                    if (isAlphanumeric(theA)) {
                                        action(1, reader, out);
                                    } else {
                                        action(3, reader, out);
                                    }
                            }
                            break;
                        default:
                            action(1, reader, out);
                            break;
                    }
            }
        }
        out.flush();
    }

    class UnterminatedCommentException extends Exception {
		private static final long serialVersionUID = 8087878070136751268L;
    }

    class UnterminatedStringLiteralException extends Exception {
		private static final long serialVersionUID = -8186058700976886L;
    }

    class UnterminatedRegExpLiteralException extends Exception {
		private static final long serialVersionUID = 3357899195246750164L;
    }
}