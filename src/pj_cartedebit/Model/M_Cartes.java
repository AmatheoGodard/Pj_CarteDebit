/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_cartedebit.Model;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import pj_cartedebit.Cl_Connection;
import pj_cartedebit.Db_mariadb;


/**
 *
 * @author amatheo
 */
public class M_Cartes {
    
    private Db_mariadb db;
    private int id, idCommande;
    private float soldeInitial, soldeActuel;
    private String numero, codeSecret, visage, commentaire;

    public M_Cartes(Db_mariadb db, int id, int idCommande, float soldeInitial, float soldeActuel, String numero, String codeSecret, String visage, String commentaire) {
        this.db = db;
        this.id = id;
        this.idCommande = idCommande;
        this.soldeInitial = soldeInitial;
        this.soldeActuel = soldeActuel;
        this.numero = numero;
        this.codeSecret = codeSecret;
        this.visage = visage;
        this.commentaire = commentaire;
    }

    public M_Cartes(Db_mariadb db, String numero, float soldeInitial, float soldeActuel, String codeSecret, String visage, String commentaire, int idCommande) throws SQLException {
        this.db = db;
        this.idCommande = idCommande;
        this.soldeInitial = soldeInitial;
        this.soldeActuel = soldeActuel;
        this.numero = numero;
        this.codeSecret = codeSecret;
        this.visage = visage;
        this.commentaire = commentaire;
        
        String sql = "INSERT INTO mcd_cartes (numero, solde_initial, solde_actuel, code_secret, visage, commentaire, id_commande)"
                        + " VALUES ('" + numero + "', '" + soldeInitial + "', '" + soldeActuel + "', '" + codeSecret + "', '" + visage + "', '" + commentaire + "', '" + idCommande +"');";
        db.sqlExec(sql);
        ResultSet res;
        res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        res.close();
    }

    public M_Cartes(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        this.id = id;
        
        String sql = "SELECT * FROM mcd_cartes WHERE id = '" + id + "'";
        ResultSet res;
        res = db.sqlSelect(sql);
        res.first();
        this.numero = res.getString("numero");
        this.soldeInitial = res.getInt("solde_initial");
        this.soldeActuel = res.getInt("solde_actuel");
        this.codeSecret = res.getString("code_secret");
        this.visage = res.getString("visage");
        this.commentaire = res.getString("commentaire");
        this.idCommande = res.getInt("id_commande");
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

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public float getSoldeInitial() {
        return soldeInitial;
    }

    public void setSoldeInitial(float soldeInitial) {
        this.soldeInitial = soldeInitial;
    }

    public float getSoldeActuel() {
        return soldeActuel;
    }

    public void setSoldeActuel(float soldeActuel) {
        this.soldeActuel = soldeActuel;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodeSecret() {
        return codeSecret;
    }

    public void setCodeSecret(String codeSecret) {
        this.codeSecret = codeSecret;
    }

    public String getVisage() {
        return visage;
    }

    public void setVisage(String visage) {
        this.visage = visage;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    public void update() throws SQLException {
        String sql = "UPDATE mcd_cartes SET "
            + "numero='" + numero + "', "
            + "solde_initial=" + soldeInitial + ", "
            + "solde_actuel=" + soldeActuel + ", "
            + "code_secret='" + codeSecret + "', "
            + "visage='" + visage + "', "
            + "commentaire='" + commentaire + "', "
            + "id_commande='" + idCommande + "'"
            + " WHERE id=" + id + ";";
        db.sqlExec(sql);
    }
    
    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_cartes WHERE id=" + id + ";";
        db.sqlExec(sql);
    }
    
    public static LinkedHashMap<Integer, M_Cartes> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }
    
    public static LinkedHashMap<Integer, M_Cartes> getRecords (Db_mariadb db, String clauseWhere) throws SQLException {
        LinkedHashMap<Integer, M_Cartes> lesCartes;
        lesCartes = new LinkedHashMap<>();
        M_Cartes uneCarte;
        
        int cle, idCommande;
        String numero, codeSecret, visage, commentaire;
        float soldeInitial, soldeActuel;
        
        String sql = "SELECT * FROM mcd_cartes WHERE " + clauseWhere + ";";
        ResultSet res;
        res = db.sqlSelect(sql);
        
        while (res.next()) {
            cle = res.getInt("id");
            numero = res.getString("numero");
            soldeInitial = res.getFloat("solde_initial");
            soldeActuel = res.getFloat("solde_actuel");
            codeSecret = res.getString("code_secret");
            visage = res.getString("visage");
            commentaire = res.getString("commentaire");
            idCommande = res.getInt("id_commande");
            
            uneCarte = new M_Cartes(db, cle, idCommande, soldeInitial, soldeActuel, numero, codeSecret, visage, commentaire);
            lesCartes.put(cle, uneCarte);
        }
        
        res.close();
        
        return lesCartes;
    }

    @Override
    public String toString() {
        return "M_Carte{" + "db=" + db + ", id=" + id + ", idCommande=" + idCommande + ", soldeInitial=" + soldeInitial + ", soldeActuel=" + soldeActuel + ", numero=" + numero + ", codeSecret=" + codeSecret + ", visage=" + visage + ", commentaire=" + commentaire + '}';
    }
    
    public static void main(String[] args) throws Exception {
        
        /** 
         *  Jeux de test pour mcd_cartes
         */
        
        Db_mariadb mabase;
        mabase = new Db_mariadb(Cl_Connection.url, Cl_Connection.login, Cl_Connection.password);
        
        M_Cartes uneCarte;
        
        /* Test du 1er constructeur 
        uneCarte = new M_Carte(mabase, 1, 0, 400, 400, "1234123412341234", "0000", "jsp", "Test du 1er Constructeur");
        System.out.println(uneCarte.toString());
        */
        
        /* Test du 2eme constructeur  
        uneCarte = new M_Carte(mabase, "1234123412341234", 400, 400, "1234", "jsp", "Test du 2eme constructeur", 1);
        System.out.println(uneCarte.toString());
       */
        
        /* Test du 3eme constructeur 
        uneCarte = new M_Carte(mabase, 3);
        System.out.println(uneCarte.toString());
        */
        
        /* Test des setteurs et du update 
        uneCarte.setNumero("0123012301230123");
        uneCarte.setSoldeInitial(400);
        uneCarte.setSoldeActuel(300);
        uneCarte.setCodeSecret("0000");
        uneCarte.setVisage("je sais pas");
        uneCarte.setCommentaire("Test du update");
        uneCarte.setIdCommande(1);
        System.out.println(uneCarte.toString());
        uneCarte.update();
        */
        
        /* Test du delete 
        uneCarte.delete();
        */
        
        //Test du record set +affichage avec sans et avec condition Where
        //Déclarer un objet dictionnaire
        LinkedHashMap<Integer, M_Cartes> lesCartes;

        //Appeler la méthode getRecods
        lesCartes = M_Cartes.getRecords(mabase);

        //Afficher le contenu du dictionnaire
        for (Integer uneCle : lesCartes.keySet()) {
            uneCarte = lesCartes.get(uneCle);
            System.out.println(uneCarte.toString());

        }
    }
    
}
