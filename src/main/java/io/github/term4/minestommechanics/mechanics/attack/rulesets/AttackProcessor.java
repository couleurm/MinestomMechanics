package io.github.term4.minestommechanics.mechanics.attack.rulesets;

import io.github.term4.minestommechanics.Services;
import io.github.term4.minestommechanics.mechanics.attack.AttackSnapshot;

/**
 * Responsible for processing attacks (or attempted attacks) from a player
 */
public interface AttackProcessor {

    // This interface simply detects when a player hits another entity (Player attacker, Entity victim/target)
    //  For now all this needs to do is handle a player sending an attack packet directed at a target.
    //  Later we will swap from pure attack packets to a more general "attack" which could also be swing packets + raycast

    /**
     * Called when the server receives an attack packet (or an emulated attack).
     *
     * @param attack the snapshot of all relevant information at the time of the attack
     */
    void processAttack(AttackSnapshot attack); // target is nullable for swing + raytraced emulated attacks

    // Ruleset
    @FunctionalInterface
    interface Ruleset {
        AttackProcessor create(Services services);
    }

    // Presets
    static Ruleset legacy() { return LegacyAttackProcessor::new; }
    static Ruleset modern() { return ModernAttackProcessor::new; }
    static Ruleset cts() { return CTSAttackProcessor::new; }
}
