package manager;

import java.util.List;
import java.util.Map;

import model.dataaccess.DataAccessFacade;
import model.domain.LibraryMember;

public class LibraryMemberManager {

	DataAccessFacade dao = new DataAccessFacade();

	public void save(LibraryMember member) {
		dao.saveNewMember(member);
	}

	public void saveList(List<LibraryMember> entities) {
		dao.loadMemberMap(entities);
	}

	public Map<String, LibraryMember> getAll() {
		return dao.readMemberMap();
	}

	public LibraryMember getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

}
