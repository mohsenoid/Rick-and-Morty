### Guestlogix Take Home Test - Mobile

At Guestlogix we feel that putting developers on the spot with advanced algorithmic puzzles doesn’t exactly highlight one’s true skillset. The intention of this assessment is to see how you approach and tackle a problem in the real world, not quivering in front of a whiteboard.

### What is the test?

You will be building a mobile application that displays a list of Rick and Morty episodes. If the user taps on an episode another list should appear showing the characters in that episode split into dead or alive (you're free to decide how to implement this split) and displayed in the order in which they were created. If the user taps on a character then a screen showing the character's picture and information should appear.

For bonus points (not mandatory) you can add functionality to kill a character (the way to kill a character is completely up to you, a swipe, a button, etc). If a character dies then everything should update accordingly.

### User Stories

* The first thing a user should see is a list of episodes.
* If the user taps into an episode the app has to display a list of characters with a clear distinction between dead and alive characters
* If the user taps into a character the app has to display that character's information and picture
* Bonus functionality: The user should have the ability to kill a character and if a character gets kill the character lists should update accordingly

### Evaluation criteria

Your submission will be evaluated on the following aspects

   * Completeness
   * Correct application of SOLID principles
   * Efficiency
   * Correct application of architectural patterns (we suggest MVC, but if you want to use a different one make sure it is readable and applied correctly)
   * Code readability and expressiveness
   * If there is a problem, the app fails gracefully
   * While we favour functionality over UI/UX your submission should be easy to use (from a user's perspective)
   * Your solution has to compile

### (Non negotiable) Requirements

The application may be done in **Java or Swift** (No Kotlin, no Obj-C). 

**No third party libraries may be used**. You can use any native tools available to you from the platform, but no open sourced, or third party libraries. 

### Project's API

https://rickandmortyapi.com/

### Submitting

1. Fork this repository and work on the challenge.
2. Run through it one last time to make sure it works!
3. Send an email with a link to your forked repository to indicate that you have completed the challenge.

### Questions

If you have any questions during the challenge feel free to email recruitment@guestlogix.com Whether it be a question about the requirements, submitting, anything, just send the email!

### F.A.Q

* Why are third party libraries not allowed?
     For two reasons: 
      1. Because we want to gauge your knowledge regarding iOS/Android. We're testing your understanding and domain with native networking methods and patterns and if you use external libraries then all the heavy lifting is done for you and the test becomes more like a chore than an actual test. 
     2. Because we're building a native SDK for iOS and Android, and as such, we want it to talk nicely with other potential             libraries our clients may be using. To take away the burden of keeping track of compatibility issues and maintenance issues with other libraries we're doing everything 100% native
     
* Can I use Alamofire/KingFisher/OkHttp/Retrofit/Gson/SwiftyJson/Rx....? 
    * No, only native libraries. Note for Android developers: This includes Volley!! 
    
* But Google/Facebook/Etc...maintains/created the library I intend to use...
  * Still no...
  
* But its a very popular library and all the cool kids are using it!
  * Nope, no external libraries. 
 
* OK so which libraries can I use? 
  * For Android, you can find all the available libraries for you here: https://developer.android.com/reference 
  If the library is not listed there, then you can't use it. 
  
  * For iOS you can find all the availabe libraries for you here: https://developer.apple.com/documentation
