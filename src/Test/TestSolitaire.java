package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import Jeu.Plateau;

public class TestSolitaire {

	@Test
	public void test() {
		Plateau p = new Plateau(7,false);
		p.ControlerSaisiUtilisateur(10, 2, 3, 65);
		p.mouvement(3, 1, 3, 3);
		p.afficherTableau();
		System.out.println("(3, 1, 3, 3)\n");
		p.mouvement(3, 3, 3,1);
		p.afficherTableau();
		System.out.println("(3, 3, 3,1)\n");
		p.mouvement(1, 3, 3,3);
		p.afficherTableau();
		System.out.println("(1, 3, 3,3)\n");
		p.mouvement(4, 3, 2,3);
		p.afficherTableau();
		System.out.println("(4, 3, 2,3)\n");
	}

}
