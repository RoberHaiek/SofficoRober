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
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class XmlFileReader {

    private final Path folderPath;
    private final int period = 5;
    private static final Logger logger = Logger.getLogger(XmlFileReader.class.getName());

    public XmlFileReader(Path folderPath) {
        this.folderPath = folderPath;
    }

    public void startWatching() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable folderWatcherTask = () -> {
            try {
                Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        if (file.toString().endsWith(".xml")) {
                            logger.info("New XML file detected: " + file);
                            Path fullPath = folderPath.resolve(file);
                            try {
                                readXMLFile(fullPath);
                                logger.info("parsing done");
                            } catch (ParserConfigurationException e) {
                                throw new RuntimeException(e);
                            } catch (SAXException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return FileVisitResult.CONTINUE;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        executor.scheduleAtFixedRate(folderWatcherTask, 0, period, TimeUnit.SECONDS);
    }

    private void readXMLFile(Path filePath) throws IOException, ParserConfigurationException, SAXException, InterruptedException {

        List<Material> materials = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.parse(filePath.toFile());
        document.getDocumentElement().normalize();

        validator(document);

        NodeList nList = document.getElementsByTagName("record");

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node node = nList.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) node;
                Material material = new Material();
                material.setMatNr(eElement.getElementsByTagName("matnr").item(0).getTextContent());
                material.setPrice(Double.parseDouble(eElement.getElementsByTagName("price").item(0).getTextContent()));
                addDescriptionElements(eElement.getElementsByTagName("description"), material);
                materials.add(material);
            }
        }

        Thread.sleep(1000);
    }

    private void addDescriptionElements(NodeList descriptionNodes, Material material){
        List<ShortText> shortTexts = new ArrayList<>();
        for(int i=0; i<descriptionNodes.getLength(); i++){
            Element element = (Element) descriptionNodes.item(i);
            NodeList textNodes = element.getElementsByTagName("text");
            for (int k = 0; k < textNodes.getLength(); k++) {
                Element textElement = (Element) textNodes.item(k);
                String lang = textElement.getAttribute("lang");
                String value = textElement.getAttribute("value");
                ShortText shortText = new ShortText().lang(lang).text(value);
                shortTexts.add(shortText);
            }
        }
        material.setShortText(shortTexts);
    }
    private void validator(Document document) throws IOException {
        try {
            String xsdFile = "Aufgabe2/resources/material_export.xsd";
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(xsdFile));

            Validator validator = schema.newValidator();
            validator.validate(new DOMSource(document));

        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("XML validation failed.");
        }
    }
}
