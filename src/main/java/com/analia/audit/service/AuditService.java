package com.analia.audit.service;

import com.analia.audit.core.AuditCore;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Audit;
import com.analia.common.model.AuditParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;


@ApplicationScoped
public class AuditService  {
    @Inject
    private AuditCore auditCoreLocal;

    public Audit saveAudit(Audit audit) throws AnaliaException {
        return auditCoreLocal.saveAudit(audit);
    }

    public AuditParam saveAuditParam(AuditParam auditParam) throws AnaliaException {
        return auditCoreLocal.saveAuditParam(auditParam);
    }
}
