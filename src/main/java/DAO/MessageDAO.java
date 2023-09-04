package DAO;
import Model.Message;
//import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    public List<Message> getAllMessages(){
        Connection con = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getInt("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return messages;
    }

    public Message insertMessage(Message message){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ResultSet pkrs = ps.getGeneratedKeys();
            if (pkrs.next()){
                int generated_message_id = (int) pkrs.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message getMessage(Integer messageID){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, messageID);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                if (message.getMessage_id() == messageID){
                    return message;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Message deleteMessage(Integer messageID){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, messageID);

            ResultSet rs = ps.executeQuery();

            Message messageDelete = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));

            return messageDelete;

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;

    }

    public Message updateMessage(Integer messageID, String messageToUpdate){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_ID = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, messageToUpdate);
            ps.setInt(2, messageID);

            ResultSet rs = ps.executeQuery();
            //while (rs.next()){
            Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                /*if (message.getMessage_id() == messageID && messageToUpdate.getMessage_text().length() < 255 && messageToUpdate.getMessage_text() != ""){*/
            //message = messageToUpdate;
            return message;
                //}
            //}
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public List<Message> getAllMessagesWithAccount(Integer accountID){
        Connection con = ConnectionUtil.getConnection();

        try{
            String sql = "SELECT m.* FROM message m INNER JOIN account a ON m.posted_by = a.account_id WHERE a.account = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, accountID);

            ResultSet rs = ps.executeQuery();
            List<Message> messageList = new ArrayList<>();;
            while (rs.next()){
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                if (message.getMessage_id() == accountID){
                    messageList.add(message);
                }
            }

            return messageList;
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }
}
