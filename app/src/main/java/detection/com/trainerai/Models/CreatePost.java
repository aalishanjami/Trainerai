package detection.com.trainerai.Models;

public class CreatePost {
    String name, content, pic, iduser, message;

    public CreatePost(String name, String content, String pic, String iduser) {
        this.name = name;
        this.content = content;
        this.pic = pic;
        this.iduser = iduser;
    }

    public String getMessage() {
        return message;
    }
}
