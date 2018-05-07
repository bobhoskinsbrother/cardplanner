package uk.co.itstherules.ui;

import org.junit.Test;
import uk.co.itstherules.TempFileWriter;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TempFileWriterTest {

    @Test
    public void writeATempFile() throws Exception {
        String path = TempFileWriter.write("/personas/upload_me.txt");
        final File file = new File(path);
        assertThat(file.exists(),is(true));
        TempFileWriter.destroy(path);
        assertThat(file.exists(), is(false));
    }
}
