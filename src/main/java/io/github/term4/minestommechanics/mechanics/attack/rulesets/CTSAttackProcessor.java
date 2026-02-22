package io.github.term4.minestommechanics.mechanics.attack.rulesets;

import io.github.term4.minestommechanics.Services;
import io.github.term4.minestommechanics.mechanics.attack.AttackSnapshot;

// This class handles combat test snapshot attacks (damage, knockback, cooldown)
public record CTSAttackProcessor(Services services) implements AttackProcessor {
    @Override public void processAttack(AttackSnapshot attack) { }
}
