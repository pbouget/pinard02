package fr.bouget.jpa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vin implements Serializable {
	public static final long serialVersionUID = 56439876525L;

	private Integer codeProduit;
	private String designation;
	private String region;
	private String couleur;
	private double prix;
	private double remise;
	private int quantite;


	public Vin() {super();}

	/**
	 * Annotations sur le getter
	 * @return codeproduit auto-généré par MySQL
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCodeProduit() {
		return this.codeProduit;
	}

	public void setCodeProduit(Integer codeProduit) {
		this.codeProduit = codeProduit;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public double getRemise() {
		return remise;
	}

	public void setRemise(double remise) {
		this.remise = remise;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (this.getCodeProduit() != null ? this.getCodeProduit()
				.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {

		if (!(object instanceof Vin)) {
			return false;
		}
		Vin v = (Vin) object;
		if (codeProduit == null || designation == null || region == null
				|| couleur == null)
		{
			return false;
		}
		else if (!codeProduit.equals(v.codeProduit)
				|| !designation.equals(v.designation)
				|| !region.equals(v.region) || !couleur.equals(v.couleur)
				|| prix != v.prix || remise != v.remise
				|| quantite != v.quantite)
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "codeProduit=" + getCodeProduit() + " designation="
				+ getDesignation() + " region=" + getRegion() + " couleur="
				+ getCouleur() + " remise=" + getRemise() + " prix="
				+ getPrix() + " quantite=" + getQuantite()+"\n";
	}
}
