package loganleo.tank;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ConfigManager {
    public static final int TANK_SPEED;
    public static final int ENEMY_COUNT;
    public static final int BULLET_SPEED;

    public static final float ENEMY_SHOOT_PROBABILITY;

    // Singleton
    private ConfigManager() {}

    static {
        InputStream inputStream = ConfigManager.class.getClassLoader().getResourceAsStream("config.json");
        String jsonString;
        try {
            assert inputStream != null;
            jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = new JSONObject(jsonString);
        TANK_SPEED = jsonObject.getInt("tank_speed");
        ENEMY_COUNT = jsonObject.getInt("enemy_count");
        BULLET_SPEED = jsonObject.getInt("bullet_speed");
        ENEMY_SHOOT_PROBABILITY = jsonObject.getFloat("enemy_shoot_probability");
    }

}
