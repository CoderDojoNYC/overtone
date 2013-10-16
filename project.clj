(defproject coderdojonyc-overtone "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main coderdojonyc.core

  ; relevant bits of information for the below
  ; includes setup for Light Table and for Overtone
  ; https://groups.google.com/forum/#!msg/light-table-discussion/5Mf64RZ_eq4/VnkzPFDAapMJ
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [overtone "0.8.1" :exclusions [org.clojure/clojure]]
                 [org.clojure/data.json "0.2.3"]
                 [lein-light-nrepl "0.0.1"]]
  :repl-options {:nrepl-middleware [lighttable.nrepl.handler/lighttable-ops]})
