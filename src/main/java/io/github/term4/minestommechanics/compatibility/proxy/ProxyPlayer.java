package io.github.term4.minestommechanics.compatibility.proxy;

import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.SendablePacket;
import net.minestom.server.network.packet.server.play.EntityMetaDataPacket;
import net.minestom.server.network.player.GameProfile;
import net.minestom.server.network.player.PlayerConnection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Custom Player that strips input-driven metadata (sneak, sprint, pose, hand active)
 * from self-packets to fix 1.21.8+ desync. Viewers receive the full packet.
 */
public class ProxyPlayer extends Player {

    private static final Set<Integer> INPUT_INDICES = Set.of(0, 6, 8);

    public ProxyPlayer(PlayerConnection connection, GameProfile profile) {
        super(connection, profile);
    }

    @Override
    public void sendPacketToViewersAndSelf(SendablePacket packet) {
        if (packet instanceof EntityMetaDataPacket meta && meta.entityId() == getEntityId()) {
            EntityMetaDataPacket stripped = stripInputMetadata(meta);
            if (stripped == null) {
                sendPacketToViewers(packet);
            } else {
                sendPacket(stripped);
                sendPacketToViewers(packet);
            }
            return;
        }

        sendPacket(packet);
        sendPacketToViewers(packet);
    }

    /**
     * Removes input-related metadata indices (0=flags, 6=pose, 8=hand) from the packet.
     * Returns null if nothing remains (packet was only input-related).
     */
    private EntityMetaDataPacket stripInputMetadata(EntityMetaDataPacket meta) {
        Map<Integer, net.minestom.server.entity.Metadata.Entry<?>> stripped = new HashMap<>();
        for (var e : meta.entries().entrySet()) {
            if (!INPUT_INDICES.contains(e.getKey())) {
                stripped.put(e.getKey(), e.getValue());
            }
        }
        return stripped.isEmpty() ? null : new EntityMetaDataPacket(meta.entityId(), stripped);
    }
}
