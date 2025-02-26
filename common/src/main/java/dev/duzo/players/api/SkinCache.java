package dev.duzo.players.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.duzo.players.Constants;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

/**
 * A handler for the skin cache files, stored in a .json file
 * Stores the key and the URL of the skin as a key-value pair
 */
public class SkinCache {
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final String CACHE_FILE = "skins.json";

	private boolean locked;
	private ArrayList<CacheData> data;

	public SkinCache() {

	}

	public boolean isLoaded() {
		return data != null && !data.isEmpty();
	}

	private void load() {
		if (this.isLoaded()) return;

		// read data from file
		try {
			Path path = getSavePath();

			data = new ArrayList<>();

			if (Files.exists(path)) {
				String json = Files.readString(path);
				Type listType = new TypeToken<ArrayList<CacheData>>() {
				}.getType();
				data = GSON.fromJson(json, listType);
				Constants.debug("Loaded skin cache");
			}
		} catch (Exception e) {
			Constants.LOG.error("Failed to load skin cache", e);
		}

		// enqueue the data
		locked = true;

		for (CacheData cache : data) {
			SkinGrabber.INSTANCE.getSkinOrDownload(cache.key, cache.url);
		}

		locked = false;
	}

	void save() {
		if (locked) return;

		try {
			Path path = getSavePath();
			String json = GSON.toJson(data);

			Files.writeString(path, json);
			Constants.debug("Saved skin cache");
		} catch (Exception e) {
			Constants.LOG.error("Failed to save skin cache", e);
		}
	}

	private Path getSavePath() throws IOException {
		Path result = Path.of(SkinGrabber.DEFAULT_DIR + "/" + CACHE_FILE);
		Files.createDirectories(result.getParent());

		return result;
	}

	/**
	 * Adds the skin to the cache
	 * Will be saved when client stops
	 *
	 * @param uuid the key
	 * @param url  the value
	 * @return true if the skin was added, false if it already exists / cache is locked
	 */
	public boolean add(String uuid, String url) {
		if (locked) {
			return false;
		}
		this.load();

		if (this.get(uuid).isEmpty()) {
			data.add(new CacheData(uuid, url));
			return true;
		}

		return false;
	}

	public Optional<CacheData> get(String uuid) {
		if (locked) return Optional.empty();

		this.load();

		Optional<CacheData> result = data.stream().filter(cache -> cache.key.equals(uuid)).findFirst();
		if (result.isEmpty()) return result;

		if (result.get().isOutdated()) {
			Constants.debug("Removing outdated skin from cache: {}", result.get().key);

			data.remove(result.get());
			return Optional.empty();
		}

		return result;
	}

	public record CacheData(String key, String url, long lastUpdate) {
		public CacheData(String key, String url) {
			this(key, url, System.currentTimeMillis());
		}

		public boolean isOutdated() {
			return System.currentTimeMillis() - lastUpdate > 1000 * 60 * 60 * 24; // 1 day
		}
	}
}
