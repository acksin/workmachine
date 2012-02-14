(ns workmachine.work
  (:use clj-html.core)
  (:require [workmachine.jobs :as jobs]
            [workmachine.workflow :as workflow]
            [workmachine.render :as render]))

(defn assign [worker-id]
  (let [worker-job (or (jobs/job-for-worker worker-id)
                       (do
                         (jobs/assign-job-to-worker worker-id)
                         (jobs/job-for-worker worker-id)))]
    (if worker-job
      (render/html worker-id worker-job)
      (html [:div "No work"]))))

(defn submit [worker-id submitted-work]
  ;; unassign the assignment from the worker.
  ;; run-engine with the next instruction.
  (let [worker-job (jobs/job-for-worker worker-id)
        merged-job (merge worker-job {:job (merge (worker-job :job) submitted-work)})]
    (println merged-job)
    (jobs/submit-job-from-worker worker-id)
    (workflow/run-engine merged-job)
    "done"))

(defn unassign [worker-id]
  (jobs/unassign-job-from-worker worker-id)
  (html [:div "Unassigned"]))
