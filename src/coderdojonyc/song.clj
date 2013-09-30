(ns coderdojonyc.song
  (:require [overtone.live :refer [metronome]]
            [coderdojonyc.lib :refer [wait-beats apply-next-beat apply-next-bar]]))

(def global-metro
  "The implicit metronome used by the functions that follow."
  (metronome 120))

(def ^:dynamic *waiting-time*
  "A time offset used for scheduling within a song."
  (atom 0))

(def ^:dynamic *sync-funs*
  "The functions that should be rewritten to be synchronous when used inside a
   dosong/loopsong expression"
  #{'println})

(defn makefn
  "Return code (a list) as the body in an anonymous function."
  [code]
  `(fn [] ~code))

(defn sync-fun?
  "Returns true if function named by symbol fun should be rewritten to be
  synchronous. Returns true when fun refers to an function defined by definst or
  defsynth, or if the symbol is found in the *sync-funs* set."

  [fun]
  (let [ftype (:type (meta (ns-resolve *ns* fun)))]
    (or (= :overtone.studio.inst/instrument ftype)
        (= :overtone.studio.synth/synth ftype)
        (contains? *sync-funs* fun))))

(defn wait
  "For use inside dosong/loopsong macro, "
  [num]
  (swap! *waiting-time* (partial + num)))

(defn make-sync [fun-exp]
  "Rewrite the "
  `(~wait-beats global-metro (deref *waiting-time*)
        ~(first fun-exp) ~@(rest fun-exp)))

(defn -dosong-helper [body]
  "Walk the syntax tree of body, rewriting functions as synchronous if they're
   marked to be according to *sync-funs*."

  ;; was originally using lazy-seq for recursion, but the binding to *sync-funs*
  ;; (in loopsong) was getting lost, which makes sense, I think, 'cause
  ;; (binding) bindings are thread-local.

  (when-let [[head & xs] body]
    (cond
     (and (seq? head) (sync-fun? (first head)))
     (cons (make-sync head) (-dosong-helper xs))

     (seq? head) (cons ;; recur on next expression
                  (cons (first head) ;; recur on rest of this expression
                        (-dosong-helper (rest head)))
                  (-dosong-helper xs))

     :else (cons head (-dosong-helper xs)))))

(defmacro dosong
  "A 'pause-ible' do form. Use the (wait n) function to indicate a pause of n
  beats. Any functions deemed 'syncrhonous' (by sync-fun?) that follow
  the (wait) invocation will wait n beats to eval. Functions not deemed
  'synchronous' are evaluated as usual.

  (dosong
   (saw-wave 100)
   (wait 1)
   (saw-wave 200)
   (wait 1)
   (println 'done)

  expands (approximately) into

  (binding [*waiting-time* (atom 0)]
    (apply-next-beat global-metro

     (wait-beats global-metro @*waiting-time* saw-wave 100)
     (wait 1) ;; bangs on *waiting-time*
     (wait-beats global-metro @*waiting-time* saw-wave 200)
     (wait 1)
     (wait-beats global-metro @*waiting-time* println 'done)))

  the saw-wave calls get rewritten because saw-wave was defined using definst,
  and the println call gets rewritten because 'println appears in the
  *sync-funs* set.

  Another example, demonstrating that control structures are preserved.

  (dosong
   (doseq [i (range 1 13)]
     (saw-wave (* i 50))
     (wait (/ 2 i))))

  expands into

  (binding [*waiting-time* (atom 0)]
    (apply-next-beat global-metro

      (doseq [i (range 1 13)]
        (wait-beats global-metro @*waiting-time* saw-wave (* i 50))
        (wait (/ 2 i)))))

"

  [& body]
  `(binding [*waiting-time* (atom 0)]
     (~apply-next-beat global-metro
                      ~@(-dosong-helper body))))

(defmacro loopsong [& body]
  "Note: Seems to break when the total song length is less than a beat."

  ;; bind body to a function named recur-xxx, mark recur-xxx as synchronous,
  ;; then append a call to recur-xxx to the end of body before running
  ;; -dosong-helper on body. dosong will rewrite the recursive call with
  ;; (wait-beats) so it gets called after everything else in the song.

  (let [fun-name (gensym "recur-")
        time-syms (conj *sync-funs* fun-name)
        code (binding [*sync-funs* time-syms]
               `(binding [*waiting-time* (atom 0)]
                  ~@(-dosong-helper
                   (conj (apply vector body)  ;; add recursive call to
                         (list fun-name)))))] ;; end of code
    `(letfn [(~fun-name [] ~code)]
       (~apply-next-bar global-metro ~fun-name))))
