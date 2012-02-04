(ns workmachine.blueprints.content
  (:use workmachine.workflow))

(def program
  '((:topic
     {:input [[:instructions "topic_instruction"]]
      :output [[:string "topic"]]})
    (:write 
     {:input [[:instructions "write_instruction"]
              [:text "topic"]
              [:text "info_link"]]
      :output [[:text "content"]]})))

(defn workflow [workflow-jobs]
  (doseq [job workflow-jobs]
    (start-workflow program job)))
