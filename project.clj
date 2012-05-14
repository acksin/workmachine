(defproject workmachine "0.1.0"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.4.0"]
                           [org.clojure/clojure-contrib "1.2.0"]
                           [cheshire "4.0.0"]
                           [noir "1.3.0-beta7"]
                           [hiccup "1.0.0"]
                           [org.clojars.tavisrudd/redis-clojure "1.3.1-SNAPSHOT"]]
            :source-paths ["src" "src/main/clojure"]
            :main workmachine.server)
