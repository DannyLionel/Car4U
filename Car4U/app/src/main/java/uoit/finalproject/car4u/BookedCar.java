package uoit.finalproject.car4u;

public class BookedCar {

    private String make;
    private String model;
    private String owner;
    private String image;
    private String carid;
    private String fromdate;
    private String todate;
    private String price;


    public BookedCar(String make, String model, String owner, String image, String carid, String fromdate, String todate, String price ) {

        this.make = make;
        this.model = model;
        this.owner = owner;
        this.model = model;
        this.image = image;
        this.carid = carid;
        this.fromdate = fromdate;
        this.todate = todate;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }
}

