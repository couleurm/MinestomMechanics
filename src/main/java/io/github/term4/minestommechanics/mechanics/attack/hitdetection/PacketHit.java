package io.github.term4.minestommechanics.mechanics.attack.hitdetection;

import io.github.term4.minestommechanics.mechanics.Cause;
import io.github.term4.minestommechanics.mechanics.attack.AttackConfig;
import io.github.term4.minestommechanics.mechanics.attack.AttackSnapshot;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.entity.EntityAttackEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/** This is the hit detection logic for attack packet based hits */
public final class PacketHit {

    public static void install(EventNode<@NotNull Event> node, @Nullable AttackConfig config, Consumer<AttackSnapshot> onHit) {

        node.addListener(EntityAttackEvent.class, e -> {
            if (!(e.getEntity() instanceof Player attacker)) return; // in reality this will always be a player, still good practice though
            Entity target = e.getTarget();

            AttackSnapshot snap = new AttackSnapshot(
                    attacker,
                    target,
                    Cause.ATTACK_PACKET,
                    config
            ); onHit.accept(snap);
        });

    }

}
