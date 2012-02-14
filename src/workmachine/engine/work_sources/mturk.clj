;; Mechanical Turk
;;
;; Post jobs to mechanical turk if the workflow program specifies it.


(ns workmachine.work-sources.mturk
  (:use clj-html.core)
  (:require [workmachine.jobs :as jobs]
            [workmachine.workflow :as workflow]
            [workmachine.render :as render]))

;; http://docs.amazonwebservices.com/AWSMechTurk/latest/AWSMturkAPI/ApiReference_ExternalQuestionArticle.html
(defn post [job]
  )

(defn read [hit-id]
  )

(defn submit [hit-id job-id]
  )