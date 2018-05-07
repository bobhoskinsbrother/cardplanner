package uk.co.itstherules.yawf.view;

import org.markdown4j.Markdown4jProcessor;
import uk.co.itstherules.string.manipulation.StringManipulator;

import java.io.IOException;

public final class Markdown implements StringManipulator{

    @Override public String manipulate(String text) {
        try {
            return new Markdown4jProcessor().process(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
