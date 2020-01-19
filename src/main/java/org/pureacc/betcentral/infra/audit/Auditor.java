package org.pureacc.betcentral.infra.audit;

public interface Auditor {
	void audit(AuditSuccess success);

	void audit(AuditFailure failure);
}
