package version0;
import java.io.*;
import java.nio.file.InvalidPathException;
import java.util.logging.Logger;
public class FileContentCopier {

	private static final Logger logger = Logger.getLogger(FileContentCopier.class.getName());
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
	private static void handleFileNotFoundException(String errorMessage, String sourceFileName, String targetFileName){
		logger.severe(
				new MessageCode.FileNotFoundBuilder()
						.withSourceFileName(sourceFileName)
						.withTargetFileName(targetFileName)
						.withErrorMessage(errorMessage)
						.build()
						.getMessage());
	}
	private static void handleInvalidPathException(String errorMessage, String sourcePath, String targetPath){
		logger.severe(
				new MessageCode.InvalidPathBuilder()
						.withSourcePath(sourcePath)
						.withTargetPath(targetPath)
						.withErrorMessage(errorMessage)
						.build()
						.getMessage());
	}
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
