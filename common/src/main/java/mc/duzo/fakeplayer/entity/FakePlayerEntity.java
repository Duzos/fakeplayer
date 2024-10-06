package mc.duzo.fakeplayer.entity;


import mc.duzo.fakeplayer.network.FPNetwork;
import mc.duzo.fakeplayer.network.s2c.SendSkinS2CPacket;
import mc.duzo.fakeplayer.util.SkinGrabber;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FakePlayerEntity extends HumanoidEntity {
	private static final TrackedData<String> SKIN_URL = DataTracker.registerData(FakePlayerEntity.class, TrackedDataHandlerRegistry.STRING);
	private static final TrackedData<Boolean> SLIM = DataTracker.registerData(FakePlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public FakePlayerEntity(EntityType<? extends HumanoidEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	protected void initDataTracker() {
		super.initDataTracker();

		this.dataTracker.startTracking(SKIN_URL,"");
		this.dataTracker.startTracking(SLIM, false);
	}

	@Override
	public boolean canPickUpLoot() {
		return true;
	}

	private void setSlim(boolean slim) {
		this.dataTracker.set(SLIM, slim);
	}
	public boolean isSlim() {
		return this.dataTracker.get(SLIM);
	}

	private void setURL(String url, boolean sync) {
		this.dataTracker.set(SKIN_URL, url);

		if (sync) this.syncURL();
	}
	public String getURL() {
		return this.dataTracker.get(SKIN_URL);
	}
	private void syncURL() {
		if (this.getWorld().isClient()) return;

		FPNetwork.CHANNEL.sendToPlayers((Iterable<ServerPlayerEntity>) this.getWorld().getPlayers(), new SendSkinS2CPacket(this));
	}

	@Override
	public void setCustomName(@Nullable Text name) {
		super.setCustomName(name);

		if (this.getWorld().isClient()) return;
		if (name == null) return;
		if (name.getString().isBlank()) return;

		this.setURL(SkinGrabber.URL + name.getString(), true);
	}
}
