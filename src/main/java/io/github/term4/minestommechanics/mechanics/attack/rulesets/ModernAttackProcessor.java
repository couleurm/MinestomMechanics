package io.github.term4.minestommechanics.mechanics.attack.rulesets;

import io.github.term4.minestommechanics.Services;
import io.github.term4.minestommechanics.mechanics.attack.AttackSnapshot;

// This class handles modern (post-1.9) combat attacks (damage, knockback, cooldowns, modifiers)
public record ModernAttackProcessor(Services services) implements AttackProcessor {
    @Override public void processAttack(AttackSnapshot attack) { }
}
