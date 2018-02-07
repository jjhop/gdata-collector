package info.jjhop.gdcollector.model;

import java.util.Date;
import java.util.UUID;
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
        Data result = new Data();
        result.createdAt = new Date(System.nanoTime());
        result.userId = UUID.fromString(data[2]);
        result.longitude = data[0];
        result.latitude = data[1];
        return result;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date createdAt;
    private UUID userId;
    private String longitude;
    private String latitude;
}
