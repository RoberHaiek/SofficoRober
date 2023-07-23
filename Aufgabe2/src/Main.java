import java.nio.file.Paths;

public class Main {
    public static void main( String [] args ){
        XmlFileReader xmlFileReader = new XmlFileReader(Paths.get("C:\\Users\\rober\\IdeaProjects\\SofficoRober\\Aufgabe2\\resources"));
        xmlFileReader.startWatching();
    }
}
