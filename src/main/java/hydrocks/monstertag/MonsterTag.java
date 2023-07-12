package hydrocks.monstertag;

import hydrocks.monstertag.commands.StartGame;
import hydrocks.monstertag.game.GeneralGame;
import hydrocks.monstertag.listeners.EntityHit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MonsterTag extends JavaPlugin {

    private static MonsterTag instance;

    public static MonsterTag getInstance() {
        return instance;
    }
    @Override
    public void onEnable() {
        instance = this;
        getCommand("startgame").setExecutor(new StartGame());
        new EntityHit(this);

    }

    @Override
    public void onDisable() {}
}
