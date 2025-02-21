package dev.duzo.players.api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.datafixers.util.Pair;
import dev.duzo.players.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Most of this code is referenced from Craig's regeneration mod, love u craig
 */
public class SkinGrabber {
	public static final SkinGrabber INSTANCE = new SkinGrabber();
	public static final String API_URL = "https://mineskin.eu/skin/";
	private static final String DEFAULT_DIR = "./" + Constants.MOD_ID + "/skins/";
	private static final ResourceLocation MISSING = new ResourceLocation(Constants.MOD_ID, "textures/skins/error.png");
	private final ConcurrentHashMap<String, ResourceLocation> downloads;
	private final ConcurrentHashMap<String, String> urls;
	private final ConcurrentQueueMap<String, String> downloadQueue;
	private final CopyOnWriteArrayList<Page> pages;
	private int ticks;

	private SkinGrabber() {
		downloads = new ConcurrentHashMap<>();
		urls = new ConcurrentHashMap<>();
		downloadQueue = new ConcurrentQueueMap<>();
		pages = new CopyOnWriteArrayList<>();
	}

	public static ResourceLocation missing() {
		return MISSING;
	}

	public static String encodeURL(String input) {
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

	public List<String> getAllKeys() {
		return List.copyOf(downloads.keySet());
	}

	// These functions were coped from HttpTexture by Mojang (thak you moywang(
	@Nullable
	private static NativeImage processLegacySkin(NativeImage image) {
		int i = image.getHeight();
		int j = image.getWidth();
		if (j == 64 && (i == 32 || i == 64)) {
			boolean flag = i == 32;
			if (flag) {
				NativeImage nativeimage = new NativeImage(64, 64, true);
				nativeimage.copyFrom(image);
				image.close();
				image = nativeimage;
				nativeimage.fillRect(0, 32, 64, 32, 0);
				nativeimage.copyRect(4, 16, 16, 32, 4, 4, true, false);
				nativeimage.copyRect(8, 16, 16, 32, 4, 4, true, false);
				nativeimage.copyRect(0, 20, 24, 32, 4, 12, true, false);
				nativeimage.copyRect(4, 20, 16, 32, 4, 12, true, false);
				nativeimage.copyRect(8, 20, 8, 32, 4, 12, true, false);
				nativeimage.copyRect(12, 20, 16, 32, 4, 12, true, false);
				nativeimage.copyRect(44, 16, -8, 32, 4, 4, true, false);
				nativeimage.copyRect(48, 16, -8, 32, 4, 4, true, false);
				nativeimage.copyRect(40, 20, 0, 32, 4, 12, true, false);
				nativeimage.copyRect(44, 20, -8, 32, 4, 12, true, false);
				nativeimage.copyRect(48, 20, -16, 32, 4, 12, true, false);
				nativeimage.copyRect(52, 20, -8, 32, 4, 12, true, false);
			}

			setNoAlpha(image, 0, 0, 32, 16);
			if (flag) {
				doNotchTransparencyHack(image, 32, 0, 64, 32);
			}

			setNoAlpha(image, 0, 16, 64, 32);
			setNoAlpha(image, 16, 48, 48, 64);
			return image;
		} else {
			image.close();
			Constants.LOG.warn("Discarding incorrectly sized ({}x{}) skin texture", j, i);
			return null;
		}
	}

	private static void doNotchTransparencyHack(NativeImage p_118013_, int p_118014_, int p_118015_, int p_118016_, int p_118017_) {
		for (int i = p_118014_; i < p_118016_; ++i) {
			for (int j = p_118015_; j < p_118017_; ++j) {
				int k = p_118013_.getPixelRGBA(i, j);
				if ((k >> 24 & 255) < 128) {
					return;
				}
			}
		}

		for (int l = p_118014_; l < p_118016_; ++l) {
			for (int i1 = p_118015_; i1 < p_118017_; ++i1) {
				p_118013_.setPixelRGBA(l, i1, p_118013_.getPixelRGBA(l, i1) & 16777215);
			}
		}

	}

	private static void setNoAlpha(NativeImage p_118023_, int p_118024_, int p_118025_, int p_118026_, int p_118027_) {
		for (int i = p_118024_; i < p_118026_; ++i) {
			for (int j = p_118025_; j < p_118027_; ++j) {
				p_118023_.setPixelRGBA(i, j, p_118023_.getPixelRGBA(i, j) | -16777216);
			}
		}
	}

	/**
	 * Get the skin for a player
	 * This will download the skin if it doesn't exist
	 *
	 * @param name The name of the player
	 * @return The skin, or a missing texture if it doesn't exist / is downloading
	 */
	public ResourceLocation getSkin(String name) {
		return getSkinOrDownload(name, API_URL + name);
	}

	public Optional<ResourceLocation> getPossibleSkin(String id) {
		id = id.toLowerCase().replace(" ", "_");

		if (downloads.containsKey(id)) {
			return Optional.of(downloads.get(id));
		}

		return Optional.empty();
	}

	public ResourceLocation getSkinOrDownload(String id, String url) {
		id = id.toLowerCase().replace(" ", "_");

		ResourceLocation existing = getPossibleSkin(id).orElse(null);
		if (existing != null) {
			return existing;
		}

		if (downloadQueue.get(id) != null) {
			return missing();
		}

		url = url.toLowerCase().replace(" ", "_");
		this.enqueueDownload(id, url);
		return missing();
	}

	public String getUrl(String key) {
		return urls.get(key);
	}

	private ResourceLocation registerSkin(String name) {
		// register new skin to prepare
		File file = new File(DEFAULT_DIR + name.toLowerCase().replace(" ", "_") + ".png");
		ResourceLocation location = fileToLocation(file);
		downloads.put(name, location);
		return location;
	}

	public void clearTextures() {
		Minecraft minecraft = Minecraft.getInstance();
		if (minecraft.level == null) {
			TextureManager manager = minecraft.getTextureManager();

			// Release the textures if the cache isnt empty
			if (!this.downloads.isEmpty()) {
				this.downloads.forEach((key, value) -> manager.release(value));
				this.downloads.clear();
			}
		}
	}

	private ResourceLocation fileToLocation(File file) {
		NativeImage image;
		try {
			image = processLegacySkin(NativeImage.read(new FileInputStream(file)));
		} catch (IOException e) {
			Constants.LOG.error("Failed to load ResourceLocation from file", e);
			return missing();
		}
		if (image == null) {
			return missing();
		}
		return registerImage(image);
	}

	private ResourceLocation registerImage(NativeImage image) {
		TextureManager manager = Minecraft.getInstance().getTextureManager();
		return manager.register("player_", new DynamicTexture(image));
	}

	private void downloadImageFromURL(String filename, File filepath, String URL) {
		try {
			URL url = new URL(URL);
			URLConnection connection = url.openConnection();
			connection.connect();
			connection.setConnectTimeout(0);

			BufferedImage image = ImageIO.read(connection.getInputStream());

			if (!filepath.exists()) {
				filepath.mkdirs();
			}

			ImageIO.write(image, "png", new File(filepath, filename + ".png"));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void tick() {
		ticks++;

		if (ticks % 5 != 0) return;
		// called every second
		this.downloadNext();

		ticks = 0;
	}

	private void enqueueDownload(String id, String url) {
		this.downloadQueue.put(id, url);

		Constants.LOG.info("Enqueued Download {} for {}", url, id);
	}

	private void downloadNext() {
		if (this.downloadQueue.isEmpty()) {
			return;
		}

		Pair<String, String> data = this.downloadQueue.remove();

		this.download(data.getFirst(), data.getSecond());
	}

	public void downloadNextPage() {
		// if any arent downloaded, dont download
		if (this.hasDownloads()) return;

		Page page = new Page(pages.size() + 1, new ArrayList<>(15));
		page.download();
		pages.add(page);
	}

	private void download(String id, String url) {
		Constants.LOG.info("Downloading {} for {}", url, id);

		urls.put(id, url);

		new Thread(() -> {
			this.downloadImageFromURL(id, new File(DEFAULT_DIR), url);
			this.registerSkin(id);

			Constants.LOG.info("Downloaded {} for {}!", url, id);
		}, Constants.MOD_ID + "-Download").start();
	}

	public int getPagesDownloaded() {
		return pages.size();
	}

	public boolean hasDownloads() {
		return !downloadQueue.isEmpty();
	}

	public int getDownloadsRemaining() {
		return downloadQueue.size();
	}

	public record Page(int index, List<String> keys) {
		public Page {
			if (index <= 0) {
				throw new IllegalArgumentException("Index must be greater than 0");
			}
		}

		public void download() {
			Constants.LOG.info("Downloading page {}", index);

			if (this.isDownloaded()) {
				Constants.LOG.warn("Page {} is already downloaded", index);
			}

			new Thread(() -> {
				try {
					URL api = new URL("https://api.mineskin.org/get/list/" + index);
					URLConnection connection = api.openConnection();
					connection.connect();
					connection.setConnectTimeout(5000);
					connection.setReadTimeout(5000);

					// read json list [skins]
					// enqueue each skin for download
					InputStream inputStream = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder stringBuilder = new StringBuilder();
					String line;

					while ((line = reader.readLine()) != null) {
						stringBuilder.append(line);
					}

					HashMap<String, String> skins = new HashMap<>();
					JsonElement data = new GsonBuilder().create().fromJson(stringBuilder.toString(), JsonElement.class);
					if (!data.isJsonObject()) {
						throw new IllegalStateException("Expected object");
					}
					data = data.getAsJsonObject().get("skins");
					if (!data.isJsonArray()) {
						throw new IllegalStateException("Expected array");
					}

					data.getAsJsonArray().forEach(element -> {
						if (!element.isJsonObject()) {
							throw new IllegalStateException("Expected object");
						}

						String url = element.getAsJsonObject().get("url").getAsString();
						String id = encodeURL(url);

						skins.put(id, url);
					});

					keys.clear();
					keys.addAll(skins.keySet());

					skins.forEach(SkinGrabber.INSTANCE::enqueueDownload);
				} catch (Exception exception) {
					Constants.LOG.error("Failed to download page, {}", index, exception);
				}
			}, Constants.MOD_ID + "-Page").start();
		}

		public boolean isDownloaded() {
			return new HashSet<>(SkinGrabber.INSTANCE.getAllKeys()).containsAll(keys);
		}
	}
}