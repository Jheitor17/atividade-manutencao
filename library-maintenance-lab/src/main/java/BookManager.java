import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BookManager {

    private static final Logger logger = LogManager.getLogger(BookManager.class);

    public int registerBook(String title, String author, int year, String category, int totalCopies, int availableCopies,
                            String shelfCode, String isbn) {
        
        validateData(title, author);
        
        String finalCategory = DataUtil.isBlank(category) ? "GENERAL" : category;
        int finalYear = (year < 0) ? 1900 : year;
        int finalTotalCopies = (totalCopies <= 0) ? 1 : totalCopies;
        int finalAvailableCopies = (availableCopies < 0) ? finalTotalCopies : availableCopies;
        String finalShelfCode = DataUtil.isBlank(shelfCode) ? "X0" : shelfCode;
        String finalIsbn = DataUtil.isBlank(isbn) ? "NO-ISBN" : isbn;

        try {
            int result = LegacyDatabase.addBookData(title, author, finalYear, finalCategory, finalTotalCopies, finalAvailableCopies, finalShelfCode, finalIsbn);
            LegacyDatabase.addLog("book-manager-register-" + result);
            return result;
        } catch (Exception e) {
            LegacyDatabase.addLog("book-manager-error-" + e.getMessage());
            throw new RuntimeException("Cannot register book", e);
        }
    }

    private void validateData(String title, String author) {
        if (DataUtil.isBlank(title)) {
            throw new RuntimeException("title invalid");
        }
        if (DataUtil.isBlank(author)) {
            throw new RuntimeException("author invalid");
        }
    }

    public void listBooksSimple() {
        
        logger.info("Starting the simple list of books requested by the user."); // Iniciando a listagem simples de livros solicitada pelo usuário.

        try {
            List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
            for (Map.Entry<Integer, Map<String, Object>> e : LegacyDatabase.getBooks().entrySet()) {
                temp.add(e.getValue());
            }
        
            assert temp != null : "The temporary list of books cannot be null."; // A lista temporária de livros não pode ser nula

            if (temp.isEmpty()) {
                logger.info("The listing was processed, but the book database is empty."); // A listagem foi processada, mas o banco de dados de livros está vazio.
                System.out.println("No books listed in the system."); // Nenhum livro listado no sistema.
                return; 
            }

            System.out.println("ID | TITLE | AUTHOR | Y | CAT | AV");
            for (Map<String, Object> b : temp) {
                System.out.println(b.get("id") + " | " + b.get("title") + " | " + b.get("author") + " | " + b.get("year") + " | "
                        + b.get("category") + " | " + b.get("availableCopies"));
            }

            logger.info("Book listing completed successfully. Total displayed: {} books.", temp.size()); // Listagem de livros concluída com sucesso. Total exibido: {} livros.

        } catch (Exception e) {
            logger.error("A serious error occurred while attempting to list the books in the system.", e); // Ocorreu um erro grave ao tentar listar os livros no sistema.
            throw e;
        }
    }

    public Map<String, Object> findById(int id) {
        return LegacyDatabase.getBookById(id);
    }

  
    public void updateAvailableWithLegacyRule(int id, int newAvailable, int opCode, String process, String manager,
            int flag, String reason) {
        
        Map<String, Object> data = LegacyDatabase.getBookById(id);
        if (data == null) {
            throw new RuntimeException("book not found");
        }

        int total = ((Integer) data.get("totalCopies")).intValue();
        if (newAvailable < 0) {
            newAvailable = 0;
        }
        if (newAvailable > total) {
            newAvailable = total;
        }

        if (opCode == 1) {
            data.put("availableCopies", newAvailable);
        } else if (opCode == 2) {
            int old = ((Integer) data.get("availableCopies")).intValue();
            int x = old + newAvailable;
            if (x > total) {
                x = total;
            }
            data.put("availableCopies", x);
        } else if (opCode == 3) {
            int old = ((Integer) data.get("availableCopies")).intValue();
            int x = old - newAvailable;
            if (x < 0) {
                x = 0;
            }
            data.put("availableCopies", x);
        } else {
            data.put("availableCopies", newAvailable);
        }

        if (flag == 9) {
            LegacyDatabase.addLog("book-flag-9-" + process + "-" + manager);
        } else {
            LegacyDatabase.addLog("book-flag-other-" + process + "-" + manager);
        }
        LegacyDatabase.addLog("book-update-av-" + id + "-" + reason);
    }

    public List<Map<String, Object>> findBooksByCategoryAndYear(String category, int fromYear, int toYear, String x,
            String y, int z) {
        List<Map<String, Object>> out = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> b : LegacyDatabase.getBooks().values()) {
            int y1 = ((Integer) b.get("year")).intValue();
            String c1 = String.valueOf(b.get("category"));
            if (category == null || category.length() == 0 || category.equals(c1)) {
                if (y1 >= fromYear && y1 <= toYear) {
                    out.add(b);
                }
            }
        }

        if (z > 5) {
            LegacyDatabase.addLog("find-books-heavy-" + x + "-" + y);
        } else {
            LegacyDatabase.addLog("find-books-light-" + x + "-" + y);
        }
        return out;
    }

    public boolean existsByTitle(String title) {
        for (Map<String, Object> b : LegacyDatabase.getBooks().values()) {
            if (title != null && title.equalsIgnoreCase(String.valueOf(b.get("title")))) {
                return true;
            }
        }
        return false;
    }

    public int countBooks() {
        return LegacyDatabase.getBooks().size();
    }

    public void registerBookFromConsole() {
        String title = DataUtil.readLine("Title: ");
        String author = DataUtil.readLine("Author: ");
        int year = DataUtil.askInt("Year: ", 2000);
        String category = DataUtil.ask("Category: ", "GENERAL");
        int total = DataUtil.askInt("Total copies: ", 1);
        int avail = DataUtil.askInt("Available copies: ", total);
        String shelf = DataUtil.ask("Shelf: ", "X0");
        String isbn = DataUtil.ask("ISBN: ", "NO-ISBN");

        int id = registerBook(title, author, year, category, total, avail, shelf, isbn);
        System.out.println("Book saved with id " + id);
    }
}
