/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package condominio.server.modelo.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import condominio.server.modelo.FUNCIONARIOS;
import condominio.server.modelo.OCORRENCIAS;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author Marlon Harnisch
 */
public class FuncionariosJpaController implements Serializable {
	
    public FuncionariosJpaController() {
        
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
    	emf = Persistence.createEntityManagerFactory("CondominioPU");
        return emf.createEntityManager();
    }

    public FUNCIONARIOS create(FUNCIONARIOS FUNCIONARIOS) throws SQLException {
        EntityManager em = null;
        FUNCIONARIOS obj;
        Connection cnt =null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cnt = em.unwrap(Connection.class);
            obj = em.merge(FUNCIONARIOS);
            if(obj.getOcorrencias()!= null && !obj.getOcorrencias().isEmpty()){
            	for (OCORRENCIAS oc : obj.getOcorrencias()) {
            		oc.setFuncionario(obj);
					em.merge(oc);
				}
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
            	cnt.close();
                em.close();
            }
        }
		return obj;
    }

    public void destroy(Long id) throws NonexistentEntityException, SQLException {
        EntityManager em = null;
        Connection cnt =null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cnt = em.unwrap(Connection.class);
            FUNCIONARIOS FUNCIONARIOS;
            try {
                FUNCIONARIOS = em.getReference(FUNCIONARIOS.class, id);
                FUNCIONARIOS.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The FUNCIONARIOS with id " + id + " no longer exists.", enfe);
            }
            em.remove(FUNCIONARIOS);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
            	cnt.close();
                em.close();
            }
        }
    }

    public List<FUNCIONARIOS> findFUNCIONARIOSEntities() {
        return findFUNCIONARIOSEntities(true, -1, -1);
    }

    public List<FUNCIONARIOS> findFUNCIONARIOSEntities(int maxResults, int firstResult) {
        return findFUNCIONARIOSEntities(false, maxResults, firstResult);
    }

    private List<FUNCIONARIOS> findFUNCIONARIOSEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FUNCIONARIOS.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public FUNCIONARIOS findFUNCIONARIOS(Long id) {
    	EntityManager em = null;
        Connection cnt =null;
        try {
        	em = getEntityManager();
            return em.find(FUNCIONARIOS.class, id);
        } finally {
            em.close();
        }
    }

    public int getFUNCIONARIOSCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FUNCIONARIOS> rt = cq.from(FUNCIONARIOS.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<FUNCIONARIOS> searchMorador(String value) {    	
    	EntityManager em = getEntityManager();
        try {
        		em.getTransaction().begin(); 
        		Connection cnt = em.unwrap(Connection.class);
        		
            Query query = em.createNativeQuery("SELECT DISTINCT cm.ID, cm.CPF, cm.EMAIL, cm.IDENTIFICADOR, cm.IMAGE, cm.NOME, cm.OBSERVACAO, cm.TIPO_MORADOR, cm.VAGA FROM FUNCIONARIOS cm left join veiculos vm on vm.IDMORADOR=cm.IDENTIFICADOR WHERE ((vm.PLACA like ?) OR (cm.NOME LIKE ?) OR (cm.CPF LIKE ?) OR (cm.IDENTIFICADOR like ?))", FUNCIONARIOS.class);
            query.setParameter(1, value +"%");
            query.setParameter(2, value +"%");
            query.setParameter(3, value +"%");
            query.setParameter(4, value +"%");
            List<FUNCIONARIOS> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<FUNCIONARIOS>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
		
    }
    
    public List<FUNCIONARIOS> searchMoradoresByID(String value) {    	
    	EntityManager em = getEntityManager();
        try {
        	em.getTransaction().begin(); 
        	Connection cnt = em.unwrap(Connection.class);        		
            Query query = em.createNativeQuery("SELECT * FROM FUNCIONARIOS WHERE IDENTIFICADOR = ? AND TIPO_MORADOR <> (SELECT ID FROM tipo_morador WHERE DESCRICAO = 'Visitante')", FUNCIONARIOS.class);
            query.setParameter(1, value);
            List<FUNCIONARIOS> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<FUNCIONARIOS>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
		
    }
    
    public List<FUNCIONARIOS> searchWhereMorador(String value) {    	
    	EntityManager em = getEntityManager();
        try {
        		em.getTransaction().begin(); 
        		Connection cnt = em.unwrap(Connection.class);
        		
            Query query = em.createNativeQuery("SELECT * FROM FUNCIONARIOS WHERE "+value, FUNCIONARIOS.class);
           // query.setParameter(1, value);
            System.out.println(query.toString());
            List<FUNCIONARIOS> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<FUNCIONARIOS>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
		
    }

	public List<FUNCIONARIOS> searchVisitante(String value) {    	
    	EntityManager em = getEntityManager();
        try {
        		em.getTransaction().begin(); 
        		Connection cnt = em.unwrap(Connection.class);
        		
            Query query = em.createNativeQuery("SELECT DISTINCT cm.ID, cm.CPF, cm.EMAIL, cm.IDENTIFICADOR, cm.IMAGE, cm.NOME, cm.OBSERVACAO, cm.TIPO_MORADOR, cm.VAGA FROM FUNCIONARIOS cm left join veiculos vm on vm.VISITANTE=cm.ID WHERE cm.TIPO_MORADOR = (SELECT ID FROM tipo_morador WHERE DESCRICAO = 'Visitante') AND ((vm.PLACA like ?) OR (cm.NOME LIKE ?) OR (cm.CPF LIKE ?))", FUNCIONARIOS.class);
            query.setParameter(1, value +"%");
            query.setParameter(2, value +"%");
            query.setParameter(3, value +"%");
            List<FUNCIONARIOS> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<FUNCIONARIOS>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }		
    }

	public List<FUNCIONARIOS> searchByIdentification(String identificador) {
		EntityManager em = getEntityManager();
        try {
        		em.getTransaction().begin(); 
        		Connection cnt = em.unwrap(Connection.class);
        		
            Query query = em.createNativeQuery("SELECT * FROM FUNCIONARIOS WHERE IDENTIFICADOR = ?", FUNCIONARIOS.class);
            query.setParameter(1, identificador);
            System.out.println(query.toString());
            List<FUNCIONARIOS> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<FUNCIONARIOS>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
	}
    
}
