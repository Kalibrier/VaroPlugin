package de.cuuky.varo.threads.daily.dailycheck.checker;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import de.cuuky.varo.Main;
import de.cuuky.varo.alert.Alert;
import de.cuuky.varo.alert.AlertType;
import de.cuuky.varo.configuration.config.ConfigEntry;
import de.cuuky.varo.configuration.messages.ConfigMessages;
import de.cuuky.varo.entity.player.VaroPlayer;
import de.cuuky.varo.entity.player.stats.stat.Strike;
import de.cuuky.varo.logger.logger.EventLogger.LogType;
import de.cuuky.varo.threads.daily.dailycheck.Checker;

public class BloodLustCheck extends Checker {

	@Override
	public void check() {
		int days = ConfigEntry.BLOODLUST_DAYS.getValueAsInt();
		boolean strike = ConfigEntry.STRIKE_ON_BLOODLUST.getValueAsBoolean();
		if(!ConfigEntry.BLOODLUST_DAYS.isIntActivated())
			return;

		for(VaroPlayer player : VaroPlayer.getAlivePlayer()) {
			Date lastContact = player.getStats().getLastEnemyContact();

			if(lastContact.before(DateUtils.addDays(new Date(), -days))) {
				new Alert(AlertType.BLOODLUST, player.getName() + " hat nun " + days + " Tage nicht gekaempft!");
				if(strike) {
					player.getStats().addStrike(new Strike("Es wurde fuer zu viele Tage nicht gekaempft.", player, "CONSOLE"));
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NO_BLOODLUST_STRIKE.getValue(player).replace("%days%", String.valueOf(days)));
				} else
					Main.getDataManager().getVaroLoggerManager().getEventLogger().println(LogType.ALERT, ConfigMessages.ALERT_NO_BLOODLUST.getValue(player).replace("%days%", String.valueOf(days)));
			}
		}
	}
}