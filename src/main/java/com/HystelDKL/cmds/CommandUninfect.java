package com.HystelDKL.cmds;

import com.HystelDKL.coronavirus;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class CommandUninfect implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
           player.sendMessage("You have successfully uninfected yourself! Dont get infected again!");

            for (Iterator<LivingEntity> iterator = Bukkit.getWorlds().get(0).getLivingEntities().iterator(); iterator.hasNext(); ) {
                LivingEntity livingEntity = iterator.next();
                coronavirus.infected.remove(livingEntity);
                coronavirus.infected.remove(player);

            }
        }

        return true;
    }
}
