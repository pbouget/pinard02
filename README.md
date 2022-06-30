# Pinard02 (corrigé) ENTITE VIN AVEC CLE PRIMAIRE AUTO-GENEREE

![pinard02](images/pinard01.png)

Objectif : écrire une entité **Vin** avec une clef auto-incrémentée par MySQL avec l'annotation **GeneratedType.IDENTITY**

## Création du projet : **Pinard02**

Vous pouvez utiliser le code du projet **pinard01**. (copier coller des classes java uniquement).
Vous pouvez aussi faire un copier-coller du projet mais dans ce cas il faudra penser à modifier
le no du nouveau projet dans le fichier **setting.gradle** soit :

```java
rootProject.name = 'pinard02'
```

- ajout de la génération automatique de la clé primaire pour notre Entity **Vin**.

### Modifier la classe Vin

- Ajouter l’annotation **@GeneratedValue** sur le champ codeProduit ou sur la méthode **getCodeProduit()**
- Modifier le type de stratégie pour la génération automatique de la clef sous MySQL (indiquée précédemment)

```java
@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getCodeProduit() {
		return this.codeProduit;
	}

```

>Attention : Il vous faudra modifier votre méthode `ajoutVin(Vin vin)` pour retourner l’objet Vin qui vient d’être ajouter dans la table. Ce même objet est en mémoire mais ne possède pas de clef primaire puisqu’elle vient d’être générer automatiquement par MySQL !

Voici le code :
```java
public Vin ajoutVin(Vin vin){
    	return vinRepository.save(vin);
    }
```

>Le code doit être également modifié dans le controleur puisque le code produit est généré par la couche de persistance.
Lorsque vous allez créer un produit **Vin**,
vous n’avez plus besoin d’initialiser un codeProduit pour la clef primaire !

## Dans le Contrôleur, il y a quelques modifications car l'identifiant est auto-généré :

```java
package fr.bouget.jpa.controller;

import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.bouget.jpa.model.Vin;
import fr.bouget.jpa.repository.VinRepository;

/**
 * @author Philippe
 * Ce TP pinard02 n'intègre pas la gestion des Exceptions.
 *
 */
@RestController
@CrossOrigin("*")
public class PinardController {
	
	@Autowired
	private VinRepository vinRepository;
	
	@GetMapping("/accueil")
	@ResponseBody
	public String home(){
		
		Vin v1=new Vin();
		v1.setDesignation("Les Hauts du Tertre 2018");
		v1.setRegion("Bordeaux (Margaux)");
		v1.setCouleur("rouge");
		v1.setPrix(11.50);
		v1.setRemise(0);
		v1.setQuantite(2);

		Vin v2=new Vin();
		v2.setDesignation("Château Marquis de Terme 2015");
		v2.setRegion("Bordeaux (Margaux)");
		v2.setCouleur("rouge");
		v2.setPrix(19.00);
		v2.setRemise(0);
		v2.setQuantite(3);

		Vin v3=new Vin();
		v3.setDesignation("Clos du Marquis 2000");
		v3.setRegion("Bordeaux (Saint-Julien)");
		v3.setCouleur("rouge");
		v3.setPrix(22.90);
		v3.setRemise(0);
		v3.setQuantite(15);

		Vin v4=new Vin();
		v4.setDesignation("Clos du Baron 1985");
		v4.setRegion("Bordeaux (Saint-Julien)");
		v4.setCouleur("blanc");
		v4.setPrix(45.20);
		v4.setRemise(0);
		v4.setQuantite(54);


		System.out.println("ajout du produit v1 : "+v1);
		v1=ajoutVin(v1);

		System.out.println("ajout du produit v2 : "+v2);
		v2=ajoutVin(v2);

		System.out.println("ajout du produit v3 : "+v3);
		v3=ajoutVin(v3);
		System.out.println("Changement de la quantité de v3 à 10 ");
		v3.setQuantite(10);
		v3=updateVin(v3);

		
		System.out.println("ajout du produit v4 : "+v4);
		v4=ajoutVin(v4);

		System.out.println("Changement de la quantité de v4 à 50 ");
		updateQuantite(v4.getCodeProduit(),50);

		System.out.println("suppression du vin "+v3);
		deleteVin(v3.getCodeProduit());

		System.out.println("liste des vins enregistrés:");
		Collection<Vin> vins=findAll();
		Iterator<Vin> it=vins.iterator();
		while(it.hasNext())
		{
			System.out.println(it.next());
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<h1>Regardez dans votre console et dans votre base de données MySQL <strong>JPA</strong></h1>");
		sb.append("<a href='http://localhost:8080/vins'>Voir la liste des vins enregistrés</a>");
		return  sb.toString();
	}

	

	public Optional<Vin> findByCodeProduit(Integer codeProduit){
		return vinRepository.findById(codeProduit);
	}
	/**
	 * Retourne tous les produits dans une liste
	 * au format JSON
	 */
	@GetMapping("/vins")
	public Collection<Vin> findAll(){

		return vinRepository.findAll();
	}
	/**
	 * Ajoute un vin
	 */
	public Vin ajoutVin(Vin vin){
		return vinRepository.saveAndFlush(vin);
	}
	/**
	 * Met un jour un enregistrement pour un objet Vin
	 */
	public Vin updateVin(Vin vin){
		return vinRepository.saveAndFlush(vin);
	}

	/**
	 * Méthode qui détruit un objet de type Vin en fonction de son code
	 * et si ce dernier existe !
	 */
	public void deleteVin(Integer codeProduit){
		
		vinRepository.delete(vinRepository.getOne(codeProduit));
	}

	/**
	 * Met à jour la quantité d'un objet de type Vin
	 */
	public void updateQuantite(Integer codeProduit, int quantite){
		(vinRepository.getOne(codeProduit)).setQuantite(quantite);
	}


}


```

- lancez SpringBoot et observez la table

- Voici la table Vin qui sera générée :

```sql

CREATE TABLE `vin` (
  `CODEPRODUIT` int(11) NOT NULL,
  `COULEUR` varchar(255) DEFAULT NULL,
  `DESIGNATION` varchar(255) DEFAULT NULL,
  `PRIX` double DEFAULT NULL,
  `QUANTITE` int(11) DEFAULT NULL,
  `REGION` varchar(255) DEFAULT NULL,
  `REMISE` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `vin`
  ADD PRIMARY KEY (`CODEPRODUIT`);

ALTER TABLE `vin`
  MODIFY `CODEPRODUIT` int(11) NOT NULL AUTO_INCREMENT;
```

[Retour vers les autres exercices](../cours/framework-back/1-jpa-orm/mapping-orm.md)