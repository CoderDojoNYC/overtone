# Coderdojo NYC's Overtone Playground
Overtone is awesome, read about installing it [here](https://github.com/overtone/overtone/wiki/Installing-Overtone). More information can be found here. [http://overtone.github.io/](http://overtone.github.io/)


### Overview

* Introductions
* Ice Breaker
* Welcome to Overtone!
  * Create + Explore
  * Publish
  * Share

### Ice Breaker - Musical Scramble
---
There are 10 musical sheets from different songs cut into pieces like a jigsaw puzzle. These pieces will be distributed among the kids, mentors and parents. The goal is to group together and reproduce these song sheets. Once they feel like they've completed the song, they will hand us the sheet and we will play it back to them during intermission and see what they produced. Make sure each group writes their name on the back of the song piece + comes up with a team name.


### Welcome to Overtone!
---

1. CREATE & EXPLORE

  a. Select your song (just find the right musical sheet)
    * Visit http://pianoitall.com (Piano It All).
    * Click on the blue button "Browse Songs".
    * Pick one song that you like the most and listen to the youtube to confirm.
    * Download Musical Sheet (on the right of the youtube video)
    
  b. Translate from the Musical Sheet to the MIDI Musical Letters
    * Use the piano reference, MIDI reference: Letters A-G. Numbers: 2-8
    * Result: List like [A5, B4, F#4, F4, G5]
  
  c. Code into the script (edit, reload)
    * Edit: Change the notes from the sample script
    * Reload, Play and Compare with the youtube music
    * Loop back to the Edit

2. PUBLISH (proposal. to be discussed/detailed)

  a. Record the music onto a WAV file (using Overtone)
  b. Write up: title, description of the experience (what I like, didn't like, improvements?), author
  c. Create a web page with this content (or use Blogger + Google Drive for WAV file)

3. SHARE
  
  a. Play a short piece. Everyone has to guess
  b. Play a longer piece. Again, see if someone can guess
  c. Play the whole song. See if anyone can guess
  
    
### The Details
---

#### Setup
1. Install Overtone.
2. Start Overtone
   * **For Mac** - click `overtone-repl-mac.command` to begin
   * **For Windows** - click `overtone-repl-windows.bat` to begin
3. Type `(use 'overtone.live)` to start Overtone

##### Detailed Setup Instructions for Mac

Instructions are located [here](https://github.com/CoderDojoNYC/overtone/blob/master/doc/mac-installation.mdown).

##### Detailed Setup Instructions for Windows (obsolete... replaced by overtone-repl-windows.bat)

```
  Download http://bit.ly/overtone-repl1
  Extract All the files onto a local drive
  Find the bin/clj-1.bat with Windows Explorer
  Double-click on bin/clj-1.bat
  
  Type at the prompt:
  (+ 1 2)
  (use 'overtone.live)
```  


#### Experimentation
Start off by downloading an overtone example (LINK GOES HERE)

Now type the following into overtone

a. 
```
(defonce metro (metronome 120))
(metro)
```
defonce allows the creation of global state at the root
http://clojuredocs.org/clojure_core/clojure.core/defonce
`metro` maintains the current tempo of the metronome.

Try few more `(metro)` to see the current tempo value (always increasing)
```
(metro)
(metro)
(metro)
```

b.
```
 ;; We use a saw-wave that we defined in the oscillators tutorial
(definst saw-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin-env attack sustain release) 1 1 0 1 FREE)
     (saw freq)
     vol))
 ```

This defines the function `saw-wave()`, a triangular wave shape, based on a linear envelope.
It has 5 parameters with their default values: 440 Hz for frequency and so on.


c.
```
(defn play [music-note ticks]
 (saw-wave (midi->hz (note music-note)) 0.01 (* 0.4 ticks)))
```
**EXPLANATION OF CODE GOES HERE**


d.
```
(defn minecraft-row1 [m beat-num]
  (at (m (+ 0 beat-num)) (play :E4 1))
  (at (m (+ 1 beat-num)) (play :E4 1))
  (at (m (+ 2 beat-num)) (play :E4 1))
  (at (m (+ 3 beat-num)) (play :F#4 1))
  (at (m (+ 4 beat-num)) (play :G4 2))
  (at (m (+ 6 beat-num)) (play :G4 2))
)
```
 **EXPLANATION OF CODE GOES HERE**


e.
```
(minecraft-row1 metro (metro))
```
 **EXPLANATION OF CODE GOES HERE**

If everything worked, you should hear some music play. Okay, now let's make our own beats!

Do you remember this code?

 ```
(defn minecraft-row1 [m beat-num]
  (at (m (+ 0 beat-num)) (play :E4 1))
  (at (m (+ 1 beat-num)) (play :E4 1))
  (at (m (+ 2 beat-num)) (play :E4 1))
  (at (m (+ 3 beat-num)) (play :F#4 1))
  (at (m (+ 4 beat-num)) (play :G4 2))
  (at (m (+ 6 beat-num)) (play :G4 2))
)
```

We're going to take a look primarily at this line `(at (m (+ 0 beat-num)) (play :E4 1))`

(+ `0` beat-num) - The zero signifies when the note will start to play.
(play `:E4` 1) - This is the note. You can find a list of notes here.
(play :E4 `1`) - The 1 is the duration of the note.


### Putting it all together…

**Example #1**

```
  (at (m (+ 0 beat-num)) (play :A4 1))
  (at (m (+ 1 beat-num)) (play :B4 1))
  (at (m (+ 2 beat-num)) (play :C4 1))
```
 
First the `A4` note will play, then the `B4`, followed by the `C4` note.


**Example #2**

```
  (at (m (+ 0 beat-num)) (play :A4 1))
  (at (m (+ 0 beat-num)) (play :B4 1))
  (at (m (+ 0 beat-num)) (play :C4 1))
```
 
`A4`, `B4` and `C4` will all play at the same time.


**Example #3**

```
  (at (m (+ 0 beat-num)) (play :A4 2))
  (at (m (+ 1 beat-num)) (play :B4 1))
  (at (m (+ 1 beat-num)) (play :C4 1))
```
 
`A4` will play first, then you will hear the notes `A4`, `B4` and `C4` play simultaneously. 


### Now it's your turn!

- You can do one of the following.
  * Work on Super Mario?
  * Create your own original music piece
  * Take an existing piece and translate it into code and remix it!
  
#### Super Mario

Instructions?????????????????  

#### Original Piece

Continue exploring and playing around with different durations, notes and timing.

#### Translation

1) Visit http://pianoitall.com (Piano It All) and search for a song sheet or follow along the video.

2) Disregard the bottom row of the notes.

3) Translate code

^ Expand on this…


## References
* Try Clojure [http://tryclj.com/](http://tryclj.com/)
* Musical Scales
  * http://www.music-mind.com/Music/Srm0039.GIF
  * http://www.church-musician-jobs.com/images/PianoKeyboard_sm.jpg
* Note Durations
  * http://library.thinkquest.org/15413/media/images/note_values.gif
  * http://www.jazclass.aust.com/basicth/bt111.gif 


## Credits
* Benno ([@bensussman](http://github.com/bensussman))
* Ehtesh ([@shurane](http://github.com/shurane))
* Omar ([@osnr](http://github.com/osnr))
* Paul ([@pfeyz](http://github.com/pfeyz))
* Pedro ([@pedroha](http://github.com/pedroha))
