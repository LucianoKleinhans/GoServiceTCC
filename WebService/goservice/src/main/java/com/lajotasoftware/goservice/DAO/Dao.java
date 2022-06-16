/*
package com.lajotasoftware.goservice.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Dao {

    public static void remove() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Connection connection;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.lajotasoftware_GoService_jar_1.0-SNAPSHOTPU");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    public void salvar(Object o){
        tx.begin();
        em.merge(o);
        tx.commit();
    }
    public void salvar(Object o,Object o2){
        tx.begin();
        em.merge(o);
        em.merge(o2);
        tx.commit();
    }
    public void remove(Object o){
        tx.begin();
        em.remove(o);
        tx.commit();
    }
    public List listaNative(Class c){
        return em.createNativeQuery("select * from "+c.getSimpleName(),c).getResultList();
    }
    public Object findbyID(Class c,int id){
        return em.find(c, id);
    }

*/
/*    public Dao()  {
        try {
            this.connection = DriverManager.
                    getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "123");
            System.out.println("Conexão com sucesso");
        } catch (SQLException ex) {
            System.out.println("Conexão sem sucesso");
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*//*


    public List listaNative(Class c, String a){
        return em.createNativeQuery("select * from "+c.getSimpleName()+" where 1=1 and "+a+" ",c).getResultList();
    }
    public List listaNativeNomeVenda(Class c, String a){
        return em.createNativeQuery("select v.* from venda v join pessoa p on v.pessoa_id = p.id where "+a+" ",c).getResultList();
    }

    public List listaNativeId(Class c, String a){
        return em.createNativeQuery("select * from "+c.getSimpleName()+" where "+a+" ",c).getResultList();
    }

}*/
