package com.duzo.fakeplayers.common.items;

import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerEntity;
import com.duzo.fakeplayers.common.entities.humanoids.FakePlayerSlimEntity;
import com.duzo.fakeplayers.core.init.FPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class FakePlayerSpawnEgg extends Item {
    private boolean chunky = false;
    private boolean slim = false;

    public FakePlayerSpawnEgg(Properties properties, boolean isSlim, boolean isChunkLoading) {
        super(properties);

        this.slim = isSlim;
        this.chunky = isChunkLoading;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        Player player = context.getPlayer();

        FakePlayerEntity faker = new FakePlayerEntity(FPEntities.FAKE_PLAYER_ENTITY.get(), level);

        if (this.slim) {
            faker = new FakePlayerSlimEntity(FPEntities.FAKE_PLAYER_SLIM_ENTITY.get(), level);
        }

        faker.setChunky(this.chunky);
        faker.moveTo(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        faker.setYBodyRot(-player.getYRot());
        level.addFreshEntity(faker);

        return InteractionResult.SUCCESS;
    }
}
