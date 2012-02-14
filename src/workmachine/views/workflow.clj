(ns workmachine.views.workflow
  (:require [workmachine.views.common :as common]
            [workmachine.engine.workflow :as workflow]
            [workmachine.models.jobs :as jobs]
            [noir.request :as request])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/" []
  (common/layout
   [:div
    [:h2 "Stats"]
    [:table.table-bordered.table
     [:tr
      [:td "Available Jobs:"]
      [:td (str (jobs/number-of-available-jobs))]]
     [:tr
      [:td "Assigned Jobs:"]
      [:td (str (jobs/number-of-assigned-jobs))]]
     [:tr
      [:td "Finished Jobs:"]
      [:td (str @jobs/finished-jobs)]]]]))


(defpage [:put "/run"] {json-params :backbone}
  (let [program (json-params :program)
        jobs (json-params :jobs)]
    (println program)
    (println jobs)
    (doseq [job jobs]
      (workflow/start-workflow (read-string program) job))
    "ok"))

(defpage "/results" []
  '(results))

