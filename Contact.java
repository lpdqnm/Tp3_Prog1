
/**
 * I N F x 1 2 0
 *
 * D�crivez votre classe Contact ici.
 * 
 * @author Quenum Leopold
 * @version Decembre 2016
 *
 * QUEL16107105
 * (votre adresse de courriel)
 */

public class Contact {

    ////////////////
    // CONSTANTES //
    ////////////////
    
	//Messages d'erreur pour ContactInvalideException
	public final static String [] MSG_ERR = {"Le nom du contact ne doit etre ni "
			+ "null, ni vide", "Le prenom du contact ne doit etre ni null, ni"
			+ " vide"};
	//Saut de ligne et signes de onctuation a inserer dans des chaines
	public final static String [] SEP = {"\n", ". ",  ", "};
	//Etiquettes dans la mehtode toString
	public final static String [] ETIQUETTES = {"Aucun.", "TELEPHONE(S) : ",
		"ADRESSE : ", "COURRIEL : ", " [FAVORI]"};
	
    
    ////////////////
    // VARIABLES //
    ////////////////
    
    // variable(s) de classe s'il y a lieu
    private static int nbrContactsFavoris;
    
    // variable(s) d'instance s'il y a lieu
    private String nom;
    private String prenom;
    private Telephone [] telephones;
    private int nbrTelephones;
    private Adresse adresse;
    private String courriel;
    private boolean favori;
    
    // constructeur(s) s'il y a lieu
    /**
     * Ce constructeur construit un objet Contact avec le nom, le prenom, un 
     * 1er No de telephone, une adresse et un courriel du contact
     *
     * @param  nom   le nom du contact a construire
     * @param  prenom   le prenom du contact � construire
     * @param  tel   un premier No de telephone du contact � ajouter tableau
     * de telephones du contact � contruire
     * @param  adresse   l'adresse du contact � construire
     * @param  courriel   le courriel contact � construire
     * @throws  ContactInvalideException si le nom ou le prenom ne sont pas
     * des chaines valides
     */
    public Contact(String nom, String prenom, Telephone tel, Adresse adresse,
    		String courriel) throws ContactInvalideException {
        this(nom, prenom);
        
        if (!estNull(tel)) {
			telephones[0] = tel;
			nbrTelephones++;
		}
        this.adresse = adresse;
        //Si le courriel n'est pas null et pas vide on l'initialise, 
        //si le courriel n' est pas null et est une chaine vide, on 
        //l'initialise avec null
        if (!strNullVide(courriel)) {
			this.courriel = courriel;
		} else if (!estNull(courriel) && courriel.isEmpty()) {
			this.courriel = null;
		}
    } // Contact
    
    /**
     * Ce constructeur construit un objet Contact avec le nom et le prenom du
     * contact
     *
     * @param  nom   le nom du contact a construire
     * @param  prenom   le prenom du contact a construire
     * @throws  ContactInvalideException si le nom ou le prenom ne sont pas
     * des chaines valides
     */
    public Contact(String nom, String prenom) throws ContactInvalideException {
        this.nom = nomPrenomValide(nom, MSG_ERR[0]);
		this.prenom = nomPrenomValide(prenom, MSG_ERR[1]);
        
        favori = false;
        telephones = new Telephone [2];
    } // Contact

    // m�thode(s) de classe s'il y a lieu
    
    /**
     * Cette methode permet de modifier la valeur de l’attribut de classe 
     * nbrContactsFavoris par la valeur passée en paramètre
     *
     * @param  nbr  la valeur de modification de l’attribut de classe 
     * nbrContactsFavoris
     */
    public static void modifierNbrContactsFavoris(int nbr) {
		nbrContactsFavoris = nbr;
	}
    
    /**
     * Cette methode permet d’obtenir la valeur de l’attribut de classe 
     * nbrContactsFavoris
     *
     * @return  la valeur de l’attribut de classe nbrContactsFavoris
     */
    public static int obtenirNbrContactsFavoris() {
		return nbrContactsFavoris;
	}
    
