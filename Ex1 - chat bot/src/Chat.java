import java.util.Scanner;
class Chat {
    public static void main(String[] args) {
        ChatterBot[] bots = new ChatterBot[2]; //init array of bots that points two null
        String[] legalReplies1 = {
                "say " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER +
               " no problem: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER, "say "+ ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};
        String[] legalReplies2 = {
                "say " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER +
                "? its easy: " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER + " is " +
                ChatterBot.REQUESTED_PHRASE_PLACEHOLDER, "i didnt understand, what is " + ChatterBot.REQUESTED_PHRASE_PLACEHOLDER};
        String[] illegalReplies1 = {
                "why like this? " + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER,
                "bot2, it seems like you dont speak english!"};
        String[] illegalReplies2 = {
                "speak clear! i cant undrestand",
                "please repeat:" + ChatterBot.ILLEGAL_REQUEST_PLACEHOLDER};
        bots[0] = new ChatterBot("bot1", legalReplies1, illegalReplies1);
        bots[1] = new ChatterBot("bot2", legalReplies2, illegalReplies2);
        String statement = "say now";
        Scanner scanner = new Scanner(System.in);
        while (true){
            for (ChatterBot bot : bots){
                statement = bot.replyTo(statement);
                System.out.print(bot.getName() + ": " + statement);
                scanner.nextLine(); //waits for user to press ENTER
            }
        }




    }
}