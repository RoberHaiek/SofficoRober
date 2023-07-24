import java.nio.file.Paths;

public class Main {
    public static void main( String [] args ){
        XmlFileReader.startWatching(Paths.get(args[0]));
    }
}
