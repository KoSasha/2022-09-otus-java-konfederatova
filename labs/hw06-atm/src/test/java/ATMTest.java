import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.mapper.CellMapper;
import ru.otus.atm.service.ATM;
import ru.otus.atm.service.ATMService;
import ru.otus.atm.type.Cell;
import ru.otus.atm.type.Denomination;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ATMTest {

    private ATMService atm;
    private CellMapper cellMapper;

    @BeforeEach
    public void setUp() {
        cellMapper = new CellMapper();
        List<Cell> startCells = new ArrayList<>();
        startCells.add(cellMapper.createCell(Denomination.FIVE_THOUSAND, 1));
        atm = new ATM(startCells, new CellMapper(), 0, 0);
    }

    // проверка баланса
    @Test
    public void getBalanceTest() {
        int balance = atm.getBalance();
        int expectedBalance = 5000;

        assertThat(balance).isEqualTo(expectedBalance);
    }

    // корректный прием купюр (две одинаковые купюры)
    @Test
    public void acceptBanknotesTwoIdenticalBanknotesTest() {
        List<Cell> newCells = new ArrayList<>();
        newCells.add(cellMapper.createCell(Denomination.TWO_HUNDRED, 2));
        atm.acceptBanknotes(newCells);

        int expectedBalance = 5400;
        assertThat(atm.getBalance()).isEqualTo(expectedBalance);
    }

    // корректный прием купюр (две разные купюры)
    @Test
    public void acceptBanknotesTest() {
        List<Cell> newCells = new ArrayList<>();
        newCells.add(cellMapper.createCell(Denomination.ONE_THOUSAND, 1));
        newCells.add(cellMapper.createCell(Denomination.TWO_THOUSAND, 1));
        atm.acceptBanknotes(newCells);

        int expectedBalance = 8000;
        assertThat(atm.getBalance()).isEqualTo(expectedBalance);
    }

    // получение корректно запрошенной суммы (одной купюрой)
    @Test
    public void getRequestedAmountOneBanknotesTest() {
        int result = this.atm.getRequestedAmount(5000);
        int expectedBalance = 5000;

        assertThat(result).isEqualTo(expectedBalance);
    }

    // получение запрошенной суммы (двумя одинаковыми купюрами)
    @Test
    public void getRequestedAmountTwoIdenticalBanknotesTest() {
        List<Cell> newCells = new ArrayList<>();
        newCells.add(cellMapper.createCell(Denomination.TWO_HUNDRED, 2));
        this.atm.acceptBanknotes(newCells);

        int result = this.atm.getRequestedAmount(400);
        int expectedBalance = 400;

        assertThat(result).isEqualTo(expectedBalance);
    }

    // получение запрошенной суммы (тремя разными купюрами)
    @Test
    public void getRequestedAmountTwoDifferentBanknotesTest() {
        List<Cell> newCells = new ArrayList<>();
        newCells.add(cellMapper.createCell(Denomination.ONE_THOUSAND, 1));
        newCells.add(cellMapper.createCell(Denomination.ONE_HUNDRED, 1));
        this.atm.acceptBanknotes(newCells);

        int result = this.atm.getRequestedAmount(6100);
        int expectedBalance = 6100;

        assertThat(result).isEqualTo(expectedBalance);
    }

    // получение некорректно запрошенной суммы (нет нужных купюр)
    @Test
    public void getRequestedAmountNoBanknotesRequiredTest() {
        List<Cell> newCells = new ArrayList<>();
        newCells.add(cellMapper.createCell(Denomination.TWO_HUNDRED, 1));
        newCells.add(cellMapper.createCell(Denomination.FIVE_HUNDRED, 1));
        this.atm.acceptBanknotes(newCells);

        int result = this.atm.getRequestedAmount(600);
        int expectedBalance = 0;

        assertThat(result).isEqualTo(expectedBalance);
    }

    // получение некорректно запрошенной суммы (> баланса банкомата)
    @Test
    public void getRequestedAmountMoreThanBalanceTest() {
        int result = this.atm.getRequestedAmount(6000);
        int expectedBalance = 0;

        assertThat(result).isEqualTo(expectedBalance);
    }

    // получение некорректно запрошенной суммы (не делится без остатка на 100)
    @Test
    public void getRequestedAmountInvalidTest() {
        int result = this.atm.getRequestedAmount(5);
        int expectedBalance = 0;

        assertThat(result).isEqualTo(expectedBalance);
    }

    // получение некорректно запрошенной суммы (отрицательное)
    @Test
    public void getRequestedAmountRequestedAmountTest() {
        int result = this.atm.getRequestedAmount(-100);
        int expectedBalance = 0;

        assertThat(result).isEqualTo(expectedBalance);
    }

}
