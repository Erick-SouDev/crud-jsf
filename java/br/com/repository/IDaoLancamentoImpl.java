package br.com.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidades.Lancamento;

@Named
public class IDaoLancamentoImpl implements IDaoLancamento, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager entityManager;
	
	
	@Override
	public List<Lancamento> consultarLimit10(Long codUser) {
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.
				 createQuery(" from Lancamento where usuario.id = " + codUser + " order by id desc ")
				.setMaxResults(10).
				getResultList();
		
		transaction.commit();
		
		return lista;
	}

	@Override
	public List<Lancamento> consultar(Long codUser) {
		List<Lancamento> lista = null;
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lista = entityManager.createQuery(" from Lancamento where usuario.id = " + codUser).getResultList();
		
		transaction.commit();
		
		return lista;
	}

	@Override
	public List<Lancamento> relatorioLancamento(String numNota, Date dataIni, Date dataFim) {
		
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		
		StringBuilder sql = new  StringBuilder();
		
		sql.append(" select l from Lancamento l ");
		
		if (dataIni == null && dataFim == null && numNota != null && !numNota.isEmpty()) {
			sql.append(" where l.numeroNotaFiscal = '").append(numNota.trim()).append("'");
			
		}else if (numNota == null || (numNota != null && numNota.isEmpty()) 
				&& dataIni != null && dataFim == null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			sql.append(" where l.dataIni >= '").append(dataIniString).append("'");
		}
		else if (numNota == null || (numNota != null && numNota.isEmpty()) 
				&& dataIni == null && dataFim != null) {
			
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			sql.append(" where l.dataFin <= '").append(datafimString).append("'");
			
		}else if (numNota == null || (numNota != null && numNota.isEmpty()) 
				&& dataIni != null && dataFim != null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			
			sql.append(" where l.dataIni >= '").append(dataIniString).append("' ");
			sql.append(" and l.dataFin <= '").append(datafimString).append("' ");
		}
		else if (numNota != null && !numNota.isEmpty() 
				&& dataIni != null && dataFim != null) {
			
			String dataIniString = new SimpleDateFormat("yyyy-MM-dd").format(dataIni);
			String datafimString = new SimpleDateFormat("yyyy-MM-dd").format(dataFim);
			
			sql.append(" where l.dataIni >= '").append(dataIniString).append("' ");
			sql.append(" and l.dataFin <= '").append(datafimString).append("' ");
			sql.append(" and l.numeroNotaFiscal = '").append(numNota.trim()).append("'");
		}
		
		
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		lancamentos = entityManager.createQuery(sql.toString()).getResultList();
		
		transaction.commit(); 
		
		return lancamentos;
	}

}
