package project3.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Data {

    // long;lat;userId
    public static Data fromString(String input) {
        String[] data = input.split(";");
        Data result = new Data(Long.parseLong(data[0]), data[0], data[1]);
        return result;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Date createdAt;
    private Long userId;
    private String longitude;
    private String latitude;

    public Data(Long userId, String longitude, String latitude) {
        this.createdAt = new Date();
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public Long getUserId() { return userId; }
    public String getLongitude() { return longitude; }
    public String getLatitude() { return latitude; }

    @Override
    public String toString() {
        return "Data#{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
