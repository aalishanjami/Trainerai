package detection.com.trainerai.Models;

public class Data {
    //THIS CLASS GETS DATA BY EXCHANGE OF JWT
    int id;
    String name, email, dob, heightfoot, heightinch, weight, pic;

    public Data(int id, String name, String email, String dob, String heightfoot, String heightinch, String weight, String pic) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.dob = dob;
        this.heightfoot = heightfoot;
        this.heightinch = heightinch;
        this.weight = weight;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDob() {
        return dob;
    }

    public String getHeightfoot() {
        return heightfoot;
    }

    public String getHeightinch() {
        return heightinch;
    }

    public String getWeight() {
        return weight;
    }

    public String getPic() {
        return pic;
    }
}
