package hydrocks.monstertag.commands;

import hydrocks.monstertag.MonsterTag;
import hydrocks.monstertag.game.GeneralGame;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartGame implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (checkArgs(args)) {
            List<Player> players = getPlayers(args);
            new GeneralGame(Integer.parseInt(args[0]), players);
            for (Player pl : players) {
                if (pl != null) {
                    pl.sendMessage("");
                    pl.sendMessage("");
                    pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 ▶ &7Players &8◀ "));
                    pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 » &a" + players.get(0).getName()));
                    if (players.get(1) != null)
                        pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 » &c" + players.get(1).getName()));
                    pl.sendMessage("");
                    pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&lFIRST TO [&7" + args[0] + "&8&l] POINTS WINS!"));
                    pl.sendMessage("");
                }
            }
        } else {
            sender.sendMessage(ChatColor.RED + "Invalid Format, use /start <Points to win> <player1> <player2>");
        }
        return false;
    }

    private boolean checkArgs(String[] args) {
        try {
            if (args.length == 3) {
                Bukkit.getPlayer(args[2]);
                Bukkit.getPlayer(args[1]);
                Integer.parseInt(args[0]);
                return true;
            } else return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private List<Player> getPlayers(String[] args) {
        List<Player> listPlayers = new ArrayList<>();
        listPlayers.add(null);
        listPlayers.add(null);

        Player p1 = Bukkit.getPlayer(args[1]);
        Player p2 = Bukkit.getPlayer(args[2]);

        if (p1 != null) listPlayers.set(0, p1);
        if (p2 != null) listPlayers.add(1, p2);

        return listPlayers;
    }
}
