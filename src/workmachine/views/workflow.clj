(ns workmachine.views.workflow
  (:require [workmachine.views.common :as common]
            [workmachine.models.jobs :as jobs])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/" []
  (common/layout
   [:p
    [:strong "Number of available jobs"]
    (str (jobs/number-of-available-jobs))]
   [:p
    [:strong "assigned jobs"]
    (prn @jobs/assigned-jobs)]
   [:p
    [:strong "available jobs"]
    (prn @jobs/available-jobs)]
   [:p
    [:strong "finished jobs"]
    (str @jobs/finished-jobs)]))


(defpage [:post "/run"] [program jobs]
  (println ((first jobs) "topic_instruction"))
  (doseq [job jobs] (workflow/start-workflow (read-string program) job))
  "ok")

(defpage "/results" []
  '(results))

