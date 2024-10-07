package me.r3bus.welplug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Welplug extends JavaPlugin implements Listener {

    private final Map<Player, ChatColor> playerColors = new HashMap<>();
    private final Map<Player, Location> playerHomes = new HashMap<>();
    private List<ChatColor> availableColors;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        this.loadAvailableColors();
        this.getLogger().info("ColorChatPlugin has been enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("ColorChatPlugin has been disabled.");
    }

    private void loadAvailableColors() {
        try {
            final Path path = Path.of(this.getDataFolder().toString(), "colors.txt");
            if (Files.exists(path)) {
                this.availableColors = Files.readAllLines(path).stream()
                        .map(ChatColor::valueOf)
                        .toList();
            } else {
                this.availableColors = List.of(
                        ChatColor.RED, ChatColor.GREEN, ChatColor.LIGHT_PURPLE,
                        ChatColor.BLACK, ChatColor.DARK_AQUA, ChatColor.WHITE,
                        ChatColor.DARK_PURPLE, ChatColor.AQUA
                );
                Files.write(path, this.availableColors.stream().map(ChatColor::name).toList());
            }
        } catch (final IOException e) {
            this.getLogger().severe("Could not load colors: " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        final ChatColor color = this.playerColors.getOrDefault(player, ChatColor.WHITE);
        final String message = event.getMessage();
        event.setMessage(color + message);
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        switch (cmd.getName().toLowerCase()) {
            case "colorlist":
                return handleColorListCommand(sender);
            case "setcolor":
                return handleSetColorCommand(sender, args);
            case "helpcolor":
                return handleHelpColorCommand(sender);
            case "versioncolor":
                return handleVersionColorCommand(sender);
            case "sethome":
                return handleSetHomeCommand(sender);
            case "home":
                return handleHomeCommand(sender);
            case "adminchatcolor":
                return handleAdminChatColorCommand(sender, args);
            case "ophelpcolor":
                return handleOpHelpColorCommand(sender);
            case "resetcolor":
                return handleResetColorCommand(sender);
            default:
                return false;
        }
    }

    private boolean handleColorListCommand(CommandSender sender) {
        if (sender instanceof Player player) {
            player.sendMessage(ChatColor.GOLD + "Available Colors:");
            for (final ChatColor color : this.availableColors) {
                player.sendMessage(color + color.name().toLowerCase());
            }
        }
        return true;
    }

    private boolean handleSetColorCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player) || args.length == 0) {
            return false;
        }
        final String colorName = args[0].toUpperCase();
        try {
            final ChatColor color = ChatColor.valueOf(colorName);
            this.playerColors.put(player, color);
            sendMessage(player, ChatColor.GREEN + "Your chat color has been set to " + color + colorName.toLowerCase() + ChatColor.GREEN + ".");
        } catch (final IllegalArgumentException e) {
            sendMessage(player, ChatColor.RED + "Invalid color. Use /colorlist to see available colors.");
        }
        return true;
    }

    private boolean handleHelpColorCommand(CommandSender sender) {
        // Provide general help information for players
        sendMessage(sender, ChatColor.GOLD + "General Help:");
        sendMessage(sender, ChatColor.LIGHT_PURPLE + "/helpcolor - Displays help for players.");
        sendMessage(sender, ChatColor.DARK_RED + "/colorlist - Lists available chat colors.");
        sendMessage(sender, ChatColor.YELLOW + "/setcolor <color> - Sets your chat color.");
        return true;
    }

    private boolean handleVersionColorCommand(CommandSender sender) {
        if (sender instanceof Player player) {
            sendMessage(player, ChatColor.GREEN + "ColorChat Plugin Version: " + ChatColor.YELLOW + this.getDescription().getVersion());
        }
        return true;
    }

    private boolean handleSetHomeCommand(CommandSender sender) {
        if (sender instanceof Player player) {
            this.playerHomes.put(player, player.getLocation());
            sendMessage(player, ChatColor.GREEN + "Home set!");
        }
        return true;
    }

    private boolean handleHomeCommand(CommandSender sender) {
        if (sender instanceof Player player) {
            if (this.playerHomes.containsKey(player)) {
                player.teleport(this.playerHomes.get(player));
                sendMessage(player, ChatColor.GREEN + "Teleported to your home!");
            } else {
                sendMessage(player, ChatColor.RED + "You haven't set a home yet! Use /sethome.");
            }
        }
        return true;
    }

    private boolean handleAdminChatColorCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player player && player.hasPermission("colorchat.admin")) {
            if (args.length > 0) {
                final String colorName = args[0].toUpperCase();
                try {
                    final ChatColor color = ChatColor.valueOf(colorName);
                    this.playerColors.put(player, color);
                    sendMessage(player, ChatColor.GREEN + "Admin chat color set to " + color + colorName.toLowerCase() + ".");
                } catch (final IllegalArgumentException e) {
                    sendMessage(player, ChatColor.RED + "Invalid color. Use /colorlist to see available colors.");
                }
            }
        } else {
            sendMessage(sender, ChatColor.RED + "You don't have permission to set admin chat colors.");
        }
        return true;
    }

    private boolean handleResetColorCommand(CommandSender sender) {
        if (sender instanceof Player player) {
            this.playerColors.remove(player);
            sendMessage(player, ChatColor.GREEN + "Your chat color has been reset to default.");
        }
        return true;
    }

    private boolean handleOpHelpColorCommand(CommandSender sender) {
        if (sender instanceof Player player && player.isOp()) {
            sendMessage(player, ChatColor.GOLD + "OP Help:");
            sendMessage(player, ChatColor.LIGHT_PURPLE + "/helpcolor - Displays general help for players.");
            sendMessage(player, ChatColor.DARK_RED + "/colorlist - Lists available chat colors.");
            sendMessage(player, ChatColor.YELLOW + "/setcolor <color> - Sets your chat color.");
        }
        return true;
    }

    private void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(message);
    }
    private boolean handleHelpCommand(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "Available Commands:");
        sender.sendMessage(ChatColor.YELLOW + "/colorlist - Lists available colors.");
        sender.sendMessage(ChatColor.YELLOW + "/setcolor <color> - Sets your chat color.");
        sender.sendMessage(ChatColor.YELLOW + "/sethome - Sets your home location.");
        // Add descriptions for each command...
        if (sender instanceof Player player) {
            player.sendMessage(ChatColor.YELLOW + "/home - Teleports to your home location.");
            player.sendMessage(ChatColor.YELLOW + "/adminchatcolor <color> - Sets the admin chat color for the current player.");
            player.sendMessage(ChatColor.YELLOW + "/resetcolor - Resets your chat color to default.");
            player.sendMessage(ChatColor.YELLOW + "/ophelpcolor - Displays help for OPs.");
            player.sendMessage(ChatColor.YELLOW + "/versioncolor - Displays the version of the plugin.");
        }
        return true;
    }

}
