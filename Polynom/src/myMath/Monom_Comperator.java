package myMath;

import java.util.Comparator;

public class Monom_Comperator implements Comparator<Monom> {

	// ******** add your code below *********
	@Override
	public int compare(Monom o1, Monom o2) {
		// TODO Auto-generated method stub
		if(o1.get_coefficient() == o2.get_coefficient() && o1.get_power() == o2.get_power())
			return 0;
		else if(o1.get_power() != o2.get_power())
			if(o1.get_power() > o2.get_power())
				return 1;
			else
				return -1;
		else 
			if(o1.get_coefficient() > o2.get_coefficient())
				return 1;
			else
				return -1;
	}

}
