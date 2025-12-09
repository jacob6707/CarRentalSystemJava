package dev.jacob6707.carrentalsystem.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single audit log entry with a timestamp and action.
 */
@XmlRootElement(name = "log")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuditLog {
    @XmlElement(name = "timestamp")
    private long timestamp;

    @XmlElement(name = "action")
    private String action;

    @XmlElement(name = "employeeId")
    private Long employeeId;

    public AuditLog() {}

    public AuditLog(long timestamp, String action, Employee employee) {
        this.timestamp = timestamp;
        this.action = action;
        this.employeeId = employee.getId();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long id) {
        this.employeeId = id;
    }

    @Override
    public String toString() {
        String formattedDate = DateTimeFormatter.ISO_DATE.format(
                Instant.ofEpochMilli(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
        );

        return "[" + employeeId + "@" + formattedDate + "] " + action;
    }
}