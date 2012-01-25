(ns workmachine.work
  (:require [workmachine.jobs :as jobs]))

;; TODO: Make sure that a worker only has one assignment.
(defn assign [worker-id]
  (let [worker-job (jobs/job-for-worker worker-id)]
    (if worker-job
      worker-job
      (jobs/assign-job-to-worker worker-id))))

(defn unassign [worker-id]
  (jobs/unassign-job-from-worker worker-id))
