(ns workmachine.jobs
  (:require [clojure.contrib.string :as string]
            [workmachine.data-input :as input-types]))

(def available-jobs (ref ()))
(def assigned-jobs (ref {}))

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
  (dosync
   (alter assigned-jobs merge {worker-id (first @available-jobs)} @assigned-jobs)
   (alter available-jobs rest))
  (job-for-worker worker-id))


;; XXX: This behavior is totally fucked. Look into it.
(defn unassign-job-from-worker [worker-id]
  (dosync
   ;; (if (not (nil? (@assigned-jobs worker-id)))
   ;;   (alter available-jobs concat (@assigned-jobs worker-id))
   (alter assigned-jobs dissoc @assigned-jobs worker-id)))
