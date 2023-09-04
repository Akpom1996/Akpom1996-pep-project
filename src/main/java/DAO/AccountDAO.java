package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public List<Account> getAllAccounts(){
        Connection con = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();

        try{
            String sql = "SELECT * FROM account;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
                accounts.add(account);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public Account insertAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (account_ID, username, password) VALUES (?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, account.getAccount_id());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getPassword());
            ps.executeUpdate();

            /*ResultSet rs = ps.executeQuery();
            while (rs.next()){
                if (account.getUsername() == rs.getString("username"))
                    return null;
                else if (account.getUsername() == "" || account.getPassword().length() < 4)
                    return null;
            }*/

            ResultSet pkrs = ps.getGeneratedKeys();
            if (pkrs.next()){
                int generated_account_id = (int) pkrs.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;

    }
    public Account getAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            Account newAccount = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));

            return newAccount;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
