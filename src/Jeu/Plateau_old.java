package Jeu;

public class Plateau_old {

	boolean[][] tableau;
	boolean caseRempli;
	boolean caseVide;
	int pion;
	
	//initialisation du plateau
	public Plateau_old(int taille) 
	{
		tableau = new boolean[taille][taille];
		caseRempli = true;
		caseVide = false;
		pion = 32;
		remplirTableau();
	}
	
	// Setter
	public void setContenuTableau(int i, int j, boolean contenu)
	{
		tableau[i][j] = contenu;
	}
	
	//Getter
	public boolean getContenuTableau(int i, int j)
	{
		return tableau[i][j];
	}
	
	//Deux premieres colonnes
	public void SupprimerDeuxPremiersColonne(int i)
	{
		for (int j = 0; j < 2; j++) 
			setContenuTableau( i,  j, caseVide);
	}
	
	//Deux dernieres colonnes
	public void SupprimerDeuxDerniersColonnes(int i)
	{
		for (int j = 5; j < tableau.length; j++) 
			setContenuTableau( i,  j, caseVide);
	}
	
	//Premier lancement.
	public void remplirTableau()
	{
		for (int i = 0; i < tableau.length; i++) 
		{
			for (int j = 0; j < tableau.length; j++) 
				setContenuTableau( i,  j, caseRempli);
			
			//Si les deux premieres lignes ou si les deux dernieres lignes alors  
			if(i<2 || i>=5)
			{
				SupprimerDeuxPremiersColonne(i);
				SupprimerDeuxDerniersColonnes(i);
			}
		}
		//Case vide au centre du plateau.
		setContenuTableau(tableau.length/2, tableau.length/2, caseVide);
	}
	
	public void afficherTableau()
	{
		for (int i = 0; i < tableau.length; i++) 
		{
			for (int j = 0; j < tableau.length; j++) 
			{
				if(getContenuTableau(i,j))
					System.out.print("O ");
				else
					System.out.print("  ");
			}
			System.out.println();
		}
	}
		
	public static void main(String[] args)
	{
		Plateau_old s = new Plateau_old(7);
		s.afficherTableau();
		
		
	}


}
