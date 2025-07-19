package com.celeryman.item.custom;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PeteStickItem extends Item {
    public PeteStickItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();

        if (world.isClient()) {
            return ActionResult.PASS;
        }
        LightningEntity lightningBolt = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
        lightningBolt.setPosition(Vec3d.of(blockPos));
        world.spawnEntity(lightningBolt);

        BlockPos platformStartBlock = new BlockPos(blockPos.getX()-1, blockPos.getY(), blockPos.getZ()-1);

        //Remember (X, Y, Z). Y is UP in Minecraft.
        for (int xOffset = 0; xOffset < 3; xOffset++) {
            for (int zOffset = 0; zOffset < 3; zOffset++) {
                world.setBlockState(
                        new BlockPos(
                                platformStartBlock.getX()+xOffset,
                                platformStartBlock.getY(),
                                platformStartBlock.getZ()+zOffset),
                        Blocks.NETHERITE_BLOCK.getDefaultState()
                );
            }
        }

        return ActionResult.SUCCESS;
    }
}
