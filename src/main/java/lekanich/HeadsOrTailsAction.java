package lekanich;

import java.util.Optional;
import com.intellij.notification.NotificationAction;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications.Bus;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import icons.HeadsOrTailsIcons;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

public class HeadsOrTailsAction extends AnAction {
	private static final Logger LOGGER = Logger.getInstance(HeadsOrTailsAction.class);
	private static final double EDGE_CONST = 1.0E-9;
	private static final double HALF_CONST = 0.5 + EDGE_CONST / 10;

	@RequiredArgsConstructor
	public enum Coin {
		HEAD("heads.or.tails.head"),
		TAIL("heads.or.tails.tail"),
		EDGE("heads.or.tails.edge");

		@Getter
		private final String key;
	}

	@Override
	public void actionPerformed(@NotNull final AnActionEvent e) {
		Coin result = flipCoin();

		LOGGER.trace(result.getKey() + " : " + result);
		String title = HeadsOrTailsBundle.message("heads.or.tails.title");
		int index = (int) (HeadsOrTailsBundle.getFunnyIntrosSize() * Math.random());
		String subTitle = HeadsOrTailsBundle.getFunnyIntrosByIndex(index);
		String message = HeadsOrTailsBundle.message(result.getKey());

		notify(e, title, subTitle, message);
	}

	public static Coin flipCoin() {
		double result = Math.random();

		if (result <= EDGE_CONST) {
			return Coin.EDGE;
		} else if (result < HALF_CONST) {
			return Coin.HEAD;
		} else {
			return Coin.TAIL;
		}
	}

	private void notify(@NotNull final AnActionEvent e,
						final String title,
						final String subTitle,
						final String message) {
		Optional.ofNullable(NotificationGroup.findRegisteredGroup(HeadsOrTailsBundle.message("heads.or.tails.title")))
				.map(group -> group.createNotification(message, NotificationType.INFORMATION)
						.setTitle(title)
						.setSubtitle(subTitle)
						.setIcon(HeadsOrTailsIcons.COIN)
						.addAction(NotificationAction.createSimpleExpiring("", () -> {
						})))
				.ifPresent(notification -> Bus.notify(notification, e.getProject()));
	}

	@Override
	public boolean isDumbAware() {
		return true;
	}
}
