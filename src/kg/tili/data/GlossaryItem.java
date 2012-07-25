package kg.tili.data;

/**
 * User: entea
 * Date: 7/24/12
 * Time: 12:51 PM
 */
public class GlossaryItem {
    private String imageUrl;
    private String text;
    private int id;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
