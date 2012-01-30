(ns workmachine.jobs
  (:require [clojure.contrib.string :as string]
            [workmachine.data-input :as input-types]))

(def available-jobs (ref []))
(def assigned-jobs (ref {}))
(def finished-jobs (ref []))

(defstruct job
  :program
  :job
  :label)

;; Available Jobs
(defn add-to-available-jobs [job]
  (dosync
   (alter available-jobs concat [job])))

(defn number-of-available-jobs []
  (count @available-jobs))

;; Assigned Jobs
(defn number-of-assigned-jobs []
  (count @assigned-jobs))

(defn job-for-worker [worker-id]
  (@assigned-jobs worker-id))

;; Assign available jobs to worker.
(defn assign-job-to-worker [worker-id]
  (let [first-job (first @available-jobs)]
    (dosync
     (alter available-jobs rest)
     (alter assigned-jobs merge {worker-id first-job}))))

(defn unassign-job-from-worker [worker-id]
  (dosync
   (alter available-jobs concat [(@assigned-jobs worker-id)])
   (alter assigned-jobs dissoc @assigned-jobs worker-id)))

(defn submit-job-from-worker [worker-id]
  (dosync
   (alter assigned-jobs dissoc @assigned-jobs worker-id)))

;; Finished Jobs

(defn add-to-finished-jobs [job]
  (dosync
   (alter finished-jobs concat [job])))