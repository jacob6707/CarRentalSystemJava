package dev.jacob6707.carrentalsystem.entities;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "auditLogs")
@XmlAccessorType(XmlAccessType.FIELD)
public class AuditLogList {
    @XmlElement(name = "entries")
    private List<AuditLog> logs;

    public AuditLogList() {}

    public AuditLogList(List<AuditLog> logs) {
        this.logs = logs;
    }

    public List<AuditLog> getEntries() {
        return logs;
    }

    public void setEntries(List<AuditLog> logs) {
        this.logs = logs;
    }
}
