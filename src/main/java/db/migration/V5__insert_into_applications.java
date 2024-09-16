package db.migration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.core.io.ClassPathResource;

public class V5__insert_into_applications extends BaseJavaMigration {

    private static final String INSERT_STATEMENT_BASE =
            "INSERT INTO applications (name, category, description, developer_id, archive, archive_name, picture_small, picture_big, download_count) values ";

    private static final String ARCHIVE_SUFFIX = ".zip";
    private static final String SMALL_LOGO_SUFFIX = "_128.png";
    private static final String BIG_LOGO_SUFFIX = "_512.png";
    private static final String APPLICATION_DIRECTORY_BASE =
            "db" + File.separator + "app-archive" + File.separator + "insert"
                    + File.separator;

    Map<String, String> getApplicationMap() {
        Map<String, String> applicationMap = new HashMap<>();

        applicationMap.put("apple",
                "('Apple', 'HEALTH', 'Apple app description', 1, ?, 'apple.zip', ?, ?, 14)");
        applicationMap.put("ball",
                "('Ball', 'MULTIMEDIA', 'Ball app description', 1, ?, 'ball.zip', ?, ?, 19)");
        applicationMap.put("house",
                "('House', 'TOOLS', 'House app description', 1, ?, 'house.zip', ?, ?, 42)");
        applicationMap.put("recycle",
                "('Recycle', 'TOOLS', 'Recycle app description', 1, ?, 'recycle.zip', ?, ?, 36)");
        applicationMap.put("rocket",
                "('Rocket', 'PRODUCTIVITY', 'Rocket app description', 1, ?, 'rocket.zip', ?, ?, 9)");
        applicationMap.put("run",
                "('Run', 'HEALTH', 'Run app description', 2, ?, 'run.zip', ?, ?, 25)");
        applicationMap.put("splash",
                "('Splash', 'MULTIMEDIA', 'Splash app description', 2, ?, 'splash.zip', ?, ?, 53)");
        applicationMap.put("tooth",
                "('Tooth', 'HEALTH', 'Tooth app description', 2, ?, 'tooth.zip', ?, ?, 62)");
        applicationMap.put("triangle",
                "('Triangle', 'PRODUCTIVITY', 'Triangle app description', 2, ?, 'triangle.zip', ?, ?, 44)");
        applicationMap.put("tux",
                "('Tux', 'GAMES', 'Tux app description', 2, ?, 'tux.zip', ?, ?, 29);");

        return applicationMap;
    }

    private byte[] getFileContent(final String appName, final String fileSuffix)
            throws IOException {
        String filePath = getFilePath(appName, fileSuffix);
        try (InputStream inputStream = new ClassPathResource(filePath).getInputStream()) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFilePath(final String appName, final String fileSuffix) {
        return APPLICATION_DIRECTORY_BASE + appName + File.separator + appName + fileSuffix;
    }

    @Override
    public void migrate(final Context context) {
        Connection connection = context.getConnection();

        Map<String, String> applicationMap = getApplicationMap();

        applicationMap.forEach((appName, value) -> {
            try {
                byte[] archiveFile = getFileContent(appName, ARCHIVE_SUFFIX);
                byte[] smallLogoFile = getFileContent(appName, SMALL_LOGO_SUFFIX);
                byte[] bigLogoFile = getFileContent(appName, BIG_LOGO_SUFFIX);

                String insertStatement = INSERT_STATEMENT_BASE + value;

                try (PreparedStatement pstmt = connection.prepareStatement(insertStatement)) {
                    pstmt.setBytes(1, archiveFile);
                    pstmt.setBytes(2, smallLogoFile);
                    pstmt.setBytes(3, bigLogoFile);

                    pstmt.executeUpdate();
                }

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}