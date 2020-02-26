package uoit.finalproject.car4u;

public class Cars {

    private String make_id;
    private String model_id;
    private String make;
    private String model;
    private String owner;
    private String price;
    private String image;
    private String carid;
    private String seats;


    public Cars(String make_id, String model_id, String carid, String make, String model, String owner, String price, String image, String seats ) {

        this.make = make;
        this.make_id = make_id;
        this.model_id = model_id;
        this.model = model;
        this.owner = owner;
        this.price = price;
        this.image = image;
        this.carid = carid;
        this.seats = seats;
    }

    public String getMake_id() {
        return make_id;
    }

    public void setMake_id(String make_id) {
        this.make_id = make_id;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}
