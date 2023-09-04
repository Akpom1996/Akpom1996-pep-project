package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */

    AccountService accountService = new AccountService();
    MessageService messageService = new MessageService();

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/login", this::postLoginHandler);
        app.get("/messages/{message_id}", this::getMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageFromAccountIDHandler);
        app.get("/accounts", this::getAllAccountsHandler);
        app.post("/register", this::postRegisterHandler);
        app.get("/messages", this::getAllMessagesHandler);
        //app.get("/accounts/{account_id}/messages", this::getAllMessagesHandler);
        app.post("/messages", this::postMessageHandler);
        //app.start(8080);

        return app;
    }

    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper map = new ObjectMapper();
        Account account = map.readValue(ctx.body(), Account.class);
        Account loginAccount = accountService.loginAccount(account);
        if(loginAccount != null){
            ctx.json(map.writeValueAsString(loginAccount));
        }else{
            ctx.status(400);
        }
    }

    public void getMessageHandler(Context ctx){
        ObjectMapper map = new ObjectMapper();
        try{
            Message message = map.readValue(ctx.body(), Message.class);
            Message messageID = messageService.getMessage(message);
            ctx.json(messageID);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteMessageHandler(Context ctx){
        ObjectMapper map = new ObjectMapper();
        try{
            Message message = map.readValue(ctx.body(), Message.class);
            Message messageID = messageService.deleteMessage(message);
            ctx.json(messageID);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void updateMessageHandler(Context ctx){
        ObjectMapper map = new ObjectMapper();
        try{
            Message message = map.readValue(ctx.body(), Message.class);
            Message updatedMessage = messageService.updateMessage(message);
            if(updatedMessage!=null){
                ctx.json(map.writeValueAsString(updatedMessage));
            }else{
                ctx.status(400);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }

    public void getMessageFromAccountIDHandler(Context ctx){
        ObjectMapper map = new ObjectMapper();
        try{
            Account account = map.readValue(ctx.body(), Account.class);
            List<Message> messageReceived = messageService.getAllMessagesFromUser(account);
            //ctx.json(map.writeValueAsString(messageReceived));
            ctx.json(messageReceived);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }   
    }

    public void getAllAccountsHandler(Context ctx){
        List<Account> accounts = accountService.getAllAccounts();
        ctx.json(accounts);
    }

    public void getAllMessagesHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    private void postRegisterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper map = new ObjectMapper();
        Account account = map.readValue(ctx.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if(addedAccount!=null){
            ctx.json(map.writeValueAsString(addedAccount));
        }else{
            ctx.status(400);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper map = new ObjectMapper();
        Message message = map.readValue(ctx.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if(addedMessage!=null){
            ctx.json(map.writeValueAsString(addedMessage));
        }else{
            ctx.status(400);
        }
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    /*private void exampleHandler(Context context) {
        context.json("sample text");
    }*/


}