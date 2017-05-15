
/**
 * I N F x 1 2 0
 *
 * D�crivez votre classe TestContact ici.
 * 
 * @author (votre nom) 
 * @version (une date)
 *
 * (votre code permanent)
 * (votre adresse de courriel)
 */
 
public class TestContact {

    // Autres m�thodes s'il y a lieu
    
    public static void main (String[] params) {
    
        try {
        	//Contact contactTest = new Contact("n", "p");
        	Contact contactTest = new Contact("n", "p", new Telephone(null, "5145140000", null), null, null);
        	//System.out.println(contactTest.isFavori());
        	contactTest.ajouterTelephone(new Telephone("cellulaire", "4380000000", null));
        	contactTest.ajouterTelephone(new Telephone("cellulaire", "4384381111", null));
        	//contactTest.setFavori(true);
        	contactTest.setFavori(false);
        	contactTest.modifierTelephone(2, "res.", "4504501000", "");
        	contactTest.supprimerTelephone(1);
        	
        	System.out.println(contactTest.toString());
		} catch (ContactInvalideException e) {
			System.out.println(e.getMessage());
		} catch (TelephoneInvalideException e) {
			System.out.println("tel invalide");
		}
        
         //String strNull = null; System.out.println(strNull); //Test afficher str NULL
   } // main
    
} // TestContact