    // m�thode(s) d'instance s'il y a lieu
    
    /////Getters/////
    public String getNom() {
		return nom;
	}
    
    public String getPrenom() {
		return prenom;
	}
    
    public Adresse getAdresse() {
		return adresse;
	}
    
    public String getCourriel() {
		return courriel;
	}
    
    public int getNbrTelephones() {
		return nbrTelephones;
	}
    
    public boolean isFavori() {
		return favori;
	}
    
    /////Setters/////
    public void setNom(String nom) throws ContactInvalideException {
        this.nom = nomPrenomValide(nom, MSG_ERR[0]);
	}
    
    public void setPrenom(String prenom) throws ContactInvalideException {
		this.prenom = nomPrenomValide(prenom, MSG_ERR[1]);
	}
    
    public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
    
    public void setCourriel(String courriel) {
    	//Si le courriel une chaine vide, on le met a null, sinon on le met a 
    	//jour
        if (courriel.isEmpty()) {
			this.courriel = null;
		} else {
			this.courriel = courriel;
		}
	}
    
    public void setFavori(boolean favori) {
		if (favori) {
			if (!this.favori) {
				nbrContactsFavoris++;
			}
			
		} else {
			if (this.favori) {
				nbrContactsFavoris--;
			}
		}
    	this.favori = favori;
	}
    
    /**
     * Cette methode permet d’ajouter le tel donné en paramètre au tableau 
     * telephones de ce contact
     *
     * @param  tel   a ajouter
     */     
    public void ajouterTelephone(Telephone tel) {
		int premPlLib;
		if (nbrTelephones == telephones.length) {
			telephones = agrandirTab(telephones);
		}
		premPlLib = premCaseLibre(telephones);
		telephones[premPlLib] = tel;
		nbrTelephones++;
	}
    
    /**
     * Cette methode permet de trouver, dans le tableau de telephones,
     *  l'indice correspondant au ieme tel du contact
     * 
     *
     * @param  ieme   telephone
     * @return  la position dans le tableau de telephones du ieme telephones,
     * ou -1 si le ieme telephone n'est pas trouve
     * 
     */ 
    public Telephone obtenirIemeTelephone(int ieme) {
    	Telephone iemeTel = null;
    	int indiceIeme = indiceIemeTel(ieme);
    	
    	if (indiceIeme >= 0) {
			iemeTel = telephones[indiceIeme];
		}
    	return iemeTel;
	}
    
    /**
     * Cette methode permet de supprimer le ieme téléphone du tableau de 
     * téléphones de ce contact ; s’il n’y a pas de ieme téléphone dans le 
     * tableau de téléphones, aucun téléphone n’est supprimé
     * 
     *
     * @param  ieme   telephone a supprimer
     * @return  true si la suppression a eu lieu, false sinon
     */ 
    public boolean supprimerTelephone(int ieme) {
		boolean suppTel = false;
		int indiceIeme = indiceIemeTel(ieme);
		
		if (indiceIeme >= 0) {
			telephones[indiceIeme] = null;
			suppTel = true;
			nbrTelephones--;
		}
		return suppTel;
	}
    
    /**
     * Cette methode permet de modifier le type, le numero, et le poste du 
     * ieme téléphone de ce contact
     * 
     *
     * @param  ieme   telephone a modifier
     * @param  type   du telephone  a modifier
     * @param  numero   du telephone  a modifier
     * @param  poste   du telephone
     * @throws  TelephoneInvalideException si le numero n'est pas valide
     * 
     */ 
    public void modifierTelephone(int ieme, String type, String numero, 
    		String poste) throws TelephoneInvalideException {
		int indiceIeme = indiceIemeTel(ieme);
		
		if (indiceIeme >= 0 && !estNull(type, numero, poste)) {
			if (!Telephone.numeroTelValide(numero)) {
				throw new TelephoneInvalideException();
			}
			//On ecrase le telephone a modifier, par le nouveau telephone
			telephones[indiceIeme] = new Telephone(type, numero, poste);
		}
	}
    
