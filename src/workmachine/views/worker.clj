(ns workmachine.views.worker
  (:require [workmachine.views.common :as common]
            [workmachine.models.jobs :as jobs]
            [workmachine.engine.workflow :as workflow]
            [workmachine.engine.data-input :as data-input]
            [workmachine.engine.data-output :as data-output])
  (:use [noir.core :only [defpartial defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial worker-layout [worker-id worker-job]
  (html5
   [:head
    [:title "WorkMachine Worker"]
    (include-css "/bootstrap/css/bootstrap.css")]
   [:body
    [:div.container
     [:div.row
      [:div.span16
       ;; TODO: Probably want to move this to the top.
       (let [instr (workflow/instruction
                    (workflow/statement
                     (worker-job :label)
                     (worker-job :program)))]
         [:form {:action (str "/worker/" worker-id "/submit") :method "POST"}
          [:div
           [:h2 "Instructions"]
           ;; Job input fields
           (map (fn [field]
                  [:div
                   (let [input-field (data-input/parse field)]
                     ((input-field :html) ((worker-job :job) (input-field :name))))
                   ])
                (instr :input))]
          [:div {:id "inputs"}
           [:h2 "Input"]
           ;; Worker Input fields
           (map (fn [field]
                  [:div
                   ((data-output/parse field) :html)])
                (instr :output))]
          [:input {:type "submit" :value "Submit" :class "btn"}]])]]]]))




(defpage "/worker/:worker-id/assign" {worker-id :worker-id}
  (let [worker-job (or (jobs/job-for-worker worker-id)
                       (do
                         (jobs/assign-job-to-worker worker-id)
                         (jobs/job-for-worker worker-id)))]
    (if worker-job
      (worker-layout worker-id worker-job)
      (html [:div "No work"]))))

(defpage [:post "/worker/:worker-id/submit"]
  {worker-id :worker-id submitted-work :submitted-work}
  ;; unassign the assignment from the worker.
  ;; run-engine with the next instruction.
  (let [worker-job (jobs/job-for-worker worker-id)
        merged-job (merge worker-job {:job (merge (worker-job :job) submitted-work)})]
    (println merged-job)
    (jobs/submit-job-from-worker worker-id)
    (workflow/run-engine merged-job)
    "done"))

(defpage "/worker/:worker-id/unassign" {worker-id :worker-id} 
  (jobs/unassign-job-from-worker worker-id)
  (html [:div "Unassigned"]))
