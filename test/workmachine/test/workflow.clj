(ns workmachine.test.workflow
  (:use [workmachine.workflow])
  (:use [clojure.test])
  (:require [workmachine.blueprints.content :as content-workflow]))

(def workflow-program-fixture content-workflow/program)

(defn workflow-fixture []
  (content-workflow/workflow
   [{:topic_instruction "What topic are you interested in seeing?"
     :research_instrction "Find links to the following topics."
     :write_instruction "Take the links about and come up with a paragraph or two about the topic."}]))

(deftest test-statement 
  (let [topic-statement (statement :topic workflow-program-fixture)
        research-statement (statement :research workflow-program-fixture)
        write-statement (statement :write workflow-program-fixture)]
    (is (instruction-label? :topic topic-statement) "Topic is not the instruction label.")
    (is (instruction-label? :research research-statement) "Research is not the instruction label.")
    (is (instruction-label? :write write-statement) "Write is not the instruction label.")))

(deftest test-instruction
  (let [topic-statement (statement :topic workflow-program-fixture)]
    (is (not (empty? (instruction topic-statement))) "The instruction for topic doesn't exist.")))

(deftest test-flow
  (is (= :research (continue :topic workflow-program-fixture)) "Continue did not give the label for the next instruction.")
  (is (= :write (continue :research workflow-program-fixture)) "Continue did not give the label for the next instruction.")
  (is (nil? (continue :write workflow-program-fixture)) "Continue gave a label for the last instruction."))

;; (deftest test-workflow
;;   (workflow-fixture)
;;   (let [worker-id "1"]
;;     (is (= 