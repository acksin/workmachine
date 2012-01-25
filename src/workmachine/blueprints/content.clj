(ns workmachine.blueprints.content
  (:use workmachine.workflow))


;;(define-workflow workflow
(defn workflow [workflow-jobs]
  (map (fn [job]
         (start-workflow
          '((:topic
             {:input [(input-types/instructions "What topic are you interested in seeing?")]
              :output [(output-types/string "topic")]))
            (:research
             {:input [(input-types/instructions "Find links to the following topics. This can be in Google, Yelp or or elsewhere.")
                      (input-types/string "topic")]
              :output [(output-types/string "info_link")]})
            (:write 
             {:input [(input-types/instructions "Take the links and write something about it.")]
              :output [(output-types/string "content")]}))
          job))
       workflow-jobs))
