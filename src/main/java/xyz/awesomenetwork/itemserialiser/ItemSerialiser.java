package xyz.awesomenetwork.itemserialiser;

import java.util.Map;

import com.google.gson.Gson;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class ItemSerialiser extends JavaPlugin {
	public void onEnable() {
		getServer().getPluginCommand("serialise").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Fun fact: the console can't hold items!");
			return false;
		}

		Player player = (Player) sender;

		// Get player's held item (can be air)
		Map<?, ?> itemMap = player.getInventory().getItemInMainHand().serialize();

		// Convert to JSON
		Gson gson = new Gson();
		String serialised = gson.toJson(itemMap);

		// Display to player and console
		getLogger().info(player.getName() + "'s serialised item: " + serialised);

		TextComponent component = new TextComponent(serialised);
		component.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, serialised));
		player.spigot().sendMessage(component);

		return true;
	}
}
