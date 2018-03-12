package dweb.aut.i18n.vaseline.interfaces;

import java.util.Map;

public interface IVaselineUserOperations {
	
	void selectMenu(String menuLink);
	void validateMenuItems(String menuItem);
	void selectMenuProduct(String product);
	void readReview(String productItem, float expectedRating);
	void writeReview(String productItem, Map<String, String> reviewInfo);

}
