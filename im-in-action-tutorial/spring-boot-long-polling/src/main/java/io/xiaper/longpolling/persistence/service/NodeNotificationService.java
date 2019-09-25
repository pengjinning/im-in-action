package io.xiaper.longpolling.persistence.service;

import io.xiaper.longpolling.persistence.model.NodeNotification;

import java.util.List;


public interface NodeNotificationService {

     List<NodeNotification> getNotifications(final String nodeId);

     List<NodeNotification> getAndRemoveNotifications(final String nodeId);

     boolean containsNotifications(final String nodeId);

     NodeNotification save(final NodeNotification node);

     void flush();

}
