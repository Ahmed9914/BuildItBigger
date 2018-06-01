package nanodegree.udacity.jokesprovider;

import java.util.Random;

public class Jokes {
    private static String[] jokesList = {"Just changed my Facebook name to 'No one' so when I see stupid posts I can click like and it will say 'No one likes this'.",
    "I am a nobody, nobody is perfect, therefore I am perfect.",
    "What do you call a bear with no teeth? -- A gummy bear!",
    "I wondered why the frisbee was getting bigger, and then it hit me.",
    "What do you call two fat people having a chat? -- A heavy discussion"};

    public static String getRandomJoke(){
        int randomIndex = new Random().nextInt(jokesList.length);
        return jokesList[randomIndex];
    }
}
