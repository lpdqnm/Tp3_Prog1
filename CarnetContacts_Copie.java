
import java.io.*;

/**
 * A COMPLETER...
 * 
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
   private static void sauvegarderAdresseDansFic(Contact contact, PrintWriter out) {
      
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
   private static void sauvegarderTelephonesDansFic(Contact contact, PrintWriter out) {
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
   

   //-----------------------------
   //MÉTHODES À COMPLÉTER
   //-----------------------------
   
   public static Contact[] ajouterContact (Contact [] contacts) {
      
      //A COMPLETER
      return null; //pour compilation seulement... a enlever.
   }
   
   public static int supprimerContact(Contact[] contacts) {
      
      //A COMPLETER
      return 0; //pour compilation seulement... a enlever.
   }
   
   public static boolean viderCarnet(Contact[] contacts) {
      
      //A COMPLETER
      return true; //pour compilation seulement... a enlever.
   }
   
   public static void afficherContacts (Contact [] contacts, int nbrContacts) {
      
      //A COMPLETER
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
