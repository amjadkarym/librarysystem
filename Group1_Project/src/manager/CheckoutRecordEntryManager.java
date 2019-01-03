package manager;

import java.util.List;
import java.util.Map;

import model.dataaccess.DataAccessFacade;
import model.domain.Book;
import model.domain.CheckoutRecordEntry;

public class CheckoutRecordEntryManager {

	DataAccessFacade dao = new DataAccessFacade();

	public void save(CheckoutRecordEntry checkoutRecordEntry) {
		dao.saveNewCheckoutRecordEntry(checkoutRecordEntry);
	}

	public void saveList(List<CheckoutRecordEntry> entities) {
	}

	public Map<String, CheckoutRecordEntry> getAll() {
		return dao.readCheckoutRecordEntryMap();
	}

	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

}
