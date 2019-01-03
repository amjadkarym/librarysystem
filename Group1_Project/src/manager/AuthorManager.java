package manager;

import java.util.List;
import java.util.Map;

import model.dataaccess.DataAccessFacade;
import model.domain.Author;

public class AuthorManager {

	DataAccessFacade dao = new DataAccessFacade();

	public void save(Author author) {
		dao.saveNewAuthor(author);
	}

	@SuppressWarnings("static-access")
	public void saveList(List<Author> entities) {
		dao.loadAuthorMap(entities);
	}

	public Map<String, Author> getAll() {
		return dao.readAuthorMap();
	}

	public Author getById(Long id) {
		return null;
	}

	public void deleteById(Long id) {

	}

}
