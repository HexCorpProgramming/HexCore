package care.cuddliness.hexcorp.hexcore.database;

import care.cuddliness.hexcorp.hexcore.HexCore;
import dev.dejvokep.boostedyaml.YamlDocument;
import lombok.Getter;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;


public class Database {
    @Getter Connection connection;
    @Getter DSLContext dsl;

    public Database(YamlDocument config) {
        String username = config.getString("database.username");
        String password = config.getString("database.password");
        String database = config.getString("database.database");
        String address = config.getString("database.address");
        String jdbcUrl = String.format("jdbc:mysql://%s/%s?useSSL=false", address, database);

        try {
            this.connection = DriverManager.getConnection(jdbcUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        initDatabase();

    }

    private void initDatabase() {
        this.dsl = DSL.using(connection, SQLDialect.MYSQL);
        InputStream is = HexCore.class.getResourceAsStream("/database_init.sql");
        String sql = new BufferedReader(new InputStreamReader(is))
                .lines()
                .collect(Collectors.joining("\n"));
        for (String line : sql.split(";")) {
            if (!line.trim().isEmpty()) {
                dsl.execute(line.trim());
            }
        }
    }

}
