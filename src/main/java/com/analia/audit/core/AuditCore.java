package com.analia.audit.core;

import com.analia.audit.persistence.AuditFacade;
import com.analia.audit.persistence.AuditParamFacade;
import com.analia.common.exception.AnaliaException;
import com.analia.common.model.Audit;
import com.analia.common.model.AuditParam;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class AuditCore {

    @Inject
    AuditFacade auditFacade;

    @Inject
    AuditParamFacade auditParamFacade;

    @Transactional
    public Audit saveAudit(Audit audit) throws AnaliaException {
        auditFacade.save(audit);
        auditFacade.flush();
        return audit;
    }

    public AuditParam saveAuditParam(AuditParam auditParam) throws AnaliaException {
        auditParamFacade.save(auditParam);
        auditParamFacade.flush();
        return auditParam;
    }
}
