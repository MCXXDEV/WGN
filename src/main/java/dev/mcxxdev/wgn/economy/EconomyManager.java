package dev.mcxxdev.wgn.economy;

import dev.mcxxdev.wgn.data.WgnAttachments;
import dev.mcxxdev.wgn.data.WgnPlayerData;
import dev.mcxxdev.wgn.network.WgnNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class EconomyManager {
	public static final int EMERALD_BUY_PRICE = 8;
	public static final int EMERALD_SELL_PRICE = 5;

	private EconomyManager() {}

	public static int balance(ServerPlayer player) {
		return WgnAttachments.get(player).coins();
	}

	public static void deposit(ServerPlayer player, int amount) {
		WgnAttachments.get(player).addCoins(amount);
		WgnNetworking.syncPlayerData(player);
	}

	public static boolean withdraw(ServerPlayer player, int amount) {
		boolean success = WgnAttachments.get(player).spendCoins(amount);
		if (success) {
			WgnNetworking.syncPlayerData(player);
		}
		return success;
	}

	public static boolean buyEmerald(ServerPlayer player) {
		if (!withdraw(player, EMERALD_BUY_PRICE)) {
			player.displayClientMessage(Component.literal("Not enough WGN coins."), true);
			return false;
		}
		player.getInventory().add(new ItemStack(Items.EMERALD));
		player.displayClientMessage(Component.literal("Purchased emerald for " + EMERALD_BUY_PRICE + " coins."), true);
		return true;
	}

	public static boolean sellEmerald(ServerPlayer player) {
		boolean removed = false;
		for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
			net.minecraft.world.item.ItemStack stack = player.getInventory().getItem(slot);
			if (stack.is(net.minecraft.world.item.Items.EMERALD)) {
				stack.shrink(1);
				removed = true;
				break;
			}
		}
		if (!removed) {
			player.displayClientMessage(Component.literal("You need an emerald to sell."), true);
			return false;
		}
		deposit(player, EMERALD_SELL_PRICE);
		player.displayClientMessage(Component.literal("Sold emerald for " + EMERALD_SELL_PRICE + " coins."), true);
		return true;
	}

	public static void rewardQuest(ServerPlayer player, int coins) {
		deposit(player, coins);
	}
}
