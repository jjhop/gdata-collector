package project3.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
@EqualsAndHashCode
public class Data {

    // long;lat;userId
    public static Data fromString(String input) {
        String[] data = input.split(";");
        return new Data() {{
            this.createdAt = new Date();
            this.userId = Long.parseLong(data[3]);
            this.longitude = data[0];
            this.latitude = data[1];
        }};
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date createdAt;
    private Long userId;
    private String longitude;
    private String latitude;
}
