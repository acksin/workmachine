(ns workmachine.workflow
  (:use [clojure.tools.macro :only (name-with-attributes)])
  (:require [resque-clojure.core :as resque]
            [workmachine.jobs :as jobs]
            [workmachine.data-input :as input-types]
            [workmachine.data-output :as output-types]))


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
  (jobs/add-to-finished-jobs job))

(defn run-engine [program job instruction-label]
  (if (nil? instruction-label)
    (finish job)
    ;; TODO: Eventually.
    ;; Should check if the data that was submitted was valid.
    ;; Do we trust the worker?
    ;;   No -> Create a job to review that the input was valid.
    ;;   Yes -> Go on to the next instruction.
    (do
      (println instruction-label)
      (jobs/add-to-available-jobs (struct-map jobs/job
                                    :program program
                                    :job job
                                    :label (continue instruction-label program))))
    ))

(defn start-workflow [program job]
  (jobs/add-to-available-jobs (struct-map jobs/job
                                :program program
                                :job job
                                :label (instruction-label (first program)))))
