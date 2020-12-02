package lekanich;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationListener;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

public class HeadsOrTailsAction extends AnAction {
    private static final double EDGE_CONST = 1.0E-9;
    private static final double HALF_CONST = 0.5 + EDGE_CONST / 10;
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
