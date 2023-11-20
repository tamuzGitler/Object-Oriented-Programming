import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 * Dan Nirel - wrote this description
 * @author Tamuz Gitler
 */
class ChatterBot {
    //---- Class Variables ----
    static final String REQUEST_PREFIX = "say ";
    static final String REQUESTED_PHRASE_PLACEHOLDER = "<phrase>";
    static final String ILLEGAL_REQUEST_PLACEHOLDER = "<request>";
    String name;
    String[] repliesToLegalRequests;
    String[] repliesToIllegalRequest;
    Random rand = new Random();

    //---- Class Constructor ----
    /**
     * Constructor of the class ChatterBot.
     * @param name - of the bot
     * @param repliesToLegalRequests - string array with replies to legal requests
     * @param repliesToIllegalRequest - string array with replies to illegal requests
     */
    ChatterBot(String name, String[] repliesToLegalRequests, String[]
            repliesToIllegalRequest) {
        this.name = name;
        this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
        this.repliesToLegalRequests = new String[repliesToLegalRequests.length];
        /* filling repliesToIllegalRequest & repliesToLegalRequests with given data */
        for(int i = 0 ; i < repliesToIllegalRequest.length ; i = i+1) {
            this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
        }
        for(int i = 0 ; i < repliesToLegalRequests.length ; i = i+1) {
            this.repliesToLegalRequests[i] = repliesToLegalRequests[i];
        }
    }

    //---- Class Methods ----
    /**
     * replies to a given statement given by another bot or by user.
     * @param statement - for the bot to reply to
     * @return - the reply of the bot
     */
    String replyTo(String statement) {
        /* handling legal statement */
        if(statement.startsWith(REQUEST_PREFIX)) {
            //we don’t repeat the request prefix, so delete it from the reply
            statement = statement.replaceFirst(REQUEST_PREFIX, "");
            return replacePlaceholderInARandomPattern(this.repliesToLegalRequests,
                    REQUESTED_PHRASE_PLACEHOLDER,statement);
        }
        /* handling illegal statement */
        return replacePlaceholderInARandomPattern(this.repliesToIllegalRequest,
                ILLEGAL_REQUEST_PLACEHOLDER,statement);
    }

    /**
     * randomly chooses a response pattern and replaces the phrase with the given statement.
     * @param possibleResponse - String array of possible responses to chose from
     * @param phrase - string with places to fill the statement in
     * @param statement - string given to the bot to response to
     * @return - the response to the given statement
     */
    String replacePlaceholderInARandomPattern(String[] possibleResponse, String
            phrase ,String statement) {
        int randomIndex = rand.nextInt(possibleResponse.length); //randomly chooses a number
        String responsePattern = possibleResponse[randomIndex]; //chooses response
        return responsePattern.replaceAll(phrase, statement); //replaces phrase in responsePatter with statement
    }

    /**
     * returns the name of the bot.
     * @return - name of the bot
     */
    String getName(){
        return this.name;
    }
}
