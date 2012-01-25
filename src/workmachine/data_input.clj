(ns workengine.data-input
  (:use clj-html.core))

(defn image [name]
  {:name name
   :html (fn [value]
           [:img {:id name
                  :src value
                  :class "input-field"}])})

(defn audio [name]
  {:name name
   :html (fn [value]
           [:audio {:id name
                    :src value
                    :class "input-field"}])})

(defn text [name]
  {:name name
   :html (fn [value]
           [:span
            {:id name :class "input-field"}
            value])})

(defn instructions [name]
  {:name name
   :html (fn [value]
           [:div
            {:id name :class "input-field"}
            value])})


(defn data-input-html-form [input-fields output-fields job-input]
  (html
   [:div
    [:h2 "Instructions"]
    ;; Job input fields
    (map (fn [input-field]
           (let [input-field-name (get input-field :name)]
             ((get input-field :html) (get job-input input-field-name))))
         input-fields)]
   [:div
    [:h2 "Input"]
    ;; Worker Input fields
    (map (fn [output-field] (get output-field :html)) output-fields)]))
