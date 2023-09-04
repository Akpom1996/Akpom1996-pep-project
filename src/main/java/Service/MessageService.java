package Service;

import DAO.MessageDAO;
import Model.Message;
import Model.Account;
import java.util.List;

public class MessageService {

    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public List<Message> getAllMessagesFromUser(Account account){
        return messageDAO.getAllMessagesWithAccount(account.getAccount_id());
    }

    public Message addMessage(Message message){
         /*for (int i = 0; i < messageDAO.getAllMessages().size(); i++){
            if (messageDAO.getAllMessages().get(i).getMessage_text() == ""){
                return null;
            }
            else if (messageDAO.getAllMessages().get(i).getMessage_text().length() >= 255){
                return null;
            }
            else if (messageDAO.getAllMessages().get(i).getPosted_by() != message.getPosted_by() && i == messageDAO.getAllMessages().size() - 1){
                return null;
            }
        }*/ 

        return messageDAO.insertMessage(message);
    }
    
    public Message getMessage(Message message){
        return messageDAO.getMessage(message.getMessage_id());
    }

    public Message deleteMessage(Message message){
        return messageDAO.deleteMessage(message.getMessage_id());
    }

    public Message updateMessage(Message message){
        return messageDAO.updateMessage(message.getMessage_id(), message.getMessage_text());
    }
}
