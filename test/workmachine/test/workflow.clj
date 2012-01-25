(ns workmachine.test.workflow
  (:use [workmachine.workflow])
  (:use [clojure.test])
  (:require [workmachine.blueprints.content :as content-workflow]))

(def workflow-definition-fixture content-workflow/workflow-definition)

(defn workflow-fixture []
  (content-workflow/workflow [{"topic" "http://google.com"}
                              {"topic" "http://yahoo.com"}
                              {"topic" "http://amazon.com"}]))

(deftest replace-me ;; FIXME: write
  (is false "No tests have been written."))
