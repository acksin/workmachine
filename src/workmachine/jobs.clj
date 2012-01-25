(ns workengine.jobs
  (:require [clojure.contrib.string :as string]
            [workengine.data-input :as input-types]))

(def available-jobs (ref ()))
(def assigned-jobs (ref {}))

(defn add-to-available-jobs [jobs]
  (dosync
   (ref-set available-jobs (concat jobs @available-jobs))))

(defn number-of-available-jobs []
  (str (count @available-jobs)))

(defn number-of-assigned-jobs []
  (str (count @assigned-jobs)))

(defn job-for-worker [worker-id]
  (@assigned-jobs worker-id))


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
