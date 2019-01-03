package manager;

import java.util.List;
import java.util.Map;

import model.dataaccess.DataAccessFacade;
import model.domain.Address;
import model.domain.Book;

public class AddressManager {

	DataAccessFacade dao = new DataAccessFacade();

	public void save(Address address) {
		dao.saveNewAddress(address);
	}

	@SuppressWarnings("static-access")
	public void saveList(List<Address> entities) {
		dao.loadAddressMap(entities);
	}

	public Map<String, Address> getAll() {
		return dao.readAddress();
	}

	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