    /**
     * Cette methode permet de retourner une représentation sous forme de +
     * chaine de caractères de ce contact
     * 
     *
     * @return  une chaine contenant les attributs d'un contact sous forme de
     * chaine de caracteres tels, le nom, le prenom, le(s) telephone(s), 
     * l'adresse et le courriel d'un contact
     */
    public String toString() {
    	String contactToString = "";
    	String lgn2 = SEP[0] + SEP[0];//2 sauts de ligne
    	Telephone iemeTel;
    	
    	//Ajout de nom et prenom
		contactToString  += nom.toUpperCase() + SEP[2] + prenom;
		if (favori) {
			contactToString += ETIQUETTES[4];
		}
		//Ligne haut/bas entourant le nom et prenom
		contactToString = SEP[0] + CarnetContacts.ligne('-', 
				contactToString.length()) + SEP[0] + contactToString + SEP[0]
				+ CarnetContacts.ligne('-', contactToString.length());
				
		//Ajout de telephone(s)
		contactToString += lgn2 + ETIQUETTES[1];
		if (nbrTelephones > 0) {
			for (int ieme = 1; ieme <= nbrTelephones; ieme++) {
				iemeTel = obtenirIemeTelephone(ieme);
				if (!estNull(iemeTel)) {
					contactToString += SEP[0] + ieme + SEP[1]
						+ iemeTel.toString();
				}
			}
		} else {
			contactToString += ETIQUETTES[0];
		}
		//Ajout d'adresse
		contactToString += lgn2 + ETIQUETTES[2];
		if (adresse != null) {
			contactToString += SEP[0] + adresse.toString();
		} else {
			contactToString += ETIQUETTES[0];
		}
		//Ajout de courriel
		contactToString += lgn2 + ETIQUETTES[3];
		if (!estNull(courriel)) {
			contactToString += courriel.toLowerCase();
		} else {
			contactToString += ETIQUETTES[0];
		}
		return contactToString;
	}
    
    /**
     * Cette methode permet de trouver, dans le tableau de telephones,
     *  l'indice correspondant au ieme tel du contact
     * 
     *
     * @param  ieme   telephone
     * @return  la position dans le tableau de telephones du ieme telephones,
     * ou -1 si le ieme telephone n'est pas trouve
     * 
     */ 
    private int indiceIemeTel(int ieme) {
		int noOrdreTel = 0;
		boolean nonTrouve = true;
		int indice = 0;
		
		if (telephones != null) {
			while (indice < telephones.length && nonTrouve) {
				if (!estNull(telephones[indice])) {
					noOrdreTel++;
				}
				if (ieme == noOrdreTel) {
					nonTrouve = false;
				} else {
					indice++;
				}
			}
		}
		
		//Si ieme telephone ne correspond a aucun indice dans le tableau, on
		//met l'indice a retourner a -1
		if (nonTrouve) {
			indice = -1;
		}
		return indice;
	}
    
    /**
     * Cette methode permet de trouver l'indice de la premiere case libre dans
     * le tableau de telephones
     *
     * @param  tab   tableau de telephones
     * @return  l'indice de la premiere place libre dans le tableau de 
     * telephones, ou retourne la longueur de ce tableau s'il n'y a aucune
     * place libre, ou retourne -1 si le tableau est null ou vide
     */ 
    private int premCaseLibre(Telephone [] tab) {
    	int i = -1;
    	if (tab != null && tab.length != 0) {
    		i = 0;
    		while (i < tab.length && !estNull(tab[i])) {
    			i++;
    		}
		}
		return i;
	}
    
