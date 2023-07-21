package version0;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.*;
public class MyFirstPrototype {

	private File firstFile;
	private File secondFile;
	private static final Logger logger = Logger.getLogger(MyFirstPrototype.class.getName());
	public MyFirstPrototype(File firstFile, File secondFile) {
		this.firstFile = firstFile;
		this.secondFile = secondFile;
	}
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
