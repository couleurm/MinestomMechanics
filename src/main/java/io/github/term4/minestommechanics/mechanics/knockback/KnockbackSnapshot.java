package io.github.term4.minestommechanics.mechanics.knockback;

import io.github.term4.minestommechanics.mechanics.Cause;
import net.minestom.server.coordinate.Point;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.Entity;
import org.jetbrains.annotations.Nullable;

public record KnockbackSnapshot(Entity target, Cause cause, @Nullable Entity source,
                                @Nullable Point origin, @Nullable Vec direction, @Nullable KnockbackConfig config) {

    public KnockbackSnapshot withTarget(Entity e) { return new KnockbackSnapshot(e, cause, source, origin, direction, config); }
    public KnockbackSnapshot withCause(Cause c)  { return new KnockbackSnapshot(target, c, source, origin, direction, config); }
    public KnockbackSnapshot withSource(Entity e) { return new KnockbackSnapshot(target, cause, e, origin, direction, config); }
    public KnockbackSnapshot withOrigin(Point p) { return new KnockbackSnapshot(target, cause, source, p, direction, config); }
    public KnockbackSnapshot withDirection(Vec v) { return new KnockbackSnapshot(target, cause, source, origin, v, config); }
    public KnockbackSnapshot withConfig(KnockbackConfig c) { return new KnockbackSnapshot(target, cause, source, origin, direction, c); }

    public Builder toBuilder() { return new Builder(target, cause, source, origin, direction, config); }

    public static final class Builder {
        private Entity target;
        private Cause cause;
        private Entity source;
        private Point origin;
        private Vec direction;
        private KnockbackConfig config;

        Builder(Entity t, Cause c, Entity s, Point o, Vec d, KnockbackConfig cfg) {
            target = t; cause = c; source = s; origin = o; direction = d; config = cfg;
        }

        public Builder target(Entity e) { this.target = e; return this; }
        public Builder cause(Cause c) { this.cause = c; return this; }
        public Builder source(Entity e) { this.source = e; return this; }
        public Builder origin(Point p) { this.origin = p; return this; }
        public Builder direction(Vec v) { this.direction = v; return this; }
        public Builder config(KnockbackConfig c) { this.config = c; return this; }

        public KnockbackSnapshot build() {
            return new KnockbackSnapshot(target, cause, source, origin, direction, config);
        }
    }
}
