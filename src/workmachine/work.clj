(ns workmachine.work
  (:use clj-html.core)
  (:require [workmachine.jobs :as jobs]
            [workmachine.workflow :as workflow]
            [workmachine.render :as render]))

;; TODO: Make sure that a worker only has one assignment.
(defn assign [worker-id]
  (let [worker-job (or (jobs/job-for-worker worker-id)
                       (do
                         (jobs/assign-job-to-worker worker-id)
                         (jobs/job-for-worker worker-id)))]
    (if worker-job
      (render/html worker-id worker-job)
      (html [:div "No work"]))))

(defn submit [worker-id & submitted-work]
  ;; Merge the submitted-work into the job.
  ;; run-engine to
;  (let [worker-job (jobs/job-for-worker worker-id)]
1)
 ;   )))

(defn unassign [worker-id]
  (jobs/unassign-job-from-worker worker-id)
  (html [:div "Unassigned"]))
