package io.github.term4.minestommechanics.util;

import net.minestom.server.coordinate.Pos;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntityTeleportEvent;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.tag.Tag;
import org.jetbrains.annotations.NotNull;

// TODO: Update tick based values to use tps scaling (util class)
/** Position delta velocity estimation for players. Minestom's getVelocity does not work for clients. */
public final class VelocityEstimator {

    private record Frame(Vec delta, Pos pos, long tick) {}
    private static final Tag<Frame> FRAME = Tag.Transient("mm:velocity-frame");
    private static final Tag<Boolean> RESET = Tag.Transient("mm:velocity-reset");
    private static final long GAP_TICKS = 6; // Ticks after which client assumed to be lag spiking (ignore first delta due to perceived teleport)
    private static final long DECAY_TICKS = 3; // Ticks after which client is assumed stationary (velocity = 0)

    private VelocityEstimator() {}

    /** Install listeners for velocity estimation. */
    public static void install(EventNode<@NotNull Event> node) {

        // Normal movement
        node.addListener(PlayerMoveEvent.class, e -> {
            onMove(e.getPlayer(), e.getNewPosition(), e.getPlayer().getAliveTicks());
        });

        // Teleport handling
        node.addListener(EntityTeleportEvent.class, e -> {
            if (e.getEntity() instanceof Player p) {
                reset(p, e.getNewPosition());
            }
        });
    }

    public static void onMove(Player p, Pos pos, long tick) {
        Frame prev = p.getTag(FRAME);
        Vec delta;

        if (Boolean.TRUE.equals(p.getTag(RESET))) {
            p.removeTag(RESET);
            delta = Vec.ZERO;
        } else if (prev != null) {
            long dt = tick - prev.tick();

            if (dt > GAP_TICKS) {
                delta = Vec.ZERO;
            } else {
                Pos last = prev.pos();
                delta = new Vec(
                        pos.x() - last.x(),
                        pos.y() - last.y(),
                        pos.z() - last.z()
                );
            }
        } else { delta = Vec.ZERO; }

        p.setTag(FRAME, new Frame(delta, pos, tick));
    }

    private static void reset(Player p, Pos newPos) {
        p.setTag(RESET, true);
        p.setTag(FRAME, new Frame(Vec.ZERO, newPos, p.getAliveTicks()));
    }

    private static Vec get(Player p) {
        Frame f = p.getTag(FRAME);
        if (f == null) return Vec.ZERO;

        long now = p.getAliveTicks();
        return (now - f.tick() > DECAY_TICKS) ? Vec.ZERO : f.delta();
    }

    public static Vec getVelocity(Entity entity) {
        return entity instanceof Player p ? get(p) : entity.getVelocity();
    }
}
