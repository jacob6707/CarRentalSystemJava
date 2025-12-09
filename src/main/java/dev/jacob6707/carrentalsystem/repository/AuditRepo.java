package dev.jacob6707.carrentalsystem.repository;

import dev.jacob6707.carrentalsystem.entities.AuditLog;
import dev.jacob6707.carrentalsystem.entities.AuditLogList;
import dev.jacob6707.carrentalsystem.entities.Employee;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuditRepo {
    public static final Path AUDIT_PATH = Paths.get("data/audit.xml");
    private static final Logger log = LoggerFactory.getLogger(AuditRepo.class);

    public static void log(String action, Employee employee) {
        AuditLog auditLog = new AuditLog(System.currentTimeMillis(), action, employee);
        AuditLogList logs = findAll();
        logs.getEntries().add(auditLog);
        try {
            JAXBContext context = JAXBContext.newInstance(AuditLog[].class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(logs, AUDIT_PATH.toFile());
        } catch (JAXBException e) {
            log.warn("Failed to write audit log: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public static AuditLogList findAll() {
        if (!Files.exists(AUDIT_PATH)) return new AuditLogList(new ArrayList<>());
        try {
            JAXBContext context = JAXBContext.newInstance(AuditLog[].class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            AuditLogList logs = (AuditLogList) unmarshaller.unmarshal(AUDIT_PATH.toFile());
            return logs;
        } catch (JAXBException e) {
            log.warn("Failed to read audit log: {}", e.getMessage());
            e.printStackTrace();
            return new AuditLogList(new ArrayList<>());
        }
    }
}
