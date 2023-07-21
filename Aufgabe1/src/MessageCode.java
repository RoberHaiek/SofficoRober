package version0;

/**
 * The MessageCode class represents a message code with additional information.
 * It allows building message codes for specific scenarios like file not found
 * and invalid path errors.
 */
public class MessageCode {

    /**
     * The message associated with the message code.
     */
    private String message;


    /**
     * Private constructor to enforce the use of the builder pattern for creating
     * MessageCode objects.
     */
    private MessageCode(){}

    /**
     * Get the message associated with the message code.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message associated with the message code.
     *
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * The FileNotFoundBuilder class is a builder for creating MessageCode objects
     * for file not found scenarios.
     */
    public static class FileNotFoundBuilder{

        private String message;
        private String sourceFileName = "";
        private String targetFileName = "";
        private String errorMessage = "";

        /**
         * Set the source file name for the message.
         *
         * @param sourceFileName The source file name.
         * @return The FileNotFoundBuilder object.
         */
        public FileNotFoundBuilder withSourceFileName(String sourceFileName){
            this.sourceFileName = "Source file name: " + sourceFileName + ". ";
            return this;
        }

        /**
         * Set the target file name for the message.
         *
         * @param targetFileName The target file name.
         * @return The FileNotFoundBuilder object.
         */
        public FileNotFoundBuilder withTargetFileName(String targetFileName){
            this.targetFileName = "Target file name: " + targetFileName + ". ";
            return this;
        }

        /**
         * Set the error message for the message.
         *
         * @param errorMessage The error message.
         * @return The FileNotFoundBuilder object.
         */
        public FileNotFoundBuilder withErrorMessage(String errorMessage){
            this.errorMessage = "ErrorMessage: " + errorMessage + ". ";
            return this;
        }

        /**
         * Build the MessageCode object with the constructed message for file not found.
         *
         * @return The constructed MessageCode object.
         */
        public MessageCode build(){
            MessageCode messageCode = new MessageCode();
            message = "File not found. " + sourceFileName + targetFileName + errorMessage;
            messageCode.setMessage(message);
            return messageCode;
        }
    }


    /**
     * The InvalidPathBuilder class is a builder for creating MessageCode objects
     * for invalid path scenarios.
     */
    public static class InvalidPathBuilder {

        private String message;
        private String sourcePath = "";
        private String targetPath = "";
        private String errorMessage = "";


        /**
         * Set the source file path for the message.
         *
         * @param sourcePath The source file path.
         * @return The InvalidPathBuilder object.
         */
        public InvalidPathBuilder withSourcePath(String sourcePath){
            this.sourcePath = "Source file path: " + sourcePath + ". ";
            return this;
        }

        /**
         * Set the target file path for the message.
         *
         * @param targetPath The target file path.
         * @return The InvalidPathBuilder object.
         */
        public InvalidPathBuilder withTargetPath(String targetPath){
            this.targetPath = "Target file path: " + targetPath + ". ";
            return this;
        }

        /**
         * Set the error message for the message.
         *
         * @param errorMessage The error message.
         * @return The InvalidPathBuilder object.
         */
        public InvalidPathBuilder withErrorMessage(String errorMessage){
            this.errorMessage = "ErrorMessage: " + errorMessage + ". ";
            return this;
        }

        /**
         * Build the MessageCode object with the constructed message for invalid path.
         *
         * @return The constructed MessageCode object.
         */
        public MessageCode build(){
            MessageCode messageCode = new MessageCode();
            message = "Path not found. " + sourcePath + targetPath + errorMessage;
            messageCode.setMessage(message);
            return messageCode;
        }
    }

    /**
     * The IOBuilder class is a builder for creating MessageCode objects
     * for IO exception scenarios.
     */
    public static class IOBuilder {

        private String message;
        private String sourcePath = "";
        private String targetPath = "";
        private String errorMessage = "";


        /**
         * Set the source file name for the message.
         *
         * @param sourcePath The source file path.
         * @return The IOBuilder object.
         */
        public IOBuilder withSourcePath(String sourcePath){
            this.sourcePath = "Source file name: " + sourcePath + ". ";
            return this;
        }

        /**
         * Set the target file name for the message.
         *
         * @param targetPath The target file path.
         * @return The IOBuilder object.
         */
        public IOBuilder withTargetPath(String targetPath){
            this.targetPath = "Target file name: " + targetPath + ". ";
            return this;
        }

        /**
         * Set the error message for the message.
         *
         * @param errorMessage The error message.
         * @return The IOBuilder object.
         */
        public IOBuilder withErrorMessage(String errorMessage){
            this.errorMessage = "ErrorMessage: " + errorMessage + ". ";
            return this;
        }

        /**
         * Build the MessageCode object with the constructed message for invalid path.
         *
         * @return The constructed MessageCode object.
         */
        public MessageCode build(){
            MessageCode messageCode = new MessageCode();
            message = "IO exception thrown. " + sourcePath + targetPath + errorMessage;
            messageCode.setMessage(message);
            return messageCode;
        }
    }
}
