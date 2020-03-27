package com.HystelDKL;

import com.HystelDKL.cmds.CommandInfect;
import com.HystelDKL.cmds.CommandUninfect;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class coronavirus extends JavaPlugin implements Listener
{

    public static Set<Entity> infected = new HashSet<>();
    public int tick = 0;

    @Override
    public void onEnable()
    {
        this.getCommand("uninfect").setExecutor(new CommandUninfect());
        this.getCommand("infect").setExecutor(new CommandInfect());
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getScheduler().runTaskTimer(this, () -> {
        for (Iterator<LivingEntity> iterator = Bukkit.getWorlds().get(0).getLivingEntities().iterator(); iterator.hasNext();) {
            LivingEntity livingEntity = iterator.next();
            if (infected.contains(livingEntity)) {
                if (tick % 20 == 0) {
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 4000, 10));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 4000, 10));
                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 4000, 1));
                    livingEntity.damage(0.2);

                    if (livingEntity.getHealth() <= 0) {
                        Player player = (Player)livingEntity;
                        Bukkit.broadcastMessage("[CORONAVIRUS] " + player.getDisplayName() + " just died from coronavirus! Be careful!!!");
                        livingEntity.getWorld().createExplosion(livingEntity.getLocation(), 2);
                        infected.remove(livingEntity);
                    for (int i = 0; i < 100; i++ )
                    {
                        Block block = livingEntity.getLocation().getBlock();
                        FallingBlock fallingBlock = livingEntity.getWorld().spawnFallingBlock(livingEntity.getEyeLocation(), Material.LIME_CARPET, DyeColor.LIME.getWoolData());
                        fallingBlock.setVelocity(new Vector(Math.random() - 0.5, Math.random() - 0.5, Math.random() - 0.5).multiply(2));
                    }
                    }
                }
            }
            Block block = livingEntity.getLocation().getBlock();
            if(infected.contains(livingEntity)) {
                if (block.getType().isTransparent() && block.getRelative(BlockFace.DOWN).getType().isOccluding()) {
                    block.setType(Material.LIME_CARPET, false);
                }
            }

            livingEntity.getWorld().playEffect(livingEntity.getEyeLocation(), Effect.SMOKE, 0);


            if (livingEntity.isDead()) {
                infected.remove(livingEntity);
                iterator.remove();
            } else if (block.getType() == Material.LIME_CARPET) {
                infected.add(livingEntity);
            }


        }
        },1,1);
    }

    @Override
    public void onDisable() {

    }

    public void onPlayerJoin(PlayerJoinEvent event)
    {
        for (Iterator<LivingEntity> iterator = Bukkit.getWorlds().get(0).getLivingEntities().iterator(); iterator.hasNext();) {
            LivingEntity livingEntity = iterator.next();
            infected.remove(livingEntity);
            }

    }
}
