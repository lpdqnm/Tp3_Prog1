
import java.io.*;

/**
 * Cette classe permet de gerer un carnet de contacts : ajout, suppression
 * de contacts ou de tous les contacts, affichage de tous contacts de la liste
 * ou des contacts favoris
 * 
 * @author Quenum Leopold
 * @version Decembre 2016
 *
 * QUEL16107105
 */
public class CarnetContacts {
   
   //-----------
   //CONSTANTES
   //-----------
   
   //DIVERS
   
   public static final File FIC_CONTACTS = new File("contacts.txt");
   public static final int LNG_INIT_TAB_CONTACTS = 2;
   public static final String SEP = ":";
   
   //BORNES DE VALIDATION
   
   public final static String MENU_MIN = "1";
   public final static String MENU_MAX = "5";
  
   //MESSAGES DIVERS
  
   public static final String PRESENTATION = "Ce programme permet de gerer un "
           + "carnet de contacts.";
   public final static String MSG_PAUSE = "\nAppuyez sur \"ENTREE\" pour "
           + "continuer...";
   
   public static final String MENU = "\n-----\nMENU\n-----\n"
           + "1. AJOUTER UN CONTACT\n2. SUPPRIMER UN CONTACT"
           + "\n3. VIDER LE CARNET DE CONTACTS\n4. AFFICHER LES CONTACTS"
           + "\n5. QUITTER\n\n"
           + "Entrez votre choix au menu : ";
   
   public final static String MSG_ERR_MENU = "\nErreur, entrez une valeur entre "
           + MENU_MIN + " et " + MENU_MAX + "... Recommencez.\n";
   
   public static final String SOUS_MENU_AFF_CONTACTS = 
           "1. AFFICHER TOUS LES CONTACTS"
           + "\n2. AFFICHER LES FAVORIS\n\n"
           + "Entrez votre choix : ";
   
   public final static String MSG_FIN_PROG = "\n\nFIN NORMALE DU PROGRAMME.";
   
   //LES TITRES, SOUS-TITRES
   public final static String [] TITRES = {"\nCARNET DE CONTACTS ", 
	   "AJOUT D'UN CONTACT", "SUPPRIMER UN CONTACT",
	   "\nFIN DE LA LISTE DE CONTACTS.", "AFFICHER LES CONTACTS"};
   
   //LONGUEURS ADMISES DES CHAINES ENTREES
   public final static int [] LG_CH = {100, 1, 5, 8, 25, 50};
   
   //MESSAGES DE SOLLICITATION
   public final static String SOL1 = " du contact : ";
   public final static String SOL2 = "Voulez-vous entrez ";
   public final static String SOL3 = " (o/n) : ";
   public final static String SOL4 = "Voulez-vous vraiment ";
   
   /////Nom, prenom, informations telephone
   public final static String [] MSG_SOL_NM_TEL = {"Nom" + SOL1, 
	   "Prenom" + SOL1, "Numero de telephone : ",  "Type du telephone (Tapez"
	   + " ENTER pour \"Domicile\"): ", "Poste telephonique (Tapez ENTER si "
	   + "aucun poste) : "};
   
   /////Courriel, informations Adresse(numero porte, rue, numero App)
   public final static String [] MSG_SOL_COUR_ADR1 = {"Courriel : ", 
	   "No de porte : ", "Rue : ", "Numero d'appartement (Taper ENTER si "
	   		+ "aucun apt.) : "};
   
   /////Informations Adresse(ville, province, pays, code ppostal)
   public final static String [] MSG_SOL_ADR2 = {"Ville : ","Province/etat : ",
	   "Pays : ", "Code postal (Taper ENTER si aucun code postal) : "};

   /////Questions dont la reponse est OUI ou NON
   public final static String [] MSG_SOL_QUEST = {SOL2 + "un telephone"+ SOL3,
	   SOL2 + "une adresse" + SOL3, SOL2 + "un courriel"+ SOL3, "Voulez-vous "
	   + "ajouter ce contact a vos favorisl"+ SOL3, SOL4 + "effacer tous les"
	   + " contacts" + SOL3, "\n" + SOL4 + "supprimer ce contact" + SOL3};
   
