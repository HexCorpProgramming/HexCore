package care.cuddliness.hex.database;

import care.cuddliness.hex.database.model.consent.ConsentModel;
import care.cuddliness.hex.database.model.drone.DroneModel;
import dev.dejvokep.boostedyaml.YamlDocument;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import lombok.Getter;

import java.util.List;

public class Database {

    @Getter private io.ebean.Database database;

    public Database(YamlDocument config){
        DatabaseConfig dbConfig = new DatabaseConfig();
        String username = config.getString("database.username");
        String password = config.getString("database.password");
        String database = config.getString("database.database");
        String address = config.getString("database.address");
        String jdbcUrl = String.format("jdbc:mysql://%s/%s?useSSL=false", address, database);

        dbConfig.setName("db");
        dbConfig.setDefaultServer(true);
        dbConfig.setRegister(true);

        DataSourceConfig dconfig = new DataSourceConfig();
        dconfig.setUrl(jdbcUrl);
        dconfig.setUsername(username);
        dconfig.setPassword(password);
        dbConfig.setPackages(List.of("care.cuddliness.hexcorp"));
        dbConfig.setDataSourceConfig(dconfig);


    }
}
