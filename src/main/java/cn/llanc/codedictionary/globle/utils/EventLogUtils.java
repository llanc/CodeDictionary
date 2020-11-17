package cn.llanc.codedictionary.globle.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.ui.MessageType;

/**
 * EventLog日志
 *
 * @author liulancong
 * @date 2020/11/12
 **/
public class EventLogUtils {

    private static final String NOTIFICATION_GROUP_ID = "Plugin Code Dictionary";
    private static final NotificationGroup notificationGroup = new NotificationGroup(NOTIFICATION_GROUP_ID, NotificationDisplayType.BALLOON);

    private EventLogUtils() {
    }

    public static void info(String msg) {
        Notification notification = notificationGroup.createNotification(msg, MessageType.INFO);
        Notifications.Bus.notify(notification);
    }

    public static void error(String msg) {
        Notification notification = notificationGroup.createNotification(msg, MessageType.ERROR);
        Notifications.Bus.notify(notification);
    }

    public static void warning(String msg) {
        Notification notification = notificationGroup.createNotification(msg, MessageType.WARNING);
        Notifications.Bus.notify(notification);
    }

    public static void exception(String msg,Exception e) {
        Notification notification = notificationGroup.createNotification(String.format("%s%n%s",msg,e.getMessage()), MessageType.ERROR);
        Notifications.Bus.notify(notification);
    }
}
