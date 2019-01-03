package manager;

import java.util.List;
import java.util.Map;

import model.dataaccess.DataAccessFacade;
import model.domain.Book;
import model.domain.CheckoutRecord;

public class CheckoutRecordManager {

	DataAccessFacade dao = new DataAccessFacade();

	public void save(CheckoutRecord checkoutRecord) {
		dao.saveNewCheckoutRecord(checkoutRecord);
	}

	public void saveList(List<CheckoutRecord> entities) {
	}

	public Map<String, CheckoutRecord> getAll() {
		return dao.readCheckoutRecordMap();
	}

	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

	public List<CheckoutRecordEntryManager> getCheckoutRecordEntires(String entryId) {

		return null;
	}

}
