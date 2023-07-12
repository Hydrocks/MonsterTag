package hydrocks.monstertag.listeners;

import hydrocks.monstertag.MonsterTag;
import hydrocks.monstertag.game.GeneralGame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityHit implements Listener {
    static MonsterTag plugin;

    public EntityHit(MonsterTag plugin) {
        EntityHit.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void EntityCheck(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            Player p = (Player) e.getDamager();
            if (GeneralGame.gameList.containsKey(p)) {

                EntityType entType = e.getEntityType();
                if (entType == GeneralGame.getEntity(p)) {
                    GeneralGame.roundWin(p);
                }
            }
        }
    }
}
