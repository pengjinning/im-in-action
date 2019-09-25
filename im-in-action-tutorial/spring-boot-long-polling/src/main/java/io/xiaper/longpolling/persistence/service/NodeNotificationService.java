package io.xiaper.longpolling.persistence.service;

import io.xiaper.longpolling.persistence.model.NodeNotification;

import java.util.List;


public interface NodeNotificationService {

    public List<NodeNotification> getNotifications(final String nodeId);

    public List<NodeNotification> getAndRemoveNotifications(final String nodeId);

    public boolean containsNotifications(final String nodeId);

    public NodeNotification save(final NodeNotification node);

    public void flush();

}
