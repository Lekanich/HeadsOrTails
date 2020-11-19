package lekanich;

import java.util.List;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

public class HeadsOrTailsAction extends AnAction {
	public static final long EDGE_PROBABILITY = 1_000_000_000;
	public static final Logger LOGGER = Logger.getInstance(HeadsOrTailsAction.class);

	@RequiredArgsConstructor
	public enum Coin {
		HEAD("heads.or.tails.head"),
		TAIL("heads.or.tails.tail"),
		EDGE("heads.or.tails.edge");

		@Getter
		private final String key;
	}

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		Coin result = flipCoin();

		LOGGER.trace(result.getKey() + " : " + result);
		String title = HeadsOrTailsBundle.message("heads.or.tails.title");
		String subTitle = augmentIntro();
		String message = HeadsOrTailsBundle.message(result.getKey());

        notify(e, title, subTitle, message);
	}

    private String augmentIntro() {
        List<String> intros = HeadsOrTailsBundle.funnyIntros();
        if (intros.isEmpty()) {
            return "";
        }

        return intros.get((int) (intros.size() * Math.random()));
    }

    public static Coin flipCoin() {
		long result = (long) (Math.random() * EDGE_PROBABILITY);
		if (result == 0) {
			return Coin.EDGE;
		} else if (result < EDGE_PROBABILITY / 2) {
			return Coin.HEAD;
		} else {
			return Coin.TAIL;
		}
	}

	private void notify(@NotNull AnActionEvent e, String title, String subTitle, String message) {
		NotificationGroup group = new NotificationGroup(message, NotificationDisplayType.BALLOON, true);
		Notification notification = group.createNotification(
				title,
				subTitle,
				message,
				NotificationType.INFORMATION,
				new NotificationListener.UrlOpeningListener(false)
		);

		Notifications.Bus.notify(notification, e.getProject());
	}

	@Override
	public boolean isDumbAware() {
		return true;
	}
}
