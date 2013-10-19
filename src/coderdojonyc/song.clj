(ns coderdojonyc.song
  (:require [overtone.live :refer [metronome]]
            [coderdojonyc.lib :refer [wait-beats apply-next-beat apply-next-bar]]))

(def global-metro
  "The implicit metronome used by the functions that follow."
  (metronome 120))

(def ^:dynamic *sched-funs*
  "Functions name symbols that should be rewritten to be (wait-beats) scheduled
   when used inside a dosong/loopsong expression"
  #{'println})

(defn sched-fun?
  "Returns true if function named by symbol fun should be rewritten to be
  (wait-beat) scheduled. Returns true when fun refers to a function that was
  defined by definst or defsynth, or if the symbol is found in the *sched-funs*
  set."

  [fun]
  (let [ftype (:type (meta (ns-resolve *ns* fun)))]
    (or (= :overtone.studio.inst/instrument ftype)
        (= :overtone.studio.synth/synth ftype)
        (contains? *sched-funs* fun))))

(defn wait
  "For use inside dosong/loopsong macro, "
  ([num]
     (throw (Exception. "(wait) can only be used inside (dosong) or (loopsong)")))
  ([num beat-offset]
     (swap! beat-offset (partial + num))))

(defn make-sched [fun-exp offset-sym]
  "Rewrite the the code in fun-exp as scheduled to execute in the future
  beat-offset beats from now."
  `(wait-beats global-metro @~offset-sym
        ~(first fun-exp) ~@(rest fun-exp)))

(defn schedule-code [body offset-sym]
  "Walk the syntax tree of body, rewriting functions as scheduled if sched-fun?
   says they should be. offset-sym is the symbol to use for the beat-offset
   counter atom. I have a hard time reasoning about recursive macros so I found
   the extra vars preferable to writing this all in a macro."

  ;; was originally using lazy-seq for recursion, but the binding to
  ;; *sched-funs* (in loopsong) was getting lost, which makes sense, I think,
  ;; 'cause (binding) bindings are thread-local.

  (when-let [[head & xs] body]
    (cond
     (and (seq? head) (sched-fun? (first head)))
     (cons (make-sched head offset-sym) (schedule-code xs offset-sym))

     (= (first head) 'wait)
     (cons `(wait ~(second head) ~offset-sym)
           (schedule-code xs offset-sym))

     (seq? head) (cons ;; recur on next expression
                  (cons (first head) ;; recur on rest of this expression
                        (schedule-code (rest head) offset-sym))
                  (schedule-code xs offset-sym))

     :else (cons head (schedule-code xs offset-sym)))))

(defn make-scheduled-expression [body looping?]
  "Rewrite the code in body to be scheduled. If looping? is truthy the
   code will be inifintely recursive."
  (let [beat-offset (gensym "beat-offset-")]
    `(letfn [(fun# []
               (let [~beat-offset (atom 0)]
                 ~@(schedule-code body beat-offset)
                 (if ~looping?
                   (wait-beats global-metro @~beat-offset fun#))))]
       (apply-next-bar global-metro fun#))))

(defmacro loopsong [& body]
  (make-scheduled-expression body true))

(defmacro dosong [& body]
  "A 'pausable' do form. Use the (wait n) function to indicate a pause of n
  beats. Any functions deemed schedulable by sched-fun? that follow the (wait)
  invocation will wait n beats to eval. Functions not deemed schedulable are
  evaluated as usual.

  (dosong
   (saw-wave 100)
   (wait 1)
   (saw-wave 200)
   (wait 1)
   (println 'done)

  expands (approximately) into

  (let [beat-offset (atom 0)]
    (wait-beats global-metro @beat-offset saw-wave 100)
    (wait 1 beat-offset)
    (wait-beats global-metro @beat-offset saw-wave 200)
    (wait 1 beat-offset)
    (wait-beats global-metro @beat-offset println 'done))

  the saw-wave calls get rewritten because saw-wave was defined using definst,
  and the println call gets rewritten because 'println appears in the
  *sched-funs* set.

  Another example, demonstrating that control structures are preserved.

  (dosong
   (doseq [i (range 1 13)]
     (saw-wave (* i 50))
     (wait (/ 2 i))))

  expands into

  (let [beat-offset (atom 0)]
    (doseq [i (range 1 13)]
      (wait-beats global-metro @beat-offset saw-wave (* i 50))
      (wait (/ 2 i) beat-offset)))

"

  (make-scheduled-expression body false))
