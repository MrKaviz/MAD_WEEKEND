package Models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class NewsModel implements Serializable {

    @Exclude
    private String id;

    private String name, brand;

    public NewsModel(){

    }

    public NewsModel(String name, String brand) {
        this.name = name;
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }


}
