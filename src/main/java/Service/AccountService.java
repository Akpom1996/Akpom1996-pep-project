package Service;

import DAO.AccountDAO;
import Model.Account;
import java.util.List;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    public Account addAccount(Account account) {
        for (int i = 0; i < accountDAO.getAllAccounts().size(); i++){
            if (accountDAO.getAllAccounts().get(i).getAccount_id() == account.getAccount_id())
                return null;
            else if (accountDAO.getAllAccounts().get(i).getUsername() == account.getUsername())
                return null;
            else if (accountDAO.getAllAccounts().get(i).getUsername() == "")
                return null;
            else if (accountDAO.getAllAccounts().get(i).getPassword().length() < 4)
                return null;
        }
        return accountDAO.insertAccount(account);
    }

    public Account loginAccount(Account account){
        for (int i = 0; i < accountDAO.getAllAccounts().size(); i++){
            if (accountDAO.getAllAccounts().get(i).getUsername() == account.getUsername() && accountDAO.getAllAccounts().get(i).getPassword() == account.getPassword()){
                return accountDAO.getAllAccounts().get(i);
            }
        }

        return null;
    }
}
