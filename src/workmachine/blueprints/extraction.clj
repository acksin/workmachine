(ns workmachine.blueprints.extraction
  (:use workmachine.workflow))

(def program [inputs outputs]
  '((:input
     {:input [inputs]
      :output [outputs]})))

(defn workflow [inputs outputs workflow-jobs]
  (doseq [job workflow-jobs]
    (start-workflow (program inputs outputs) job)))

(defn parse-and-run [params]
  
  )