package br.com.realmexpert.command;

import br.com.realmexpert.util.ZipUtil;
import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BackupWorldCommand extends Command {

	public BackupWorldCommand(String name) {
		super(name);
		this.description = "Create a backup of your world.";
		this.usageMessage = "/backupworld";
		this.setPermission("RealmExpertMC.command.backupworld");
	}

	@Override
	public boolean execute(CommandSender sender, String currentAlias, String[] args) {
		if(sender.isOp()) {
			if(args.length != 1) {
				sender.sendMessage("You need to specify the world name.");
				return true;
			}

			World world = Bukkit.getWorld(args[0]);
			if(!new File(args[0]).exists() || world == null) {
				sender.sendMessage("This world doesn't exists.");
				return true;
			}
			world.save();
			new Thread(() -> {
				try {
					sender.sendMessage("Creating world backup, please wait...");
					LocalDateTime now = LocalDateTime.now();
					File zip = new File("./RealmExpertMCBackups/" + args[0] + "-" + now.getDayOfMonth() + "-" + now.getMonthValue() + "-" + now.getYear() + "-" + now.getHour() + "-" + now.getMinute() + "-"+now.getSecond()+".zip");
					zip.getParentFile().mkdirs();
					zip.createNewFile();

					ZipUtil.zipFolder(Paths.get("./"+args[0]), zip.toPath());
					sender.sendMessage("The world has been successfully saved!");
				} catch (Exception e) {
					sender.sendMessage("Failed to save world or this world doesn't exists.");
					e.printStackTrace();
				}
			}).start();
		}
		return true;
	}
}