    /**
     * Cette methode permet d'agrandir le tableau de telephones de 2 cases
     *
     * @param  tab   tableau de telephones a agrandir
     * @return  retourne un tableau agrandi de 2 cases
     */    
    private Telephone [] agrandirTab(Telephone [] tab) {
		Telephone [] nvTab = null;
		
		if (tab != null) {
			nvTab = new Telephone [tab.length + 2];
			for (int i = 0; i < tab.length; i++) {
				nvTab[i] = tab[i];
			}
		}
		return nvTab;
	}
    
    /**
     * Cette m�thode permet de valider le nom ou le prenom du contact (chaine
     * non null et non vide) et leve une exception ContactInvalideException si
     * la chaine n'est pas valide
     *
     * @param  nomPren   la chaine a valider
     * @param  msgErr   message d'erreur lorsque l'exception est leve
     * @throws  ContactInvalideException si la chaine n'est pas valide
     * @return  la chaine str si elle est valide
     */
    private String nomPrenomValide(String nomPren, String msgErr) 
    		throws ContactInvalideException {
    	if (strNullVide(nomPren)) {
			throw new ContactInvalideException(msgErr);
		}
		return nomPren;
	} // nomPrenomValide
 
    /**
     * Cette m�thode permet de tester si une chaine est Null OU Vide
     *
     * @param  str   la chaine a tester
     * @return  true si la str est Null OU Viode et false sinon
     */
    private boolean strNullVide(String str) {
		return estNull(str) || str.isEmpty();
	} // strNullVide
    
    /**
     * Cette m�thode permet de tester si au moins une des  3 chaines est NULL
     *
     * @param  str1   la 1ere chaine a tester
     * @param  str2   la 2eme chaine a tester
     * @param  str3   la 3eme chaine a tester
     * @return  true si au moins une des chaines str1, str2, str3 NULL et 
     * false sinon
     */
    private boolean estNull(String str1, String str2, String str3) {
		return estNull(str1) || estNull(str2) || estNull(str3);
	} // estNull
    
    /**
     * Cette m�thode permet de tester si une chaine est NULL
     *
     * @param  str   la chaine a tester
     * @return  true si la str est NULL et false sinon
     */
    private boolean estNull(String str) {
		return str == null;
	} // estNull
    
    /**
     * Cette m�thode permet de tester si un objet Telephone  est NULL
     *
     * @param  tel   l'objet tel a tester
     * @return  true si tel est NULL et false sinon
     */
    private boolean estNull(Telephone tel) {
		return tel == null;
	} // estNull
    
    //Methode a copier-coller dans votre classe Contact avant d'executer
    //la classe de tests partiels.
 	public String toString2 (String attribut) {
       String s = "";
       if (attribut == null) {
          String contact = nom + " " + prenom + " " + favori + "\n";
          if (telephones == null) {
             contact = contact + "erreur";
          } else {
             for (Telephone tel : telephones) {
                   contact = contact + tel + "\n";
             }
          }
          contact = contact + adresse;
          contact = contact + "\n" + courriel;
       
          s = contact.trim();
       } else if (attribut.equals("nom")) {
          s = nom;
       } else if (attribut.equals("prenom")) {
          s = prenom;
       } else if (attribut.equals("adresse")) {
          s = "null";
          if (adresse != null) {
             s = adresse.toString();
          }
       } else if (attribut.equals("courriel")) {
          s = "null";
          if (courriel != null) {
             s = courriel;
          }
       } else if (attribut.equals("favori")) {
          s = favori + "";
       } else if (attribut.equals("nbrTelephones")) {
          s = nbrTelephones + "";
       } else if (attribut.equals("telephones")) {
          if (telephones == null) {
             s = "erreur";
          } else {
             s = "";
             for (Telephone tel : telephones) {
                s = s + tel + "\n";
             }
             s = s.trim();
          } 
       }
       return s;
    }
    
    

        
} // Contact
