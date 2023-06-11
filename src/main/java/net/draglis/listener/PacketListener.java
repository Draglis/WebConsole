package net.draglis.webconsole.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.server.DataPacketReceiveEvent;
import cn.nukkit.event.server.DataPacketSendEvent;
import cn.nukkit.network.protocol.*;
import net.draglis.Sergium;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PacketListener implements Listener {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @EventHandler
    public void onReceivePacket(DataPacketReceiveEvent event) throws IOException {
        DataPacket packet = event.getPacket();
        Player player = event.getPlayer();
        String packetData;

        if (
                packet instanceof AddBehaviorTreePacket ||
                packet instanceof AddEntityPacket ||
                packet instanceof AddItemEntityPacket ||
                packet instanceof AddPaintingPacket ||
                packet instanceof AddPlayerPacket ||
                packet instanceof AdventureSettingsPacket ||
                packet instanceof AnimateEntityPacket ||
                packet instanceof AnimatePacket ||
                packet instanceof AnvilDamagePacket ||
                packet instanceof AvailableCommandsPacket ||
                packet instanceof AvailableEntityIdentifiersPacket ||
                packet instanceof BiomeDefinitionListPacket ||
                packet instanceof BlockEntityDataPacket ||
                packet instanceof BlockEventPacket ||
                packet instanceof BlockPickRequestPacket ||
                packet instanceof BookEditPacket ||
                packet instanceof BossEventPacket ||
                packet instanceof CameraPacket ||
                packet instanceof ChangeDimensionPacket ||
                packet instanceof ChunkRadiusUpdatedPacket ||
                packet instanceof ClientboundMapItemDataPacket ||
                packet instanceof ClientToServerHandshakePacket ||
                packet instanceof CodeBuilderPacket ||
                packet instanceof CommandBlockUpdatePacket ||
                packet instanceof CommandOutputPacket ||
                packet instanceof CommandRequestPacket ||
                packet instanceof CompletedUsingItemPacket ||
                packet instanceof CompressedBiomeDefinitionListPacket ||
                packet instanceof ContainerClosePacket ||
                packet instanceof ContainerOpenPacket ||
                packet instanceof ContainerSetDataPacket ||
                packet instanceof CraftingDataPacket ||
                packet instanceof CraftingEventPacket ||
                packet instanceof CreatePhotoPacket ||
                packet instanceof CreativeContentPacket ||
                packet instanceof DebugInfoPacket ||
                packet instanceof DisconnectPacket ||
                packet instanceof EduUriResourcePacket ||
                packet instanceof EmoteListPacket ||
                packet instanceof EmotePacket ||
                packet instanceof EntityEventPacket ||
                packet instanceof EntityFallPacket ||
                packet instanceof EntityPickRequestPacket ||
                packet instanceof EventPacket ||
                packet instanceof FilterTextPacket ||
                packet instanceof GameRulesChangedPacket ||
                packet instanceof GUIDataPickItemPacket ||
                packet instanceof HurtArmorPacket ||
                packet instanceof InitiateWebSocketConnectionPacket ||
                packet instanceof InteractPacket ||
                packet instanceof InventoryContentPacket ||
                packet instanceof InventorySlotPacket ||
                packet instanceof InventoryTransactionPacket ||
                packet instanceof ItemComponentPacket ||
                packet instanceof ItemFrameDropItemPacket ||
                packet instanceof ItemStackRequestPacket ||
                packet instanceof ItemStackResponsePacket ||
                packet instanceof LabTablePacket ||
                packet instanceof LecternUpdatePacket ||
                packet instanceof LessonProgressPacket ||
                packet instanceof LevelChunkPacket ||
                packet instanceof LevelEventPacket ||
                packet instanceof LevelSoundEventPacket ||
                packet instanceof LoginPacket ||
                packet instanceof MapInfoRequestPacket ||
                packet instanceof MobArmorEquipmentPacket ||
                packet instanceof MobEffectPacket ||
                packet instanceof MobEquipmentPacket ||
                packet instanceof ModalFormRequestPacket ||
                packet instanceof ModalFormResponsePacket ||
                packet instanceof MoveEntityAbsolutePacket ||
                packet instanceof MoveEntityDeltaPacket ||
                packet instanceof MovePlayerPacket ||
                packet instanceof NetworkChunkPublisherUpdatePacket ||
                packet instanceof NetworkSettingsPacket ||
                packet instanceof NetworkStackLatencyPacket ||
                packet instanceof NPCRequestPacket ||
                packet instanceof OpenSignPacket ||
                packet instanceof PacketViolationWarningPacket ||
                packet instanceof PhotoInfoRequestPacket ||
                packet instanceof PlayerActionPacket ||
                packet instanceof PlayerArmorDamagePacket ||
                packet instanceof PlayerAuthInputPacket ||
                packet instanceof PlayerEnchantOptionsPacket ||
                packet instanceof PlayerFogPacket ||
                packet instanceof PlayerHotbarPacket ||
                packet instanceof PlayerInputPacket ||
                packet instanceof PlayerListPacket ||
                packet instanceof PlayerSkinPacket ||
                packet instanceof PlaySoundPacket ||
                packet instanceof PlayStatusPacket ||
                packet instanceof PositionTrackingDBClientRequestPacket ||
                packet instanceof PositionTrackingDBServerBroadcastPacket ||
                packet instanceof RemoveEntityPacket ||
                packet instanceof RequestAbilityPacket ||
                packet instanceof RequestChunkRadiusPacket ||
                packet instanceof RequestNetworkSettingsPacket ||
                packet instanceof ResourcePackChunkDataPacket ||
                packet instanceof ResourcePackChunkRequestPacket ||
                packet instanceof ResourcePackClientResponsePacket ||
                packet instanceof ResourcePackDataInfoPacket ||
                packet instanceof ResourcePacksInfoPacket ||
                packet instanceof ResourcePackStackPacket ||
                packet instanceof RespawnPacket ||
                packet instanceof RiderJumpPacket ||
                packet instanceof ScriptCustomEventPacket ||
                packet instanceof ServerSettingsRequestPacket ||
                packet instanceof ServerSettingsResponsePacket ||
                packet instanceof ServerToClientHandshakePacket ||
                packet instanceof SetCommandsEnabledPacket ||
                packet instanceof SetDifficultyPacket ||
                packet instanceof SetEntityDataPacket ||
                packet instanceof SetEntityLinkPacket ||
                packet instanceof SetEntityMotionPacket ||
                packet instanceof SetHealthPacket ||
                packet instanceof SetLastHurtByPacket ||
                packet instanceof SetLocalPlayerAsInitializedPacket ||
                packet instanceof SetPlayerGameTypePacket ||
                packet instanceof SetSpawnPositionPacket ||
                packet instanceof SetTimePacket ||
                packet instanceof SetTitlePacket ||
                packet instanceof ShowCreditsPacket ||
                packet instanceof ShowProfilePacket ||
                packet instanceof SimpleEventPacket ||
                packet instanceof SpawnExperienceOrbPacket ||
                packet instanceof SpawnParticleEffectPacket ||
                packet instanceof StartGamePacket ||
                packet instanceof StopSoundPacket ||
                packet instanceof StructureBlockUpdatePacket ||
                packet instanceof SubClientLoginPacket ||
                packet instanceof TakeItemEntityPacket ||
                packet instanceof TextPacket ||
                packet instanceof TickSyncPacket ||
                packet instanceof ToastRequestPacket ||
                packet instanceof TransferPacket ||
                packet instanceof TrimDataPacket ||
                packet instanceof UpdateAbilitiesPacket ||
                packet instanceof UpdateAdventureSettingsPacket ||
                packet instanceof UpdateAttributesPacket ||
                packet instanceof UpdateBlockPacket ||
                packet instanceof UpdateBlockSyncedPacket ||
                packet instanceof UpdateClientInputLocksPacket ||
                packet instanceof UpdateEquipmentPacket ||
                packet instanceof UpdatePlayerGameTypePacket ||
                packet instanceof UpdateSoftEnumPacket ||
                packet instanceof UpdateSubChunkBlocksPacket ||
                packet instanceof UpdateTradePacket ||
                packet instanceof VideoStreamConnectPacket
        ) {
            packetData = packet.toString();
        } else {
            packetData = "Unknown packet data";
        }

        String str =
                "[" + LocalDateTime.now().format(formatter) + "] " +
                player.getName() + " => " + "Server" + " | " +
                packet.getClass().getSimpleName() + " | " +
                packet.packetId() + " | " +
                packetData + "\n";

        BufferedWriter writer = new BufferedWriter(new FileWriter(Sergium.getInstance().getDataFolder().getPath() + "/WebConsole/content.txt", true));
        writer.append(str);
        writer.close();
    }

    @EventHandler
    public void onSendPacket(DataPacketSendEvent event) throws IOException {
        DataPacket packet = event.getPacket();
        Player player = event.getPlayer();
        String packetData;

        if (
                packet instanceof AddBehaviorTreePacket ||
                        packet instanceof AddEntityPacket ||
                        packet instanceof AddItemEntityPacket ||
                        packet instanceof AddPaintingPacket ||
                        packet instanceof AddPlayerPacket ||
                        packet instanceof AdventureSettingsPacket ||
                        packet instanceof AnimateEntityPacket ||
                        packet instanceof AnimatePacket ||
                        packet instanceof AnvilDamagePacket ||
                        packet instanceof AvailableCommandsPacket ||
                        packet instanceof AvailableEntityIdentifiersPacket ||
                        packet instanceof BiomeDefinitionListPacket ||
                        packet instanceof BlockEntityDataPacket ||
                        packet instanceof BlockEventPacket ||
                        packet instanceof BlockPickRequestPacket ||
                        packet instanceof BookEditPacket ||
                        packet instanceof BossEventPacket ||
                        packet instanceof CameraPacket ||
                        packet instanceof ChangeDimensionPacket ||
                        packet instanceof ChunkRadiusUpdatedPacket ||
                        packet instanceof ClientboundMapItemDataPacket ||
                        packet instanceof ClientToServerHandshakePacket ||
                        packet instanceof CodeBuilderPacket ||
                        packet instanceof CommandBlockUpdatePacket ||
                        packet instanceof CommandOutputPacket ||
                        packet instanceof CommandRequestPacket ||
                        packet instanceof CompletedUsingItemPacket ||
                        packet instanceof CompressedBiomeDefinitionListPacket ||
                        packet instanceof ContainerClosePacket ||
                        packet instanceof ContainerOpenPacket ||
                        packet instanceof ContainerSetDataPacket ||
                        packet instanceof CraftingDataPacket ||
                        packet instanceof CraftingEventPacket ||
                        packet instanceof CreatePhotoPacket ||
                        packet instanceof CreativeContentPacket ||
                        packet instanceof DebugInfoPacket ||
                        packet instanceof DisconnectPacket ||
                        packet instanceof EduUriResourcePacket ||
                        packet instanceof EmoteListPacket ||
                        packet instanceof EmotePacket ||
                        packet instanceof EntityEventPacket ||
                        packet instanceof EntityFallPacket ||
                        packet instanceof EntityPickRequestPacket ||
                        packet instanceof EventPacket ||
                        packet instanceof FilterTextPacket ||
                        packet instanceof GameRulesChangedPacket ||
                        packet instanceof GUIDataPickItemPacket ||
                        packet instanceof HurtArmorPacket ||
                        packet instanceof InitiateWebSocketConnectionPacket ||
                        packet instanceof InteractPacket ||
                        packet instanceof InventoryContentPacket ||
                        packet instanceof InventorySlotPacket ||
                        packet instanceof InventoryTransactionPacket ||
                        packet instanceof ItemComponentPacket ||
                        packet instanceof ItemFrameDropItemPacket ||
                        packet instanceof ItemStackRequestPacket ||
                        packet instanceof ItemStackResponsePacket ||
                        packet instanceof LabTablePacket ||
                        packet instanceof LecternUpdatePacket ||
                        packet instanceof LessonProgressPacket ||
                        packet instanceof LevelChunkPacket ||
                        packet instanceof LevelEventPacket ||
                        packet instanceof LevelSoundEventPacket ||
                        packet instanceof LoginPacket ||
                        packet instanceof MapInfoRequestPacket ||
                        packet instanceof MobArmorEquipmentPacket ||
                        packet instanceof MobEffectPacket ||
                        packet instanceof MobEquipmentPacket ||
                        packet instanceof ModalFormRequestPacket ||
                        packet instanceof ModalFormResponsePacket ||
                        packet instanceof MoveEntityAbsolutePacket ||
                        packet instanceof MoveEntityDeltaPacket ||
                        packet instanceof MovePlayerPacket ||
                        packet instanceof NetworkChunkPublisherUpdatePacket ||
                        packet instanceof NetworkSettingsPacket ||
                        packet instanceof NetworkStackLatencyPacket ||
                        packet instanceof NPCRequestPacket ||
                        packet instanceof OpenSignPacket ||
                        packet instanceof PacketViolationWarningPacket ||
                        packet instanceof PhotoInfoRequestPacket ||
                        packet instanceof PlayerActionPacket ||
                        packet instanceof PlayerArmorDamagePacket ||
                        packet instanceof PlayerAuthInputPacket ||
                        packet instanceof PlayerEnchantOptionsPacket ||
                        packet instanceof PlayerFogPacket ||
                        packet instanceof PlayerHotbarPacket ||
                        packet instanceof PlayerInputPacket ||
                        packet instanceof PlayerListPacket ||
                        packet instanceof PlayerSkinPacket ||
                        packet instanceof PlaySoundPacket ||
                        packet instanceof PlayStatusPacket ||
                        packet instanceof PositionTrackingDBClientRequestPacket ||
                        packet instanceof PositionTrackingDBServerBroadcastPacket ||
                        packet instanceof RemoveEntityPacket ||
                        packet instanceof RequestAbilityPacket ||
                        packet instanceof RequestChunkRadiusPacket ||
                        packet instanceof RequestNetworkSettingsPacket ||
                        packet instanceof ResourcePackChunkDataPacket ||
                        packet instanceof ResourcePackChunkRequestPacket ||
                        packet instanceof ResourcePackClientResponsePacket ||
                        packet instanceof ResourcePackDataInfoPacket ||
                        packet instanceof ResourcePacksInfoPacket ||
                        packet instanceof ResourcePackStackPacket ||
                        packet instanceof RespawnPacket ||
                        packet instanceof RiderJumpPacket ||
                        packet instanceof ScriptCustomEventPacket ||
                        packet instanceof ServerSettingsRequestPacket ||
                        packet instanceof ServerSettingsResponsePacket ||
                        packet instanceof ServerToClientHandshakePacket ||
                        packet instanceof SetCommandsEnabledPacket ||
                        packet instanceof SetDifficultyPacket ||
                        packet instanceof SetEntityDataPacket ||
                        packet instanceof SetEntityLinkPacket ||
                        packet instanceof SetEntityMotionPacket ||
                        packet instanceof SetHealthPacket ||
                        packet instanceof SetLastHurtByPacket ||
                        packet instanceof SetLocalPlayerAsInitializedPacket ||
                        packet instanceof SetPlayerGameTypePacket ||
                        packet instanceof SetSpawnPositionPacket ||
                        packet instanceof SetTimePacket ||
                        packet instanceof SetTitlePacket ||
                        packet instanceof ShowCreditsPacket ||
                        packet instanceof ShowProfilePacket ||
                        packet instanceof SimpleEventPacket ||
                        packet instanceof SpawnExperienceOrbPacket ||
                        packet instanceof SpawnParticleEffectPacket ||
                        packet instanceof StartGamePacket ||
                        packet instanceof StopSoundPacket ||
                        packet instanceof StructureBlockUpdatePacket ||
                        packet instanceof SubClientLoginPacket ||
                        packet instanceof TakeItemEntityPacket ||
                        packet instanceof TextPacket ||
                        packet instanceof TickSyncPacket ||
                        packet instanceof ToastRequestPacket ||
                        packet instanceof TransferPacket ||
                        packet instanceof TrimDataPacket ||
                        packet instanceof UpdateAbilitiesPacket ||
                        packet instanceof UpdateAdventureSettingsPacket ||
                        packet instanceof UpdateAttributesPacket ||
                        packet instanceof UpdateBlockPacket ||
                        packet instanceof UpdateBlockSyncedPacket ||
                        packet instanceof UpdateClientInputLocksPacket ||
                        packet instanceof UpdateEquipmentPacket ||
                        packet instanceof UpdatePlayerGameTypePacket ||
                        packet instanceof UpdateSoftEnumPacket ||
                        packet instanceof UpdateSubChunkBlocksPacket ||
                        packet instanceof UpdateTradePacket ||
                        packet instanceof VideoStreamConnectPacket
        ) {
            packetData = packet.toString();
        } else {
            packetData = "Unknown packet data";
        }

        String str =
                "[" + LocalDateTime.now().format(formatter) + "] " +
                        player.getName() + " <= " + "Server" + " | " +
                        packet.getClass().getSimpleName() + " | " +
                        packet.packetId() + " | " +
                        packetData + "\n";

        BufferedWriter writer = new BufferedWriter(new FileWriter(Sergium.getInstance().getDataFolder().getPath() + "/WebConsole/content.txt", true));
        writer.append(str);
        writer.close();
    }
}
