package tp2_event ;

import tp1_date.Clavier;
import tp1_date.Date;
import tp4_except.ExceptionDate ;

public class Evenement implements Comparable<Evenement> {
    private Date chDate ;
    private String chTitre ;
    private String chLieu ;
    private static int chNbParametre = 0 ;

    public Evenement(Date parDate, String parTitre, String parLieu) {
        chDate = parDate ;
        chTitre = parTitre ;
        chLieu = parLieu ;
        chNbParametre++ ;
    }

    public String toString() {
        return chTitre+":"+chDate+":"+chLieu ;
    }

    // Question 6
    public static Evenement lireEvenement() throws ExceptionDate {
        return new Evenement(Date.lireDate(), Clavier.lireString(), Clavier.lireString()) ;
    }

    // Question 7
    // si this < parEvt ---> -1
    // si this = parEvt --->  0
    // si this > parEvt ---> +1
    public int compareTo(Evenement parEvt) {
        if (parEvt.chDate.compareTo(this.chDate)>0)
            return 1 ;
        else if (parEvt.chDate.compareTo(this.chDate)<0)
            return -1 ;
        else {
            if (parEvt.chTitre.compareTo(this.chTitre)>0)
                return 1 ;
            else if (parEvt.chTitre.compareTo(this.chTitre)<0)
                return -1 ;
            else {
                if (parEvt.chLieu.compareTo(this.chLieu)>0)
                    return 1 ;
                else if (parEvt.chLieu.compareTo(this.chLieu)<0)
                    return -1 ;
                else
                    return 0 ;
            }
        }
    }


    // accesseurs
    public Date getChDate() {
        return this.chDate ;
    }

    public String getChTitre() {
        return this.chTitre ;
    }

    public String getChLieu() {
        return this.chLieu ;
    }

    public static int getChNbParametre() {
        return chNbParametre ;
    }

    // modifieurs
    public void setChDate(Date parDate) {
        this.chDate = parDate ;
    }

    public void setChTitre(String parTitre) {
        this.chTitre = parTitre ;
    }

    public void setChLieu(String parLieu) {
        this.chLieu = parLieu ;
    }
}
