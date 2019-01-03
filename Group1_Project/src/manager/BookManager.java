package manager;

import java.util.List;
import java.util.Map;

import model.dataaccess.DataAccessFacade;
import model.domain.Book;

public class BookManager {

	DataAccessFacade dao = new DataAccessFacade();

	public void save(Book book) {
		dao.saveNewBook(book);
	}

	public void saveList(List<Book> entities) {
		dao.saveBookList(entities);
	}

	public Map<String, Book> getAll() {
		return dao.readBooksMap();
	}

	public Book getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
