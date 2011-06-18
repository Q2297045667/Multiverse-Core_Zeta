/**
 * Copyright (C) 2011 DThielke <dave.thielke@gmail.com>
 * 
 * This work is licensed under the Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-nc-nd/3.0/ or send a letter to
 * Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 **/

package com.onarandombox.MultiverseCore.command.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.command.BaseCommand;

public class HelpCommand extends BaseCommand {
    private static final int CMDS_PER_PAGE = 8;

    public HelpCommand(MultiverseCore plugin) {
        super(plugin);
        name = "Help";
        description = "Displays the help menu";
        usage = ChatColor.AQUA + "/mv help " + ChatColor.GOLD + "[page#]";
        minArgs = 0;
        maxArgs = 1;
        identifiers.add("mv help");
        identifiers.add("mv");
        identifiers.add("mvhelp");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        int page = 0;
        if (args.length != 0) {
            try {
                page = Integer.parseInt(args[0]) - 1;
            } catch (NumberFormatException e) {
            }
        }

        List<BaseCommand> commands = plugin.getCommandManager().getCommands();

        int numPages = commands.size() / CMDS_PER_PAGE;
        if (commands.size() % CMDS_PER_PAGE != 0) {
            numPages++;
        }

        if (page >= numPages || page < 0) {
            page = 0;
        }
        sender.sendMessage(ChatColor.GREEN + "-----[ " + ChatColor.WHITE + plugin.getTag().replace("[", "").replace("]", "") + " Help <" + (page + 1) + "/" + numPages + ">" + ChatColor.GREEN + " ]-----");
        int start = page * CMDS_PER_PAGE;
        int end = start + CMDS_PER_PAGE;
        if (end > commands.size()) {
            end = commands.size();
        }
        for (int c = start; c < end; c++) {
            BaseCommand cmd = commands.get(c);
            sender.sendMessage(ChatColor.AQUA + "  " + cmd.getUsage());
        }

        sender.sendMessage(ChatColor.GREEN + "For more info on a particular command, type '/<command> ?'");
    }

}