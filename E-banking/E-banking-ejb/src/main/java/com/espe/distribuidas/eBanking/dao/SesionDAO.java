/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.espe.distribuidas.eBanking.dao;

import com.espe.distribuidas.eBanking.modelo.Sesion;
import com.espe.distribuidas.eBanking.modelo.Usuario;
import com.espe.distribuidas.eBanking.persistence.PersistenceManager;
import org.bson.types.ObjectId;
import org.mongodb.morphia.dao.BasicDAO;

/**
 *
 * @author Andres Vr
 */
public class SesionDAO extends BasicDAO<Sesion, ObjectId>{
     public SesionDAO() {
        super(Sesion.class,PersistenceManager.getMongoClient(), PersistenceManager.getMorphia(),"ebanking");
    }
}
