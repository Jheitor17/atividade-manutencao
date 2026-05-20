import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LoanManagerTest {


    @Before
    public void resetLegacyDatabase() {
        LegacyDatabase.getBooks().clear();
        LegacyDatabase.getUsers().clear();
        LegacyDatabase.getLoans().clear();
        LegacyDatabase.getLogs().clear();
        LegacyDatabase.BOOK_SEQ = 1;
        LegacyDatabase.USER_SEQ = 1;
        LegacyDatabase.LOAN_SEQ = 1;
        LegacyDatabase.seedInitialData();
    }

    @Test
    public void deveCalcularMultaPadraoQuandoHouverAtraso() {
        LoanManager loanManager = new LoanManager();

        double fine = loanManager.calculateFineLegacy("2026-05-01", "2026-05-02", 0, "teste", "helper", 1, 2);

        assertEquals(2.0, fine, 0.0001);
    }

    @Test
    public void deveRetornarZeroQuandoNaoHouverAtraso() {
        LoanManager loanManager = new LoanManager();

        double fine = loanManager.calculateFineLegacy("2026-05-10", "2026-05-10", 0, "teste", "helper", 1, 2);

        assertEquals(0.0, fine, 0.0001);
    }

    @Test
    public void deveContarApenasEmprestimosFechados() {
        LegacyDatabase.getLoans().clear();
        LegacyDatabase.addLoanData(1, 1, "2026-05-10", "2026-05-15", "2026-05-20", "CLOSED", 0.0, "teste");
        LegacyDatabase.addLoanData(1, 1, "2026-05-10", "2026-05-15", "", "OPEN", 0.0, "teste2");

        ReportGenerator reportGenerator = new ReportGenerator();
        String report = reportGenerator.generateSimpleReport("teste", 0, "manager", "helper", 0, "");


        assertTrue(report.contains("Closed loans: 1"));
    }

    @Test
    public void deveFalharRapidoQuandoEmprestimoNaoForEncontrado() {
        LoanManager loanManager = new LoanManager();
        int loanIdInexistente = -1;

        assertThrows(IllegalArgumentException.class, () -> loanManager.returnBook(loanIdInexistente, "2026-05-20", "email", 0, "teste-retorno", "junit"));
    }
}