   //MESSAGES D'ERREUR
   public final static String ERR1 = "\nErreur, ";
   public final static String ERR2_1 = " doit contenir ";
   public final static String ERR2_2 = "entre 1 et ";
   public final static String ERR2 = ERR2_1 + ERR2_2;
   public final static String ERR3_1 = " caracteres... ";
   public final static String ERR3_2 = "Recommencez.";
   public final static String ERR3 = ERR3_1 + ERR3_2;
   public final static String ERR4 = ERR3_1 + "\n" + ERR3_2;
   
   /////Nom, prenom, informations telephone
   public final static String [] MSG_ERR_NM_TEL = {ERR1 + "le nom" + ERR2 
	   + LG_CH[4] + ERR3, ERR1 + "le prenom" + ERR2 + LG_CH[4] + ERR3, ERR1 
	   + "le numero" + ERR2_1 + "exactement 7 ou 10 chiffres... " + ERR3_2};
   
   /////Courriel, informations Adresse(numero porte, rue, numero App)
   public final static String [] MSG_ERR_COUR_ADR1 = {ERR1 + "le courriel" 
		   + ERR2_1 + "entre 5 et 100" + ERR3, ERR1 + "le numero de porte" 
		   + ERR2 + LG_CH[3] + ERR3, ERR1 + "le nom de la rue" + ERR2
		   + LG_CH[5] + ERR3};
   
   /////Informations Adresse(ville, province, pays, code ppostal)
   public final static String [] MSG_ERR_ADR2 = {ERR1 + "le nom de la ville"
		   + ERR2 + LG_CH[5] + ERR3, ERR1 + "le nom de la province/etat" + ERR2
		   + LG_CH[5] + ERR4,ERR1 + "le nom du pays" + ERR2 + LG_CH[5] + ERR3};
		   
   public final static String MSG_ERR_REP = ERR1 + "repondez par (o)ui ou "
   		+ "(n)on !";
   public final static String MSG_ERR_EXCEP_TEL = "Numéro de téléphone " 
   		+ "invalide";
   public final static String MSG_ERR_EXCEP_ADR = "Adresse invalide";
   
   public final static String MSG_ERR_SS_MENU = ERR1
		   + "entrez une valeur entre 1 et 2... " + ERR3_2 + "\n";
   
   //REPONSE FAVORI
   public final static boolean REP_FAV = true;
   //Affichage de tous les contacts
   public final static boolean AFF_TT = true;
   //Message aucun contacts trouves
   public final static String MSG_AUCUN_CONT = "\nLe carnet ne contient aucun "
   		+ "contact portant le nom : ";
   
   //Caracteres utilises
   public final static char [] CAR = {'*', '(', ')', ' '};
   
   //-------------------------------------------------------
   //METHODES D'ENREGISTREMENT DES CONTACTS DANS UN FICHIER 
   //-------------------------------------------------------
   
   /**
    * Lit la premiere ligne du fichier ficContacts qui est un nombre entier, 
    * et le retourne. Ce nombre correspond au nombre de contacts enregistres
    * dans le fichier. Si ficContacts n'existe pas, retourne 0.
    * @param ficContacts le fichier dont on veut lire la premiere ligne.
    * @return l'entier lu sur la premiere ligne du fichier ficContacts s'il
    *         existe, 0 sinon.
    */
   public static int lireFichierNbrContacts (File ficContacts) {
      int nbr = 0;
      BufferedReader in;
      if (ficContacts.exists()) {
         try {
            in = new BufferedReader(new FileReader(ficContacts));
            nbr = Integer.parseInt(in.readLine().trim());
            in.close(); 
         } catch (IOException e) {
            //ne se produira pas
         } 
      }
      return nbr;
   }
   
