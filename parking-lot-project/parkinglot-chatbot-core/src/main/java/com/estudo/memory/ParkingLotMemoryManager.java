package com.estudo.memory;

import io.quarkus.logging.Log;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;

@ApplicationScoped
public class ParkingLotMemoryManager {

    private static final String PREFIX = "pk-memory:";

    private final ValueCommands<String, String> valueCommands;

    private final Duration memoryTime;

    public ParkingLotMemoryManager(RedisDataSource redisDb,
                                   @ConfigProperty(name = "parkinglot.memoryTime") String memoryTime) {
        this.valueCommands = redisDb.value(String.class);
        this.memoryTime = Duration.ofMinutes(Long.parseLong(memoryTime));
    }

    public void set(String memoryId, String messages) {
        Log.info("set memory Duration minutes=" + memoryTime.toMinutes() + " convert seconds=" + memoryTime.toSeconds());
        valueCommands.setex(PREFIX + memoryId, memoryTime.getSeconds(), messages);
    }

    public String get(String memoryId) {
        Log.info("get");
        return valueCommands.get(PREFIX + memoryId);
    }

    public void delete(String memoryId) {
        Log.info("delete");
        valueCommands.getdel(PREFIX + memoryId);
    }
}
