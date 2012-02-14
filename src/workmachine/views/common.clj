(ns workmachine.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page-helpers :only [include-css html5]])
  (:require [clj-html.core :as clj-html]
            [workmachine.workflow :as workflow]
            [workmachine.data-input :as data-input]
            [workmachine.data-output :as data-output]))

(defpartial layout [& content]
  (html5
   [:head
    [:title "workmachine"]
    (include-css "/css/reset.css")]
   [:body
    [:div#wrapper
     content]]))


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