   /**
    * Lit les contacts enregistres dans le fichier ficContacts et retourne
    * un tableau contenant ces contacts. Si le fichier n'existe pas, ou s'il ne
    * contient aucun contact, retourne un tableau de longueur LNG_INIT_TAB_CONTACTS 
    * dont toutes les cases sont initialisees a null.
    * @param ficContacts le fichier dans lequel lire les contacts.
    * @return un tableau contenant les contacts lus.
    */
   public static Contact [] lireFichierContacts (File ficContacts, 
                                                  int nbrContacts) {
      BufferedReader in;
      Contact [] contacts = new Contact[LNG_INIT_TAB_CONTACTS];
      Contact contact;
      String nom;
      String prenom;
      String tmp;
      int i = 0;
      
      if (ficContacts.exists() && nbrContacts != 0) {
         contacts = new Contact[nbrContacts + 2];
         try {
            in = new BufferedReader(new FileReader(ficContacts));
            in.readLine(); //sauter le nombre de contacts
            
            while (in.ready()) {
               nom = in.readLine();
               prenom = in.readLine();
               contact = new Contact(nom, prenom);
               lireTelephonesDansFic(in, contact);
               lireAdresseDansFic(in, contact);
               
               tmp = in.readLine();
               if (!tmp.equals("null"))
                  contact.setCourriel(tmp);
               
               tmp = in.readLine();
               if (tmp.equals("true"))
                  contact.setFavori(true);
               
               contacts[i] = contact; 
               i++;
            }
            in.close();
            
         } catch (IOException | ContactInvalideException 
                 | TelephoneInvalideException | AdresseInvalideException e) {
            //ne devrait pas se produire
         } 
      }
      return contacts;
   }
   
   /**
    * Lit l'adresse d'un contact dans le fichier associe au parametre in, puis
    * assigne l'adresse lue au contact donne.
    * @param in le flux associe au fichier a lire.
    * @param contact le contact auquel assigner l'adresse lue.
    * @throws AdresseInvalideException si l'adresse lue est invalide
    * @throws IOException s'il se produit une erreur d'entree/sortie.
    */
   private static void lireAdresseDansFic(BufferedReader in, Contact contact) 
                                  throws AdresseInvalideException, IOException {
      String tmp;
      String [] tokens;
      Adresse adresse = null;
      tmp = in.readLine();
      
      if (!tmp.equals("null")) {
         adresse = new Adresse();
         tokens = tmp.split(SEP);
         adresse.setNoPorte(tokens[0]);
         adresse.setNomRue(tokens[1]);
         tmp = tokens[2];
         if (tokens[2].equals("null"))
            tmp = null;
         adresse.setApt(tmp);
         adresse.setVille(tokens[3]);
         adresse.setProvinceEtat(tokens[4]);
         adresse.setPays(tokens[5]);
         tmp = tokens[6];
         if (tmp.equals("null"))
            tmp = null;
         adresse.setCodePostal(tmp);
      }
      contact.setAdresse(adresse);
      
   }
   
   /**
    * Lit les telephones d'un contact dans le fichier associe au parametre in, 
    * puis assigne les telephones lus au contact donne.
    * @param in le flux associe au fichier a lire.
    * @param contact le contact auquel assigner les telephones lus.
    * @throws TelephoneInvalideException si un telephone lu est invalide.
    * @throws IOException s'il se produit une erreur d'entree/sortie.
    */
   private static void lireTelephonesDansFic (BufferedReader in, Contact contact)
            throws TelephoneInvalideException, IOException {
      Telephone tel;
      String type;
      String no;
      String poste;
      String nbr = in.readLine().trim();
      int nbrTel = Integer.parseInt(nbr);
      for (int i = 0 ; i < nbrTel ; i++) {
         type = in.readLine();
         no = in.readLine();
         poste = in.readLine();
         if (poste.equals("null"))
            poste = null;
         tel = new Telephone(type, no, poste);
         contact.ajouterTelephone(tel);
      }
   }
   
   //-------------------------------------------------------
   //METHODES DE SAUVEGARDE DES CONTACTS DANS UN FICHIER 
   //-------------------------------------------------------
   
