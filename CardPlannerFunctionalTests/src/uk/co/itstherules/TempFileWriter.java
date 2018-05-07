package uk.co.itstherules;

import java.io.*;
import java.text.MessageFormat;
import java.util.UUID;

public final class TempFileWriter {

    private TempFileWriter() {  }

    public static String write(String filePath) throws IOException {
        final InputStream stream = TempFileWriter.class.getResourceAsStream(filePath);
        final String outputPath = MessageFormat.format("{0}/{1}", System.getProperty("java.io.tmpdir"), UUID.randomUUID().toString());
        final FileWriter writer = new FileWriter(outputPath);
        InputStreamReader reader = new InputStreamReader(stream);
        int c;
        while((c = reader.read()) != -1){
            writer.write(c);
        }
        writer.close();
        return outputPath;
    }


    public static void destroy(String filePath) throws Exception {
        final File file = new File(filePath);
        final long fiveSecondsLater = System.currentTimeMillis()+5000;
        while(file.exists() && !file.delete()) {
           Thread.sleep(20);
            if(System.currentTimeMillis() > fiveSecondsLater){
                throw new RuntimeException("Unable to delete file: "+filePath);
            }
        }
    }

}