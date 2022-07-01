package tw.com.core.menu;

import java.util.Comparator;


public class MenuDataComparator implements Comparator<MenuData> {

	@Override
	public int compare(MenuData arg0, MenuData arg1) {
		if (arg0.getSysOrder() > arg1.getSysOrder() ) {
			return 1;
		}
		else if (arg0.getSysOrder() < arg1.getSysOrder()) {
			return -1;
		}
		else {
			if (arg0.getModOrder() > arg1.getModOrder()) {
				return 1;
			}
			else if (arg0.getModOrder() < arg1.getModOrder()) {
				return -1;
			}
			else {
				if (arg0.getPgOrder() > arg1.getPgOrder()) {
					return 1;
				}
				else if (arg0.getPgOrder() < arg1.getPgOrder()) {
					return -1;
				}
			}
		}
		return 0;
	}

	
}