   /**
    * Ecrit tous les contacts non null presents dans contacts, dans le fichier
    * ficContacts. La methode ecrit d'abord le nombre de contacts a ecrire 
    * (nbrContacts) sur la premiere ligne du fichier, et ecrit ensuite les 
    * contacts non null, l'un a la suite de l'autre. Si le fichier ficContacts 
    * existe, il sera ecrase.
    * @param contacts les contacts a ecrire (sauvegarder).
    * @param ficContacts le fichier dans lequel ecrire les contacts.
    * @param nbrContacts le nombre de contacts non null dans contacts.
    */
   public static void sauvegarderContacts (Contact[] contacts, File ficContacts,
           int nbrContacts) {
      PrintWriter out;
      try {
         //si le fichier existe, il sera ecrase
         out = new PrintWriter(new FileWriter(ficContacts));
         out.println(nbrContacts);
         
         for (Contact c : contacts) {
            if (c != null) {
               out.println(c.getNom());
               out.println(c.getPrenom());
               out.println(c.getNbrTelephones());
               sauvegarderTelephonesDansFic(c, out);
               sauvegarderAdresseDansFic(c, out);
               out.println(c.getCourriel());
               out.println(c.isFavori());
            }
         }
         out.close();
         
      } catch (IOException e) {
         //ne se produira pas.
      }
   }
   
   /**
    * Ecrit l'adresse du contact donne dans le fichier associe au parametre 
    * out. Si l'adresse du contact est null, la methode ecrit "null".
    * @param contact le contact possedant l'adresse qu'on veut ecrire dans le 
    *                fichier.
    * @param out le flux associe au fichier dans lequel on veut ecrire l'adresse
    *            du contact.
    */
   private static void sauvegarderAdresseDansFic(Contact contact,
		   PrintWriter out) {
      
      if (contact.getAdresse() != null) {
         out.print(contact.getAdresse().getNoPorte() + SEP 
                  + contact.getAdresse().getNomRue() + SEP
                  + contact.getAdresse().getApt() + SEP
                  + contact.getAdresse().getVille() + SEP
                  + contact.getAdresse().getProvinceEtat() + SEP
                  + contact.getAdresse().getPays() + SEP
                  + contact.getAdresse().getCodePostal() + "\n"
          );
      } else {
         out.println("null");
      }
   }

   /**
    * Ecrit les telephones du contact donne dans le fichier associe au parametre
    * out. Si le contact n'a aucun telephone, la methode n'ecrit rien dans 
    * le fichier.
    * @param contact le contact possedant les telephones qu'on veut ecrire dans
    *                le fichier.
    * @param out le flux associe au fichier dans lequel on veut ecrire les
    *            telephones de contact.
    */
   private static void sauvegarderTelephonesDansFic(Contact contact,
		   PrintWriter out) {
      int i = 1;
      while (contact.obtenirIemeTelephone(i) != null) {
         out.println(contact.obtenirIemeTelephone(i).getType());
         out.println(contact.obtenirIemeTelephone(i).getNumero());
         out.println(contact.obtenirIemeTelephone(i).getPoste());
         i++;
      }
   }

   //-------------------------------------------------------
   //AUTRES METHODES UTILITAIRES
   //-------------------------------------------------------
   
   /**
    * Affiche une breve presentation de ce logiciel.
    */
   public static void presenterLogiciel() {
      System.out.println(PRESENTATION);
   }
   
   /**
    * Affiche le msg donne, puis demande a l'utilisateur d'appuyer sur ENTREE
    * pour continuer.
    * @param msg le message a afficher.
    */
   public static void pause (String msg) {
      System.out.println(msg);
      System.out.print(MSG_PAUSE);
      Clavier.lireFinLigne();
   }
   
   /**
    * Construit une ligne formee avec le symbole donne, de la longueur donnee.
    * Exemple : 
    *    ligne('*', 10)  retourne la chaine "**********".
    * 
    * @param symbole le symbole a utiliser pour construire la ligne.
    * @param longueur le nombre de symboles dans la ligne construite.
    * @return une ligne formee avec le symbole donne, de la longueur donnee.
    */
   public static String ligne(char symbole, int longueur) {
      String ligne = "";
      for (int i = 0 ; i < longueur ; i++) {
          ligne = ligne + symbole;
      }
      return ligne;
   }
   
