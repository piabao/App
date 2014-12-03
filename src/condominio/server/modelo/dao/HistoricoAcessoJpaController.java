/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.server.modelo.dao;

import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.HISTORICO_ACESSO;
import condominio.server.modelo.TELEFONES;
import condominio.server.modelo.VEICULOS;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Marlon Harnisch
 */
public class HistoricoAcessoJpaController implements Serializable {

    public HistoricoAcessoJpaController() {
         emf = Persistence.createEntityManagerFactory("CondominioPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HISTORICO_ACESSO HISTORICO) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(HISTORICO);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

	public List<HISTORICO_ACESSO> findHistoricoVisitante(Long id) {
		EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Connection cnt = em.unwrap(Connection.class);
            Query query = em.createNativeQuery("SELECT * FROM historico_acesso WHERE IDVISITANTE = ?", HISTORICO_ACESSO.class);
            query.setParameter(1, id);
            List<HISTORICO_ACESSO> resultList = query.getResultList();
            cnt.close();
            return resultList;
        } catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
            em.close();
        }
	}
	
	public HISTORICO_ACESSO findHistoricoAcesso(Long id) {
		EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Connection cnt = em.unwrap(Connection.class);
            Query query = em.createNativeQuery("SELECT * FROM historico_acesso WHERE ID = ?", HISTORICO_ACESSO.class);
            query.setParameter(1, id);
            HISTORICO_ACESSO resultList = (HISTORICO_ACESSO) query.getSingleResult();
            cnt.close();
            return resultList;
        } catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
            em.close();
        }
	}

	public List findStillInside() {
		EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            Connection cnt = em.unwrap(Connection.class);
            Query query = em.createNativeQuery("SELECT CASA, DATAENTRADA, ha.VAGA, cm.NOME, "
            		+ "v.PLACA , ha.ID FROM historico_acesso ha join cadastro_morador cm on cm.ID=ha.IDVISITANTE "
            		+ "join veiculos v on v.ID=ha.IDVEICULO WHERE DATASAIDA IS NULL;");
            List resultList = query.getResultList();
            cnt.close();
            return resultList;
        } catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
            em.close();
        }
	}    
}
