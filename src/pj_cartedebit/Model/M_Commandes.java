/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pj_cartedebit.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import pj_cartedebit.Db_mariadb;

/**
 *
 * @author amatheo
 */
public class M_Commandes {
    
    private Db_mariadb db;
    private int id, idUser;
    private LocalDateTime dateAchat, created_at, updated_at;
    private String commentaire;

    public M_Commandes(Db_mariadb db, int id, int idUser, LocalDateTime dateAchat, LocalDateTime created_at, LocalDateTime updated_at, String commentaire) {
        this.db = db;
        this.id = id;
        this.idUser = idUser;
        this.dateAchat = dateAchat;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.commentaire = commentaire;
    }

    public M_Commandes(Db_mariadb db, int idUser, LocalDateTime dateAchat, LocalDateTime created_at, LocalDateTime updated_at, String commentaire) throws SQLException {
        this.db = db;
        this.idUser = idUser;
        this.dateAchat = dateAchat;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.commentaire = commentaire;
        
        String sql = "INSERT INTO mcd_roles (date_achat, commentaire, created_at, updated_at, id_user)"
                        + " VALUES ('" + dateAchat + "', '" + commentaire + "', '" + created_at + "', '" + updated_at + "', '" + idUser + "');";
        db.sqlExec(sql);
        ResultSet res;
        res = db.sqlLastId();
        res.first();
        this.id = res.getInt("id");
        res.close();
    }

    public M_Commandes(Db_mariadb db, int id) throws SQLException {
        this.db = db;
        this.id = id;
        
        String sql = "SELECT * FROM mcd_commandes WHERE id = '" + id + "'";
        ResultSet res;
        res = db.sqlSelect(sql);
        res.first();
        this.dateAchat = res.getObject("date_achat", LocalDateTime.class);
        this.commentaire = res.getString("commentaire");
        this.created_at = res.getObject("created_at", LocalDateTime.class);
        this.updated_at = res.getObject("updated_at", LocalDateTime.class);
        this.idUser = res.getInt("id_user");
        res.close();
    }

    public Db_mariadb getDb() {
        return db;
    }

    public void setDb(Db_mariadb db) {
        this.db = db;
    }

    public int getId() {
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public LocalDateTime getDateAchat() {
        return dateAchat;
    }

    public void setDateAchat(LocalDateTime dateAchat) {
        this.dateAchat = dateAchat;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    
    public void update() throws SQLException {
        String sql = "UPDATE mcd_commandes SET "
                + "date_achat='" + dateAchat + "', "
                + "commentaire='" + commentaire + "'"
                + "created_at='" + created_at + "'"
                + "updated_at='" + updated_at + "'"
                + "id_user='" + idUser + "', "
                + " WHERE id=" + id + ";";
        db.sqlExec(sql);
    }

    public void delete() throws SQLException {
        String sql = "DELETE FROM mcd_commandes WHERE id=" + id + ";";
        db.sqlExec(sql);
    }
    
    public static LinkedHashMap<Integer, M_Commandes> getRecords(Db_mariadb db) throws SQLException {
        return getRecords(db, "1 = 1");
    }

    public static LinkedHashMap<Integer, M_Commandes> getRecords(Db_mariadb db, String clauseWhere) throws SQLException {
        LinkedHashMap<Integer, M_Commandes> lesCommandes;
        lesCommandes = new LinkedHashMap<>();
        M_Commandes uneCommande;

        int cle, id_User;
        LocalDateTime dateAchat, created_at, updated_at;
        String commentaire;

        String sql = "SELECT * FROM mcd_commandes WHERE " + clauseWhere + ";";
        ResultSet res;
        res = db.sqlSelect(sql);

        while (res.next()) {
            cle = res.getInt("id");
            dateAchat = res.getObject("date_achat", LocalDateTime.class);
            commentaire = res.getString("commentaire");
            created_at = res.getObject("created_at", LocalDateTime.class);
            updated_at = res.getObject("updated_at", LocalDateTime.class);
            id_User = res.getInt("id_user");

            uneCommande = new M_Commandes(db, cle, id_User, updated_at, created_at, updated_at, commentaire);
            lesCommandes.put(cle, uneCommande);
        }

        res.close();

        return lesCommandes;
    }

    @Override
    public String toString() {
        return "M_Commandes{" + "db=" + db + ", id=" + id + ", idUser=" + idUser + ", dateAchat=" + dateAchat + ", created_at=" + created_at + ", updated_at=" + updated_at + ", commentaire=" + commentaire + '}';
    }
}
