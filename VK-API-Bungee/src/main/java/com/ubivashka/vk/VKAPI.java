package com.ubivashka.vk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Random;

import com.ubivashka.vk.logfilter.LogFilter;
import com.ubivashka.vk.utils.CallbackAPI;
import com.ubivashka.vk.utils.VKUtil;
import com.ubivashka.vk.vklisteners.LongpollAPI;
import com.ubivashka.vk.vklisteners.MessageReceiver;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiAccessException;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class VKAPI extends Plugin {
	public VKUtil vkutil;

	public CallbackAPI callbackAPI;

	public Configuration config;

	public Random random;

	public Integer ts = null;

	public GroupActor actor;

	public VkApiClient vk;

	private static VKAPI instance;

	public void onEnable() {
		instance = this;
		new LogFilter();
		loadConfig();
		HttpTransportClient httpTransportClient = HttpTransportClient.getInstance();
		this.vk = new VkApiClient((TransportClient) httpTransportClient);
		this.random = new Random();
		this.actor = new GroupActor(Integer.valueOf(getConfig().getInt("groupInfo.groupID")),
				getConfig().getString("groupInfo.groupToken"));
		setTS();
		if (this.ts == null)
			return;
		System.out.println(ChatColor.GREEN + "Group launched!");
		new MessageReceiver(this);
		new LongpollAPI(this);
		this.vkutil = new VKUtil(this);
		this.callbackAPI = new CallbackAPI(this);
	}

	public static VKAPI getInstance() {
		if (instance == null) {
			System.err.println("Bukkit VKAPI");
			return null;
		}
		return instance;
	}

	public void setTS() {
		int delay = getConfig().getInt("settings.delay");
		if (delay <= 0 || delay > 4) {
			System.out.println(ChatColor.RED
					+ "Ваш delay в конфиг слишком низкий, или слишком высокий, при неправильной работе, попробуйте поставить её на нормальное значение (2)");
		}
		try {
			ts = vk.messages().getLongPollServer(actor).execute().getTs();
		} catch (ApiAccessException e) {
			System.out.println(ChatColor.RED + "Код ошибки: " + e.getCode());
			System.out.println(ChatColor.RED + "В сайте https://vk.com/dev/errors описаны все ошибки связанные с ВК");
			if (e.getCode() == 1) {
				System.out.println(ChatColor.RED + "Попробуйте запустить плагин чуть позже");
			}
			if (e.getCode() == 15) {
				System.out.println(ChatColor.RED + "Доступ запрещён.\r\n" + "Убедитесь, что Вы не ошиблись в конфиге");
			}
			if (e.getCode() == 5) {
				System.out.println(
						ChatColor.RED + "Авторизация не удалась. \r\n" + "Убедитесь, что Вы не ошиблись в конфиге");
			}
			System.out.println("\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n");
			e.printStackTrace();
		} catch (ApiException e) {
			System.out.println(ChatColor.RED + "Ошибка:");
			e.printStackTrace();
			return;
		} catch (ClientException e) {
			System.out.println(ChatColor.RED + "Нету доступа к сайту!");
			e.printStackTrace();
		}
	}

	private void loadConfig() {
		try {
			if (!getDataFolder().exists())
				getDataFolder().mkdir();
			File file = new File(getDataFolder(), "config.yml");
			if (!file.exists())
				try {
					InputStream in = getResourceAsStream("config.yml");
					try {
						Files.copy(in, file.toPath(), new java.nio.file.CopyOption[0]);
						if (in != null)
							in.close();
					} catch (Throwable throwable) {
						if (in != null)
							try {
								in.close();
							} catch (Throwable throwable1) {
								throwable.addSuppressed(throwable1);
							}
						throw throwable;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			this.config = ConfigurationProvider.getProvider(YamlConfiguration.class)
					.load(new File(getDataFolder(), "config.yml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Configuration getConfig() {
		return this.config;
	}
}
