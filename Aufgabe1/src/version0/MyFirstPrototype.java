package version0;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.*;

/**
 * The MyFirstPrototype class represents a prototype for comparing two files for
 * content equality. It reads the content of two files using BufferedReader and
 * determines whether they are identical or not.
 */
public class MyFirstPrototype {

	private File firstFile;
	private File secondFile;
	private static final Logger logger = Logger.getLogger(MyFirstPrototype.class.getName());

	/**
	 * Creates a new MyFirstPrototype instance with the specified first and second files.
	 *
	 * @param firstFile  The first file to be compared.
	 * @param secondFile The second file to be compared.
	 */
	public MyFirstPrototype(File firstFile, File secondFile) {
		this.firstFile = firstFile;
		this.secondFile = secondFile;
	}

	/**
	 * Compares the content of the two files and determines if they are identical.
	 *
	 * @return true if the files are identical, false otherwise.
	 * @throws IOException if an I/O error occurs during file reading.
	 */
	public boolean isIdentical() throws IOException {

		try(BufferedReader firstFileBufferReader = new BufferedReader(new FileReader(firstFile, StandardCharsets.UTF_8));
			BufferedReader secondFileBufferReader = new BufferedReader(new FileReader(secondFile, StandardCharsets.UTF_8))){

			String firstFileLine = "";
			String secondFileLine = "";
			while (firstFileLine != null && secondFileLine != null && firstFileLine.compareTo(secondFileLine) == 0) {
				firstFileLine = firstFileBufferReader.readLine();
				secondFileLine = secondFileBufferReader.readLine();
			}

			String loggerMessage = firstFileLine == null && secondFileLine == null ? Constants.FILES_IDENTICAL : Constants.FILES_NOT_IDENTICAL;
			logger.info(loggerMessage);

			return (firstFileLine == null && secondFileLine == null);

		}catch (FileNotFoundException e){
			handleException(e.getMessage());
			throw e;
		}
	}

	/**
	 * Handles file not found exceptions and logs an error message.
	 *
	 * @param errorMessage The error message associated with the exception.
	 */
	private void handleException(String errorMessage){
		logger.severe(
				new MessageCode.FileNotFoundBuilder()
						.withSourceFileName(firstFile.getName())
						.withTargetFileName(secondFile.getName())
						.withErrorMessage(errorMessage)
						.build()
						.getMessage());
	}

}
