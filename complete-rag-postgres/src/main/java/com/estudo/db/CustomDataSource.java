package com.estudo.db;

import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.UUID;

public class CustomDataSource {

    public static final Logger logger = LoggerFactory.getLogger(CustomDataSource.class);

    private final DataSource dataSource;

    private CustomDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static CustomDataSource of(
            String host,
            String port,
            String database,
            String user,
            String password) {
        final var dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://" + host + ":" + port + "/" + database);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return new CustomDataSource(dataSource);
    }

    public void createChatSession(UUID chatId) {
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement("INSERT INTO chats (chat_id, created_at) VALUES (?, ?)")) {
            ps.setObject(1, chatId);
            ps.setObject(2, LocalDateTime.now());
            ps.executeUpdate();
            new Chat(chatId, null, LocalDateTime.now());
        } catch (Exception e) {
            logger.error("Error while creating chat session", e);
        }
    }

    public Chat getChatSessionMessages(UUID chatId) {
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement("SELECT * FROM chats WHERE chat_id = ?")) {

        ps.setObject(1, chatId);
        try (var rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Chat(
                        rs.getObject("chat_id", UUID.class),
                        rs.getString("content"),
                        rs.getObject("created_at", LocalDateTime.class)
                );
            }
            }
        } catch (Exception e) {
            logger.error("Error while getting chat session messages", e);
        }
        return null;
    }

    public void updateChatSession(UUID chatId, String content) {
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement("UPDATE chats SET content = ? WHERE chat_id = ?")) {
            ps.setString(1, content);
            ps.setObject(2, chatId);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("Error while updating chat session", e);
        }
    }

    public void deleteChatSession(UUID chatId) {
        try (var conn = dataSource.getConnection();
             var ps = conn.prepareStatement("DELETE FROM chats WHERE chat_id = ?")) {
            ps.setObject(1, chatId);
            ps.executeUpdate();
        } catch (Exception e) {
            logger.error("Error while deleting chat session", e);
        }
    }
}
