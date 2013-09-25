# overtone-project

A Clojure library designed to ... well, that part is up to you.

### Usage For Mac 
1) Double click **overtone-repl.command** or open up terminal and type **./overtone-repl.command**

You should see something similar to below
```
$ ./overtone-repl.command
nREPL server started on port 60096 on host 127.0.0.1
REPL-y 0.2.1
Clojure 1.3.0
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)

user=> 
```

2) When you see `user=>` type the following.

    (use 'overtone.live)


You should see something similar.

```
nREPL server started on port 60096 on host 127.0.0.1
REPL-y 0.2.1
Clojure 1.3.0
    Docs: (doc function-name-here)
          (find-doc "part-of-name-here")
  Source: (source function-name-here)
 Javadoc: (javadoc java-object-or-class-here)
    Exit: Control+D or (exit) or (quit)

user=> (use 'overtone.live)
--> Loading Overtone...
--> Booting internal SuperCollider server...
Found 0 LADSPA plugins
*** ERROR: open directory failed '/Users/ricky/Library/Application Support/SuperCollider/synthdefs'
Number of Devices: 3
   0 : "AirPlay"
   1 : "Built-in Microph"
   2 : "Built-in Output"

"Built-in Microph" Input Device
   Streams: 1
      0  channels 2

"AirPlay" Output Device
   Streams: 1
      0  channels 2

SC_AudioDriver: sample rate = 44100.000000, driver's block size = 512
--> Connecting to internal SuperCollider server...
--> Connection established

    _____                 __
   / __  /_  _____  _____/ /_____  ____  ___
  / / / / | / / _ \/ ___/ __/ __ \/ __ \/ _ \
 / /_/ /| |/ /  __/ /  / /_/ /_/ / / / /  __/
 \____/ |___/\___/_/   \__/\____/_/ /_/\___/

   Collaborative Programmable Music. v0.8


Hello. Do you feel it? I do. Creativity is rushing through your veins today!

nil
user=>
```

3) You are ready to start using overtone now! Type the following to test out overtone.

```
user=> (demo (sin-osc))

#<synth-node[loading]: user/audition-synth 30>
```


## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
