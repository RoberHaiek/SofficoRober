import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class Test {

    private final Path folderPath;
    private final int period = 5;
    private static final Logger logger = Logger.getLogger(Test.class.getName());

    public Test(Path folderPath) {
        this.folderPath = folderPath;
    }

    public void testing() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File( "file" ));
        Schema schema = null;
        try {

            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory2 = SchemaFactory.newInstance(language);
            schema = factory2.newSchema(new File("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }

    public void startWatching() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable folderWatcherTask = () -> {
            try {
                WatchService watchService = FileSystems.getDefault().newWatchService();
                folderPath.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

                WatchKey key = watchService.poll(1, TimeUnit.SECONDS);
                if (key != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path filePath = (Path) event.context();
                        if (filePath.toString().endsWith(".xml")) {
                            logger.info("New XML file detected: " + filePath);
                            // Do your processing for the new XML file here
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        };

        executor.scheduleAtFixedRate(folderWatcherTask, 0, period, TimeUnit.SECONDS);
    }
}
