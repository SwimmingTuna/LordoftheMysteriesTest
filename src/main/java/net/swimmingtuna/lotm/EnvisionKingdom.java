package net.swimmingtuna.lotm;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class EnvisionKingdom extends Item {

    public EnvisionKingdom(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player pPlayer, InteractionHand hand) {
        if (!pPlayer.level().isClientSide) {
            BlockPos playerPos = pPlayer.getOnPos();
            generateCathedral(pPlayer, playerPos, level);
            if (!pPlayer.getAbilities().instabuild) {
                pPlayer.getCooldowns().addCooldown(this, 900);
            }
        }
            return super.use(level, pPlayer, hand);
        }

    private void generateCathedral(Player pPlayer, BlockPos playerPos, Level level) {
        if (!pPlayer.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) level;
            StructureTemplate template = serverLevel.getStructureManager().getOrCreate(new ResourceLocation(LOTM.MOD_ID, "data/structures/teststructure.nbt"));
            if (template != null) {
                pPlayer.sendSystemMessage(Component.literal("author is" + template.getAuthor()));
                template.placeInWorld(serverLevel, playerPos, playerPos, new StructurePlaceSettings(), null, 3);
                pPlayer.sendSystemMessage(Component.literal("template size is" + template.getSize()));
                pPlayer.sendSystemMessage(Component.literal("pos is" + playerPos));
            }
        }
    }
}