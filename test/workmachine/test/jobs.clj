(ns workmachine.test.jobs
  (:use [clojure.test])
  (:use [workmachine.jobs])
  (:require [workmachine.blueprints.content :as content-workflow]
            [workmachine.workflow :as workflow]))

(content-workflow/workflow [{"topic" "http://google.com"}
                            {"topic" "http://yahoo.com"}
                            {"topic" "http://amazon.com"}])

(deftest test-available-jobs
  (is (not (empty? @available-jobs)) "Available jobs should have couple jobs.")
  (is (= 3 (number-of-available-jobs)) "There should be three jobs"))


(deftest test-assigned-jobs
  (let [worker-id "1"]
    (is (= 3 (number-of-available-jobs)) "There should be three jobs")
    (is (= 0 (number-of-assigned-jobs)) "Mystery assigned job.")
    (is (nil? (job-for-worker worker-id)) "Mystery assigned to worker.")
    (assign-job-to-worker worker-id)
    (is (= 2 (number-of-available-jobs)) "There should be one less available jobs")
    (is (= 1 (number-of-assigned-jobs)) "Worker was not assigned a job.")
    (unassign-job-from-worker worker-id)
    (is (= 3 (number-of-available-jobs)) "There should be one more available jobs")))
  