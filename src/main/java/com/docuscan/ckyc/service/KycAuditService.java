package com.docuscan.ckyc.service;

import com.docuscan.ckyc.model.KycAudit;
import com.docuscan.ckyc.repository.KycAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KycAuditService {

    private final KycAuditRepository kycAuditRepository;

    public KycAudit createKycAudit(KycAudit audit) {
        return kycAuditRepository.save(audit);
    }
}
