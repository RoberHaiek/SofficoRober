package version0;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


public class FileContentCopier {


	public static void helpMe( File source, File target ) throws IOException {

		FileInputStream  fIn  = new FileInputStream( source );
		FileOutputStream fOut = new FileOutputStream( target );

		byte [] content = new byte[ 4096 ];

		int length = fIn.read( content );
		fOut.write( content, 0, length );

		fIn.close();
		fOut.close();
	}

}
