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

import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author Marlon Harnisch
 */
public class CadastroMoradorJpaController implements Serializable {
	
    public CadastroMoradorJpaController() {
        
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
    	emf = Persistence.createEntityManagerFactory("CondominioPU");
        return emf.createEntityManager();
    }

    public CADASTRO_MORADOR create(CADASTRO_MORADOR CADASTRO_MORADOR) throws SQLException {
        EntityManager em = null;
        CADASTRO_MORADOR obj;
        Connection cnt =null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cnt = em.unwrap(Connection.class);
            obj = em.merge(CADASTRO_MORADOR);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
            	cnt.close();
                em.close();
            }
        }
		return obj;
    }

    public void edit(CADASTRO_MORADOR CADASTRO_MORADOR) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        Connection cnt =null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cnt = em.unwrap(Connection.class);
            CADASTRO_MORADOR = em.merge(CADASTRO_MORADOR);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = CADASTRO_MORADOR.getId();
                if (findCADASTRO_MORADOR(id) == null) {
                    throw new NonexistentEntityException("The cADASTRO_MORADOR with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                cnt.close();
            	em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, SQLException {
        EntityManager em = null;
        Connection cnt =null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cnt = em.unwrap(Connection.class);
            CADASTRO_MORADOR CADASTRO_MORADOR;
            try {
                CADASTRO_MORADOR = em.getReference(CADASTRO_MORADOR.class, id);
                CADASTRO_MORADOR.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The CADASTRO_MORADOR with id " + id + " no longer exists.", enfe);
            }
            em.remove(CADASTRO_MORADOR);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
            	cnt.close();
                em.close();
            }
        }
    }

    public List<CADASTRO_MORADOR> findCADASTRO_MORADOREntities() {
        return findCADASTRO_MORADOREntities(true, -1, -1);
    }

    public List<CADASTRO_MORADOR> findCADASTRO_MORADOREntities(int maxResults, int firstResult) {
        return findCADASTRO_MORADOREntities(false, maxResults, firstResult);
    }

    private List<CADASTRO_MORADOR> findCADASTRO_MORADOREntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CADASTRO_MORADOR.class));
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

    public CADASTRO_MORADOR findCADASTRO_MORADOR(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CADASTRO_MORADOR.class, id);
        } finally {
            em.close();
        }
    }

    public int getCADASTRO_MORADORCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CADASTRO_MORADOR> rt = cq.from(CADASTRO_MORADOR.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<CADASTRO_MORADOR> searchMorador(String value) {    	
    	EntityManager em = getEntityManager();
        try {
        		em.getTransaction().begin(); 
        		Connection cnt = em.unwrap(Connection.class);
        		
            Query query = em.createNativeQuery("SELECT DISTINCT cm.ID, cm.CPF, cm.EMAIL, cm.IDENTIFICADOR, cm.IMAGE, cm.NOME, cm.OBSERVACAO, cm.TIPO_MORADOR, cm.VAGA FROM cadastro_morador cm left join veiculos vm on vm.IDMORADOR=cm.IDENTIFICADOR WHERE ((vm.PLACA like ?) OR (cm.NOME LIKE ?) OR (cm.CPF LIKE ?) OR (cm.IDENTIFICADOR like ?)) LIMIT 10", CADASTRO_MORADOR.class);
            query.setParameter(1, value +"%");
            query.setParameter(2, value +"%");
            query.setParameter(3, value +"%");
            query.setParameter(4, value +"%");
            List<CADASTRO_MORADOR> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<CADASTRO_MORADOR>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
		
    }
    
    public List<CADASTRO_MORADOR> searchMoradoresByID(String value) {    	
    	EntityManager em = getEntityManager();
        try {
        	em.getTransaction().begin(); 
        	Connection cnt = em.unwrap(Connection.class);        		
            Query query = em.createNativeQuery("SELECT * FROM cadastro_morador WHERE IDENTIFICADOR = ? AND TIPO_MORADOR <> (SELECT ID FROM tipo_morador WHERE DESCRICAO = 'Visitante')", CADASTRO_MORADOR.class);
            query.setParameter(1, value);
            List<CADASTRO_MORADOR> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<CADASTRO_MORADOR>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
		
    }
    
    public List<CADASTRO_MORADOR> searchWhereMorador(String value) {    	
    	EntityManager em = getEntityManager();
        try {
        		em.getTransaction().begin(); 
        		Connection cnt = em.unwrap(Connection.class);
        		
            Query query = em.createNativeQuery("SELECT * FROM cadastro_morador WHERE "+value, CADASTRO_MORADOR.class);
           // query.setParameter(1, value);
            System.out.println(query.toString());
            List<CADASTRO_MORADOR> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<CADASTRO_MORADOR>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
		
    }

	public List<CADASTRO_MORADOR> searchVisitante(String value) {    	
    	EntityManager em = getEntityManager();
        try {
        		em.getTransaction().begin(); 
        		Connection cnt = em.unwrap(Connection.class);
        		
            Query query = em.createNativeQuery("SELECT DISTINCT cm.ID, cm.CPF, cm.EMAIL, cm.IDENTIFICADOR, cm.IMAGE, cm.NOME, cm.OBSERVACAO, cm.TIPO_MORADOR, cm.VAGA FROM cadastro_morador cm left join veiculos vm on vm.VISITANTE=cm.ID WHERE cm.TIPO_MORADOR = (SELECT ID FROM tipo_morador WHERE DESCRICAO = 'Visitante') AND ((vm.PLACA like ?) OR (cm.NOME LIKE ?) OR (cm.CPF LIKE ?))", CADASTRO_MORADOR.class);
            query.setParameter(1, value +"%");
            query.setParameter(2, value +"%");
            query.setParameter(3, value +"%");
            List<CADASTRO_MORADOR> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<CADASTRO_MORADOR>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
		
    }

	public List<CADASTRO_MORADOR> searchByIdentification(String identificador) {
		EntityManager em = getEntityManager();
        try {
        		em.getTransaction().begin(); 
        		Connection cnt = em.unwrap(Connection.class);
        		
            Query query = em.createNativeQuery("SELECT * FROM cadastro_morador WHERE IDENTIFICADOR = ?", CADASTRO_MORADOR.class);
            query.setParameter(1, identificador);
            System.out.println(query.toString());
            List<CADASTRO_MORADOR> mor =query.getResultList();
            cnt.close();
            return mor;          
        } catch (SQLException e) {
        	return new ArrayList<CADASTRO_MORADOR>();
        	//e.printStackTrace();
		} finally {
        	em.close();
        	emf.close();
        }
	}
    
}
