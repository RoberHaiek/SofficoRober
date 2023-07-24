import java.nio.file.Paths;

public class Main {
    public static void main( String [] args ){
        XmlFileReader xmlFileReader = new XmlFileReader(Paths.get(args[0]));
        xmlFileReader.startWatching();
    }
}