   /**
    * Encadre le titre donne dans une boite formee du symbole donne.
    * 
    * Exemple : boiteTitre('#', "TITRE") retourne la chaine qui lorsqu'affichee
    *           donnera ceci : 
    * 
    *          #########
    *          # TITRE #
    *          #########
    * 
    * @param symbole le symbole avec lequel former la boite (le cadre).
    * @param titre le titre a encadrer.
    * @return la chaine representant le titre encadre dans une boite formee
    *         avec le symbole donne.
    */
   public static String boiteTitre(char symbole, String titre) {
      String titreBoite = "\n";
      int longueur;
      if (titre != null && titre.length() > 0) {
         longueur = titre.length() + 4;
         titreBoite = titreBoite + ligne(symbole, longueur)
                 + "\n" + symbole + " " + titre.toUpperCase() 
                 + " " + symbole + "\n" + ligne(symbole, longueur);
      }
      return titreBoite + "\n";
   }
   
   /**
    * Affiche le msgSol donne (question) puis saisit, et valide la reponse 
    * (oui ou non) de l'utilisateur. Une reponse valide est 'o' ou 'O' pour OUI
    * et 'n' ou 'N' pour non.
    * @param msgSol le message de sollicitation (une question) demandant une 
    *               reponse oui ou non.
    * @param msgErr le message d'erreur affiche lors d'un entree invalide.
    * @return true si l'utilisateur a repondu oui, false sinon.
    */
   public static boolean questionOuiNon(String msgSol, String msgErr) {
      String rep; //reponse de l'utilisateur

      System.out.print(msgSol);
      rep = Clavier.lireString();

      while (!rep.equalsIgnoreCase("O") && !rep.equalsIgnoreCase("N")) {
         System.out.println(msgErr);
         System.out.print(msgSol);
         rep = Clavier.lireString();
      }
      return rep.equalsIgnoreCase("O");
   }
   
   /**
    * Affiche le msgSol donne pour solliciter une chaine de caracteres, et 
    * retourne la chaine entree par l'utilisateur.
    * @param msgSol le message de sollicitation de la chaine de caracteres.
    * @return la valeur entree par l'utilisateur.
    */
   public static String lireChaine (String msgSol) {
      String chaine;
      System.out.print(msgSol);
      chaine = Clavier.lireString();
      return chaine;
   }
   
   /**
    * Sollicite, lit, et valide une chaine de caracteres qui doit etre de 
    * longueur comprise entre lngMin et lngMax inclusivement.
    * @param msgSol le message de sollicitation de la chaine de caracteres.
    * @param msgErr le message d'erreur affiche lorsque la chaine entree est 
    *               invalide.
    * @param lngMin longueur minimum valide de la chaine entree.
    * @param lngMax longueur maximum valide de la chaine entree.
    * @return la chaine entree, de longueur valide.
    */
   public static String validerChaine (String msgSol, String msgErr, 
           int lngMin, int lngMax) {
      String chaine;
      do {
         System.out.print(msgSol);
         chaine = Clavier.lireString();
         if (chaine.length() < lngMin || chaine.length() > lngMax) {
            System.out.println(msgErr);
         }
      } while (chaine.length() < lngMin || chaine.length() > lngMax);
      
      return chaine;
   }
   
 //-------------------------------------------------------
   //METHODES UTILITAIRES SUPPLÉMENTAIRES
   //-------------------------------------------------------
   
   /**
    * Cette methode permet de solliciter, de lire au clavier, et de valider 
    * avec la methode numeroTelValide() de la classe Telephone la chaine numero
    *  de telephone
    * @return la chaine entree au clavier et valide
    */
   public static String validerNumTel() {
	   String numero;
	   boolean numValide;
	   do {
		   numero = lireChaine(MSG_SOL_NM_TEL[2]);
		   numValide = Telephone.numeroTelValide(numero);
		   if (!numValide) {
			   System.out.println(MSG_ERR_NM_TEL[2]);
		   }
	  } while (!numValide);
	   return numero;
   }
   
