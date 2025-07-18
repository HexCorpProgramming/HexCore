package care.cuddliness.hex.message;

import care.cuddliness.hex.HexCore;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MessageUtil {

    public static void sendMessage(String input, Player player) {
        MiniMessage.miniMessage().deserialize(input);
        HexCore.getHexCore().miniMessage().player(player).sendMessage(MiniMessage.miniMessage().deserialize(input));

    }

    public static void broadcast(String input, World world) {
        MiniMessage.miniMessage().deserialize(input);
        for (Player p : Bukkit.getOnlinePlayers()) {
            HexCore.getHexCore().miniMessage().player(p).sendMessage(MiniMessage.miniMessage().deserialize(input));
        }

    }
}
