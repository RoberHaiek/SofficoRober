public class ShortText {
    private String lang;
    private String text;

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
