(defproject workmachine "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "1.0.1"]
                 [clj-html "0.1.0"]
                 [resque-clojure "0.2.0"]]
  :dev-dependencies [[lein-ring "0.4.5"]
                     [swank-clojure "1.2.0"]]
  :ring {:handler workmachine.core/app})