   /**
    * Cette methode permet de solliciter et de lire au clavier les informations
    * d'un telephone et aussi de valider avec la methode validerNumTel() le 
    * numero de telephone, puis construit un objet Telephone avec les 
    * informations de type, numero, poste
    * @return un objet Telephone avec le type, le numero valide et le poste
    */
   public static Telephone infoTelephone() {
	   Telephone tel = null;
	   String type = lireChaine(MSG_SOL_NM_TEL[3]);
	   String numero = validerNumTel();
	   String poste = lireChaine(MSG_SOL_NM_TEL[4]);
	   
	   try {
		   tel = new Telephone(type, numero, poste);
	   } catch (TelephoneInvalideException e) {
			System.out.println(MSG_ERR_EXCEP_TEL);
		}
	   	return tel;
   }
   
   /**
    * Cette methode permet de solliciter et de lire au clavier les informations
    * d'une adresse et aussi de valider certaines de ces informations puis 
    * construit un objet Adresse avec les informations
    * @return un objet Adresse avec le numero de porte, la rue, le numero 
    * d'appartement, la ville, la prvovince/etat, le pays et le code postal
    */
   public static Adresse infoAdresse() {
	   Adresse adresse = null;
	   String numPorte = validerChaine(MSG_SOL_COUR_ADR1[1], 
			   MSG_ERR_COUR_ADR1[1], LG_CH[1], LG_CH[3]);
	   String rue = validerChaine(MSG_SOL_COUR_ADR1[2], 
			   MSG_ERR_COUR_ADR1[2], LG_CH[1], LG_CH[5]);
	   String numApp = lireChaine(MSG_SOL_COUR_ADR1[3]);
	   String ville = validerChaine(MSG_SOL_ADR2[0], MSG_ERR_ADR2[0],
			   LG_CH[1], LG_CH[5]);
	   String provinceEtat = validerChaine(MSG_SOL_ADR2[1], MSG_ERR_ADR2[1],
			   LG_CH[1], LG_CH[5]);
	   String pays = validerChaine(MSG_SOL_ADR2[2], MSG_ERR_ADR2[2],
			   LG_CH[1], LG_CH[5]);
	   String codePostal = lireChaine(MSG_SOL_ADR2[3]);
	   
	   try {
		   adresse = new Adresse(numPorte, rue, numApp, ville, provinceEtat, 
				   pays, codePostal);
	   } catch (AdresseInvalideException e) {
			System.out.println(MSG_ERR_EXCEP_ADR);
	   }
	   return adresse;
   }
   
   /**
    * Cette methode permet de placer un contact dans la 1ere place libre du 
    * tableau de contacts, ou de doubler d'abord la capacité du tableau s'il
    * n'y a pas de place libre avant de placer le contact
    * @param  contacts  un tableau de contact 
    * @param  unContact  un nouveau contact ajouté au tableau de contacts
    * @return  un tableau de contact avec le nouveau contact ajouté
    */
   public static Contact[] ajoutContactTab(Contact [] contacts,
		   Contact unContact) {
	   Contact [] tabContacts;
	   int i = 0;
	   
	   //Trouve ou non la 1ere place libre dans le tableau
	   while (i < contacts.length && contacts[i] != null) {
		   i++;
	   }
	   
	   //On place unContact dans la 1ere place libre du tableau de contacts
	   //s'il y a lieu, sinon on double la longueur du tableau de contacts 
	   //avant de placer unContact a la 1ere place libre
	   if (i < contacts.length) {
		   contacts[i] = unContact;//ajoute le nouveau contact
		   tabContacts = contacts;
	   } else {
		   tabContacts = new Contact[2 * contacts.length];
		   for (int j = 0; j < contacts.length; j++) {
			   tabContacts[j] = contacts[j];
		   }
		   tabContacts[i] = unContact;//ajoute le nouveau contact
	   }
	   return  tabContacts;
   }
   
