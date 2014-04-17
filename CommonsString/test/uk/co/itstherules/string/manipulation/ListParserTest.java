package uk.co.itstherules.string.manipulation;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public final class ListParserTest {

    @Test public void canWithNull() {
        final List<String> reply = ListParser.parse(null);
        assertThat(reply.size(), is(0));
    }

    @Test public void canWithEmpty() {
        final List<String> reply = ListParser.parse("");
        assertThat(reply.size(), is(0));
    }

    @Test public void canWithOne() {
        final List<String> reply = ListParser.parse("one");
        assertThat(reply.size(), is(1));
        assertThat(reply.get(0), is("one"));
    }

    @Test public void canWithTwo() {
        final List<String> reply = ListParser.parse("[one,two]");
        assertThat(reply.size(), is(2));
        assertThat(reply.get(0), is("one"));
        assertThat(reply.get(1), is("two"));
    }

    @Test public void canWithThree() {
        final List<String> reply = ListParser.parse("one,two,three");
        assertThat(reply.size(), is(3));
        assertThat(reply.get(0), is("one"));
        assertThat(reply.get(1), is("two"));
        assertThat(reply.get(2), is("three"));
    }

}
