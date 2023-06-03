package de.cuuky.varo.api.player.strike;

import de.cuuky.varo.api.player.VaroPlayerEvent;
import de.cuuky.varo.entity.player.stats.stat.Strike;

public class PlayerStrikeRemoveEvent extends VaroPlayerEvent {

	private final Strike strike;

	public PlayerStrikeRemoveEvent(Strike strike) {
		super(strike.getStriked());

		this.strike = strike;
	}

	public Strike getStrike() {
		return this.strike;
	}
}
