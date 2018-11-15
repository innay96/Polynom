package myMath;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {

	@Override
	public int compare(Monom o1, Monom o2) {
		/**Compare between 2 Monoms at some point
		 * @return positive number if  the first one is bigger
		 * @return negative number if the second one is bigger
		 * @return 0 if equals
		 */
		int pow1 = o1.getPower();
		int pow2 = o2.getPower();
		return (pow1-pow2);


	}
}	