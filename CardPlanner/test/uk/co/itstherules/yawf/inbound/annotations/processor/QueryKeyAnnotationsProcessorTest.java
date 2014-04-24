package uk.co.itstherules.yawf.inbound.annotations.processor;

import org.junit.Test;
import uk.co.itstherules.yawf.inbound.annotations.CacheInstruction;
import uk.co.itstherules.yawf.inbound.annotations.QueryKey;

import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static uk.co.itstherules.yawf.inbound.annotations.processor.FieldRepresentation.FieldType.*;

public final class QueryKeyAnnotationsProcessorTest {

    @Test public void canBuildSimpleString() throws Exception {
        class EasyExample {

            @QueryKey("key") private String string;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        final FieldRepresentation representation = reply.get("key");
        assertThat(representation.model().toString(), is(input.toString()));
        assertThat(representation.fieldType(), is(STRING));
        assertThat(representation.value(), is(nullValue()));
        assertFalse(representation.shouldRetrieveFromCache());
    }

    @Test public void canBuildSimpleStringWithCacheInstruction() throws Exception {
        class EasyExample {

            @QueryKey(value = "key", cache = CacheInstruction.FromCache) private String string;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        final FieldRepresentation representation = reply.get("key");
        assertThat(representation.model().toString(), is(input.toString()));
        assertThat(representation.fieldType(), is(STRING));
        assertThat(representation.value(), is(nullValue()));
        assertTrue(representation.shouldRetrieveFromCache());
    }

    @Test public void canBuildSimpleStringWithAValue() throws Exception {
        class EasyExample {

            @QueryKey("key") private String string = "hi";
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        final FieldRepresentation representation = reply.get("key");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(STRING));
        assertThat(representation.value().toString(), is("hi"));
    }

    @Test public void canBuildSimpleInteger() throws Exception {
        class EasyExample {

            @QueryKey("free") private Integer integer;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        final FieldRepresentation representation = reply.get("free");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(INTEGER));
        assertThat(representation.value(), is(nullValue()));
    }

    @Test public void canBuildSimpleIntegerWithValue() throws Exception {
        class EasyExample {

            @QueryKey("free") private Integer integer = 1;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        final FieldRepresentation representation = reply.get("free");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(INTEGER));
        final Integer value = (Integer) representation.value();
        assertThat(value, equalTo(1));
    }

    @Test public void canBuildInt() throws Exception {
        class EasyExample {

            @QueryKey("free") private int integer;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        final FieldRepresentation representation = reply.get("free");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(INTEGER));
        final Integer value = (Integer) representation.value();
        assertThat(value, is(0));
    }

    @Test public void canBuildIntWithValue() throws Exception {
        class EasyExample {

            @QueryKey("free") private int integer = 1;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        FieldRepresentation representation = reply.get("free");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(INTEGER));
        assertTrue(representation.value().equals(1));
    }

    @Test(expected = IllegalStateException.class)
    public void cannotBuildWithTwoKeysTheSame() throws Exception {
        class EasyExample {

            @QueryKey("free") private String string;
            @QueryKey("free") private Integer integer;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        unit.process(input);
    }

    @Test public void canBuildWithTwoFields() throws Exception {
        class EasyExample {

            @QueryKey("streeng") private String string = "blah";
            @QueryKey("intijir") private Integer integer = 98;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        FieldRepresentation representation = reply.get("streeng");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(STRING));
        assertTrue(representation.value().equals("blah"));
        representation = reply.get("intijir");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(INTEGER));
        assertTrue(representation.value().equals(98));
    }

    @Test public void canFollowObjectWhenInstantiated() throws Exception {
        class NestedEasy {

            @QueryKey("nestedString") private String string = "blah";
        }
        final NestedEasy nestedEasy = new NestedEasy();
        class EasyExample {

            @QueryKey("nestedWithin") private NestedEasy nested = nestedEasy;
            @QueryKey("intijir") private Integer integer = 98;
        }
        EasyExample input = new EasyExample();
        QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
        Map<String, FieldRepresentation> reply = unit.process(input);
        FieldRepresentation representation = reply.get("intijir");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(INTEGER));
        assertTrue(representation.value().equals(98));
        representation = reply.get("nestedWithin");
        assertTrue(representation.model().equals(input));
        assertThat(representation.fieldType(), is(OBJECT));
        representation = reply.get("nestedWithin.nestedString");
        assertNotNull(representation);
        assertTrue(representation.model().equals(nestedEasy));
        assertThat(representation.fieldType(), is(STRING));
        assertTrue(representation.value().equals("blah"));
    }

    @Test public void throwsWhenTryingToFollowANullNonNativeField() throws Exception {
        try {
            WithNestedExample withNestedExample = new WithNestedExample();
            QueryKeyAnnotationsProcessor unit = new QueryKeyAnnotationsProcessor();
            unit.process(withNestedExample);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("NestedExample WithNestedExample.nested is an object, and will be mapped, thus it requires a non-null value"));
        }
    }

    public static class WithNestedExample {

        @QueryKey("nestedWithin") private NestedExample nested = null;
        @QueryKey("intijir") private Integer integer = 98;
    }

    public static class NestedExample {

        @QueryKey("nestedString") private String string = "blah";
    }
}

