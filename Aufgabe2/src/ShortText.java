public class ShortText {
    private String matnr;
    private String lang;
    private String text;

    public static String TEXT = "text";
    public static String LANG = "lang";
    public static String VALUE = "value";

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public ShortText matnr(String matnr){
        this.matnr = matnr;
        return this;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ShortText lang(String lang){
        this.lang = lang;
        return this;
    }

    public ShortText text(String text){
        this.text = text;
        return this;
    }
}