   /**
    * Cette methode permet d'afficher tous les contacts du carnet de contacts
    * ou seulement les contacts favoris
    * @param  contacts un tableau de contact 
    * @param  nbrContacts nombre de contacts dans le tableau de contacts
    * @param  afficherTous  un booleen qui permet d'afficher tous les contacts
    * s'il est a true, ou les contacts favoris s'il est a false
    */
   public static void afficherTousConacts(Contact [] contacts, 
		   int nbrContacts, boolean afficherTous) {
	   int nbrContactsAffiches;
	   int compteurContacts = 0;
	   
	   if (afficherTous) nbrContactsAffiches = nbrContacts;
	   else nbrContactsAffiches = Contact.obtenirNbrContactsFavoris();
	   
	   System.out.println(TITRES[0] + CAR[1] + nbrContactsAffiches + CAR[2] 
			   + "\n");
	   
	   for (int i = 0; i < contacts.length; i++) {
		   if (contacts[i] != null && (afficherTous || contacts[i].isFavori())) {
			   System.out.println(contacts[i]);
			   compteurContacts++;
			   if(compteurContacts == nbrContactsAffiches) {
				   pause(TITRES[3]);
			   } else {
				   pause("");
			   }
			   
		   }
	   }
	   if (nbrContactsAffiches == 0) {
		   pause(TITRES[3]);
	   }
   }

   //-----------------------------
   //MÉTHODES À COMPLÉTER
   //-----------------------------
   
   /**
    * Cette methode permet d'ajouter un nouveau contact au tableau des 
    * contacts, apres avoir sollicite et lu ou valider les elements du nouveau
    * conctact à ajouter
    *  
    * @param  contacts un tableau de contact 
    * @return  le tableau de contact contenant le nouveau contact ajoute
    */
   	public static Contact[] ajouterContact (Contact [] contacts) {
		String nom;
		String prenom;
		Contact contact = null;
		String courriel;
		
		System.out.println(boiteTitre(CAR[0], TITRES[1]));
		  
		nom = validerChaine(MSG_SOL_NM_TEL[0], MSG_ERR_NM_TEL[0], LG_CH[1], 
				  LG_CH[4]);
		prenom = validerChaine(MSG_SOL_NM_TEL[1], MSG_ERR_NM_TEL[1], LG_CH[1], 
				  LG_CH[4]);
		  
		try {  
			//Creation de contact
			contact = new Contact(nom, prenom);
		} catch (ContactInvalideException e) {
			System.out.println(e.getMessage());
		} 		
		
		//Ajout possible de telephone
		while (questionOuiNon(MSG_SOL_QUEST[0], MSG_ERR_REP)) {
			contact.ajouterTelephone(infoTelephone());
		}
		
		//Ajout possible d'adresse
		if (questionOuiNon(MSG_SOL_QUEST[1], MSG_ERR_REP)) {
			contact.setAdresse(infoAdresse());
		}
		
		//Ajout possible de courriel
		if (questionOuiNon(MSG_SOL_QUEST[2], MSG_ERR_REP)) {
			courriel = validerChaine(MSG_SOL_COUR_ADR1[0], 
					MSG_ERR_COUR_ADR1[0], LG_CH[2], LG_CH[0]);
			contact.setCourriel(courriel);
		}
			
		//Mettre le contact a favori ou non
		if (questionOuiNon(MSG_SOL_QUEST[3], MSG_ERR_REP)) {
			contact.setFavori(REP_FAV);
		}
		
		//Ajout du contact au tableau de contacts
		contacts = ajoutContactTab(contacts, contact);
		
		return contacts;
   }
   
