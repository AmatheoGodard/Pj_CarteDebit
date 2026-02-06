/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_cartedebit.Model;

import pj_cartedebit.Db_mariadb;

/**
 *
 * @author amatheo
 */
public class M_Categories {
    
    private Db_mariadb db;
    private int id;
    private String code, nom, commentaire;

    public M_Categories(Db_mariadb db, int id, String code, String nom, String commentaire) {
        this.db = db;
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.commentaire = commentaire;
    }

    public M_Categories(Db_mariadb db, String code, String nom, String commentaire) {
        this.db = db;
        this.code = code;
        this.nom = nom;
        this.commentaire = commentaire;
    }
    
   
}
