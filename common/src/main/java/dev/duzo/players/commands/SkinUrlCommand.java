package dev.duzo.players.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.duzo.players.Constants;
import dev.duzo.players.entities.FakePlayerEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SkinUrlCommand {
	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(
				Commands.literal(Constants.MOD_ID)
						.then(Commands.literal("url")
								.requires((p) -> p.hasPermission(Commands.LEVEL_GAMEMASTERS))
								.then(Commands.argument("target", EntityArgument.entity())
										.then(Commands.argument("texture", StringArgumentType.greedyString())
												.executes(SkinUrlCommand::execute)))));
	}

	private static int execute(CommandContext<CommandSourceStack> context) {
		CommandSourceStack source = context.getSource();

		FakePlayerEntity target;
		try {
			Entity found = EntityArgument.getEntity(context, "target");

			if (!(found instanceof FakePlayerEntity)) {
				source.sendFailure(Component.literal("Target is not fake player"));
				return 0;
			}

			target = (FakePlayerEntity) found;
		} catch (CommandSyntaxException exception) {
			source.sendFailure(Component.literal("Invalid target entity"));
			return 0;
		}

		String texture = StringArgumentType.getString(context, "texture");
		String key = encode(texture);
		String name = target.hasCustomName() ? target.getCustomName().getString() : "duzo";

		target.setSkin(new FakePlayerEntity.SkinData(name, key, texture));

		source.sendSuccess(() -> Component.literal("Skin set to " + texture), true);
		return Command.SINGLE_SUCCESS;
	}

	private static String encode(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(input.getBytes());
			StringBuilder hexString = new StringBuilder();
			for (byte b : hash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
