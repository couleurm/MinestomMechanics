package io.github.term4.minestommechanics.mechanics.attack;

import io.github.term4.minestommechanics.mechanics.Cause;
import net.minestom.server.entity.Entity;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @param attacker the entity initiating the attack
 * @param target the target entity of the attack
 * @param cause the cause of the attack (what kind of attack was this?)
 *
 */
public record AttackSnapshot (Entity attacker, @Nullable Entity target, Cause cause, @Nullable AttackConfig config) {

    public AttackSnapshot withAttacker(Entity e) { return new AttackSnapshot(e, target, cause, config); }
    public AttackSnapshot withTarget(Entity e) { return new AttackSnapshot(attacker, e, cause, config); }
    public AttackSnapshot withConfig(AttackConfig c) { return new AttackSnapshot(attacker, target, cause, c); }

    public Builder toBuilder() { return new Builder(attacker, target, cause, config); }

    public static final class Builder {
        private Entity attacker;
        private @Nullable Entity target;
        private final Cause cause;
        private @Nullable AttackConfig config;

        Builder(Entity a, @Nullable Entity t, Cause c, @Nullable AttackConfig cfg) { attacker = a; target = t; cause = c; config = cfg; }

        public Builder attacker(Entity e) { attacker = e; return this; }
        public Builder target(Entity e) { target = e; return this; }
        public Builder config(AttackConfig cfg) { config = cfg; return this; }

        public AttackSnapshot build() {
            return new AttackSnapshot(attacker, target, cause, config);
        }
    }
}