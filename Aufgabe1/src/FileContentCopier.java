package version0;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.logging.Logger;

/**
 * The FileContentCopier class provides a utility for copying the content of a source file to a target file.
 * It reads the content of the source file using FileInputStream and writes it to the target file using
 * FileOutputStream.
 */
public class FileContentCopier {

	private static final Logger logger = Logger.getLogger(FileContentCopier.class.getName());

	/**
	 * Copies the content of the source file to the target file.
	 *
	 * @param source The source file to copy from.
	 * @param target The target file to copy to.
	 */
	public static void copySourceToTarget(File source, File target ){

		try(FileInputStream fileInputStream  = new FileInputStream( source );
			FileOutputStream fileOutputStream = new FileOutputStream( target )){
			byte [] content = new byte[ Constants.BUFFER_SIZE];
			int inputFileLength = fileInputStream.read(content);
			int offset = 0;

			while(inputFileLength >= 0){
				fileOutputStream.write(content, offset, Math.min(inputFileLength, Constants.BUFFER_SIZE));
				offset += Constants.BUFFER_SIZE;
				inputFileLength -= Constants.BUFFER_SIZE;
			}
			logger.info(Constants.COPIED_SUCCESSFULLY);
		}catch (Exception e){
			if(e instanceof FileNotFoundException){
				handleFileNotFoundException(e.getMessage(), source.getName(), target.getName());
			}else if(e instanceof InvalidPathException){
				handleInvalidPathException(e.getMessage(), source.getPath(), target.getPath());
			}else{
				handleIOException(e.getMessage(), source.getName(), target.getName());
			}
		}
	}

	/**
	 * Handles FileNotFoundException and logs an error message.
	 *
	 * @param errorMessage   The error message associated with the exception.
	 * @param sourceFileName The name of the source file.
	 * @param targetFileName The name of the target file.
	 */
	private static void handleFileNotFoundException(String errorMessage, String sourceFileName, String targetFileName){
		logger.severe(
				new MessageCode.FileNotFoundBuilder()
						.withSourceFileName(sourceFileName)
						.withTargetFileName(targetFileName)
						.withErrorMessage(errorMessage)
						.build()
						.getMessage());
	}

	/**
	 * Handles InvalidPathException and logs an error message.
	 *
	 * @param errorMessage The error message associated with the exception.
	 * @param sourcePath   The path of the source file.
	 * @param targetPath   The path of the target file.
	 */
	private static void handleInvalidPathException(String errorMessage, String sourcePath, String targetPath){
		logger.severe(
				new MessageCode.InvalidPathBuilder()
						.withSourcePath(sourcePath)
						.withTargetPath(targetPath)
						.withErrorMessage(errorMessage)
						.build()
						.getMessage());
	}

	/**
	 * Handles IOException and logs an error message.
	 *
	 * @param errorMessage The error message associated with the exception.
	 * @param sourcePath   The path of the source file.
	 * @param targetPath   The path of the target file.
	 */
	private static void handleIOException(String errorMessage, String sourcePath, String targetPath){
		logger.severe(
				new MessageCode.IOBuilder()
						.withSourcePath(sourcePath)
						.withTargetPath(targetPath)
						.withErrorMessage(errorMessage)
						.build()
						.getMessage());
	}

}
