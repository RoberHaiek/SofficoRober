import java.util.List;

public class Material {
    private String matNr;
    private double price;

    private List<ShortText> shortTexts;

    public String getMatNr() {
        return matNr;
    }

    public void setMatNr(String matNr) {
        this.matNr = matNr;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setShortText(List<ShortText> shortTexts){
        this.shortTexts = shortTexts;
    }

    public List<ShortText> getShortText(){
        return shortTexts;
    }

}
