package lab1;

public class Recipient {
    private int awardID;
    private String name;
    private int year;
    private String city;
    private String category;

    /**
     * Empty constructor.
     */
    public Recipient() {
    }

    /**
     * Full constructor.
     * @param awardID
     * @param name
     * @param year
     * @param city
     * @param category
     */
    public Recipient(int awardID, String name, int year, String city, String category) {
        this.awardID = awardID;
        this.name = name;
        this.year = year;
        this.city = city;
        this.category = category;
    }

    public int getAwardID() {
        return awardID;
    }
    public void setAwardID(int awardID) {
        this.awardID = awardID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
