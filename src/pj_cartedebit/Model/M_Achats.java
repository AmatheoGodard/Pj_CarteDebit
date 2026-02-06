/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_cartedebit.Model;

import java.time.LocalDate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import pj_cartedebit.Cl_Connection;
import pj_cartedebit.Db_mariadb;

/**
 *
 * @author amatheo
 */
public class M_Achats {
    
    private Db_mariadb db;
    private int id;
    private LocalDate dateAchat;
    private float montant;
    private String commentaire;

    public M_Achats(Db_mariadb db, int id, LocalDate dateAchat, float montant, String commentaire) {
        this.db = db;
        this.id = id;
        this.dateAchat = dateAchat;
        this.montant = montant;
        this.commentaire = commentaire;
    }

    public M_Achats(Db_mariadb db, LocalDate dateAchat, float montant, String commentaire) throws SQLException {
        this.db = db;
        this.dateAchat = dateAchat;
        this.montant = montant;
        this.commentaire = commentaire;
        
        String sql = "INSERT INTO mcd_achats (date_achat, montant, commentaire)"
                        + " VALUES ('" + dateAchat + "', '" + montant + "', '" + commentaire + "');";
        db.sqlExec(sql);
        ResultSet res;
        res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        res.close();
    }

    public M_Achats(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        this.id = id;
        
        String sql = "SELECT * FROM mcd_achats WHERE id = '" + id + "';";
        ResultSet res;
        res = db.sqlSelect(sql);
        res.first();
        this.dateAchat = res.getObject("date_achat", LocalDate.class);
        this.montant = res.getFloat("montant");
        this.commentaire = res.getString("commentaire");
        res.close();
    }

    public int getId() {
        return id;
    }

    public Db_mariadb getDb() {
        return db;
    }

    public void setDb(Db_mariadb db) {
        this.db = db;
    }

    public LocalDate getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDate dateAchat) {
        this.dateAchat = dateAchat;
    }

    public float getMontant() {
        return montant;
    }

    public void setMontant(float montant) {
        this.montant = montant;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    public void update() throws SQLException {
        String sql = "UPDATE mcd_achats SET "
                + "date_achat='" + dateAchat + "', "
                + "montant='" + montant + "', "
                + "commentaire='" + commentaire + "'"
                + " WHERE id=" + id + ";";
        db.sqlExec(sql);
    }
    
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_achats WHERE id=" + id + ";";
        db.sqlExec(sql);
    }
    
    public static LinkedHashMap<Integer, M_Achats> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }
    
    public static LinkedHashMap<Integer, M_Achats> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {
        LinkedHashMap<Integer, M_Achats> lesAchats;
        lesAchats = new LinkedHashMap<>();
        M_Achats unAchat;
        
        int cle;
        float montant;
        LocalDate dateAchat;
        String commentaire;
        
        String sql = "SELECT * FROM mcd_achats WHERE " + clauseWhere + ";";
        ResultSet res;
        res = db.sqlSelect(sql);
        
        while (res.next()) {
            cle = res.getInt("id");
            dateAchat = res.getObject("date_achat", LocalDate.class);
            montant = res.getFloat("montant");
            commentaire = res.getString("commentaire");
            
            unAchat = new M_Achats(db, cle, dateAchat, montant, commentaire);
            lesAchats.put(cle, unAchat);
        }
        
        res.close();
        
        return lesAchats;
    }

    @Override
    public String toString() {
        return "M_Achat{" + "db=" + db + ", id=" + id + ", dateAchat=" + dateAchat + ", montant=" + montant + ", commentaire=" + commentaire + '}';
    }
    
    public static void main (String[] args) throws Exception {
        
        /**
         *  Jeux de test pour mcd_achats
         */
        
        Db_mariadb mabase;
        mabase = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
        
        M_Achats unAchat;
        
        /* Test du 1er constructeur
        unAchat = new M_Achat(mabase, 1, LocalDate.now(), 10, "Test du 1er constructeur");
        System.out.println(unAchat.toString());
        */
        
        /* Test du 2eme constructeur 
        unAchat = new M_Achat(mabase, LocalDate.now(), 20, "Test du 2eme constructeur");
        System.out.println(unAchat.toString());
        */
        
        /* Test du 3eme constructeur 
        unAchat = new M_Achat(mabase, 1);
        System.out.println(unAchat.toString());
        */
        
        /* Test des setteurs et du update 
        unAchat.setDateAchat(LocalDate.now());
        unAchat.setMontant(50);
        unAchat.setCommentaire("Test du update");
        System.out.println(unAchat.toString());
        unAchat.update();
        */
        
        /* Test du delete 
        unAchat.delete();
        */
        
        //Test du record set +affichage avec sans et avec condition Where
        //Déclarer un objet dictionnaire
        LinkedHashMap<Integer, M_Achats> lesAchats;

        //Appeler la méthode getRecods
        lesAchats = M_Achats.getRecords(mabase);

        //Afficher le contenu du dictionnaire
        for (Integer uneCle : lesAchats.keySet()) {
            unAchat = lesAchats.get(uneCle);
            System.out.println(unAchat.toString());
        }
    }
}
