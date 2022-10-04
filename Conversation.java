import java.util.*;
/** 
*. The class coversation contains the code for the   
  chatbot program which responds to user's input. 
*. @version Spring 2022
*. @Author Ramsha Rauf
*/
public class Conversation {

  //the mirror words
    static String[] mirrorWords = new String[] {
      "I",
      "i",
      "me",
      "Me",
      "am",
      "Am",
      "you",
      "You",
      "my",
      "My",
      "your",
      "are",
      "Are",
      "was",
      "Was"
    };

    //the responding words.
    static String[] respondingWords = new String[] {
      "you",
      "you",
      "you",
      "You",
      "are",
      "Are",
      "I",
      "I",
      "your",
      "my",
      "My",
      "am",
      "Am",
      "were",
      "Were"
    };

    //the canned responses
    static String[] canned_responses = new String[] {
      "I am not sure I understand.",
      "Mm-hm.",
      "I am sorry, I didn't understand.",
      "I didn't quite get you.",
      "Interesting."
    };

/**
* The method main is where the entire program runs. 
* Main calls response and transcript method.
*/
  public static void main(String[] arguments) {
    String followup = "";
    
    //asks the user how many rounds and stores it int response.
    Scanner round = new Scanner(System.in);  
    System.out.print("How many rounds?(please choose from 1 and 10 only) ");
    int response = round.nextInt();

    //creates a string [] transcript_array to store the responses of the computer and user.
    int transcript_lines = ((response *2)+2);
    String [] transcript_array = new String [transcript_lines];
    //variable sentence_counter ensures that the responses of the user and computer are stored in the correct order
    int sentence_counter = 0;
  
    //runs the program for the number of rounds the user inputted.
    for (int i = 1; i <= response; i++){
      
      //if it is the first round the default question will run
      if (i==1){

        followup = "Hi there! What's on your mind? ";
        System.out.println(followup);
        //stores the first question in the transcript_array and increases sentence_counter by 1
        transcript_array= transcript(transcript_array, followup, sentence_counter);
        sentence_counter+=1;


        //asks for the user's input and stores in user_input.
        Scanner userinput = new Scanner(System.in);
        String user_input = userinput.nextLine();
        
        //the user's input is stored in transcript_array and sentence counter is increased by 1
        transcript_array= transcript(transcript_array, user_input, sentence_counter);
        sentence_counter+=1;

        //method response generates a response for what the user inputted and stores it in followup
        followup = response(user_input);


        
      //else command will run when i is not 1 meaning when it is not the first round  
      }else{
        //user input is stored in user_input
        Scanner userinput = new Scanner(System.in);
        String user_input = userinput.nextLine();
        
        //user's response is stored in the transcript array and sentence counter is increased by 1
        transcript_array= transcript(transcript_array, user_input, sentence_counter);
        sentence_counter+=1;

        //a response to the user's input is stored in followup 
        followup = response(user_input);
 
      }
          
            
       
      //followup which is the computer's response to the user's input is printed and stored in transcript array afterwhich the sentence_counter is increased by 1 
      System.out.println(followup);
      transcript_array= transcript(transcript_array, followup, sentence_counter);
        sentence_counter+=1;

      //followup is resetted to blank so the next round can run
      followup = "";
    }
    
      
    //if all rounds are complete followup is set up to be a goodbye command 
    followup = "I will catch you later! Bye-bye. ";
    System.out.println(followup);

    //the goodbye command is stored in the transcript array as the last command 
    transcript_array= transcript(transcript_array, followup, sentence_counter);
        
    
    
    //prints out the entire transcript array beginning with the title "TRANSCRIPT"
    System.out.println("\n\nTRANSCRIPT:\n");
    for (int i = 0; i <= transcript_lines-1; i++){
      System.out.println(transcript_array[i]);
    }

  }

/**
* The method response generates a response according to what the user said by accessing the canned responses and the mirror and responding words accordingly.
* @param String user_input which is what the user has inputted
* @return string followup which is the computer's respoonse according to what the user has inputted.
*/

  public static String response(String user_input){

    //boolean no_words_found declared and defaulted to false. It becomes true when any mirror words are found
    boolean no_words_found = false;

    //String followup declared and defaulted to blank
    String followup = "";

    //Integer words_found declared and defaulted to 0 and keeps track of how many mirror words are found.
    Integer words_found = 0;

    //Integer total_words declared and defaulted to 0 and increases after every word in efforts to keep track of the total words in the user_input
    Integer total_words = 0;

    //removes all the punctuations from the user's input
    user_input = user_input.replaceAll("\\p{Punct}","");

    //splits the user_input into individual words that are stored in userwords so they can be analyzed and replaced
    String[] userwords = user_input.split(" ");

    //analyzes every word in the user input
     for (String element: userwords) {

          //total words is increased by 1 everytime the for loop runs
          total_words +=1;

          //boolean found checks if the word is a mirror words or not.
          boolean found = Arrays.stream(mirrorWords).anyMatch(element::equals);

          //if boolean found is true that means that the word is a mirror word. 
          if (found){
            //words_found increases by 1
            words_found +=1;

            //int index is the index number of the word in the mirrorWords array
            int index = Arrays.asList(mirrorWords).indexOf(element);

            //string add is the word that will replace the mirror word that was found with a corresponding word.
            String add = respondingWords[index];

            //if it was the last word in the sentence then there will not be a space after the word
            if (total_words == userwords.length){
              followup = followup + add;

            }else{
              //if there are more words left in the sentence then there will be a space after that word. 
              followup = followup + add + " ";
            }

          //else statement runs if the word was not a mirror word and the boolean found was false 
          }else{
            //if it was the last word then the word would not have a space after it otherwise it will have a space after.
            if (total_words == userwords.length){
              followup = followup + element;
            }else{
              followup = followup + element + " ";
            }

            //if there were words_found was 0 and it was the word then no_words_found is true otherwise it is false 
            if ((words_found == 0) & (total_words == userwords.length)){
              no_words_found = true;

            }else{
              no_words_found = false;
            }
           
            
          }   
        }

        //if no words found is true then a random response from the canned responses is inputted as the followup
        if (no_words_found == true){
              Random rand = new Random();
              int randomIndex = rand.nextInt(canned_responses.length);
              followup = canned_responses[randomIndex];

            } else{
              //otherwise the followup is the sentence that was formed in the previous code with a question mark at the end
              followup += "?";
            }

    //followup is returned
    return followup;
  }

/**
* The method transcript formats and ensures that the transcript array(the entire conversation between the computer and the user) is in the correct order.
* @param transcript_array which is an array contains placeholder for the responses of the user and the computer and get updated as the conversation continues. String followup contains the individual responses of the user or the computer. int sentence_counter is the index number of where followup belongs.
* @return String[] transcript_array which is the updated transcript array of the conversation.  
*/

  public static String[] transcript(String[] transcript_array,String followup, int sentence_counter){
    //updates the index sentence_counter in transcript array with the followup and returns the updated transcript array. 
    transcript_array[sentence_counter]= followup;
    return transcript_array;

  }
}




