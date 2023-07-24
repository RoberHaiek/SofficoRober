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
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class XmlFileReader {

    private Set<String> processedFiles = new HashSet<>();
    private Map<String, InternalFile> failedFiles = new HashMap<>();
    private final Path folderPath;
    private final List<String> fileTypes;
    private final MySQLAccess dao;
    private final int period = 5;
    private static final Logger logger = Logger.getLogger(XmlFileReader.class.getName());

    public XmlFileReader(Path folderPath) {
        this.folderPath = folderPath;
        this.fileTypes = Arrays.asList(".xml");
        this.dao = new MySQLAccess();
    }

    public void startWatching() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable folderWatcherTask = () -> {
            try {
                Files.walkFileTree(folderPath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
                        long lastModified = 0;
                        try{
                            BasicFileAttributes attributes = Files.readAttributes(file, BasicFileAttributes.class);
                            lastModified = attributes.lastModifiedTime().toMillis();
                            if (isXmlSuffix(file.toString()) && isNotProcessed(file.toString())
                                && (isNotFailed(file.toString()) || (isFailedButModified(file.toString(), lastModified)))) {
                                    logger.info(Constants.PROCESSING_FILE + file);
                                    Path fullPath = folderPath.resolve(file);
                                    List<Material> materials = readXmlFile(fullPath);
                                    logger.info(Constants.PARSING_SUCCESSFUL);
                                    for(Material material: materials) {
                                        dao.storeMaterial(material);
                                    }
                                    processedFiles.add(file.toString());
                            }
                        } catch (Exception e) {
                            exceptionHandler(e, file.toString(), lastModified);
                        }
                        return FileVisitResult.CONTINUE;
                    }});
            } catch (IOException e) {
                exceptionHandler(e);
            }};
        executor.scheduleAtFixedRate(folderWatcherTask, 0, period, TimeUnit.SECONDS);
    }

    private List<Material> readXmlFile(Path filePath) throws IOException, ParserConfigurationException, SAXException {

        List<Material> materials = new ArrayList<>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
        Document document = builder.parse(filePath.toFile());
        document.getDocumentElement().normalize();

        validator(document);

        NodeList nodeList = document.getElementsByTagName(Material.RECORD);

        for (int currentNodeIndex = 0; currentNodeIndex < nodeList.getLength(); currentNodeIndex++) {
            Node node = nodeList.item(currentNodeIndex);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) node;
                Material material = new Material();
                material.setMatNr(eElement.getElementsByTagName(Material.MATNR).item(0).getTextContent());
                material.setPrice(Double.parseDouble(eElement.getElementsByTagName(Material.PRICE).item(0).getTextContent()));
                addDescriptionElements(eElement.getElementsByTagName(Material.DESCRIPTION), material);
                materials.add(material);
            }
        }
        return materials;
    }

    private void addDescriptionElements(NodeList descriptionNodes, Material material){
        List<ShortText> shortTexts = new ArrayList<>();
        for(int i=0; i<descriptionNodes.getLength(); i++){
            Element element = (Element) descriptionNodes.item(i);
            NodeList textNodes = element.getElementsByTagName(ShortText.TEXT);
            for (int j = 0; j < textNodes.getLength(); j++) {
                Element textElement = (Element) textNodes.item(j);
                String matnr = material.getMatNr();
                String lang = textElement.getAttribute(ShortText.LANG);
                String value = textElement.getAttribute(ShortText.VALUE);
                ShortText shortText = new ShortText().lang(lang).text(value).matnr(matnr);
                shortTexts.add(shortText);
            }
        }
        material.setShortText(shortTexts);
    }
    private void validator(Document document) throws SAXException, IOException {

        String xsdFile = Constants.VALIDATION_FILE_PATH;
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(new File(xsdFile));

        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
    }

    private boolean isXmlSuffix(String fileName){
        return fileName.endsWith(fileTypes.get(0));
    }

    private boolean isNotProcessed(String fileName){
        return !processedFiles.contains(fileName);
    }

    private boolean isNotFailed(String fileName){
        return !failedFiles.containsKey(fileName);
    }

    private boolean isFailedButModified(String fileName, long lastModified){
        return failedFiles.containsKey(fileName) && (lastModified != failedFiles.get(fileName).getTimestamp());
    }


    private void exceptionHandler(Exception e, String fileName, long lastModified){
        failedFiles.put(fileName, new InternalFile().fileName(fileName).timestamp(lastModified));
        logger.severe(new MessageCode.ParsingExceptionBuilder()
                .withSourceFileName(fileName)
                .withErrorMessage(e.getMessage())
                .build().getMessage());
    }

    private void exceptionHandler(Exception e){
        logger.severe(e.toString());
    }
}