   	/**
     * Cette methode permet de supprimer un ou plusieurs contacts du tableau 
     * des contacts, apres sollicitation, lecture et validation du nom et du
     * prenom de chaque contact a supprimer
     *  
     * @param  contacts un tableau de contact 
     * @return  le nombre de contacts supprimes
     */
   public static int supprimerContact(Contact[] contacts) {
	   String nom;
	   String prenom;
	   int nbrContactsTrouv = 0;
	   int nbrContactsSupp = 0;
	   int nbrContactsFavSupp = 0;
	   
	   System.out.println(boiteTitre(CAR[0], TITRES[2]));
	   
	   nom = validerChaine(MSG_SOL_NM_TEL[0], MSG_ERR_NM_TEL[0], LG_CH[1], 
				  LG_CH[4]);
		prenom = validerChaine(MSG_SOL_NM_TEL[1], MSG_ERR_NM_TEL[1], LG_CH[1], 
				  LG_CH[4]);
		
		for (int i = 0; i < contacts.length; i++) {
			if (contacts[i] != null 
					&& contacts[i].getNom().equalsIgnoreCase(nom) 
					&& contacts[i].getPrenom().equalsIgnoreCase(prenom)) {
				System.out.println(contacts[i]);
				nbrContactsTrouv++;
				if (questionOuiNon(MSG_SOL_QUEST[5], MSG_ERR_REP)) {
					if (contacts[i].isFavori()) {
						nbrContactsFavSupp++;
					}
					contacts[i] = null;
					nbrContactsSupp++;
				}
			}
		}
		
		if (nbrContactsTrouv == 0) {
			System.out.println(MSG_AUCUN_CONT + nom.toUpperCase() + CAR[3] 
					+ prenom.toUpperCase());
			pause("");
		}
		Contact.modifierNbrContactsFavoris(Contact.obtenirNbrContactsFavoris()
				- nbrContactsFavSupp);
		
		return nbrContactsSupp; 
   }
   
   /**
    * Cette methode permet de supprimer tous les contacts du tableau des 
    * contacts 
    *  
    * @param  contacts un tableau de contact 
    * @return  true si tous les contacts sont supprimes et false sinon
    */
   public static boolean viderCarnet(Contact[] contacts) {
      boolean carnetVide = questionOuiNon(MSG_SOL_QUEST[4], MSG_ERR_REP);
      
      if (carnetVide) {
    	  contacts = null;
    	  Contact.modifierNbrContactsFavoris(0);
      }
	  
      return carnetVide; 
   }
   
   /**
    * Cette methode permet d'afficher tous les contacts du tableau de contacts
    *  
    * @param  contacts un tableau de contact 
    * @param  nbrContacts est le nombre de contacts dans le tableau de contacts 
    */
   public static void afficherContacts (Contact [] contacts, int nbrContacts) {
		  
		//Choix du sou-menu
		String choix;
		  
		System.out.println(boiteTitre(CAR[0], TITRES[4]));
		
		do {
			System.out.print(SOUS_MENU_AFF_CONTACTS);
			choix = Clavier.lireString();
		 
			switch (choix) {
		    	case "1" : 
		    		afficherTousConacts(contacts, nbrContacts, AFF_TT);
		    		break;
		   
		    	case "2" :
		    		afficherTousConacts(contacts, nbrContacts, !AFF_TT);
		           break;
		        default : 
		            System.out.println(MSG_ERR_SS_MENU);
		      }
		      
		   } while (!choix.equals("1") && !choix.equals("2"));

   }
   

   /**
    * Point d'entree de l'application de gestion d'un carnet de contacts 
    * personnels.
    * @param args (aucun)
    */
   public static void main (String [] args) {

       //nombre de contacts dans le tableau de contacts
      int nbrContacts = lireFichierNbrContacts(FIC_CONTACTS);
      
      //tableau de contacts
      Contact [] contacts = lireFichierContacts(FIC_CONTACTS, nbrContacts);
      
      //Choix au menu principal
      String choix;

      presenterLogiciel();
      
      do {
         System.out.print(MENU);
         choix = Clavier.lireString();
         
         switch (choix) {
            case "1" : 
               contacts = ajouterContact(contacts);
               nbrContacts++;
               break;
               
            case "2" :
               nbrContacts = nbrContacts - supprimerContact(contacts);
               break;
               
            case "3" : 
               if (viderCarnet(contacts)) {
                  nbrContacts = 0;
                  contacts = new Contact[LNG_INIT_TAB_CONTACTS];
               }
               break;
               
            case "4" :
               afficherContacts(contacts, nbrContacts);
               break;
               
            case MENU_MAX :
               //Sauvegarde des contacts dans le fichier FIC_CONTACTS
               sauvegarderContacts (contacts, FIC_CONTACTS, nbrContacts);
               break;
            default : 
               System.out.println(MSG_ERR_MENU);
         }
         
      } while (!choix.equals(MENU_MAX));
      
      //afficher la fin du programme
      System.out.println(MSG_FIN_PROG);
   }
   
} //fin classe CarnetContacts
