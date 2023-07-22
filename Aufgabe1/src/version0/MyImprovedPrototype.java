package version0;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

/**
 * MyImprovedPrototype is identical to MyFirstPrototype in terms on functionality but
 * is an improved version using the SHA-256 algorithm in order to detect small differences
 * in larger files more efficiently
 */
public class MyImprovedPrototype {

    private File firstFile;
    private File secondFile;
    private static final Logger logger = Logger.getLogger(MyImprovedPrototype.class.getName());
    private static final String HASHING_ALGORITHM = "SHA-256";

    /**
     * Creates a new MyImprovedPrototype instance with the specified first and second files.
     *
     * @param firstFile  The first file to be compared.
     * @param secondFile The second file to be compared.
     */
    public MyImprovedPrototype(File firstFile, File secondFile) {
        this.firstFile = firstFile;
        this.secondFile = secondFile;
    }

    /**
     * Compares the content of the two files and determines if they are identical
     * using the SHA-256 hashing algorithm
     *
     * @return true if the files are identical, false otherwise.
     * @throws IOException if an I/O error occurs during file reading.
     */
    public boolean isIdentical() throws IOException {
        String firstFileHash = getFileHash(firstFile);
        String secondFileHash = getFileHash(secondFile);

        if (firstFileHash.equals(secondFileHash)) {
            logger.info(Constants.FILES_IDENTICAL);
            return true;
        } else {
            logger.info(Constants.FILES_NOT_IDENTICAL);
            return false;
        }
    }

    /**
     * Converts the file content to hashed for more efficient comparison
     *
     * @param file The file to be hashed.
     * @return String of the hashed file.
     * @throws IOException if an I/O error occurs during file reading.
     */
    private String getFileHash(File file) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
            MessageDigest messageDigest = MessageDigest.getInstance(HASHING_ALGORITHM);
            byte[] buffer = new byte[Constants.BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            byte[] hashBytes = messageDigest.digest();
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(Constants.SHA_256_UNAVAILABLE);
        }
    }

    /**
     * Converts byte code array to a String
     *
     * @param bytes is the hashed bytes code
     * @return String from hex bytes after conversion.
     * @throws IOException if an I/O error occurs during file reading.
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
