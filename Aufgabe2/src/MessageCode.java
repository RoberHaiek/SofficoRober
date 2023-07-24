public class MessageCode {

    private String message;
    private MessageCode(){}
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    /*
        The ParserConfigurationException can be thrown in the following situations:
        1- When the XML parser implementation does not support the requested configuration options.
        2- When there are errors in the XML document being parsed, such as malformed XML syntax or invalid structure.
        3- When the XML document uses features or properties that are not supported by the XML parser implementation.

        SAXException might be thrown as well as a problem with the parser or the XML
    */
    public static class ParsingExceptionBuilder {

        private String message;
        private String fileName = "";
        private String errorMessage = "";

        public ParsingExceptionBuilder withSourceFileName(String fileName){
            this.fileName = "File name: " + fileName + ". ";
            return this;
        }

        public ParsingExceptionBuilder withErrorMessage(String errorMessage){
            this.errorMessage = "ErrorMessage: " + errorMessage + ". ";
            return this;
        }

        public MessageCode build(){
            MessageCode messageCode = new MessageCode();
            message = "Failed to parse the file. " + fileName + errorMessage;
            messageCode.setMessage(message);
            return messageCode;
        }
    }

    public static class DBExceptionBuilder {

        private String message;
        private String tableName = "";
        private String action = "";
        private String errorMessage = "";

        public DBExceptionBuilder withTableName(String tableName){
            this.tableName = "Table name: " + tableName + ". ";
            return this;
        }

        public DBExceptionBuilder withAction(String action){
            this.action = "Action: " + action + ". ";
            return this;
        }

        public DBExceptionBuilder withErrorMessage(String errorMessage){
            this.errorMessage = "ErrorMessage: " + errorMessage + ". ";
            return this;
        }

        public MessageCode build(){
            MessageCode messageCode = new MessageCode();
            message = "Failed to perform action on database. " + tableName + action + errorMessage;
            messageCode.setMessage(message);
            return messageCode;
        }
    }

    public static class ClosingExceptionBuilder {

        private String message;
        private String errorMessage = "";

        public ClosingExceptionBuilder withErrorMessage(String errorMessage){
            this.errorMessage = "ErrorMessage: " + errorMessage + ". ";
            return this;
        }

        public MessageCode build(){
            MessageCode messageCode = new MessageCode();
            message = "Failed to close connection to database. " + errorMessage;
            messageCode.setMessage(message);
            return messageCode;
        }
    }

    public static class StoredSuccessfully{
        private String message;
        private String materialName;
        private String tableName = "";

        public StoredSuccessfully withMaterialName(String materialName){
            this.materialName = materialName;
            return this;
        }

        public StoredSuccessfully withTableName(String tableName){
            this.tableName = tableName;
            return this;
        }

        public MessageCode build(){
            MessageCode messageCode = new MessageCode();
            message = "Material " + materialName + " stored in " + tableName + " successfully";
            messageCode.setMessage(message);
            return messageCode;
        }
    }

}
