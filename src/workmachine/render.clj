(ns workmachine.render
  (:require [clj-html.core :as clj-html]
            [workmachine.workflow :as workflow]
            [workmachine.data-input :as data-input]
            [workmachine.data-output :as data-output]))

(defn- html-template [form-code]
  (clj-html/html
   [:html
    [:head
     [:title "WorkMachine Worker"]
     [:link {:rel "stylesheet" :href "http://twitter.github.com/bootstrap/1.4.0/bootstrap.min.css"}]]
    [:body
     [:div {:class "container"}
      [:div {:class "row"}
       [:div {:class "span16"}
        form-code]]]]]))
     
(defn html [worker-id worker-job]
  (let [instr (workflow/instruction
               (workflow/statement
                (worker-job :label)
                (worker-job :program)))]
    (html-template
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
               ((data-output/parse field) :html)])
            (instr :output))]
      [:input {:type "submit" :value "Submit" :class "btn"}]])))


