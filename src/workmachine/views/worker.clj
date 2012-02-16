(ns workmachine.views.worker
  (:require [workmachine.views.common :as common]
            [workmachine.models.jobs :as jobs]
            [workmachine.engine.workflow :as workflow]
            [workmachine.engine.data-input :as data-input]
            [workmachine.engine.data-output :as data-output]
            [noir.request :as request])
  (:use [noir.core :only [defpartial defpage]]
        [hiccup.core :only [html]]
        [hiccup.page-helpers :only [include-css html5]]))

(defpartial worker-layout [& content]
  (html5
   [:head
    [:title "WorkMachine Worker"]
    (include-css "/bootstrap/css/bootstrap.css")]
   [:body
    [:div.container
     [:div.row
      [:div.span16
       content
       ]]]]))




(defpage "/worker/:worker-id/assign" {:keys [worker-id]}
  (let [worker-job (or (jobs/job-for-worker worker-id)
                       (do
                         (jobs/assign-job-to-worker worker-id)
                         (jobs/job-for-worker worker-id)))]
    (worker-layout
     (if worker-job
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
                     ((input-field :html) ((worker-job :job) (keyword (input-field :name)))))
                   ])
                (instr :input))]
          [:div {:id "inputs"}
           [:h2 "Input"]
           ;; Worker Input fields
           (map (fn [field]
                  [:div
                   ((data-output/parse field) :html)
                   ])
                (instr :output))]
          [:input {:type "submit" :value "Submit" :class "btn"}]])
       
       [:div "No work"]))))

(defpage [:post "/worker/:worker-id/submit"] {:keys [worker-id params] :as request}
  (let [submitted-work (dissoc request :worker-id)
        worker-job (jobs/job-for-worker worker-id)
        merged-job (merge worker-job {:job (merge (worker-job :job) submitted-work)})]
    (jobs/submit-job-from-worker worker-id)
    (workflow/run-engine merged-job)
    "done"))

(defpage "/worker/:worker-id/unassign" {:keys [worker-id]} 
  (jobs/unassign-job-from-worker worker-id)
  (html [:div "Unassigned"]))
