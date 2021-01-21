package cn.llanc.codedictionary.globle.utils;

import com.intellij.notification.*;
import com.intellij.notification.impl.NotificationGroupEP;
import com.intellij.notification.impl.NotificationGroupManagerImpl;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import org.jetbrains.annotations.Nullable;

/**
 * EventLog日志
 *
 * @author liulancong
 * @date 2020/11/12
 **/
public class EventLogUtils {

    private static final String NOTIFICATION_GROUP_ID = "Plugin Code Dictionary Notification Group";

    private static final NotificationGroup NOTIFICATION_GROUP = new NotificationGroup(NOTIFICATION_GROUP_ID, NotificationDisplayType.BALLOON);

    private EventLogUtils() {
    }

    public static void info(String msg) {
        Notification notification = NOTIFICATION_GROUP.createNotification(msg, MessageType.INFO);
        Notifications.Bus.notify(notification);
    }

    public static void error(String msg) {
        Notification notification = NOTIFICATION_GROUP.createNotification(msg, MessageType.ERROR);
        Notifications.Bus.notify(notification);
    }

    public static void warning(String msg) {
        Notification notification = NOTIFICATION_GROUP.createNotification(msg, MessageType.WARNING);
        Notifications.Bus.notify(notification);
    }

    public static void exception(String msg, Exception e) {
        Notification notification = NOTIFICATION_GROUP.createNotification(String.format("%s%n%s", msg, e.getMessage()), MessageType.ERROR);
        Notifications.Bus.notify(notification);
    }
}
