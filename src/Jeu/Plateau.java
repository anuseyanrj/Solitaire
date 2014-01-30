package Jeu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Plateau 
{

	Pion[][] tableau;
	int[] tableauInterdit;
	boolean caseRempli;
	boolean caseVide;
	boolean modeTriche;
	int pion;
	int taille;
	
	//initialisation du plateau
	public Plateau(int taille, boolean triche) 
	{
		this.taille = taille;
		tableau = new Pion[this.taille][this.taille];
		tableauInterdit = new int[this.taille];
		caseRempli = true;
		caseVide = false;
		modeTriche = triche;
		pion = 32;
		remplirTableau();
	}
	
	// Setter
	public void setContenuTableau(int i, int j, Pion contenu)
	{
		tableau[i][j] = contenu;
	}
	
	//Getter
	public Pion getContenuTableau(int i, int j)
	{
		return tableau[i][j];
	}
	
	//Premieres colonnesexclus du plateau
	public void SupprimerDeuxPremiersColonne(int i)
	{
		for (int j = 0; j < taille/2 - 1; j++) 
			setContenuTableau( i,  j, new Pion(caseVide));
	}
	
	//Dernieres colonnes exclus du plateau
	public void SupprimerDeuxDerniersColonnes(int i)
	{
		for (int j = taille - taille/2 + 1; j < tableau.length; j++) 
			setContenuTableau( i,  j, new Pion(caseVide));
	}
	
	//Premier lancement.
	public void remplirTableau()
	{
		int indice = 0;
		for (int i = 0; i < tableau.length; i++) 
		{
			for (int j = 0; j < tableau.length; j++) 
				setContenuTableau( i,  j, new Pion(caseRempli));
			
			//Si les deux premieres lignes ou si les deux dernieres lignes alors  
			if(i<taille/2 - 1 || i >taille - taille/2 )
			{
				tableauInterdit[indice]=i;
				SupprimerDeuxPremiersColonne(i);
				SupprimerDeuxDerniersColonnes(i);
				indice++;
			}
		}
		//Case vide au centre du plateau.
		setContenuTableau(tableau.length/2, tableau.length/2, new Pion(false));
	}
	
	//Vérification de la case d'arrivee du déplacement
	//Si elle fait parti du tableauInterdit, la méthode renvoie TRUE
	//Dans ce cas, le déplacement est refusé à l'utilisateur
	public boolean FiltrerTableauInterdit(int x2, int y2)
	{
		for (int i = 0; i < tableauInterdit.length; i++) 
			for (int j = 0; j < tableauInterdit.length; j++) 
				if (tableauInterdit[i]==x2 && tableauInterdit[j] == y2) 
					return true;
		return false;
	}
	
	//Controle du saisi de l'utilisateur,
	//Si il est bien compris dans les plages du tableau entre 0 et la taille - 1
	public boolean ControlerSaisiUtilisateur(int x1, int y1, int x2, int y2)
	{		
		return (taille - 1 >= x1 && x1>=0) && (taille - 1 >= y1 && y1>=0) && (taille - 1 >= x2 && x2>=0) && (taille - 1 >= y2 && y2>=0) ;  
	}
	
	// Affichage du plateau.
	public void afficherTableau()
	{
		
		for (int i = 0; i < tableau.length+1; i++) 
		{ 
			if (i< tableau.length) 
			{
				if (i<9) 				
					System.out.print(i + 1 +"  ");
				else 
					System.out.print(i + 1 +" ");
			}
			else 
				System.out.print("  ");
			for (int j = 0; j < tableau.length; j++) 
			{
				if (i<tableau.length) 
				{				
					if(getContenuTableau(i,j).getPion())
						System.out.print("0  ");
					else
						System.out.print("   ");
				}
				else 
				{
					if (j<9) 				
						System.out.print(j + 1 +"  ");
					else 
						System.out.print(j + 1 +" ");					
				}
			}
			System.out.println();
		}
	}
		
	//Comparaison permettant de vérifier si le déplacement Verticale est possible 
	//entre la case de départ et la case d'arrivée.
	public boolean comparerCoordonneesVertical(int x1, int y1, int x2, int y2)
	{		
		return (Math.abs(y2-y1)==0 && Math.abs(x2-x1)==2) && !FiltrerTableauInterdit(x2, y2);
	}
	
	//Comparaison permettant de vérifier si le déplacement Horizontale est possible 
	//entre la case de départ et la case d'arrivée.
	public boolean comparerCoordonneesHorizontal(int x1, int y1, int x2, int y2)
	{
		return (Math.abs(y2-y1)==2 && Math.abs(x2-x1)==0) && !FiltrerTableauInterdit(x2, y2);
	}
	
	//Comparaison de possibilité de déplacement si le pion d'arrivée est vide et que le pion de départ est rempli.
	public boolean comparerCaseDepartEtCaseArrive(int x1, int y1, int x2, int y2)
	{
		return this.getContenuTableau(x1, y1).getPion() != this.getContenuTableau(x2, y2).getPion();
	}
	
	//Comparaison HORIZONTALEMENT de la case situé entre la case de départ et la case d'arrivée.
	//Si oui alors la case du "milieu" est effacé 
	//apres effacement renvoie TRUE
	public boolean comparerCaseAvantArriverHorizontal(int x1, int y1, int x2, int y2)
	{
		boolean c = false;
		if (this.getContenuTableau(x1, y1).getPion() == this.getContenuTableau(x2, (y2 + comparerCoordonnes(y1, y2))).getPion())
		{
			this.setContenuTableau(x2, y2 +comparerCoordonnes(y1, y2), new Pion(caseVide));
			c = true;
		}
		return c;
	}
	
	//Comparaison VERTICALEMENT de la case situé entre la case de départ et la case d'arrivée.
	//La case du milieu est déterminé à partir de la case d'arrivée -1 ou +1 en fonction du déplacement (voir comparerCoordonnées()).
	//Si oui alors la case du "milieu" est effacé si et seulement si la case de départ et la case du "milieu" ont la même valeur.
	//apres effacement renvoie TRUE
	public boolean comparerCaseAvantArriverVertical(int x1, int y1, int x2, int y2)
	{
		boolean c = false;
		if (this.getContenuTableau(x1, y1).getPion() == this.getContenuTableau((x2 + comparerCoordonnes(x1,x2)) , y2).getPion()) 
		{
			this.setContenuTableau(x2 + comparerCoordonnes(x1,x2), y2 , new Pion(caseVide));
			c = true;
		}
		return c;
	}
	//Abscisse ou ordonnee de la case de depart est superieur a l'abscisse de la case d'arrivee alors +1 si non -1
	private static int comparerCoordonnes(int x1, int x2)
	{
		if(x1>x2)
			return +1;
		else
			return -1;
	}
	
	//Si le déplacement vericalement ou horizontalement est possible alors 
	// TRUE 
	//sinon FALSE
	public boolean comparerMouvement(int x1, int y1, int x2, int y2)
	{
		return (comparerCoordonneesHorizontal(x1,y1,x2,y2) && comparerCaseDepartEtCaseArrive(x1,y1,x2,y2) && comparerCaseAvantArriverHorizontal(x1,y1,x2,y2)) || (comparerCoordonneesVertical(x1,y1,x2,y2) && comparerCaseDepartEtCaseArrive(x1,y1,x2,y2) && comparerCaseAvantArriverVertical(x1,y1,x2,y2));
	}

	//Si comparerMouvement est TRUE 
	//On appelle la fonction effecteurMouvement()
	//On soustrait pion = pion - 1
	//Et affichage d'un message
	public void mouvement(int x1, int y1, int x2, int y2)
	{
		String ok = "Déplacement impossible.";
		if(comparerMouvement(x1, y1, x2, y2))
		{
				this.effectuerMouvement(x1,y1,x2,y2);
				pion--;			
				ok = "Déplacement reussi.";
		}		
		System.out.println("\n" + ok);
		System.out.println("Nombre de pions restant : " +pion +"\n");
	}
	
	//on effecture la permutation entre la case de départ et la case d'arrivée.
	public void effectuerMouvement(int x1, int y1, int x2, int y2)
	{
		boolean c = this.getContenuTableau(x1, y1).getPion();
		this.setContenuTableau(x1, y1, this.getContenuTableau(x2, y2) ) ;
		this.setContenuTableau(x2, y2, new Pion(c));
	}
	
	//Boucle de jeu permettant à l'utilisateur de jouer, decontinuer à jouer ou de recommencer. 
	public void boucleDeJeu()
	{
		this.afficherTableau();
		while(pion >1)
		{
			
			try
			{
				/*System.out.println("1 - Continuer, Jouer.\n2 - Arreter.\n3 - Recommencer.");
				BufferedReader entree= new BufferedReader(new InputStreamReader(System.in)); 
				String a =  entree.readLine();
				int n =  Integer.parseInt(a);
				
				switch (n) 
				{
					case 1: */
						Jouer();						
					/*	break;
					case 2:
						pion = -1;
						break;
					case 3 :
						remplirTableau();
						this.boucleDeJeu();
	
					default:
						break;
				}*/
				
			}
			catch(Exception e)
			{
				System.out.println("La saisi est erronée.");
			}
		}
		if(pion == -1)
			System.out.println("Vous avez abandonné. Fin du jeu.");
		if(pion == 1)
			System.out.println("Vous avez gagné.");
	}	
	
	//Fonction permettant la saisie de l'utilisateur
	//Permettant de vérifier la saisie puis d'indiquer si le déplacement est possible ou non
	// Ou de redemander à l'utilisateur de saisi à nouveau des coordonées.
	public void Jouer()
	{
		try
		{
			BufferedReader entree= new BufferedReader(new InputStreamReader(System.in)); 
			System.out.println("Saisir ligne du pion a bouger");
			String a =  entree.readLine();			
			int x1 =  Integer.parseInt(a) - 1;
			System.out.println("Saisir colonne du pion a bouger");			
			String b = entree.readLine(); 
			int y1 =  Integer.parseInt(b) - 1;
			System.out.println("Saisir ligne du pion d'arrivée");
			String c =  entree.readLine();
			int x2 =  Integer.parseInt(c) - 1;
			System.out.println("Saisir colonne du pion d'arrivée");
			String d = entree.readLine(); 
			int y2 =  Integer.parseInt(d) - 1;
			if (ControlerSaisiUtilisateur(x1,y1,x2,y2)) 
			{
				this.mouvement(x1,y1,x2,y2);
				this.afficherTableau();
			}
			else
			{
				System.out.println("Les pions choisis ne font pas parti du jeu.");
				Jouer();
			}

		}
		catch(Exception e)
		{
			System.out.println("La saisi est erronnée.");
			Jouer();
		}
	}
	
	public static void main(String[] args)
	{
		Plateau s = new Plateau(15, false);
		s.boucleDeJeu();
	}
}
