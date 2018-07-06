package org.openmrs.module.mergepatientdata.api.dao.Impl;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.mergepatientdata.api.dao.MergePatientDataAuditDao;
import org.openmrs.module.mergepatientdata.api.model.audit.MergePatientDataAuditMessage;
import org.openmrs.module.mergepatientdata.api.model.audit.PaginatedAuditMessage;
import org.springframework.beans.factory.annotation.Autowired;

public class MergePatientDataAuditDaoImpl implements MergePatientDataAuditDao {
	
	@Autowired
	private DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public MergePatientDataAuditMessage saveMessage(MergePatientDataAuditMessage message) {
		getSession().saveOrUpdate(message);
		return message;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PaginatedAuditMessage getAuditMessages(int page, int pageSize) {
		Long itemCount = (Long) getSession().createQuery("select count(*) from MergePatientDataAuditMessage").uniqueResult();
		
		Criteria criteria = getSession().createCriteria(MergePatientDataAuditMessage.class);
		criteria.setFirstResult((page - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		List<MergePatientDataAuditMessage> list = Collections.checkedList(criteria.list(),
		    MergePatientDataAuditMessage.class);
		return new PaginatedAuditMessage(itemCount, list, page, pageSize);
	}
	
	@Override
	public MergePatientDataAuditMessage getMessageByUuid(String uuid) {
		return (MergePatientDataAuditMessage) getSession().createCriteria(MergePatientDataAuditMessage.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
}
