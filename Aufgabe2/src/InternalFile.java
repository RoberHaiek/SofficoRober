public class InternalFile {

    private String fileName;
    private long timestamp;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public InternalFile fileName(String fileName){
        this.fileName = fileName;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public InternalFile timestamp(long timestamp){
        this.timestamp = timestamp;
        return this;
    }
}
