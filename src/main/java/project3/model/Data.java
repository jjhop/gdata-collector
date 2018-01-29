package project3.model;

import java.util.Date;
import java.util.UUID;

import static java.lang.Thread.currentThread;
import static java.lang.System.nanoTime;

public class Data {

    public static Data fromString(String input) {
        String[] data = input.split(";");
        Data result = new Data(UUID.fromString(data[2]), data[0], data[1]);
        result.id = randomId(input, String.valueOf(nanoTime()), currentThread().getName());
        return result;
    }

    private static UUID randomId(String...args) {
        return UUID.nameUUIDFromBytes(String.join("<>", args).getBytes());
    }

    private UUID id;
    private Date createdAt;
    private UUID userId;
    private String longitude;
    private String latitude;

    public Data(UUID userId, String longitude, String latitude) {
        this.createdAt = new Date();
        this.userId = userId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public UUID getId() { return id; }
    public Date getCreatedAt() { return createdAt; }
    public UUID getUserId() { return userId; }
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
