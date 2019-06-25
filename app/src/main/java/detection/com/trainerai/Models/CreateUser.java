package detection.com.trainerai.Models;

public class CreateUser {
    String name, email, password, dob, heightfoot, heightinch, weight, pic;
    Integer id;

    public CreateUser(String name, String email, String password, String dob, String heightfoot, String heightinch, String weight, String pic) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.dob = dob;
        this.heightfoot = heightfoot;
        this.heightinch = heightinch;
        this.weight = weight;
        this.pic = pic;
    }

    public Integer getId() {
        return id;
    }
}
