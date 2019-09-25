package io.xiaper.longpolling.persistence.dao;

import io.xiaper.longpolling.persistence.model.NodeNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NodeNotificationDao extends JpaRepository<NodeNotification, Long> {

    @Query("SELECT n FROM NodeNotification n WHERE n.nodeId = :nodeId AND n.timestamp > :timestamp")
    List<NodeNotification> getNotifications(@Param("nodeId") final String nodeId, @Param("timestamp") final Date timestamp);

}
