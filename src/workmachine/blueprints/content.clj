(ns workmachine.blueprints.content
  (:use workmachine.workflow))

(def program
  '((:topic
     {:input [[:instructions "topic_instruction" "What topic are you interested in seeing?"]]
      :output [[:string "topic"]]})
    (:research
     {:input [[:instructions "research_instruction" "Find links to the following topics. This can be in Google, Yelp or or elsewhere."]
              [:text "topic"]]
      :output [[:string "info_link"]]})
    (:write 
     {:input [(:instructions "write_instruction" "Take the links and write something about it.")]
      :output [[:string "content"]]})))

;;(define-workflow workflow
(defn workflow [workflow-jobs]
  (doseq [job workflow-jobs]
    (start-workflow program job)))

