import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BookManagerTest {
    private BookManager bookManager;
    
    
    @Before 
    public void setUp() {
        bookManager = new BookManager();
        LegacyDatabase.getBooks().clear(); // Esta linha garante que o banco inicie LIMPO antes de cada teste
    }
    
    @Test
    public void testarListagemSimplesDeLivrosComSucessoQuandoNãoVazia() {
        // Adicionamos um livro para que a lista NÃO esteja vazia
        //LegacyDatabase.addBookData("Clean Code", "Robert C. Martin", 2008, "Software", 3, 3, "A1", "ISBN-111");

        // O método deve rodar perfeitamente sem crashar caso tenha algum item na lista, se não tiver, ele crasha.
        bookManager.listBooksSimple();
    }
}
