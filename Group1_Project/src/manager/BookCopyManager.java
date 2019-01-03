package manager;

import java.util.List;
import java.util.Map;

import model.dataaccess.DataAccessFacade;
import model.domain.Book;
import model.domain.BookCopy;

public class BookCopyManager {

	DataAccessFacade dao = new DataAccessFacade();

	public void save(BookCopy bookCopy) {
		dao.saveNewBookCopy(bookCopy);
	}

	public void saveList(List<BookCopy> entities) {
	}

	public Map<String, BookCopy> getAll() {
		return dao.readBookCopyMap();
	}

	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}

}
