package hydrocks.monstertag.game;

import hydrocks.monstertag.MonsterTag;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class GeneralGame {

    static int points;
    private static EntityType entity;
    private static List<Player> players;
    public static HashMap<List<Player>, EntityType> typeList = new HashMap<>();
    public static HashMap<Player, Integer> gameList = new HashMap<>();
    public GeneralGame(int points, List<Player> players) {
        GeneralGame.points = points;
        GeneralGame.players = players;
        for (Player pl : GeneralGame.players) {
            gameList.put(pl, 0);
        }
        getEntityType();
    }

    private static EntityType entRNG() {
        int max = EntityType.values().length;
        int random = new Random().nextInt(max);

        EntityType[] entities = EntityType.values();
        return entities[random];
    }

    public static void getEntityType() {
        new BukkitRunnable() {
            int ct = 10;
            boolean check = false;
            public void run() {
                EntityType e = entRNG();

                if (!check && e.isAlive() && e.getEntityClass() != ArmorStand.class) {
                    check = true;
                    setEntity(e);
                }

                for (Player pl : players) {
                    if (pl != null) {
                        if (ct <= 0) {
                            pl.playSound(pl.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3, 0);
                            pl.sendTitle(ChatColor.translateAlternateColorCodes('&', "&aFind and TAG"), ChatColor.GRAY + "" + getEnt(), 10, 200, 0);
                            pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aFind and TAG &7" + getEnt()));
                            this.cancel();
                        } else {
                            pl.sendMessage(ChatColor.GREEN + "Round starting in " + ct + "...");
                        }
                    }
                }

                ct--;
            }
        }.runTaskTimer(MonsterTag.getInstance(), 0, 20);
    }
    private static void setEntity(EntityType e) {
        entity = e;
        setPlayerEntities();
    }
    private static void setPlayerEntities() {
        typeList.put(players, entity);
    }
    public static EntityType getEnt() {
        return typeList.get(players);
    }
    public static void roundWin(Player winner) {
        gameList.put(winner, gameList.get(winner) + 1);

        for (Player pl : players) {
            if (pl != null) {
                pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7" + winner.getName() + " &awon this round"));
                pl.sendMessage("");
                pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 ▶ &7Points &8◀ "));
                pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 » &7" + players.get(0) + " &a" + gameList.get(players.get(0))));
                if (players.get(1) != null)
                    pl.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8 » &7" + players.get(1) + " &c" + gameList.get(players.get(1))));
            }
        }

        if (gameList.get(winner) >= points) {
            gameWin(winner);
        }

        getEntityType();
    }
    public static EntityType getEntity(Player p) {
        if (players.contains(p)) {
            return typeList.get(players);
        }
        return null;
    }
    private static void gameWin(Player winner) {
        for (Player players : players) {
            gameList.remove(players);
            players.sendMessage("");
            players.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7" + winner.getName() + " &awins!"));
        }
        typeList.remove(players);

    }

}
