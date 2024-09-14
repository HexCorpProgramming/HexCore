package care.cuddliness.hex.utils;

import com.mojang.authlib.GameProfile;
import org.bukkit.craftbukkit.v1_21_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class NameUtil {

    //Changing player name with some field reflections
    public static void changePlayerName(Player player, String name) {
        CraftPlayer ep = ((CraftPlayer) player);
        GameProfile gameProfile = ep.getHandle().getGameProfile();
        Field field = null;
        try {
            field = gameProfile.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(gameProfile, name);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }

    }
}
