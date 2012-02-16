(ns workmachine.engine.workflow
  (:use [clojure.tools.macro :only (name-with-attributes)])
  (:require [workmachine.models.jobs :as jobs]
            [workmachine.engine.data-input :as input-types]
            [workmachine.engine.data-output :as output-types]))

(defn instruction-label [statement]
  (first statement))

(defn instruction-label? [label statement]
  (= (instruction-label statement) label))

(defn instruction [statement]
  (last statement))

(defn statement [lbl program]
  (first (filter (fn [stmt] (= (instruction-label stmt) lbl)) program)))

(defn continue [label program]
  (defn find-label-recur [program-acc]
    (if (empty? program-acc)
      nil
      (let [stmt (first program-acc)]
        (if (instruction-label? label stmt)
          (first (rest program-acc))
          (recur (rest program-acc))))))
  (instruction-label (find-label-recur program)))


(defn finish [job]
  (println "finished")
  (jobs/add-to-finished-jobs job))

(defn run-engine [job]
  ;; TODO: Eventually.
  ;;
  ;; Workers:
  ;; Each job should have the worker that performed the job.
  ;; - We should keep a reputation map for each worker such that we know their reputation at any given point in time.
  ;;
  ;; Review:
  ;; Check if there is a review associated with the input.
  ;;  - if not then create a review job.
  ;;  - 
  ;;
  ;; Should check if the data that was submitted was valid.
  ;; Do we trust the worker?
  ;;   No -> Create a job to review that the input was valid.
  ;;   Yes -> Go on to the next instruction.
  (println (job :label))

  (let [next-instruction-label (continue (job :label) (job :program))]
    (if (nil? next-instruction-label)
      (finish job)
      (jobs/add-to-available-jobs (merge job {:label next-instruction-label})))))

;; TODO: The params should actually just be a hash which we can
;; merge. Would make it a lot easier.
(defn start-workflow [program job workflow-name]
  (jobs/add-to-available-jobs (struct-map jobs/job
                                :name workflow-name
                                :program program
                                :job job
                                :label (instruction-label (first program)))))
