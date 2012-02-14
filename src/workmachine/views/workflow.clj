(ns workmachine.views.workflow
  (:require [workmachine.views.common :as common]
            [workmachine.engine.workflow :as workflow]
            [workmachine.models.jobs :as jobs])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/" []
  (common/layout
   [:div
    [:h2 "Stats"]
    [:table.table-bordered.table
     [:tr
      [:td "Number of Available Jobs:"]
      [:td (str (jobs/number-of-available-jobs))]]
     [:tr
      [:td "Assigned Jobs:"]
      [:td (prn @jobs/assigned-jobs)]]
     [:tr
      [:td "Available Jobs:"]
      [:td (prn @jobs/available-jobs)]]
     [:tr
      [:td "Finished Jobs:"]
      [:td (str @jobs/finished-jobs)]]]]))


(defpage [:post "/run"] [program jobs]
  (println ((first jobs) "topic_instruction"))
  (doseq [job jobs] (workflow/start-workflow (read-string program) job))
  "ok")

(defpage "/results" []
  '(results))

