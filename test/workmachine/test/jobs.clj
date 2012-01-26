(ns workmachine.test.jobs
  (:use [clojure.test])
  (:use [workmachine.jobs])
  (:require [workmachine.blueprints.content :as content-workflow]
            [workmachine.workflow :as workflow]))

(str (content-workflow/workflow [{"topic" "http://google.com"}
                                 {"topic" "http://yahoo.com"}
                                 {"topic" "http://amazon.com"}]))

(deftest test-available-jobs
  (is (not (empty? @available-jobs)) "Available jobs should have couple jobs.")
  (is (= 3 (number-of-available-jobs)) "There should be three jobs"))


;(deftest test-assigned-jobs
  