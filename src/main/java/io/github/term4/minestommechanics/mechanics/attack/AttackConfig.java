package io.github.term4.minestommechanics.mechanics.attack;

import io.github.term4.minestommechanics.mechanics.attack.rulesets.AttackProcessor;

public final class AttackConfig {

    public boolean enabled = true;
    public boolean packetHits = true;

    public AttackProcessor.Ruleset ruleset = AttackProcessor.legacy();

    public double reach = 3.0;
    public long sprintBuffer = 8;

    public AttackConfig() {}
}
