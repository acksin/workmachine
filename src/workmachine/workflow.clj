(ns workmachine.workflow
  (:use [clojure.tools.macro :only (name-with-attributes)])
  (:require [resque-clojure.core :as resque]
            [workmachine.jobs :as jobs]
            [workmachine.data-input :as input-types]
            [workmachine.data-output :as output-types]))

;; (defmacro define-workflow
;;   "Define a workflow and post the jobs to available jobs"
;;   [name program]
;;   `(defn ~name work-jobs#
;;      (map (fn [job#]
;;             (run-engine ~program job#))
;;             work-jobs#)))

;; (defmacro define-instruction
;;   "Define the instructions which will be returned."
;;   [name args instruction]
;;   `(defn ~name [~@args]
;;      (fn [job-input#]
;;        (instruction job-input#))))
       
       

(defn instruction-label [statement]
  (first statement))

(defn instruction-label? [label statement]
  (= (instruction-label statement) label))

;; TODO: This is if we want to be be able to redo certain tasks.
;; (defn verify? [statement]
;;   (let [statement-metadata (first (rest statement))]
;;     (and (instance? clojure.lang.PersistentArrayMap statement-metadata)
;;          (contains? statement-metadata :verify))))


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

(defn data-input [fields]
  ;; {:html (render/html input-fields output-fields job-input)
  ;;  :input-fields input-fields
  ;;  :output-fields output-fields
  ;;  :job-input job-input
  ;;  :job-output nil}
  :continue)

(defn run-engine [program job instruction-label]
  (if (nil? instruction-label)
    nil
    (let [run-instruction (data-input (instruction (statement instruction-label program)))]
      (println instruction-label)
      (case run-instruction
        ;;      [:goto, label] (recur program job label)
        :continue (recur program job (continue instruction-label program))))))

(defn start-workflow [program job]
  (jobs/add-to-available-jobs {:program program
                               :job job
                               :label (instruction-label (first program))}))
