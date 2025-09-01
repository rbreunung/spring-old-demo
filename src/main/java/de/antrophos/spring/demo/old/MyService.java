package de.antrophos.spring.demo.old;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.messaging.Message;

public class MyService {

    public static final Logger LOGGER = LoggerFactory.getLogger(MyService.class);

    private final JdbcTemplate jdbcTemplate;
    private final String watchedDirectory;

    public MyService(String watchedDirectory, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.watchedDirectory = watchedDirectory;

        LOGGER.info("Watched Directory from XML: " + watchedDirectory);
        Path path = Paths.get(watchedDirectory);
        System.out.println("This would be " + path.toAbsolutePath());
        // Add your directory monitoring logic here.
    }

    public void receiveMessage(Message<String> message) {
        String fileContent = message.getPayload();
        LOGGER.info("Received file from directory {}: {}", watchedDirectory, fileContent);

        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
                "INSERT INTO messages (message) VALUES (?)",
                Types.CLOB);
        PreparedStatementCreator statement = preparedStatementCreatorFactory
                .newPreparedStatementCreator(Collections.singletonList(message));

        Long id = jdbcTemplate.execute(statement, ps -> {
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            return rs.getLong(1);
        });

        LOGGER.info("Written message with id {} to database.", id);
    }
}
