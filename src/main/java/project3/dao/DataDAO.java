package project3.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import project3.model.Data;

@Component
public class DataDAO {

    private final static Logger LOG = LoggerFactory.getLogger(DataDAO.class);

    private final SimpleJdbcInsert insert;

    public DataDAO(DataSource dataSource) {
        insert = new SimpleJdbcInsert(dataSource)
                .withTableName("geo_data")
                .usingColumns("longitude", "latitude", "user_id", "id");
    }

    public void save(Data d) {
        LOG.trace("Trying to save data [{}]", d);
        Map<String, String> params = new HashMap<>();
        params.put("longitude", d.getLongitude());
        params.put("latitude", d.getLatitude());
        params.put("user_id", d.getUserId().toString());
        params.put("data_id", UUID.randomUUID().toString());
        insert.execute(params);
        LOG.trace("Data [{}] successfully saved.", d);
    }
}
