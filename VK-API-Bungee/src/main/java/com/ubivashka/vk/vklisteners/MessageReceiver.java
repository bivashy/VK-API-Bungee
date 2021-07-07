package com.ubivashka.vk.vklisteners;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.ubivashka.vk.VKAPI;
import com.ubivashka.vk.events.VKMessageEvent;
import com.ubivashka.vk.runnables.ProxyRunnable;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.messages.responses.GetLongPollHistoryResponse;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Event;

public class MessageReceiver {
	private VKAPI plugin;

	public List<Message> messages = null;

	public MessageReceiver(VKAPI plugin) {
		this.plugin = plugin;
		startTask();
	}

	private void startTask() {
		System.out.println(ChatColor.GREEN + "LongPool message listener enabled");
		new ProxyRunnable() {
			public void run() {
				MessagesGetLongPollHistoryQuery historyQuery = plugin.vk.messages().getLongPollHistory(plugin.actor)
						.ts(plugin.ts);
				try {
					messages = ((GetLongPollHistoryResponse) historyQuery.execute()).getMessages().getItems();
				} catch (ApiException | com.vk.api.sdk.exceptions.ClientException e) {
					e.printStackTrace();
				}
				if (messages != null && !messages.isEmpty()) {
					for (int i = 0; i < messages.size(); i++) {
						callEvent(messages.get(i));
					}
					messages = null;
				}
				plugin.setTS();
			}
		}.runAfterEvery(plugin, 0L, plugin.getConfig().getInt("settings.delay"), TimeUnit.SECONDS);
	}

	private void callEvent(Message message) {
		if (message.getFromId().intValue() == -plugin.actor.getGroupId().intValue())
			return;
		if (plugin.getConfig().getBoolean("settings.disableGroupListener") && message.getFromId().intValue() < 0)
			return;
		VKMessageEvent messageEvent = new VKMessageEvent(message);
		plugin.getProxy().getPluginManager().callEvent((Event) messageEvent);
		if (messageEvent.isCancelled())
			plugin.vkutil.deleteMessage(message);
	}
}
